SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS sites (
  id int PRIMARY KEY auto_increment,
  description VARCHAR,
  completed BOOLEAN
  engineerid INTEGER
);

CREATE TABLE IF NOT EXISTS engineers (
id int PRIMARY KEY auto_increment,
name VARCHAR
);