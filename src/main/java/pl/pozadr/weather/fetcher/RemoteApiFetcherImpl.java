package pl.pozadr.weather.fetcher;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.pozadr.weather.dto.CityInput;
import pl.pozadr.weather.model.currentWeather.City;
import pl.pozadr.weather.model.currentWeather.WeatherForecast;

import java.util.Optional;

@Service
public class RemoteApiFetcherImpl implements RemoteApiFetcher {
    public Optional<WeatherForecast> fetchWeatherForecastFromRemoteApi(City city) {
        String weatherUrl = "https://www.metaweather.com/api/location/" +
                city.getWoeid();

        RestTemplate restTemplateWeather = new RestTemplate();
        return Optional.ofNullable(restTemplateWeather.getForObject(weatherUrl, WeatherForecast.class));
    }

    public Optional<City[]> fetchCitiesFromRemoteApi(CityInput cityInput) {
        String cityUrl = "https://www.metaweather.com/api/location/search/?query=" +
                cityInput.getName().toLowerCase();

        RestTemplate restTemplateCity = new RestTemplate();
        return Optional.ofNullable(restTemplateCity.getForObject(cityUrl, City[].class));
    }
}
