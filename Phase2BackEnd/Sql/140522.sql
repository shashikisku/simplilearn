create database mytrgdb;
use mytrgdb;
create table studentdata(roll_no int,
						first_name varchar(30),
                        last_name varchar(30),
                        standard int,
                        grade varchar(30));
show tables;
show databases;
SET SQL_SAFE_UPDATES=0;
select * from studentdata;
update studentdata set roll_no=102 where grade='A';
delete from studentdata where roll_no=104;