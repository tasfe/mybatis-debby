DROP TABLE IF EXISTS t_animal;

CREATE TABLE t_animal (
  id INT IDENTITY NOT NULL,
  name VARCHAR(32),
  weight DOUBLE,
  CONSTRAINT PK PRIMARY KEY (id)
);