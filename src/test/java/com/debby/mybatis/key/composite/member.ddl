create table if not EXISTS t_member (
  id int IDENTITY NOT NULL,
  name VARCHAR(32) NOT NULL,
  age INT
);

alter table t_member drop primary key;
alter table t_member add primary key (id, name);