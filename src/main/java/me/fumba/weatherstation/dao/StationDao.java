package me.fumba.weatherstation.dao;

import java.util.List;

import me.fumba.weatherstation.model.Sensor;
import me.fumba.weatherstation.model.Station;

public interface StationDao {

	Station findById(long id);
	
	List<Sensor> findAllStationSensors(long id);

	void saveStation(Station station);

	void updateStation(Station station);

	void deleteStation(long id);

	List<Station> findAllStations();

	boolean isStationExist(Station station);
}
