package me.fumba.weatherstation.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import me.fumba.weatherstation.dao.StationDao;
import me.fumba.weatherstation.model.Sensor;
import me.fumba.weatherstation.model.Station;

@ComponentScan(basePackages = "me.fumba")
@RestController
public class WeatherStationController {

	@Autowired
	StationDao stationDao;

	private static final Logger logger = LoggerFactory.getLogger(WeatherStationController.class);

	/**
	 * Get a station using its id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/station/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Station> getStation(@PathVariable("id") long id) {
		logger.info("Station id : " + id);
		Station station = stationDao.findById(id);
		if (station == null) {
			System.out.println("Station with id " + id + " not found");
			return new ResponseEntity<Station>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Station>(station, HttpStatus.OK);
	}

	/**
	 * Retrieve all stations
	 * 
	 */
	@RequestMapping(value = "/stations", method = RequestMethod.GET)
	public ResponseEntity<List<Station>> listAllUsers() {
		List<Station> stations = stationDao.findAllStations();
		if (stations.isEmpty()) {
			return new ResponseEntity<List<Station>>(HttpStatus.NO_CONTENT); // HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Station>>(stations, HttpStatus.OK);
	}

	/**
	 * Creates new station
	 */
	@RequestMapping(value = "/station", method = RequestMethod.POST)
	public ResponseEntity<Void> createStation(@RequestBody Station station, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Station " + station.getId());
		if (stationDao.isStationExist(station)) {
			System.out.println("A Station with id " + station.getId() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		stationDao.saveStation(station);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(station.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	/**
	 * Update a station
	 * 
	 * @param id
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/station/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Station> updateStation(@PathVariable("id") long id, @RequestBody Station station) {
		logger.info("Updating Station " + id);
		Station currentStation = stationDao.findById(id);
		if (currentStation == null) {
			logger.info("Station with id " + id + " not found");
			return new ResponseEntity<Station>(HttpStatus.NOT_FOUND);
		}
		currentStation.setName(station.getName());
		currentStation.setLocation(station.getLocation());
		stationDao.updateStation(currentStation);
		return new ResponseEntity<Station>(currentStation, HttpStatus.OK);
	}

	/**
	 * Retrieve all sensors related to the station
	 * 
	 */
	@RequestMapping(value = "/station/{id}/sensors", method = RequestMethod.GET)
	public ResponseEntity<List<Sensor>> findAllStationSensors(@PathVariable("id") long id) {
		Station currentStation = stationDao.findById(id);
		if (currentStation == null) {
			logger.info("Station with id " + id + " not found");
			return new ResponseEntity<List<Sensor>>(HttpStatus.NOT_FOUND);
		}
		List<Sensor> sensors = stationDao.findAllStationSensors(id);
		if (sensors == null || sensors.isEmpty()) {
			return new ResponseEntity<List<Sensor>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Sensor>>(sensors, HttpStatus.OK);
	}

	/**
	 * Delete a station
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/station/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Station> deleteStation(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Station with id " + id);
		Station user = stationDao.findById(id);
		if (user == null) {
			logger.info("Unable to delete. Station with id " + id + " not found");
			return new ResponseEntity<Station>(HttpStatus.NOT_FOUND);
		}
		stationDao.deleteStation(id);
		return new ResponseEntity<Station>(HttpStatus.NO_CONTENT);
	}
}