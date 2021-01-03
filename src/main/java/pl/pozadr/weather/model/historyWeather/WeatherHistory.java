package pl.pozadr.weather.model.historyWeather;

import javax.persistence.*;

@Entity
@Table(name = "weather_history")
public class WeatherHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private Long temperature;

    private String weatherStateName;

    private Long airPressure;

    private String windDirectionCompass;

    private String sunRise;

    private String sunSet;

    private Long windSpeed;

    private String IconLink;

    public WeatherHistory() {
    }

    public WeatherHistory(String city, Long temperature, String weatherStateName, Long airPressure, String windDirectionCompass, String sunRise, String sunSet, Long windSpeed, String iconLink) {
        this.city = city;
        this.temperature = temperature;
        this.weatherStateName = weatherStateName;
        this.airPressure = airPressure;
        this.windDirectionCompass = windDirectionCompass;
        this.sunRise = sunRise;
        this.sunSet = sunSet;
        this.windSpeed = windSpeed;
        IconLink = iconLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getTemperature() {
        return temperature;
    }

    public void setTemperature(Long temperature) {
        this.temperature = temperature;
    }

    public String getWeatherStateName() {
        return weatherStateName;
    }

    public void setWeatherStateName(String weatherStateName) {
        this.weatherStateName = weatherStateName;
    }

    public Long getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(Long airPressure) {
        this.airPressure = airPressure;
    }

    public String getWindDirectionCompass() {
        return windDirectionCompass;
    }

    public void setWindDirectionCompass(String windDirectionCompass) {
        this.windDirectionCompass = windDirectionCompass;
    }

    public String getSunRise() {
        return sunRise;
    }

    public void setSunRise(String sunRise) {
        this.sunRise = sunRise;
    }

    public String getSunSet() {
        return sunSet;
    }

    public void setSunSet(String sunSet) {
        this.sunSet = sunSet;
    }

    public Long getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Long windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getIconLink() {
        return IconLink;
    }

    public void setIconLink(String iconLink) {
        IconLink = iconLink;
    }
}
