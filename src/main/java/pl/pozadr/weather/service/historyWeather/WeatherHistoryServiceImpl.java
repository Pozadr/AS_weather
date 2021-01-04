package pl.pozadr.weather.service.historyWeather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.pozadr.weather.fetcher.RemoteApiFetcher;

import pl.pozadr.weather.model.currentWeather.City;
import pl.pozadr.weather.model.currentWeather.ConsolidatedWeather;
import pl.pozadr.weather.model.currentWeather.IconLink;
import pl.pozadr.weather.model.currentWeather.WeatherForecast;
import pl.pozadr.weather.model.historyWeather.WeatherHistory;
import pl.pozadr.weather.repository.WeatherHistoryRepo;

import java.util.List;
import java.util.Optional;


@Service
public class WeatherHistoryServiceImpl implements WeatherHistoryService {
    private String followedCity;
    private final WeatherHistoryRepo weatherHistoryRepo;
    private final RemoteApiFetcher remoteApiFetcher;
    private WeatherForecast weatherForecast;
    private ConsolidatedWeather forecastFirstDay;

    @Autowired
    public WeatherHistoryServiceImpl(RemoteApiFetcher remoteApiFetcher, WeatherHistoryRepo weatherHistoryRepo) {
        this.remoteApiFetcher = remoteApiFetcher;
        this.weatherHistoryRepo = weatherHistoryRepo;
        this.weatherForecast = new WeatherForecast();
        this.forecastFirstDay = new ConsolidatedWeather();
        this.followedCity = "None";
    }

    @Scheduled(fixedDelay = 10000)
    private void saveDataToDataBase() {
        if (!followedCity.isBlank() || !followedCity.equals("None")) {
            Optional<City[]> citiesOpt = remoteApiFetcher.fetchCitiesFromRemoteApi(followedCity);
            if (citiesOpt.isPresent() && !(citiesOpt.get().length == 0)) {
                boolean isWeatherSet = setWeatherForecast(citiesOpt.get()[0]);
                if (isWeatherSet) {
                    saveWeather();
                }
            }
        }
    }

    @Override
    public List<WeatherHistory> findAll() {
        return weatherHistoryRepo.findAll();
    }

    @Override
    public List<WeatherHistory> findByFollowedCity() {
        return weatherHistoryRepo.findByCity(followedCity);
    }

    @Override
    public boolean setCityToFollow(String cityToFollow) {
        Optional<City[]> citiesOpt = remoteApiFetcher.fetchCitiesFromRemoteApi(cityToFollow);
        if (citiesOpt.isPresent() && !(citiesOpt.get().length == 0)) {
            this.followedCity = citiesOpt.get()[0].getTitle();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getFollowedCity() {
        return followedCity;
    }


    private boolean setWeatherForecast(City city) {
        Optional<WeatherForecast> weatherForecastOpt =
                remoteApiFetcher.fetchWeatherForecastFromRemoteApi(city);
        if (weatherForecastOpt.isPresent()) {
            weatherForecast = weatherForecastOpt.get();
            forecastFirstDay = weatherForecast.getConsolidatedWeather().get(0);
        } else {
            System.out.println("Error: Remote API data fetcher failure.");
            return false;
        }
        return true;
    }

    private void saveWeather() {
        WeatherHistory weatherHistory = new WeatherHistory();
        weatherHistory.setCity(weatherForecast.getTitle());
        weatherHistory.setTemperature(Math.round(forecastFirstDay.getTheTemp()));
        weatherHistory.setWeatherStateName(forecastFirstDay.getWeatherStateName());
        weatherHistory.setAirPressure(Math.round(forecastFirstDay.getAirPressure()));
        weatherHistory.setWindSpeed(Math.round(forecastFirstDay.getWindSpeed()));
        weatherHistory.setWindDirectionCompass(forecastFirstDay.getWindDirectionCompass());
        weatherHistory.setSunRise(weatherForecast.getSunRise().substring(11, 16));
        weatherHistory.setSunSet(weatherForecast.getSunSet().substring(11, 16));
        weatherHistory.setIconLink(getIconLink());

        String dateTime = forecastFirstDay.getCreated().substring(0, 10) +
                " " +
                forecastFirstDay.getCreated().substring(11, 19);
        weatherHistory.setDateTime(dateTime);

        weatherHistoryRepo.save(weatherHistory);
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
