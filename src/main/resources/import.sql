--create table product(
--    id_product integer,
--    description text,
--    price numeric
--);

insert into product (id_product, description, price) values (1, 'A', 1000);
insert into product (id_product, description, price) values (2, 'B', 5000);
insert into product (id_product, description, price) values (3, 'C', 30);


insert into coupon(id, code, percentage) values (1, 'VALE20', 20);
insert into coupon(id, code, percentage) values (2, 'VALE10', 10);