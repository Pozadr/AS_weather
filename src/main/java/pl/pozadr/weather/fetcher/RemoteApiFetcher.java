package pl.pozadr.weather.fetcher;

import pl.pozadr.weather.dto.CityInput;
import pl.pozadr.weather.model.City;
import pl.pozadr.weather.model.WeatherForecast;

import java.util.Optional;

public interface RemoteApiFetcher {
    Optional<WeatherForecast> fetchWeatherForecastFromRemoteApi(City city);
    Optional<City[]> fetchCitiesFromRemoteApi(CityInput cityInput);
}
