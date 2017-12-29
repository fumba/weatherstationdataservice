package me.fumba.weatherstation.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
	public void saveStation(Station station) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateStation(Station station) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteStation(long id) {
		// TODO Auto-generated method stub

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