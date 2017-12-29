# weatherstationdataservice

Locate \target\weather-data-service-0.1.0.jar and start website by executing

$ java -jar weather-data-service-0.1.0.jar &


1. NON-EXISTING STATION - $ curl http://localhost:8080/station/5


2. EXISTING STATION - $ curl http://localhost:8080/station/1


3. RETRIEVE ALL STATTIONS - $ curl http://localhost:8080/stations


4. ADDING A NEW STATION - $ curl -H "Content-Type: application/json" -X POST -d '{"id":"4","name":"NEW #4FOUR","location":"444 New Entry PA 17325"}' http://localhost:8080/station


5. UPDATING AN EXISTING STATION - $ curl -H "Content-Type: application/json" -X PUT -d '{"id":"1","name":"UPDATED#4FOUR","location":"Updated-444 New Entry PA 17325"}' http://localhost:8080/station/1

Assumption : the id provided in the JSON body is ignored when retrieving and updating the station. 


6. DELETING A STATION - $ curl -X DELETE http://localhost:8080/station/1



