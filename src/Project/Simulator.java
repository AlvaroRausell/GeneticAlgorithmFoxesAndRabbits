package Project;

import javafx.stage.WindowEvent;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private  double FOX_CREATION_PROBABILITY;
    // The probability that a rabbit will be created in any given grid position.
    private  double RABBIT_CREATION_PROBABILITY;
    private  double TIGER_CREATION_PROBABILITY;
    private  double DEER_CREATION_PROBABILITY;
    private  double MOUSE_CREATION_PROBABILITY;
    private  double GRASS_CREATION_PROBABILITY;
    private  double VULTURE_CREATION_PROBABILITY;
    private boolean stop = false;
    Double[] creationProbs = {FOX_CREATION_PROBABILITY,RABBIT_CREATION_PROBABILITY
            ,TIGER_CREATION_PROBABILITY,DEER_CREATION_PROBABILITY,MOUSE_CREATION_PROBABILITY,
    GRASS_CREATION_PROBABILITY,VULTURE_CREATION_PROBABILITY};
    // List of animals in the field.
    private List<Project.Cell> animals;
    // The current state of the field.
    private Project.Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private Project.SimulatorView view;

    private Project.CalendarAndWeather time;

    private ArrayList<Object []> firstHalf;
    private ArrayList<Object[]> secondHalf;

    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator(ArrayList<Object[]> firstHalf, ArrayList<Object[]> secondHalf) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
        this.firstHalf = firstHalf;
        this.secondHalf = secondHalf;
        creationProbs = new Double[secondHalf.get(3).length];
        for (int i = 0; i< secondHalf.get(3).length;i++){
            creationProbs [i] = (double)secondHalf.get(3)[i];
        }

    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        time = new Project.CalendarAndWeather(this);
        animals = new ArrayList<>();
        field = new Project.Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new Project.SimulatorView(depth, width);
        view.setColor(Project.Rabbit.class, Color.ORANGE);
        view.setColor(Project.Fox.class, Color.BLUE);
        view.setColor(Project.DeadCell.class, Color.lightGray);
        view.setColor(Project.Tiger.class, Color.MAGENTA);
        view.setColor(Project.Deer.class,Color.BLACK);
        view.setColor(Project.Mouse.class,Color.RED);
        view.setColor(Project.Grass.class,Color.GREEN);
        view.setColor(Project.Vulture.class,Color.YELLOW);
        // Setup a valid starting point.


        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation() throws Exception {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public int simulate(int numSteps) throws Exception {
        int fitness = 0;
        for(int step = 1; step <= numSteps && view.isViable(field)&&!stop; step++) {
            simulateOneStep();
            fitness = step;

            // delay(60);   // uncomment this to run more slowly
        }
        view.setVisible(false);
        view.dispose();
        return fitness;
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.Rabbit
     */
    public void simulateOneStep() throws Exception {
        step++;

        // Provide space for newborn animals.
        List<Project.Cell> newAnimals = new ArrayList<>();
        // Let all rabbits act.
        for(Iterator<Project.Cell> it = animals.iterator(); it.hasNext(); ) {
            Project.Cell animal = it.next();
            animal.act(newAnimals);

            if( animal.isEaten()) {
                it.remove();

            }
        }
               
        // Add the newly born foxes and rabbits to the main lists.
        animals.addAll(newAnimals);
        time.update(animals);
       stop = view.showStatus(step, field,time);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        step = 0;
        animals.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field,time);
    }
    public Constructor makeConstructor(Class classType) throws NoSuchMethodException {
        if (classType != Project.Grass.class)
            return classType.getDeclaredConstructor(Project.Field.class, Project.Location.class, boolean.class, Project.CalendarAndWeather.class,Object[].class);

        return classType.getDeclaredConstructor(Project.Field.class, Project.Location.class, Project.CalendarAndWeather.class,Object[].class);
    }

    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        HashMap<Constructor,Double> constructorDoubleHashMap = new HashMap<>();
        constructorDoubleHashMap.put(makeConstructor(Project.Fox.class), (Double) creationProbs[0]);
        constructorDoubleHashMap.put(makeConstructor(Project.Rabbit.class),(Double) creationProbs[1]);
        constructorDoubleHashMap.put(makeConstructor(Project.Mouse.class),(Double) creationProbs[2]);
        constructorDoubleHashMap.put(makeConstructor(Project.Deer.class),(Double) creationProbs[3]);
        constructorDoubleHashMap.put(makeConstructor(Project.Tiger.class),(Double) creationProbs[4]);
        constructorDoubleHashMap.put(makeConstructor(Project.Grass.class),(Double) creationProbs[5]);
        constructorDoubleHashMap.put(makeConstructor(Project.Vulture.class),(Double) creationProbs[6]);



        Random rand = Project.Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Project.Location location = new Project.Location(row, col);
                double randomValue = rand.nextDouble();
                Object [] arguments;
                Constructor animal;
                double smallestProb = 1;
                for (Map.Entry e : constructorDoubleHashMap.entrySet()){
                    if (randomValue <=(double) e.getValue()&&(double)e.getValue()<smallestProb) {
                        animal = (Constructor) e.getKey();
                        smallestProb = (double)e.getValue();
                        int index =Arrays.asList(creationProbs).indexOf(e.getValue());
                        if (index<4)
                            arguments = new Object[]{field,location,false,time,firstHalf.get(index)};
                        else if (animal.equals(makeConstructor(Project.Grass.class)))
                            arguments = new Object[]{field,location,time,secondHalf.get(index-4)};
                        else
                            arguments = new Object[]{field,location,false,time,secondHalf.get(index-4)};


                            try {
                                animals.add((Project.Cell) animal.newInstance(arguments));
                            }catch (Exception error){
                                error.printStackTrace();
                                System.out.println("FOR: "+animal+"\n\n");

                            }
                    }
                }





                // else leave the location empty.
            }
        }
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }

    public int getStep() {
        return step;
    }

}
