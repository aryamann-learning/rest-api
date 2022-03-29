CREATE TABLE airport_weather_details (
apt_id INT(10) auto_increment primary key,
    airport VARCHAR(10),
    apt_date INT(5),
    apt_hour INT(5),    
    apt_tmp INT(5),
    apt_wdr INT(5),
    apt_wsp INT(5),
    apt_gst INT(5),
    apt_p01 INT(5),
    apt_cig INT(5),
    apt_vis INT(5)
);