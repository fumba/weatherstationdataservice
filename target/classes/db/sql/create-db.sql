--DROP TABLE stations IF EXISTS;

CREATE TABLE stations (
  id         INTEGER PRIMARY KEY,
  name VARCHAR(30),
  location  VARCHAR(50)
);

CREATE TABLE Sensors (
  id         INTEGER PRIMARY KEY,
  name VARCHAR(30),
  type  VARCHAR(50),
  station_id   INTEGER,
);
