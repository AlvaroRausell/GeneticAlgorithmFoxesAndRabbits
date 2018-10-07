package Project;

import java.util.Random;

public class Weather {
    private String name;
    private Disease [] diseases;

    public Weather(String name, Disease[]diseases){
        this.name = name;
        this.diseases = diseases;
    }

    public String getName() {
        return name;
    }
    public boolean hasDiseases(){
        return !(diseases == null);
    }
    public Disease getRandomDisease(){
        return diseases[new Random().nextInt(diseases.length)];
    }
}
