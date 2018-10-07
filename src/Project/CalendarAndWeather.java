package Project;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CalendarAndWeather {
    private int dayCount;
    private String dayTime;
    private Season currentSeason;
    private Simulator simulator;
    private static final double DISEASE_PROBABILITY = 0.3;
    private static final Disease[] DISEASES = {new Disease("FLU",5,animal -> animal.incrementAge())};

    public static final Weather[] WEATHERS = {new Weather("SUNNY",null),new Weather("RAINY",DISEASES),
            new Weather("SNOWY",DISEASES),new Weather("CLOUDY",DISEASES)};

    public static final Season[] SEASONS = {
            new Season("SPRING",new HashMap<Weather,Integer>(){{
                put(WEATHERS[0],3);
                put(WEATHERS[1],2);
                put(WEATHERS[2],0);
                put(WEATHERS[3],1);
            }}),
            new Season("SUMMER",new HashMap<Weather,Integer>(){{
                put(WEATHERS[0],3);
                put(WEATHERS[1],1);
                put(WEATHERS[2],0);
                put(WEATHERS[3],1);
            }}),
            new Season("AUTUMN",new HashMap<Weather,Integer>(){{
                put(WEATHERS[0],1);
                put(WEATHERS[1],3);
                put(WEATHERS[2],1);
                put(WEATHERS[3],3);
            }}),
            new Season("WINTER",new HashMap<Weather,Integer>(){{
                put(WEATHERS[0],1);
                put(WEATHERS[1],3);
                put(WEATHERS[2],3);
                put(WEATHERS[3],2);

            }})
    };
    public CalendarAndWeather (Simulator simulator){
        currentSeason = SEASONS[0];
        dayCount = 0;
        dayTime = "DAY";
        this.simulator = simulator;
    }
    public void update(List<Cell>cells){
        if (simulator.getStep()%480 == 0&&simulator.getStep() != 0){
            dayCount++;
            dayTime = dayTime.equals("DAY")? "NIGHT" : "DAY";
            currentSeason = nextSeason();
            currentSeason.setWeather();
        }
        if (simulator.getStep()%48 == 0&&simulator.getStep() != 0){
            dayCount++;
            dayTime = dayTime.equals("DAY")? "NIGHT" : "DAY";
            currentSeason.setWeather();
        } else if (simulator.getStep()%24 == 0&&simulator.getStep() != 0){
            dayTime = dayTime.equals("DAY")? "NIGHT" : "DAY";
            currentSeason.setWeather();
        }
        if (getCurrentWeather().hasDiseases()){
            for (Cell cell: cells)
                    if (new Random().nextDouble()<=DISEASE_PROBABILITY)
                        cell.setDisease(getCurrentWeather().getRandomDisease());

        }
    }

    public String getDayTime() {
        return dayTime;
    }

    public Season getCurrentSeason() {
        return currentSeason;
    }
    public Season nextSeason(){
        for (int i= 0; i< SEASONS.length; i++){
            if (currentSeason.equals(SEASONS[i])){
                return i ==(SEASONS.length-1) ? SEASONS[0]:SEASONS[i+1];
            }
        }
        return SEASONS[0];
    }

    public int getDayCount() {
        return dayCount;
    }
    public Weather getCurrentWeather(){
       return currentSeason.getCurrentWeather();
    }
}
