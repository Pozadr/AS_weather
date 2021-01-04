package pl.pozadr.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pozadr.weather.model.historyWeather.WeatherHistory;

@Repository
public interface WeatherHistoryRepo extends JpaRepository<WeatherHistory, Long> {


}
