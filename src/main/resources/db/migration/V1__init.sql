create table PRODUCT (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL ,
    price FLOAT NOT NULL ,
    quantity_in_stock INTEGER NOT NULL ,
    CONSTRAINT id unique (id)
);

insert into PRODUCT (id, name, price, quantity_in_stock) values (2, 'Product A', 10.99, 10);
insert into PRODUCT (id, name, price, quantity_in_stock) values (3, 'Product B', 10.99, 10);
insert into PRODUCT (id, name, price, quantity_in_stock) values (8, 'Product G', 10.99, 10);


