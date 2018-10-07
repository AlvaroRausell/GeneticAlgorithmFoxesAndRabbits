package Project;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Vulture extends Project.Animal implements Project.Predator {
    // Characteristics shared by all foxes (class variables).

    // The age at which a fox can start to breed.
    private static  int BREEDING_AGE = 20;
    // The age to which a fox can live.
    private static  int MAX_AGE = 150;
    // The likelihood of a fox breeding.
    private static  double BREEDING_PROBABILITY = 0.64;
    // The maximum number of births.
    private static  int MAX_LITTER_SIZE = 4;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static  int DEADCELL_FOOD_VALUE = 5;
    private static  double ATTACK_PROBABILITY = 0.09;

    private static  int FIGHT_SCORE = 8;
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
    public Vulture(Project.Field field, Project.Location location, boolean randomAge, Project.CalendarAndWeather time, Object[] stats)
    {
        super(field, location,randomAge, new HashMap<Class,Integer>(){{
            put(Project.DeadCell.class,DEADCELL_FOOD_VALUE);
        }},time);
        BREEDING_AGE = (int) stats[0];
        MAX_AGE = (int)stats[1];
        BREEDING_PROBABILITY = (double) stats[2];
        MAX_LITTER_SIZE = (int) stats[3];
        DEADCELL_FOOD_VALUE = (int) stats[4];
        ATTACK_PROBABILITY = (double) stats [5];
        FIGHT_SCORE = (int) stats[6];
        this.stats = stats;

    }
    @Override
    public Object[] getStats() {
        return stats;
    }
    public void act (List<Project.Cell> cells) throws Exception {
        super.act(cells);
        attack(field,location,cells);
    }

    public double getAttackProb(){
        return ATTACK_PROBABILITY;
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

    public void setDead(){
        super.setDead(newCells);
    }

    public int getFightScore() {
        return FIGHT_SCORE;
    }
}
