create table if not exists t_product (
  id int identity primary key NOT NULL,
  create_time TIMESTAMP,
  title varchar DEFAULT 'test1',
  quantity int,
  price DECIMAL,
  sold_out BOOL,
  weight DOUBLE,
  pm TINYINT,
  product_category_id int
);

// ALTER TABLE t_product ALTER COLUMN title SET DEFAULT 'test1';
