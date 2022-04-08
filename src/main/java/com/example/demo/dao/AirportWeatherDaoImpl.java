package com.example.demo.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.HourlyWeatherDataDto;

@Repository
public class AirportWeatherDaoImpl implements AirportweatherDao {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void saveAirportWeatherData(String airport, LocalDate date, HourlyWeatherDataDto dto) {
		final String insertQuery = "insert into FPS_WEATHER_DATA(AIRPORT_ICAO_CD,READING_DT,READING_HOUR_NBR,TEMPERATURE_NBR,"
				+ "WIND_DIRECTION_NBR, WIND_SPEED_NBR,WIND_GUST_NBR,PERCIPITATION_CHANGE_PCT,CEILING_HEIGHT_NBR,VISIBILITY_NBR) "
				+ "values (:airport,:date,:hour,:tmp,:wdr,:wsp,:gst,:p01,:cig,:vis)";
		final String updateQuery = "update FPS_WEATHER_DATA set  TEMPERATURE_NBR = :tmp, WIND_DIRECTION_NBR= :wdr,"
				+ "WIND_SPEED_NBR = :wsp, WIND_GUST_NBR = :gst, PERCIPITATION_CHANGE_PCT = :p01, CEILING_HEIGHT_NBR = :cig, VISIBILITY_NBR = :vis where AIRPORT_ICAO_CD= :airport and READING_DT= :date and READING_HOUR_NBR = :hour";
		LinkedHashMap<String, Object> namedParameters = new LinkedHashMap<>();
		namedParameters.put("airport", airport);
		namedParameters.put("date", Date.valueOf(date));
		namedParameters.put("hour", dto.getHourlyForecast());
		namedParameters.put("tmp", dto.getTemperature());
		namedParameters.put("wdr", dto.getWindDirection());
		namedParameters.put("wsp", dto.getWindSpeed());
		namedParameters.put("gst", dto.getWindGust());
		namedParameters.put("p01", dto.getPrecipChance());
		namedParameters.put("cig", dto.getCeilingHeight());
		namedParameters.put("vis", dto.getVisibility());
		NamedParameterJdbcTemplate namedParamterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

		try {
			namedParamterJdbcTemplate.update(insertQuery, namedParameters);
		} catch (DuplicateKeyException e) {
			namedParamterJdbcTemplate.update(updateQuery, namedParameters);
		}
	}

	@Override
	public void saveAirportWeatherData(String airport, List<HourlyWeatherDataDto> lst) {

		final String insertQuery = "insert into FPS_WEATHER_DATA(AIRPORT_ICAO_CD,READING_DT,READING_HOUR_NBR,TEMPERATURE_NBR,"
				+ "WIND_DIRECTION_NBR, WIND_SPEED_NBR,WIND_GUST_NBR,PERCIPITATION_CHANGE_PCT,CEILING_HEIGHT_NBR,VISIBILITY_NBR) "
				+ "values (?,?,?,?,?,?,?,?,?,?)"
				+ "ON DUPLICATE KEY "
				+ "UPDATE TEMPERATURE_NBR=TEMPERATURE_NBR, WIND_DIRECTION_NBR=WIND_DIRECTION_NBR, WIND_SPEED_NBR=WIND_SPEED_NBR, WIND_GUST_NBR=WIND_GUST_NBR,"
				+ " PERCIPITATION_CHANGE_PCT=PERCIPITATION_CHANGE_PCT, CEILING_HEIGHT_NBR=CEILING_HEIGHT_NBR, VISIBILITY_NBR=VISIBILITY_NBR";
		this.jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, airport);
				ps.setDate(2, Date.valueOf(lst.get(i).getDate()));
				ps.setInt(3, lst.get(i).getHourlyForecast());
				if (lst.get(i).getTemperature() != null)
					ps.setInt(4, lst.get(i).getTemperature());
				else
					ps.setNull(4, Types.INTEGER);

				if (lst.get(i).getWindDirection() != null)
					ps.setInt(5, lst.get(i).getWindDirection());
				else
					ps.setNull(5, Types.INTEGER);

				if (lst.get(i).getWindSpeed() != null)
					ps.setInt(6, lst.get(i).getWindSpeed());
				else
					ps.setNull(6, Types.INTEGER);

				if (lst.get(i).getWindGust() != null)
					ps.setInt(7, lst.get(i).getWindGust());
				else
					ps.setNull(7, Types.INTEGER);

				if (lst.get(i).getPrecipChance() != null)
					ps.setInt(8, lst.get(i).getPrecipChance());
				else
					ps.setNull(8, Types.INTEGER);

				if (lst.get(i).getCeilingHeight() != null)
					ps.setInt(9, lst.get(i).getCeilingHeight());
				else
					ps.setNull(9, Types.INTEGER);

				if (lst.get(i).getVisibility() != null)
					ps.setInt(10, lst.get(i).getVisibility());
				else
					ps.setNull(10, Types.INTEGER);
			}

			@Override
			public int getBatchSize() {
				return lst.size();
			}
		});
	}
}
