package pl.pozadr.weather;

import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import pl.pozadr.weather.model.CatFact;

@Controller
public class CatController {
    public CatController() {
        RestTemplate restTemplate = new RestTemplate();
        CatFact[] catFacts = restTemplate.getForObject("https://cat-fact.herokuapp.com/facts/random?animal_type=cat&amount=2",
                CatFact[].class);

        assert catFacts != null;
        for (CatFact catfact: catFacts) {
            System.out.println(catfact);
        }
    }
}
