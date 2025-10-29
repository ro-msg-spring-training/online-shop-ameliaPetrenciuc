CREATE TABLE customer(
   id UUID PRIMARY KEY,
   first_name VARCHAR(100),
   last_name VARCHAR(100),
   username VARCHAR(100) UNIQUE NOT NULL,
   password VARCHAR(100) NOT NULL,
   email_address VARCHAR(100)
);

CREATE TABLE orders(
   id UUID PRIMARY KEY,
   customer_id UUID REFERENCES customer(id),
   created_at TIMESTAMP not null,
   address_country VARCHAR(100),
   address_city VARCHAR(100),
   address_county VARCHAR(100),
   address_street_address VARCHAR(100)
);

CREATE TABLE product_category(
    id UUID PRIMARY KEY ,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000)
);

CREATE TABLE supplier(
    id UUID PRIMARY KEY ,
    name VARCHAR(100),
    phone_number VARCHAR(100)
);

CREATE TABLE product(
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    price DECIMAL(10,2) NOT NULL,
    weight DOUBLE PRECISION,
    category_id UUID REFERENCES product_category(id),
    supplier_id UUID REFERENCES supplier(id),
    image_url VARCHAR(1000)
);

CREATE TABLE location(
    id UUID PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    address_country VARCHAR(100),
    address_city VARCHAR(100),
    address_county VARCHAR(100),
    address_street_address VARCHAR(100)
);

CREATE TABLE order_detail(
   order_id UUID REFERENCES orders(id),
   product_id UUID REFERENCES product(id),
   shipped_from UUID REFERENCES location(id),
   quantity INTEGER NOT NULL,
   PRIMARY KEY (order_id, product_id)
);

CREATE TABLE stock(
    product_id UUID REFERENCES product(id),
    location_id UUID REFERENCES location(id),
    quantity INTEGER,
    PRIMARY KEY (product_id, location_id)
);

