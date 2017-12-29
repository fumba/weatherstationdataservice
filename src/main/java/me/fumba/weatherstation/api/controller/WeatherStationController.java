package me.fumba.weatherstation.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.fumba.weatherstation.dao.StationDao;
import me.fumba.weatherstation.model.Station;

@ComponentScan(basePackages = "me.fumba")
@RestController
public class WeatherStationController {

	@Autowired
	StationDao stationDao;

	private static final Logger logger = LoggerFactory.getLogger(WeatherStationController.class);

	@RequestMapping(value = "/station/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Station> getStation(@PathVariable("id") long id) {
		logger.info("Station id : " + id);
		Station station = stationDao.findById(id);
		if (station == null) {
			System.out.println("Stattion with id " + id + " not found");
			return new ResponseEntity<Station>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Station>(station, HttpStatus.OK);
	}

}