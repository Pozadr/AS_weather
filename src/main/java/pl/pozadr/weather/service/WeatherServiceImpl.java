package pl.pozadr.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pozadr.weather.dto.CityInput;
import pl.pozadr.weather.dto.WeatherInfo;
import pl.pozadr.weather.fetcher.RemoteApiFetcher;
import pl.pozadr.weather.model.currentWeather.City;
import pl.pozadr.weather.model.currentWeather.ConsolidatedWeather;
import pl.pozadr.weather.model.currentWeather.IconLink;
import pl.pozadr.weather.model.currentWeather.WeatherForecast;

import java.util.Optional;


@Service
public class WeatherServiceImpl implements WeatherService {
    private WeatherForecast weatherForecast;
    private ConsolidatedWeather forecastFirstDay;
    private final RemoteApiFetcher remoteApiFetcher;


    @Autowired
    public WeatherServiceImpl(RemoteApiFetcher remoteApiFetcher) {
        this.remoteApiFetcher = remoteApiFetcher;
    }


    @Override
    public boolean setWeatherForecast(CityInput cityInput) {
        Optional<City[]> searchCityOpt = remoteApiFetcher.fetchCitiesFromRemoteApi(cityInput);
        if (searchCityOpt.isEmpty() || searchCityOpt.get().length == 0) {
            return false;
        }
        City[] cities = searchCityOpt.get();

        Optional<WeatherForecast> weatherForecastOpt =
                remoteApiFetcher.fetchWeatherForecastFromRemoteApi(cities[0]);
        if (weatherForecastOpt.isPresent()) {
            this.weatherForecast = weatherForecastOpt.get();
            forecastFirstDay = weatherForecast.getConsolidatedWeather().get(0);
        } else {
            System.out.println("Error: Remote API data fetcher failure.");
            return false;
        }
        return true;
    }


    @Override
    public WeatherInfo getWeatherInfo() {
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setCity(weatherForecast.getTitle());
        weatherInfo.setTemperature(Math.round(forecastFirstDay.getTheTemp()));
        weatherInfo.setWeatherStateName(forecastFirstDay.getWeatherStateName());
        weatherInfo.setAirPressure(Math.round(forecastFirstDay.getAirPressure()));
        weatherInfo.setWindSpeed(Math.round(forecastFirstDay.getWindSpeed()));
        weatherInfo.setWindDirectionCompass(forecastFirstDay.getWindDirectionCompass());
        weatherInfo.setSunRise(weatherForecast.getSunRise().substring(11, 16));
        weatherInfo.setSunSet(weatherForecast.getSunSet().substring(11, 16));
        weatherInfo.setIconLink(getIconLink());
        return weatherInfo;
    }


    private String getIconLink() {
        String weatherState = weatherForecast.getConsolidatedWeather().get(0).getWeatherStateAbbr();
        String link = "";
        try {
            link = IconLink.find(weatherState);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
        return link;
    }
}
