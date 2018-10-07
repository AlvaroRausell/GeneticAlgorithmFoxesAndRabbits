package Project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal extends Project.Cell
{


    //Gender of the animal
    protected boolean isMale;
    //Age of the animal
    protected int age;



    protected HashMap<Class,Integer> foodClasses;









    /**
     * Create a new animal at location in field.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Project.Field field, Project.Location location, boolean randomAge, HashMap<Class,Integer> foodClasses, Project.CalendarAndWeather time)
    {
        super(field,location,time);
        this.foodClasses = foodClasses;
        alive = true;
        isMale = new Random().nextBoolean();


        if(randomAge) {
            age = new Random().nextInt(getMaxAge());
            foodLevel = 20;

        }
        else {
            age = 0;
            foodLevel = getMaxFoodValue();
        }

    }

    public void breed(List<Project.Cell> animals) throws Exception {

        int random = new Random().nextInt((this).getMaxLitterSize()+1);

        for (int i = 0; i< random;i++){
            Project.Location birthPlace = getField().freeAdjacentLocation(location);

            if (birthPlace == null)
                return;
            Project.Cell animal = this.getClass().getDeclaredConstructor(Project.Field.class, Project.Location.class, boolean.class, Project.CalendarAndWeather.class,Object[].class)
                    .newInstance(getField(), birthPlace, false,getTime(),getStats());

            if (getDisease() != null)
                getDisease().transfer(animal);
            animals.add(animal);
        }
    }
    public abstract Object[] getStats();

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    public void act(List<Project.Cell> newAnimals) throws Exception {

        newCells = newAnimals;
        if (isSick()) {
            getDisease().getSymptom().affect(this);
            getDisease().decreaseDuration();
            DEBUG_DEATH_CAUSE = "SICK";
            if (disease.getDuration() == 0)
                DEBUG_DEATH_CAUSE= "";
                disease = null;
        }
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            if (canBreed())
                breed(newAnimals);
            // Move towards a source of food if found.
            Project.Location newLocation = findFood();
            if(newLocation == null) {
                // No food found - try to move to a free location- tending to get closer to same species.
                List<Project.Location> surroundings = getField().getFreeAdjacentLocations(getLocation());
                newLocation = getField().freeAdjacentLocation(location);

                for (Project.Location location: surroundings){
                    if (foodNear(location)){
                        newLocation = location;
                        break;
                    }
                    else if (partnerNear(location)) {
                        newLocation = location;
                        break;
                    }
                }

            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead(newCells);
            }
        }
    }
    public boolean partnerNear(Project.Location location){
        List<Project.Location> locations = getField().adjacentLocations(location);
        for (Project.Location place: locations) {
            Project.Cell being = (Project.Cell) getField().getObjectAt(place);
            if (being != null && being.getClass()==this.getClass()&&((Animal)being).isMale() != isMale()&&being !=this)
                return true;
        }
        return false;
    }
    public boolean foodNear(Project.Location location){
        List<Project.Location> locations = getField().adjacentLocations(location);
        for (Project.Location place: locations) {
            Project.Cell being = (Project.Cell) getField().getObjectAt(place);
            if (being != null)
                for (Map.Entry food: foodClasses.entrySet())
                    if (being.getClass() == food.getKey())
                        return true;
        }
        return false;
    }

    //Gets maximum nutritional value of the prey
    public int getMaxFoodValue() {
        int maxFoodValue = 0;
        for (Map.Entry prey: foodClasses.entrySet())
            if ((int)prey.getValue() > maxFoodValue)
                maxFoodValue = (int)prey.getValue();

        return maxFoodValue;
    }

    protected Project.Location findFood(){
        List <Project.Location> adjacentLocations = getField().adjacentLocations(getLocation());
        for (Project.Location l : adjacentLocations){
            Project.Cell food =(Project.Cell) getField().getObjectAt(l);
            if (food != null)
            { for (Map.Entry e : foodClasses.entrySet())
                if (e.getKey().equals(food.getClass())){
                    Project.Cell prey = (Project.Cell)getField().getObjectAt(l);
                    prey.setEaten();
                    if (prey.isSick())
                        setDisease(prey.getDisease());
                    foodLevel = this.getPreyNutritionalValue(prey);
                    return l;
                }}

        } return null;}


    public void incrementHunger(){
        foodLevel--;
        if (foodLevel == 0){
            DEBUG_DEATH_CAUSE = "STARVATION";
            this.setDead(newCells);
        }
    }
    public void incrementHunger(int times){
        foodLevel-= times;
        if (foodLevel <= 0){
            this.setDead(newCells);
        }
    }
    public int getPreyNutritionalValue(Project.Cell prey){
        return foodClasses.get(prey.getClass());
    }
    public abstract int getMaxLitterSize();
    public abstract int getBreedingAge();
    public abstract int getMaxAge();
    public abstract double getBreedingProb();

    public boolean isMale() {
        return isMale;
    }

    /**
     * An animal can breed if it is female, is old enough and the chances are on its side.
     */
    public boolean canBreed()
    {
        List<Project.Location> surroundings = getField().adjacentLocations(getLocation());
        for (Project.Location location : surroundings) {
            Project.Cell possiblePartner = (Project.Cell) getField().getObjectAt(location);
            if (possiblePartner != null && possiblePartner.getClass() == this.getClass() && !((Animal) possiblePartner).isMale() && ((Animal) possiblePartner).age >= getBreedingAge())
                if (age >= getBreedingAge() && !isMale && new Random().nextDouble() <= getBreedingProb()) {
                    if (this.isSick())
                        possiblePartner.setDisease(getDisease());
                    else if (possiblePartner.isSick())
                        setDisease(possiblePartner.getDisease());
                    return true;

                }
        }

        return false;

    }
    public void incrementAge(){
        age++;

        if (age>getMaxAge()){
            DEBUG_DEATH_CAUSE += "OLD AGE";
            setDead(newCells);
        }
    }
    public void incrementAge(int times){
        age+= times;

        if (age>getMaxAge()){
            setDead(newCells);
        }
    }


}
