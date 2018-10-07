package Project;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Rabbit extends Project.Animal
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static  int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static  int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static  double breedingProbability = 0.92;
    // The maximum number of births.
    private static double springBreedProb;
    private static double notSpringBreedProb;
    private static  int MAX_LITTER_SIZE = 3;

    private static  int PLANT_NUTRITIONAL_VALUE = 5;

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
    public Rabbit(Project.Field field, Project.Location location, boolean randomAge, Project.CalendarAndWeather time, Object[] stats)
    {
        super(field, location,randomAge, new HashMap<Class,Integer>(){{
            put(Project.Grass.class,PLANT_NUTRITIONAL_VALUE);
        }},time);
        BREEDING_AGE = (int) stats[0];
        MAX_AGE = (int)stats[1];
        springBreedProb = (double) stats[2];
        notSpringBreedProb = (double) stats[3];
        MAX_LITTER_SIZE = (int) stats[4];
        PLANT_NUTRITIONAL_VALUE = (int) stats[5];
        this.stats = stats;
    }

    @Override
    public Object[] getStats() {
        return stats;
    }

    public void act (List<Project.Cell> cells) throws Exception {
        if (getTime().getCurrentSeason().getName().equals("SPRING")){
            breedingProbability = springBreedProb;
        } else
            breedingProbability = notSpringBreedProb;
        if (getTime().getDayTime().equals("DAY"))
            super.act(cells);
    }

    public int getBreedingAge() {
        return BREEDING_AGE;
    }
    public int getMaxAge(){
        return MAX_AGE;
    }

    public double getBreedingProb(){
        return breedingProbability;
    }

    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }
}
