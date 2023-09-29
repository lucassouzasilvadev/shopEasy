--create table product(
--    id_product integer,
--    description text,
--    price numeric
--);

insert into product (id_product, description, price, height, width, length, weight, currency) values (1, 'camera', 1000, 20, 15, 10, 1, 'BRL');
insert into product (id_product, description, price, height, width, length, weight, currency) values (2, 'guitarra', 5000, 100, 30, 10, 3, 'BRL');
insert into product (id_product, description, price, height, width, length, weight, currency) values (3, 'geladeira', 3000, 200, 100, 50, 40, 'BRL');
insert into product (id_product, description, price, height, width, length, weight, currency) values (4, 'dimensao negativa', 30, -200, 100, 50, 40, 'BRL');
insert into product (id_product, description, price, height, width, length, weight, currency) values (5, 'peso negativo', 30, 200, 100, 50, -40, 'BRL');
insert into product (id_product, description, price, height, width, length, weight, currency) values (6, 'frete minimo', 30, 10, 10, 10, 0.9, 'BRL');
insert into product (id_product, description, price, height, width, length, weight, currency) values (7, 'dolar', 1000, 100, 30, 10, 3, 'USD');


insert into coupon(id, code, percentage, validate) values (1, 'VALE20', 20, 20230925);
insert into coupon(id, code, percentage, validate) values (2, 'VALE10', 10, 20230920);