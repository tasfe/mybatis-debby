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

create table if not EXISTS t_member (
  id int IDENTITY NOT NULL,
  name VARCHAR(32) NOT NULL,
  age INT
);

alter table t_member drop primary key;
alter table t_member add primary key (id, name);


