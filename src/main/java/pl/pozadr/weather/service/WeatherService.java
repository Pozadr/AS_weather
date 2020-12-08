package pl.pozadr.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pozadr.weather.dto.CityInput;
import pl.pozadr.weather.fetcher.RemoteApiFetcherImpl;
import pl.pozadr.weather.model.City;
import pl.pozadr.weather.model.ConsolidatedWeather;
import pl.pozadr.weather.model.WeatherForecast;

import java.util.Optional;


@Service
public class WeatherService {
    private WeatherForecast weatherForecast;
    private ConsolidatedWeather forecastFirstDay;
    private final RemoteApiFetcherImpl remoteApiFetcherImpl;

    @Autowired
    public WeatherService(RemoteApiFetcherImpl remoteApiFetcherImpl) {
        this.remoteApiFetcherImpl = remoteApiFetcherImpl;
    }

    public boolean setWeatherForecast(CityInput cityInput) {
        Optional<City[]> searchCityOpt = remoteApiFetcherImpl.fetchCitiesFromRemoteApi(cityInput);
        if (searchCityOpt.isEmpty() || searchCityOpt.get().length == 0) {
            return false;
        }
        City[] cities = searchCityOpt.get();

        Optional<WeatherForecast> weatherForecastOpt = remoteApiFetcherImpl.fetchWeatherForecastFromRemoteApi(cities[0]);
        if (weatherForecastOpt.isPresent()) {
            this.weatherForecast = weatherForecastOpt.get();
            forecastFirstDay = weatherForecast.getConsolidatedWeather().get(0);
        } else {
            System.out.println("Error: Remote API data fetcher failure.");
            return false;
        }
        return true;
    }


    public String getIconLink() {
        String weatherState = weatherForecast.getConsolidatedWeather().get(0).getWeatherStateAbbr();
        switch (weatherState) {
            case "sn": {
                return "https://www.metaweather.com//static/img/weather/sn.svg";
            }
            case "sl": {
                return "https://www.metaweather.com//static/img/weather/sl.svg";
            }
            case "h": {
                return "https://www.metaweather.com//static/img/weather/h.svg";
            }
            case "t": {
                return "https://www.metaweather.com//static/img/weather/t.svg";
            }
            case "hr": {
                return "https://www.metaweather.com//static/img/weather/hr.svg";
            }
            case "lr": {
                return "https://www.metaweather.com//static/img/weather/lr.svg";
            }
            case "s": {
                return "https://www.metaweather.com//static/img/weather/s.svg";
            }
            case "hc": {
                return "https://www.metaweather.com//static/img/weather/hc.svg";
            }
            case "lc": {
                return "https://www.metaweather.com//static/img/weather/lc.svg";
            }
            case "c": {
                return "https://www.metaweather.com//static/img/weather/c.svg";
            }
            default: {
                return "Error!";
            }
        }
    }

    public String getCity() {
        return weatherForecast.getTitle();
    }

    public Long getTemperature() {
        return Math.round(forecastFirstDay.getTheTemp());
    }

    public String getWeatherStateName() {
        return forecastFirstDay.getWeatherStateName();
    }

    public Long getAirPressure() {
        return Math.round(forecastFirstDay.getAirPressure());
    }

    public Long getWindSpeed() {
        return Math.round(forecastFirstDay.getWindSpeed());
    }

    public String getWindDirectionCompass() {
        return forecastFirstDay.getWindDirectionCompass();
    }

    public String getSunRise() {
        return weatherForecast.getSunRise().substring(11, 16);
    }

    public String getSunSet() {
        return weatherForecast.getSunSet().substring(11, 16);
    }

}
