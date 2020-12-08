package pl.pozadr.weather.service;

import pl.pozadr.weather.dto.CityInput;
import pl.pozadr.weather.dto.WeatherInfo;

public interface WeatherService {
    boolean setWeatherForecast(CityInput cityInput);

    WeatherInfo getWeatherInfo();
}
