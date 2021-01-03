package pl.pozadr.weather.service.currentWeather;

import pl.pozadr.weather.dto.WeatherInfo;

public interface WeatherService {
    boolean setWeatherForecast(String cityInput);

    WeatherInfo getWeatherInfo();
}
