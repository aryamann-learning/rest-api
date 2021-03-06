CREATE TABLE FPS_WEATHER_DATA (
    AIRPORT_ICAO_CD VARCHAR(10),
    READING_DT DATE,
    READING_HOUR_NBR INT(5),
    TEMPERATURE_NBR INT(5),
    WIND_DIRECTION_NBR INT(5),
    WIND_SPEED_NBR INT(5),
    WIND_GUST_NBR INT(5),
    PERCIPITATION_CHANGE_PCT INT(5),
    CEILING_HEIGHT_NBR INT(5),
    VISIBILITY_NBR INT(5),
    CONSTRAINT FPS_WEATHER_DATA_PK PRIMARY KEY (AIRPORT_ICAO_CD , READING_DT , READING_HOUR_NBR)
);

CREATE TABLE FPS_WEATHER_DATA_HISTORY (
    AIRPORT_ICAO_CD VARCHAR(10),
    READING_DT DATE, #yyyy-MM-dd
    READING_HOUR_NBR INT(5),
    TEMPERATURE_NBR INT(5),
    WIND_DIRECTION_NBR INT(5),
    WIND_SPEED_NBR INT(5),
    WIND_GUST_NBR INT(5),
    PERCIPITATION_CHANGE_PCT INT(5),
    CEILING_HEIGHT_NBR INT(5),
    VISIBILITY_NBR INT(5),
    CREATED_TMSTP TIMESTAMP
);


CREATE DEFINER=`root`@`localhost` TRIGGER `testdb`.`FPS_WEATHER_DATA_TRG` AFTER INSERT ON `fps_weather_data` FOR EACH ROW
BEGIN
insert into FPS_WEATHER_DATA_HISTORY values (
NEW.AIRPORT_ICAO_CD,
NEW.READING_DT,
    NEW.READING_HOUR_NBR,
    NEW.TEMPERATURE_NBR,
    NEW.WIND_DIRECTION_NBR,
    NEW.WIND_SPEED_NBR,
    NEW.WIND_GUST_NBR,
    NEW.PERCIPITATION_CHANGE_PCT,
    NEW.CEILING_HEIGHT_NBR,
    NEW.VISIBILITY_NBR,
    NOW());
END

CREATE DEFINER=`root`@`localhost` TRIGGER `testdb`.`fps_weather_data_AFTER_UPDATE` AFTER UPDATE ON `fps_weather_data` FOR EACH ROW
BEGIN
insert into FPS_WEATHER_DATA_HISTORY values (
NEW.AIRPORT_ICAO_CD,
NEW.READING_DT,
    NEW.READING_HOUR_NBR,
    NEW.TEMPERATURE_NBR,
    NEW.WIND_DIRECTION_NBR,
    NEW.WIND_SPEED_NBR,
    NEW.WIND_GUST_NBR,
    NEW.PERCIPITATION_CHANGE_PCT,
    NEW.CEILING_HEIGHT_NBR,
    NEW.VISIBILITY_NBR,
    NOW());
END