package pl.pozadr.weather.fetcher;

import pl.pozadr.weather.model.currentWeather.City;
import pl.pozadr.weather.model.currentWeather.WeatherForecast;

import java.util.Optional;

public interface RemoteApiFetcher {
    Optional<WeatherForecast> fetchWeatherForecastFromRemoteApi(City city);
    Optional<City[]> fetchCitiesFromRemoteApi(String cityInput);
}
