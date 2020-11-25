package pl.pozadr.weather.clinet;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.pozadr.weather.api.Dog;

import java.util.Objects;
import java.util.stream.Stream;

@Controller
public class DogController {

    private RestTemplate restTemplate;

    public DogController() {
        this.restTemplate = new RestTemplate();
    }

    //@EventListener(ApplicationReadyEvent.class)
    public void getDogs() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("amount", "3");
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<Dog[]> exchange = restTemplate.exchange("http://localhost:8080/dogs/",
                HttpMethod.GET,
                httpEntity,
                Dog[].class);
        Stream.of(Objects.requireNonNull(exchange.getBody())).forEach(System.out::println);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addDog() {
        Dog newDog = new Dog("NewDog", "Chow chow");
        HttpEntity httpEntity = new HttpEntity(newDog);
        restTemplate.exchange("http://localhost:8080/dogs/",
                HttpMethod.POST,
                httpEntity,
                void.class);
    }


}
