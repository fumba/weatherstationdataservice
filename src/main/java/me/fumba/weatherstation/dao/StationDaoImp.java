package me.fumba.weatherstation.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import me.fumba.weatherstation.model.Station;

@Repository
public class StationDaoImp implements StationDao {

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public Station findById(long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String sql = "SELECT * FROM stations WHERE id=:id";
		Station result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new StationMapper());
		} catch (Exception e) {
		}
		return result;
	}

	@Override
	public List<Station> findAllStations() {
		String sql = "SELECT * FROM stations";
		List<Station> result = null;
		try {
			result = namedParameterJdbcTemplate.query(sql, new StationMapper());
		} catch (Exception e) {
		}
		return result;
	}

	@Override
	public void saveStation(Station station) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", station.getId());
		params.put("name", station.getName());
		params.put("location", station.getLocation());
		String sql = "INSERT into stations (id, name, location) values (:id, :name, :location)";
		namedParameterJdbcTemplate.update(sql, params);
	}

	@Override
	public void updateStation(Station station) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", station.getId());
		params.put("name", station.getName());
		params.put("location", station.getLocation());
		String sql = "UPDATE stations set name = :name, location = :location where id = :id";
		namedParameterJdbcTemplate.update(sql, params);
	}

	@Override
	public void deleteStation(long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String sql = "delete from stations where id = :id";
		namedParameterJdbcTemplate.update(sql, params);
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

	private static final class StationMapper implements RowMapper<Station> {
		public Station mapRow(ResultSet rs, int rowNum) throws SQLException {
			Station station = new Station();
			station.setId(rs.getInt("id"));
			station.setName(rs.getString("name"));
			station.setLocation(rs.getString("location"));
			return station;
		}
	}

}