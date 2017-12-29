# weatherstationdataservice


NON-EXISTING STATION - $ curl http://localhost:8080/station/5

EXISTING STATION - $ curl http://localhost:8080/station/1

RETRIEVE ALL STATTIONS - $ curl http://localhost:8080/stations

ADDING A NEW STATION - $ curl -H "Content-Type: application/json" -X POST -d '{"id":"4","name":"NEW #4FOUR","location":"444 New Entry PA 17325"}' http://localhost:8080/station

