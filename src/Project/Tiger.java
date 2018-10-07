package Project;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by k1763934 on 17/02/18.
 */
public class Tiger extends Project.Animal implements Project.Predator {
    // The age at which a fox can start to breed.
    private static  int BREEDING_AGE = 15;
    // The age to which a fox can live.
    private static  int MAX_AGE = 70;
    // The likelihood of a fox breeding.
    private static  double BREEDING_PROBABILITY = 0.67;
    // The maximum number of births.
    private static  int MAX_LITTER_SIZE = 2;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static  int RABBIT_FOOD_VALUE = 5;
    private static  int DEER_FOOD_VALUE = 15;
    private static  double ATTACK_PROBABILITY = 0.1;
    private static int fightScore = 15;
    // A shared random number generator to control breeding.
    private static final Random rand = Project.Randomizer.getRandom();
    private Object[] stats;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Tiger(Project.Field field, Project.Location location, boolean randomAge, Project.CalendarAndWeather time, Object[] stats)
    {
        super(field, location,randomAge, new HashMap<Class,Integer>(){{
            put(Project.Rabbit.class,RABBIT_FOOD_VALUE);
           put(Project.Deer.class,DEER_FOOD_VALUE);
        }},time);
        BREEDING_AGE = (int) stats[0];
        MAX_AGE = (int)stats[1];
        BREEDING_PROBABILITY = (double) stats[2];
        MAX_LITTER_SIZE = (int) stats[3];
        RABBIT_FOOD_VALUE = (int) stats[4];
        DEER_FOOD_VALUE = (int) stats[5];
        ATTACK_PROBABILITY = (double) stats [6];
        fightScore = (int) stats[7];
        this.stats = stats;
    }
    public void act (List<Project.Cell> cells) throws Exception {

        if (getTime().getDayTime().equals("NIGHT")) {
            fightScore += 5;
            super.act(cells);
            attack(field,location,cells);
        } else if (isSick()){
            disease.getSymptom().affect(this);
            fightScore -= fightScore < 5? 0:5;
        }

    }


    @Override
    public Object[] getStats() {
        return stats;
    }
    public int getBreedingAge() {
        return BREEDING_AGE;
    }
    public int getMaxAge(){
        return MAX_AGE;
    }

    public double getBreedingProb(){
        return BREEDING_PROBABILITY;
    }

    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    public double getAttackProb(){
        return ATTACK_PROBABILITY;
    }

    public int getFightScore() {
        return fightScore;
    }
    public void setDead(){
        super.setDead(newCells);
    }
}
