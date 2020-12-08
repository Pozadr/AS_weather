package pl.pozadr.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.pozadr.weather.dto.CityInput;
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
        if (cityInput.getName().trim().equals("")) {
            return "weatherError";
        }
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
        Long airPressure = weatherService.getAirPressure();
        Long windSpeed = weatherService.getWindSpeed();
        String windDirectionCompass = weatherService.getWindDirectionCompass();
        String sunRise = weatherService.getSunRise();
        String sunSet = weatherService.getSunSet();


        model.addAttribute("cityInput", new CityInput());
        model.addAttribute("city", city);
        model.addAttribute("temperature", temperature);
        model.addAttribute("weatherStateName", weatherStateName);
        model.addAttribute("iconLink", weatherStateIcon);
        model.addAttribute("airPressure", airPressure);
        model.addAttribute("windSpeed", windSpeed);
        model.addAttribute("windDirectionCompass", windDirectionCompass);
        model.addAttribute("sunRise", sunRise);
        model.addAttribute("sunSet", sunSet);

        return "weatherView";
    }


    @GetMapping("/go-to-home-page")
    public String goToHomePage() {
        return "redirect:/weather-home";
    }
}
