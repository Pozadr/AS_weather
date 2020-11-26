package pl.pozadr.weather.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.pozadr.weather.controller.thymeleaf.CityInput;
import pl.pozadr.weather.model.City;
import pl.pozadr.weather.model.ConsolidatedWeather;
import pl.pozadr.weather.model.WeatherForecast;


@Service
public class WeatherService {
    private WeatherForecast weatherForecast;
    private ConsolidatedWeather forecastFirstDay;

    public boolean setWeatherForecast(CityInput cityInput) {
        String cityUrl = "https://www.metaweather.com/api/location/search/?query=" +
                cityInput.getName().toLowerCase();

        RestTemplate restTemplateCity = new RestTemplate();
        City[] searchCity = restTemplateCity.getForObject(cityUrl, City[].class);
        if (searchCity == null || searchCity.length == 0) {
            return false;
        }

        String weatherUrl = "https://www.metaweather.com/api/location/" +
                searchCity[0].getWoeid();

        RestTemplate restTemplateWeather = new RestTemplate();
        this.weatherForecast = restTemplateWeather.getForObject(weatherUrl, WeatherForecast.class);
        assert weatherForecast != null;
        forecastFirstDay = weatherForecast.getConsolidatedWeather().get(0);

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

}
