--create table product(
--    id_product integer,
--    description text,
--    price numeric
--);

insert into product (id_product, description, price, height, width, length, weight) values (1, 'camera', 1000, 20, 15, 10, 1);
insert into product (id_product, description, price, height, width, length, weight) values (2, 'guitarra', 5000, 100, 30, 10, 3);
insert into product (id_product, description, price, height, width, length, weight) values (3, 'geladeira', 3000, 200, 100, 50, 40);
insert into product (id_product, description, price, height, width, length, weight) values (4, 'dimensao negativa', 30, -200, 100, 50, 40);
insert into product (id_product, description, price, height, width, length, weight) values (5, 'peso negativo', 30, 200, 100, 50, -40);
insert into product (id_product, description, price, height, width, length, weight) values (6, 'frete minimo', 30, 10, 10, 10, 0.9);


insert into coupon(id, code, percentage, validate) values (1, 'VALE20', 20, 20230925);
insert into coupon(id, code, percentage, validate) values (2, 'VALE10', 10, 20230920);