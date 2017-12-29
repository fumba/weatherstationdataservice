package me.fumba.weatherstation.dao;

import me.fumba.weatherstation.model.Station;

public interface StationDao {

	Station findById(long id);
	
	void saveStation(Station station);
	
	void updateStation(Station station);
	
	void deleteStation(long id);
}
