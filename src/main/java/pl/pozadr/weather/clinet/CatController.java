package pl.pozadr.weather.clinet;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import pl.pozadr.weather.clinet.model.CatFact;

@Controller
public class CatController {

    private CatFact getCatFact() {
        RestTemplate restTemplate = new RestTemplate();
        CatFact catFact = restTemplate.getForObject("https://cat-fact.herokuapp.com/facts/random", CatFact.class);
        JsonNode image = restTemplate.getForObject("https://aws.random.cat/meow", JsonNode.class).get("file");
        assert catFact != null;
        catFact.setSrc(image.asText());
        return catFact;
    }

    @GetMapping("animal-fact")
    public String get(Model model) {
        model.addAttribute("catFact", getCatFact());
        return "animalView";
    }
}
