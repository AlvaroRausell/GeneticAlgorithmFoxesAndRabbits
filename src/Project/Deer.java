package Project;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by k1763934 on 17/02/18.
 */
public class Deer extends Project.Animal {

    // The age at which a rabbit can start to breed.
    private static  int BREEDING_AGE = 7;
    // The age to which a rabbit can live.
    private static  int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static  double BREEDING_PROBABILITY = 0.57;
    // The maximum number of births.
    private static  int MAX_LITTER_SIZE = 3;

    private static  int PLANT_NUTRITIONAL_VALUE =5;

    // A shared random number generator to control breeding.
    private static final Random rand = Project.Randomizer.getRandom();

    // Individual characteristics (instance fields).
    private Object[] stats;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Deer(Project.Field field, Project.Location location, boolean randomAge, Project.CalendarAndWeather time, Object[] stats)
    {
        super(field, location,randomAge, new HashMap<Class,Integer>(){{
            put(Project.Grass.class,PLANT_NUTRITIONAL_VALUE);
        }},time);
        BREEDING_AGE = (int) stats[0];
        MAX_AGE = (int)stats[1];
        BREEDING_PROBABILITY = (double) stats[2];
        MAX_LITTER_SIZE = (int) stats[3];
        PLANT_NUTRITIONAL_VALUE = (int) stats[4];
    this.stats = stats;
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
}
