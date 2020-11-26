package pl.pozadr.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.pozadr.weather.controller.thymeleaf.CityInput;
import pl.pozadr.weather.service.WeatherService;

@Controller
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping("/weather-home")
    public String getWeatherHome(Model model) {
        model.addAttribute("cityInput", new CityInput());
        return "weatherHome";
    }


    @GetMapping("/set-weather")
    public String setWeather(@ModelAttribute CityInput cityInput) {
        boolean isWeatherSet = weatherService.setWeatherForecast(cityInput);
        if (isWeatherSet) {
            return "redirect:/weather-view";
        }
        return "weatherError";
    }


    @GetMapping("/weather-view")
    public String getWeather(Model model) {
        String city = weatherService.getCity();
        Long temperature = weatherService.getTemperature();
        String weatherStateName = weatherService.getWeatherStateName();
        String weatherStateIcon = weatherService.getIconLink();

        model.addAttribute("city", city);
        model.addAttribute("temperature", temperature);
        model.addAttribute("weatherStateName", weatherStateName);
        model.addAttribute("iconLink", weatherStateIcon);
        model.addAttribute("cityInput", new CityInput());
        return "weatherView";
    }


    @GetMapping("/go-to-home-page")
    public String goToHomePage() {
        return "redirect:/weather-home";
    }
}
