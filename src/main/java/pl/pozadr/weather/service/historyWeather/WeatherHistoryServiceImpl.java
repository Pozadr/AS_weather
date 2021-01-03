package pl.pozadr.weather.service.historyWeather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.pozadr.weather.model.historyWeather.WeatherHistory;
import pl.pozadr.weather.repository.WeatherHistoryRepo;

@Service
public class WeatherHistoryServiceImpl implements WeatherHistoryService {
    WeatherHistoryRepo weatherHistoryRepo;

    @Autowired
    public WeatherHistoryServiceImpl(WeatherHistoryRepo weatherHistoryRepo) {
        this.weatherHistoryRepo = weatherHistoryRepo;
    }

    @Override
    @Scheduled(fixedDelay = 5000)
    public void scheduledSaveDataToDataBase() {
        weatherHistoryRepo.save(new WeatherHistory());
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 5000);
    }
}
