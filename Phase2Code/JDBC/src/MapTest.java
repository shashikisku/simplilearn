import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;

public class MapTest {

	public static void main(String[] args) {
		Item item=new Item();
		Item item1=new Item();
		item.setRef("sk");
		item.setPrice(0.0);
		item1.setRef("sk");
		item1.setPrice(0.0);
		
		Collection<Item> collectioOfItem=new ArrayList();
		collectioOfItem.add(item);
		collectioOfItem.add(item1);
		System.out.println(collectioOfItem);

		System.out.print("Hello");
		Map<String,Double> tempMap = new HashMap<>();
		tempMap.put("a",3.0);
		tempMap.put("b",0.0);
		tempMap.put("b",0.0);
		Double v=tempMap.get("b");
		System.out.print(v*0);
		System.out.print(tempMap);
//		Map <String,Double> todo=collectioOfItem.stream().collect(Collectors.toMap(i->i.getRef(), i->i.getPrice()));
		Map <String,Double> podo = new HashMap();
		for(Item abc:collectioOfItem) {
			podo.put(abc.getRef(), abc.getPrice());
		}
//		System.out.println("todo"+todo);
		System.out.println("podo"+podo);

	}
	

}
