package pl.pozadr.weather.controller.thymeleaf;

public class CityInput {
    private String name;

    public CityInput() {
    }

    public CityInput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
