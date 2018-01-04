create table if not exists t_product (
  id INT IDENTITY NOT NULL,
  create_time TIMESTAMP,
  title VARCHAR(32) DEFAULT 'test1',
  quantity INT,
  price DECIMAL,
  sold_out BOOL,
  weight DOUBLE,
  pm TINYINT,
  product_category_id INT,
  CONSTRAINT PK PRIMARY KEY (id)
);