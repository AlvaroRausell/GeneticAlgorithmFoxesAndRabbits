package Project;

import java.util.List;

public class Grass extends Project.Cell implements Project.Plant {

    private static  int MAX_SPREAD = 3;

    private double pollinationProbability  = 0.50;

    private double randomSpreadProbability = 0.15;

    private int age = 0;

    private static  int MAX_AGE = 5;
    private Object[] stats;
    public Grass(Project.Field field, Project.Location location, Project.CalendarAndWeather time, Object[] stats){
        super(field,location,time);
        alive = true;
        MAX_SPREAD = (int)stats[0];
        pollinationProbability = (double)stats[1];
        randomSpreadProbability = (double)stats[2];
        MAX_AGE = (int) stats[3];
        this.stats = stats;
    }

    @Override
    public Object[] getStats() {
        return stats;
    }

    public void setEaten(){
        super.setEaten();
    }
    @Override
    public int getPollenSpread() {
        return MAX_SPREAD;
    }

    @Override
    public double getPollinationProb() {
        return pollinationProbability;
    }

    @Override
    public double getRandomSpawnProb() {
        return randomSpreadProbability;
    }

    public void act(List<Project.Cell> newPlants) throws Exception {
        if (alive) {
            pollinate(field, location, getTime(), newPlants);
            incrementAge();
        }
    }

    public void incrementAge(){
        age++;

        if (age>MAX_AGE){
            setEaten();
        }
    }
    public void incrementAge(int times){
      age+=times;

        if (age>MAX_AGE){
            setEaten();
        }
    }



}
