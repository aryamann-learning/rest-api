CREATE TABLE FPS_WEATHER_METADATA (
  READING_DT                      DATE,       -- Weather Reading Date
  READING_HOUR_NBR                NUMBER,     -- Weather Reading Hour
  LAST_MODIFIED_TMSTP             TIMESTAMP   -- Last Modified timestamp of the weather data file
);

CREATE TABLE FPS_WEATHER_DATA (
  AIRPORT_ICAO_CD                 VARCHAR2(4),-- Airport ICAO code
  READING_DT                      DATE,       -- Weather Reading Date
  READING_HOUR_NBR                NUMBER,     -- Weather Reading Hour
  TEMPERATURE_NBR                 NUMBER,     -- Temperature Reading
  WIND_DIRECTION_NBR              NUMBER,     -- Wind Direction Reading
  WIND_SPEED_NBR                  NUMBER,     -- Wind Speed Reading
  WIND_GUST_NBR                   NUMBER,     -- Wind Gust Reading
  PERCIPITATION_CHANGE_PCT        NUMBER,     -- Percipitation change percentange
  CEILING_HEIGHT_NBR              NUMBER,     -- Ceiling height
  VISIBILITY_NBR                  NUMBER,     -- visibility

  CONSTRAINT FPS_WEATHER_DATA_PK PRIMARY KEY (AIRPORT_ICAO_CD, READING_DT, READING_HOUR_NBR)
);

CREATE TABLE FPS_WEATHER_DATA_HISTORY (
  AIRPORT_ICAO_CD                 VARCHAR2(4),
  READING_DT                      DATE,
  READING_HOUR_NBR                NUMBER,
  TEMPERATURE_NBR                 NUMBER,
  WIND_DIRECTION_NBR              NUMBER,
  WIND_SPEED_NBR                  NUMBER,
  WIND_GUST_NBR                   NUMBER,
  PERCIPITATION_CHANGE_PCT        NUMBER,
  CEILING_HEIGHT_NBR              NUMBER,
  VISIBILITY_NBR                  NUMBER,
  CREATED_TMSTP                   TIMESTAMP
);

create index FPS_WEATHER_DATA_HISTORY_INDEX on FPS_WEATHER_DATA_HISTORY (AIRPORT_ICAO_CD, READING_DT, READING_HOUR_NBR);

create or replace trigger FPS_WEATHER_DATA_TRG
	after insert or update
	on FPS_WEATHER_DATA
  for each row
  declare
  begin
  insert into FPS_WEATHER_DATA_HISTORY
  (
    AIRPORT_ICAO_CD,
    READING_HOUR_NBR,
    READING_DT,
    TEMPERATURE_NBR,
    WIND_DIRECTION_NBR,
    WIND_SPEED_NBR,
    WIND_GUST_NBR,
    PERCIPITATION_CHANGE_PCT,
    CEILING_HEIGHT_NBR,
    VISIBILITY_NBR,
    CREATED_TMSTP
  ) values (
    :new.AIRPORT_ICAO_CD,
    :new.READING_HOUR_NBR,
    :new.READING_DT,
    :new.TEMPERATURE_NBR,
    :new.WIND_DIRECTION_NBR,
    :new.WIND_SPEED_NBR,
    :new.WIND_GUST_NBR,
    :new.PERCIPITATION_CHANGE_PCT,
    :new.CEILING_HEIGHT_NBR,
    :new.VISIBILITY_NBR,
    current_timestamp
  );
  END;