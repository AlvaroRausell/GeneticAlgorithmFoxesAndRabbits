package Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Season {
    private String name;
    private HashMap<Project.Weather,Integer> weatherProbs;
    private Project.Weather currentWeather;


    public Season(String name, HashMap<Project.Weather,Integer> weatherProbs){
        this.name = name;
        this.weatherProbs = weatherProbs;
        setWeather();
    }

    public String getName() {
        return name;
    }

    public void setWeather(){
        ArrayList<Project.Weather> weathers = new ArrayList<>();
        for(Map.Entry entry : weatherProbs.entrySet()){
            for(int i = 0; i< (int)entry.getValue();i++){
                weathers.add((Project.Weather) entry.getKey());
            }
        }
        Random rand = new Random();
       Project.Weather weather = weathers.get(rand.nextInt(weathers.size()));
        currentWeather = weather;
    }

    public Project.Weather getCurrentWeather() {
        return currentWeather;
    }
}
