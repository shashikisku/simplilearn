create table products(product_id int,
					product_name varchar(30),
                    product_cost int);
select * from products;
insert into products values(4,"mouse",3200);
delete from products where product_cost=6000;
update products set product_cost=3300 where product_id=4;