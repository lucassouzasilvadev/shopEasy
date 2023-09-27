--create table product(
--    id_product integer,
--    description text,
--    price numeric
--);

insert into product (id_product, description, price, altura, largura, profundidade, peso) values (1, 'camera', 1000, 20, 15, 10, 1);
insert into product (id_product, description, price, altura, largura, profundidade, peso) values (2, 'guitarra', 5000, 100, 30, 10, 3);
insert into product (id_product, description, price, altura, largura, profundidade, peso) values (3, 'geladira', 30, 200, 100, 50, 40);
insert into product (id_product, description, price, altura, largura, profundidade, peso) values (4, 'dimensao negativa', 30, -200, 100, 50, 40);
insert into product (id_product, description, price, altura, largura, profundidade, peso) values (5, 'peso negativo', 30, 200, 100, 50, -40);

insert into coupon(id, code, percentage, validate) values (1, 'VALE20', 20, 20230925);
insert into coupon(id, code, percentage, validate) values (2, 'VALE10', 10, 20230920);