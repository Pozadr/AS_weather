package pl.pozadr.weather.api;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dogs")
public class DogApi {
    private List<Dog> dogList;

    public DogApi() {
        dogList = new ArrayList<>();
        dogList.add(new Dog("Max", "German Shepard"));
        dogList.add(new Dog("Rex", "Husky"));
        dogList.add(new Dog("Burek", "Somoyed"));
    }

    @GetMapping
    public List<Dog> getAmount(@RequestHeader int amount) {
        return dogList.subList(0, amount);
    }

    @PostMapping
    public void addDog (@RequestBody Dog dog) {
        dogList.add(dog);
    }

}
