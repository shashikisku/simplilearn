use mytrgdb; 
create table userdetails(first_name varchar(30),
						last_name varchar(30),
                        address varchar(30),
                        user_name varchar(30),
                        password varchar(30));
select * from userdetails; 
SET SQL_SAFE_UPDATES=0;
delete from userdetails where first_name="shashi";