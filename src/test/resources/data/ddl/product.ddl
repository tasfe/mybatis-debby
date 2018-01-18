DROP TABLE IF EXISTS t_product;

CREATE TABLE t_product (
  id INT IDENTITY NOT NULL,
  create_time TIMESTAMP,
  title VARCHAR(32) DEFAULT 'test',
  quantity INT,
  price DECIMAL,
  sold_out BOOL,
  weight DOUBLE,
  pm TINYINT,
  product_category_id INT,
  CONSTRAINT PK PRIMARY KEY (id)
);