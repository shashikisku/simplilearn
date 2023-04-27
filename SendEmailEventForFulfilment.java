
package com.fluentretail.jds.email.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentretail.api.client.ReadOnlyFluentApiClient;
import com.fluentretail.api.model.Entity;
import com.fluentretail.api.model.article.Article;
import com.fluentretail.api.model.article.ArticleConsignment;
import com.fluentretail.api.model.attribute.Attribute;
import com.fluentretail.api.model.consignment.Consignment;
import com.fluentretail.api.model.fulfilment.Fulfilment;
import com.fluentretail.api.model.location.LocationAttributes;
import com.fluentretail.api.model.location.LocationSummary;
import com.fluentretail.api.model.order.Order;
import com.fluentretail.api.model.sku.Sku;
import com.fluentretail.common.model.email.EmailCommand;
import com.fluentretail.common.model.email.Notification;
import com.fluentretail.common.model.email.NotificationItem;
import com.fluentretail.common.utils.AttributesUtil;
import com.fluentretail.common.utils.CompileTemplateFunction;
import com.fluentretail.jds.util.Constants;
import com.fluentretail.jds.util.FulfilmentUtils;
import com.fluentretail.jds.util.RetailerSettingsUtil;
import com.fluentretail.rubix.context.Context;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.fluentretail.jds.util.Constants.LOCAL_ERP_LOCATION_ID;
import static java.util.Calendar.SECOND;

/**
 * Send event with email command attribute to trigger SendEmailFromEvent rule
 */
@RuleInfo(
        name = "SendEmailEventForFulfilment",
        description = "Send event {" + Constants.RULE_PROP_EVENT_NAME + "} for the fulfilment with {" + Constants.RULE_PROP_SECOND_TO_DELAY
                + "} delay, Email receiver {" + Constants.RULE_PROP_RECEIVER
                + "} and setting {" + Constants.RULE_PROP_EMAIL_CONFIG_SETTING + "}.",
        accepts = {
                @EventInfo(entityType = "FULFILMENT"),
        },
        produces = {
                @EventInfo(
                        eventName = Constants.RULE_PROP_EVENT_NAME,
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS)
        }
)

@ParamString(name = Constants.RULE_PROP_EVENT_NAME,
        description = "The name of event to be sent."
)
@ParamString(name = Constants.RULE_PROP_EMAIL_CONFIG_SETTING,
        description = "Setting name defined in retailer setting '"
                + com.fluentretail.common.Constants.RETAILER_SETTING_EMAIL_SERVICE_CONFIG + "'"
)
@ParamString(name = Constants.RULE_PROP_RECEIVER,
        description = "The receiver's Email address."
)
@ParamTimePeriod(
        name = Constants.RULE_PROP_SECOND_TO_DELAY,
        description = "The time to delay.")
@Slf4j
@Deprecated
public class SendEmailEventForFulfilment extends SendEmailEventForArticleWithDCUpdate {

    @Override
    public void run(Context context) {
        validateAndGetRuleProperties(context);

        String acctId = context.getEvent().getAccountId();
        log.info("[{}]JD Sports plugin: {}", acctId, getClass().getSimpleName());

        Fulfilment fulfil = (Fulfilment) context.getEntity();

        log.info("[{}]Going to send email event for Fulfilment with id = [{}]",
                acctId, fulfil.getId());

        sendEventForEmail(context, context.getProp(Constants.RULE_PROP_SECOND_TO_DELAY, Integer.class));
        log.info("[{}]Email trigger has been sent out for Fulfilment with id = [{}]", acctId, fulfil.getId());
    }

    private EmailCommand composeEmailCommand(Context ctx) {

        Map<String, Object> templateVars = populateTemplateVariable(ctx);
        EmailCommand.EmailCommandBuilder cmdBuilder = EmailCommand.builder();
        String toEmail = ctx.getProp(Constants.RULE_PROP_RECEIVER);
        populateReceiver(ctx, toEmail, cmdBuilder);

        cmdBuilder.emailConfigSetting(ctx.getProp(Constants.RULE_PROP_EMAIL_CONFIG_SETTING));
        return cmdBuilder.templateVariables(templateVars).build();
    }

    @Override
    Order.Customer getCustomer(Context context) {
        Entity entity = context.getEntity();
        Fulfilment fulfilment = (Fulfilment) entity;
        return context.api().getOrder(fulfilment.getOrderId()).getCustomer();
    }

    private void addUKDCbookedConsignment(String orderId, Context ctx, Map<String, Object> templateVariables) {

        Order order = ctx.api().getOrder(orderId);
        final ImmutableList<Fulfilment> fulfilments = ctx.api().getFulfilments(orderId);
        List<ArticleConsignment> articleConsignments = ctx.api()
                .getArticleFromFulfilment(orderId, ctx.getEntity().getId())
                .stream().filter(articleConsignment -> articleConsignment.getArticle().getFulfilmentId().equalsIgnoreCase(ctx.getEntity().getId()))
                .collect(Collectors.toList());
        List<Notification> notifications = Lists.newArrayList();

        for (ArticleConsignment articleConsignment : articleConsignments) {
            Article article = articleConsignment.getArticle();
            /*Start FRS-130946*/
            Collection<Article.Item> items=article.getItems().stream().filter(p->Integer.parseInt(p.getQuantity())>0).collect(Collectors.toList());
            /*End FRS-130946*/
            List<NotificationItem> notificationItems = CompileTemplateFunction.createSkusForFulfilledItems(ctx.api(),
                    order.getRetailer().getRetailerId(), ImmutableList.copyOf(items));
            Consignment consignmentEntity = articleConsignment.getConsignment();
            Notification.Builder notificationBuilder = Notification.builder().sku(notificationItems);
            if (Objects.nonNull(consignmentEntity)) {
                String carrierValue =RetailerSettingsUtil.getRetailerSettingValue(ctx.api(), ctx.getEvent().getRetailerId(),
                        consignmentEntity.getCarrierName());
                if(null != carrierValue) {
                    notificationBuilder.carrierName(consignmentEntity.getCarrierName())
                            .consignmentNo(consignmentEntity.getConsignmentRef())
                            .trackingUrl(RetailerSettingsUtil.getRetailerSettingValue(ctx.api(), ctx.getEvent().getRetailerId(),
                                    consignmentEntity.getCarrierName()) + consignmentEntity.getConsignmentRef());
                }
                else{
                    notificationBuilder.carrierName(consignmentEntity.getCarrierName())
                            .consignmentNo(consignmentEntity.getConsignmentRef())
                            .trackingUrl(consignmentEntity.getConsignmentRef());
                }
            } else {
                log.warn("[{}]No consignment found for fulfilment#{} and article#{}", ctx.getEvent().getAccountId(),
                        articleConsignment.getArticle().getFulfilmentId(),
                        articleConsignment.getArticle().getArticleId());
            }
            notifications.add(notificationBuilder.build());
        }
        templateVariables.put(Constants.EMAIL_VAR_UKDC_CONSIGNMENTS, notifications);
    }

    private void populateItemsFromFulfilment(Fulfilment fulfilment, Context ctx, Map<String, Object> templateVariables) {

        List<Notification> notifications = Lists.newArrayList();

        fulfilment.getItems().stream().map(item -> Article.Item.builder().build()).collect(Collectors.toList());
        List<NotificationItem> notificationItems =
                CompileTemplateFunction.createSkusForFulfilledItems(ctx.api(), ctx.getEvent().getRetailerId(),
                        fulfilment.getItems().stream().map(item ->
                                Article.Item.builder()
                                        .articleFilledQty(item.getRequestedQty()).quantity(item.getRequestedQty() + "")
                                        .skuRef(item.getOrderItemRef()).build())
                                .collect(Collectors.toList()));
        Notification notification = Notification.builder()
                .sku(notificationItems)
                .carrierName(StringUtils.EMPTY)
                .consignmentNo(StringUtils.EMPTY)
                .trackingUrl(StringUtils.EMPTY).build();

        notifications.add(notification);
        templateVariables.put(Constants.EMAIL_VAR_FULFILMENT_ITEMS, notifications);
    }

    private void sendEventForEmail(Context ctx, int delay) {
        Event evt = ctx.getEvent();
        EmailCommand emailCmd = composeEmailCommand(ctx);
        ObjectMapper objMapper = new ObjectMapper();

        HashMap<String, Object> attrs = new HashMap<>();

        try {
            attrs.put(com.fluentretail.common.Constants.EVENT_ATTRIBUTE_EMAIL_COMMAND,
                    objMapper.writeValueAsString(emailCmd));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //Schedule the event to SECONDS_TO_DELAY later to make sure all entities are there already
        Calendar scheduledTime = Calendar.getInstance();
        scheduledTime.add(SECOND, delay);
        String evtName = ctx.getProp(Constants.RULE_PROP_EVENT_NAME);
        log.info("[{}]Sending Email event [{}] with attribute {}", ctx.getEvent().getAccountId(), evtName, attrs);
        Event event = evt.toBuilder()
                .name(evtName)
                .scheduledOn((scheduledTime.getTime()))
                .attributes(attrs).build();
        ctx.action().sendEvent(event);
    }

    private Map<String, Object> populateTemplateVariable(Context context) {
        Map<String, Object> templateVariables = new HashMap<>();
        ReadOnlyFluentApiClient api = context.api();
        Fulfilment fulfilment = (Fulfilment) context.getEntity();
        String accountId = context.getEvent().getAccountId();
        String orderId = fulfilment.getOrderId();
        String fulfilmentId = fulfilment.getFulfilmentId();
        log.info("[{}] -------- POPULATE for Fulfilment - Order: {}", accountId, api.getOrder(orderId));
        CompileTemplateFunction.INIT_ORDER.apply(orderId, api, templateVariables);
        CompileTemplateFunction.INIT_UNFULFILLED_ITEM.apply(orderId, api, templateVariables);
        CompileTemplateFunction.INIT_FULFILLED_CONSIGNMENTS.apply(orderId, api, templateVariables);
        CompileTemplateFunction.INIT_AGENT.apply(fulfilmentId, api, templateVariables);
        addUKDCbookedConsignment(orderId, context, templateVariables);
        populateItemsFromFulfilment(fulfilment, context, templateVariables);
        populateItemsFromOrder(orderId, context, templateVariables);
        populateorderRefofEDN(orderId, templateVariables, api);
        log.info("[{}] -----------------------------------------------", accountId);
        populateReceiptNumberForFulfilment(context,templateVariables);
        templateVariables.put("orderId",orderId);
        templateVariables.put("retailerId",context.getEvent().getRetailerId());
        templateVariables.put(Constants.ATTR_FULFILMENTID,fulfilment.getId());

        //  Changes for FRS-116712 and FRS-116345
        templateVariables = buildTemplateVariables(fulfilment, orderId, templateVariables, api);

        return templateVariables;
    }

    /* FFWB-542 - START*/

    private void populateorderRefofEDN( String orderId, Map<String, Object> templateVariables,ReadOnlyFluentApiClient api) {
        Order order = api.getOrder(orderId);
        String orderRef = order.getOrderRef();
        if(orderRef.length() < 10){
            orderRef = StringUtils.repeat("0", 10 - orderRef.length()) + orderRef;
        }
        templateVariables.put("orderRef",orderRef);
    }

    /* FFWB-542 - END*/


    /**
     *gets paid price for return fulfilment
     * @param apiClient
     * @param fulfilment
     * @return
     */
    private Double getTotalPaidPrice(ReadOnlyFluentApiClient apiClient, Fulfilment fulfilment){
        Order order = apiClient.getOrder(fulfilment.getOrderId());
        Map<String, Integer> refundedFulfilmentSkuMap = getSkuRefAndAtyMap(fulfilment.getItems());
        Map<String, Map<String, Object>> refundedFulfilmentSkuMapWithPrice = buildRefundFulfilmentMapWithPrice(order.getItems(), refundedFulfilmentSkuMap);
        java.util.List<Notification> notifications = getNotification(apiClient,refundedFulfilmentSkuMapWithPrice);
        return CollectionUtils.isEmpty(notifications) ? null : getTotalRefundAmount(notifications);
    }


    /**
     *  The following method calculates the total paid price
     * @param notifications
     * @return
     */
    static Double getTotalRefundAmount(java.util.List<Notification> notifications) {
        Double totalRefund = 0.0D;
        NotificationItem n;
        for(Iterator var2 = ((Notification)notifications.get(0)).getSku().iterator(); var2.hasNext(); totalRefund = totalRefund + n.getPrice()) {
            n = (NotificationItem)var2.next();
        }
        return totalRefund;
    }



    /**
     * Method creates notifications for calculating total paid price
     * @param apiClient
     * @param refundedFulfilmentSkuMapWithPrice
     * @return
     */
    private java.util.List<Notification> getNotification(ReadOnlyFluentApiClient apiClient,Map<String, Map<String, Object>> refundedFulfilmentSkuMapWithPrice){
        java.util.List<NotificationItem> noticationItems = createNotificationItemsFromRefundedSkuMap(apiClient, refundedFulfilmentSkuMapWithPrice);
        return !org.apache.commons.collections4.CollectionUtils.isEmpty(noticationItems) ?
                Lists.newArrayList(new Notification[]{Notification.builder().sku(noticationItems).build()}) : null;
    }



    /**
     * Creates notification items
     * @param apiClient
     * @param map
     * @return
     */
    static java.util.List<NotificationItem> createNotificationItemsFromRefundedSkuMap(ReadOnlyFluentApiClient apiClient, Map<String, Map<String, Object>> map) {
        java.util.List<NotificationItem> notificationItems = Lists.newArrayList();
        Iterator var3 = map.entrySet().iterator();
        while(var3.hasNext()) {
            Map.Entry<String, Map<String, Object>> entry = (Map.Entry)var3.next();
            if (!((Map)entry.getValue()).isEmpty() && Integer.valueOf(((Map)entry.getValue()).get("qty").toString()) > 0) {
                NotificationItem notificationItem = createSkuWithPrice(apiClient, (String)entry.getKey(), Integer.valueOf(((Map)entry.getValue()).get("qty").toString()), Double.valueOf(((Map)entry.getValue()).get("refundamount").toString()));
                notificationItems.add(notificationItem);
            }
        }
        return notificationItems;
    }

    /**
     *
     * @param apiClient
     * @param skuRef
     * @param qty
     * @param price
     * @return
     */
    static NotificationItem createSkuWithPrice(ReadOnlyFluentApiClient apiClient, String skuRef, Integer qty, Double price) {
        java.util.List<Sku> skuEntities = apiClient.searchSku(skuRef).getResults();
        Sku skuEntity = (Sku)skuEntities.get(0);
        String category = (String)skuEntity.getCategories().stream().map((c) -> {
            return c.getName();
        }).collect(Collectors.joining(" /"));
        String color = AttributesUtil.getAttributeValueAsString("COLOUR_DESC", skuEntity.getAttributes());
        String size = AttributesUtil.getAttributeValueAsString("SIZE_DESC", skuEntity.getAttributes());
        return NotificationItem.builder().skuRef(skuRef).productName(skuEntity.getName())
                .category(category).color(color).size(size).qty(qty).productRef(skuEntity.getProductRef())
                .price(price).priceFormatted((new DecimalFormat(",###.00")).format(Optional.ofNullable(price).orElse(0.0D))).build();
    }


    /**
     * Calculate the map for refunded items where the key is the SKU reference,
     * and the value is another map where the key is quantity, and the value is total refund amount.
     * @param orderItems is the list of order items.
     * @param refundedFulfilmentSkuMap is the map of items where the key is reference and the value is the requested quantity.
     * @return refundedFulfilmentSkuMapWithPrice the map for refunded items where the key is the SKU reference, and the value is another map where the key is quantity, and the value is total refund amount
     */
    static Map<String, Map<String, Object>> buildRefundFulfilmentMapWithPrice(Collection<Order.Item> orderItems, Map<String, Integer> refundedFulfilmentSkuMap) {
        Map<String, Map<String, Object>> refundedFulfilmentSkuMapWithPrice = Maps.newHashMap();
        Iterator refundedFulfilmentSkuMapIterator = refundedFulfilmentSkuMap.entrySet().iterator();
        while(refundedFulfilmentSkuMapIterator.hasNext()) {
            Map.Entry<String, Integer> fulfilmentItem = (Map.Entry) refundedFulfilmentSkuMapIterator.next();
            Order.Item orderItem = (Order.Item)orderItems.stream().filter((o) -> {
                return o.getSkuRef().equals(fulfilmentItem.getKey());
            }).findAny().orElse((Order.Item) null);
            if (orderItem != null) {
                Double totalRefundPrice = (double)(Integer)fulfilmentItem.getValue() * orderItem.getSkuPrice() + (double)(Integer)fulfilmentItem.getValue() * orderItem.getSkuTaxPrice();
                Map<String, Object> skuDetails = Maps.newHashMap();
                skuDetails.put("qty", fulfilmentItem.getValue());
                skuDetails.put("refundamount", totalRefundPrice);
                refundedFulfilmentSkuMapWithPrice.put(fulfilmentItem.getKey(), skuDetails);
            }
        }
        return refundedFulfilmentSkuMapWithPrice;
    }


    /**
     * Iterates through the fulfilment items and return the fulfilment item reference and requested quantity as a map.
     * @param fulfilmentItems is the list of fulfilment items
     * @return skuQuantityRequestedMap is a map of where SKU ref is the key and requested quantity is the value
     */
    private Map<String,Integer> getSkuRefAndAtyMap(List<Fulfilment.Item> fulfilmentItems){
        Map<String,Integer> skuQuantityRequestedMap = new HashMap<>();
        fulfilmentItems.stream().forEach((item)->{
            skuQuantityRequestedMap.put(item.getFulfilmentItemRef()==null?item.getOrderItemRef():item.getFulfilmentItemRef(),item.getRequestedQty());
        });
        return skuQuantityRequestedMap;
    }


    private void populateReceiptNumberForFulfilment(Context context, Map<String, Object> templateVariables) {
        Fulfilment fulfil = (Fulfilment) context.getEntity();
         if(CollectionUtils.isNotEmpty(fulfil.getAttributes())) {
             final Optional<Attribute> receiptNumberAttribute = fulfil.getAttributes().stream()
                     .filter(attr -> Constants.ATTR_RECEIPT_NUMBER.equals(attr.getName()))
                     .findFirst();
             if(receiptNumberAttribute.isPresent()){
                 templateVariables.put(Constants.ATTR_RECEIPT_NUMBER,receiptNumberAttribute.get().getValue());
             }else{
                 log.warn("No Receipt attribute present for Fulfilment with id {}",fulfil.getId());
             }
         }
    }

    /**
     * Add more data to the template variable by looking up order and fulfilment details.
     * @param fulfilment
     * @param orderId
     * @param templateVariables
     * @param apiClient
     * @return
     */
    private Map<String, Object> buildTemplateVariables(Fulfilment fulfilment, String orderId, Map<String, Object> templateVariables, ReadOnlyFluentApiClient apiClient) {

        Order order = apiClient.getOrder(fulfilment.getOrderId());
        templateVariables.put("orderNumber", order.getOrderRef());
        templateVariables.put("firstName", order.getCustomer().getFirstName());
        templateVariables.put("fullName", order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());
        templateVariables.put("contactNumber", order.getCustomer().getMobile());

        List<Notification> notifications = createNotificationForRefundFulfilments(apiClient, fulfilment.getItems(), order);
        Double totalPaid = getTotalPaidPrice(apiClient, fulfilment);

        templateVariables.put("TOTAL_PAID_PRICE", totalPaid);
        templateVariables.put("TOTAL_PAID_PRICE_FORMATTED", (new DecimalFormat(",###.00")).format(Optional.ofNullable(totalPaid).orElse(0.0D)));
        templateVariables.put("CURRENCY", org.apache.commons.collections4.CollectionUtils.isEmpty(notifications) ? null : "AUD");
        templateVariables.put("refundedOrderItems", org.apache.commons.collections4.CollectionUtils.isEmpty(notifications) ? null : notifications);

        return templateVariables;
    }

    /**
     * Creating a notificationItems object out of items and quantities as part of the refund fulfilment.
     * @param apiClient
     * @param fulfilmentItems
     * @param order
     * @return
     */
    private List<Notification> createNotificationForRefundFulfilments(ReadOnlyFluentApiClient apiClient, java.util.List<com.fluentretail.api.model.fulfilment.Fulfilment.Item> fulfilmentItems, Order order){

        Map<String, Integer> refundedfulfilmentSkuMap = FulfilmentUtils.buildRefundFulfilmentMapV2(fulfilmentItems);
        Map<String, Map<String, Object>> refundedFulfilmentSkuMapWithPrice = buildRefundFulfilmentMapWithPrice(order.getItems(), refundedfulfilmentSkuMap);
        List<NotificationItem> noticationItems = createNotificationItemsFromRefundedSkuMap(apiClient, refundedFulfilmentSkuMapWithPrice);
        return !org.apache.commons.collections4.CollectionUtils.isEmpty(noticationItems) ? Lists.newArrayList(new Notification[]{Notification.builder().sku(noticationItems).build()}) : null;

    }

}

