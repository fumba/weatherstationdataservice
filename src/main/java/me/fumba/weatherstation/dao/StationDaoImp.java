package me.fumba.weatherstation.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import me.fumba.weatherstation.api.controller.WeatherStationController;
import me.fumba.weatherstation.model.Sensor;
import me.fumba.weatherstation.model.Station;

@Repository
public class StationDaoImp implements StationDao {

	private static final String STATION_LOCATION_STRING = "location";
	private static final String STATION_NAME_STRING = "name";
	private static final String STATION_ID_STRING = "id";

	private static final String SENSOR_ID_STRING = "id";
	private static final String SENSOR_NAME_STRING = "name";
	private static final String SENSOR_TYPE_STRING = "type";
	private static final String SENSOR_STATION_ID_STRING = "station_id";

	private static final String STATIONS_DELETE_BY_ID_QUERY = "delete from stations where id = :id";
	private static final String STATIONS_UPDATE_BY_ID_QUERY = "UPDATE stations set name = :name, location = :location where id = :id";
	private static final String STATIONS_INSERT_QUERY = "INSERT into stations (id, name, location) values (:id, :name, :location)";
	private static final String STATIONS_SELECT_ALL_QUERY = "SELECT * FROM stations";
	private static final String STATIONS_SELECT_BY_ID_QUERY = "SELECT * FROM stations WHERE id=:id";
	private static final String SENSORS_SELECT_ALL_QUERY = "SELECT * FROM sensors WHERE station_id=:id";

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(StationDaoImp.class);

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public Station findById(long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(STATION_ID_STRING, id);
		Station result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(STATIONS_SELECT_BY_ID_QUERY, params,
					new StationMapper());
		} catch (Exception e) {
			logger.debug(e.toString());
		}
		return result;
	}

	@Override
	public List<Station> findAllStations() {
		List<Station> result = null;
		try {
			result = namedParameterJdbcTemplate.query(STATIONS_SELECT_ALL_QUERY, new StationMapper());
		} catch (Exception e) {
			logger.debug(e.toString());
		}
		return result;
	}

	@Override
	public void saveStation(Station station) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(STATION_ID_STRING, station.getId());
		params.put(STATION_NAME_STRING, station.getName());
		params.put(STATION_LOCATION_STRING, station.getLocation());
		namedParameterJdbcTemplate.update(STATIONS_INSERT_QUERY, params);
	}

	@Override
	public void updateStation(Station station) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(STATION_ID_STRING, station.getId());
		params.put(STATION_NAME_STRING, station.getName());
		params.put(STATION_LOCATION_STRING, station.getLocation());
		namedParameterJdbcTemplate.update(STATIONS_UPDATE_BY_ID_QUERY, params);
	}

	@Override
	public void deleteStation(long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(STATION_ID_STRING, id);
		namedParameterJdbcTemplate.update(STATIONS_DELETE_BY_ID_QUERY, params);
	}

	/**
	 * Checks to see if a station with the specified id exists
	 */
	@Override
	public boolean isStationExist(Station station) {
		if (this.findById(station.getId()) != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Sensor> findAllStationSensors(long id) {
		List<Sensor> result = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(STATION_ID_STRING, id);
			result = namedParameterJdbcTemplate.query(SENSORS_SELECT_ALL_QUERY, params, new SensorMapper());
		} catch (Exception e) {
			logger.debug(e.toString());
		}
		return result;
	}

	private static final class StationMapper implements RowMapper<Station> {
		public Station mapRow(ResultSet rs, int rowNum) throws SQLException {
			Station station = new Station();
			station.setId(rs.getInt(STATION_ID_STRING));
			station.setName(rs.getString(STATION_NAME_STRING));
			station.setLocation(rs.getString(STATION_LOCATION_STRING));
			return station;
		}
	}

	private static final class SensorMapper implements RowMapper<Sensor> {
		public Sensor mapRow(ResultSet rs, int rowNum) throws SQLException {
			Sensor sensor = new Sensor();
			sensor.setId(rs.getLong(SENSOR_ID_STRING));
			sensor.setName(rs.getString(SENSOR_NAME_STRING));
			sensor.setType(rs.getString(SENSOR_TYPE_STRING));
			sensor.setStationId(rs.getLong(SENSOR_STATION_ID_STRING));
			return sensor;
		}
	}

}