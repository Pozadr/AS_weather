package pl.pozadr.weather.service.historyWeather;

import pl.pozadr.weather.model.historyWeather.WeatherHistory;

import java.util.List;

public interface WeatherHistoryService {
    List<WeatherHistory> findAll();
    List<WeatherHistory> findByFollowedCity();
    boolean setCityToFollow(String cityToFollow);
    String getFollowedCity();
}
