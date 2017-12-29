--DROP TABLE stations IF EXISTS;

CREATE TABLE stations (
  id         INTEGER PRIMARY KEY,
  name VARCHAR(30),
  location  VARCHAR(50)
);
