package pl.pozadr.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pozadr.weather.model.historyWeather.WeatherHistory;

import java.util.List;

@Repository
public interface WeatherHistoryRepo extends JpaRepository<WeatherHistory, Long> {
    List<WeatherHistory> findByCity(String city);

}
