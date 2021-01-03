package pl.pozadr.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.pozadr.weather.dto.WeatherInfo;
import pl.pozadr.weather.service.currentWeather.WeatherService;
import pl.pozadr.weather.service.currentWeather.WeatherServiceImpl;

@Controller
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherServiceImpl weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping("/weather-home")
    public String getWeatherHome(Model model) {
        return "weatherHome";
    }


    @GetMapping("/set-weather")
    public String setWeather(String city) {
        if (city.trim().equals("")) {
            return "weatherError";
        }
        boolean isWeatherSet = weatherService.setWeatherForecast(city);
        if (isWeatherSet) {
            return "redirect:/weather-view";
        }
        return "weatherError";
    }


    @GetMapping("/weather-view")
    public String getWeather(Model model) {
        WeatherInfo weatherInfo = weatherService.getWeatherInfo();

        model.addAttribute("city", weatherInfo.getCity());
        model.addAttribute("temperature", weatherInfo.getTemperature());
        model.addAttribute("weatherStateName", weatherInfo.getWeatherStateName());
        model.addAttribute("iconLink", weatherInfo.getIconLink());
        model.addAttribute("airPressure", weatherInfo.getAirPressure());
        model.addAttribute("windSpeed", weatherInfo.getWindSpeed());
        model.addAttribute("windDirectionCompass", weatherInfo.getWindDirectionCompass());
        model.addAttribute("sunRise", weatherInfo.getSunRise());
        model.addAttribute("sunSet", weatherInfo.getSunSet());

        return "weatherView";
    }


    @GetMapping("/go-to-home-page")
    public String goToHomePage() {
        return "redirect:/weather-home";
    }
}
