import Project.Simulator;

import java.util.ArrayList;
import java.util.Random;

public class Genotype {
    private static int totalNumber = 1;
    private int id;
    Object [] valuesRabbit;
    Object [] valuesFox;
    Object [] valuesTiger;
    Object [] valuesDeer;
    Object [] valuesGrass;
    Object [] valuesMouse;
    Object [] valuesVulture;
    Object [] valuesSim;

    ArrayList<Object[]> firstHalf;
    ArrayList<Object[]> secondHalf;
    int fitness;
    public Genotype () throws Exception {
        fitness = 0;
        id = totalNumber++;
        this.firstHalf = new ArrayList<>();
        this.secondHalf = new ArrayList<>();
        Random rand = new Random();

        double rabbitBreeding = rand.nextDouble();
        //                          BREEDING                    MAX AGE                 SPRING BREED    BREED               LITTER SIZE             PLANT FV
        valuesRabbit = new Object[]{rand.nextInt(200)+1,rand.nextInt(300)+1,rabbitBreeding,rabbitBreeding/2,rand.nextInt(8)+1,rand.nextInt(30)};
        //                          BREEDING                    MAX AGE                BREEDING PROB        LITTER SIZE             RABBIT FV               MOUSE FV                ATTACK PROB  FIGHT SCORE
        valuesFox = new Object[]{rand.nextInt(200)+1,rand.nextInt(300)+1,rand.nextDouble(),rand.nextInt(8)+1,rand.nextInt(70)+1,rand.nextInt(40)+1,rand.nextDouble(),rand.nextInt(3)+1};
        //                          BREEDING                   MAX AGE                  BREEDING PROB       LITTER SIZE            RABBIT FV                DEER FV                 ATTACK PROB    FIGHT SCORE
        valuesTiger = new Object[]{rand.nextInt(200)+1,rand.nextInt(300)+1,rand.nextDouble(),rand.nextInt(8)+1,rand.nextInt(50)+1,rand.nextInt(70)+1,rand.nextDouble(),rand.nextInt(10)+1};
        //                              BREEDING                MAX AGE                 BREEDING PROB       LITTER SIZE             PLANT FV
        valuesDeer = new Object[]{rand.nextInt(200)+1,rand.nextInt(300)+1,rand.nextDouble(),rand.nextInt(8)+1,rand.nextInt(20)+1};
        //                              LITTER SIZE        POLLINATION PROB     RANDOM SPREAD       MAX AGE
        valuesGrass = new Object[]{rand.nextInt(8)+1,rand.nextDouble(),rand.nextDouble()+0.01, rand.nextInt(300)+1};

        //                            BREEDING                    MAX AGE                BREEDING PROB     NOT SUMMER PROB                  LITTER SIZE             DEAD CELL FV
        rabbitBreeding =rand.nextDouble();
        valuesMouse = new Object[]{rand.nextInt(200)+1,rand.nextInt(300)+1,rabbitBreeding,rabbitBreeding/2,rand.nextInt(8)+1,rand.nextInt(30)+1};
        //                              BREEDING                   MAX AGE                  BREEDING PROB       LITTER SIZE            DEAD CELL FV              ATTACK PROB    FIGHT SCORE

        valuesVulture = new Object[]{rand.nextInt(200)+1,rand.nextInt(300)+1,rand.nextDouble(),rand.nextInt(8)+1,rand.nextInt(50)+1,rand.nextDouble(),rand.nextInt(6)+1};
        //                         FOX CREATION      RABBIT CREATION   TIGER CREATION  DEER CREATION      MOUSE CREATION    GRASS CREATION   VULT. CREATION
        valuesSim = new Object[]{rand.nextDouble(),rand.nextDouble(),rand.nextDouble(),rand.nextDouble(),rand.nextDouble(),rand.nextDouble(),rand.nextDouble()};
        setHalves();
        run();
    }
    public Genotype (ArrayList<Object[]> firstHalf, ArrayList<Object[]> secondHalf) throws Exception {
        this.firstHalf = new ArrayList<>();
        this.secondHalf = new ArrayList<>();
        id = totalNumber++;
        fitness = 0;
        //GENOTYPE A
        valuesRabbit = firstHalf.get(1);
        valuesFox = firstHalf.get(0);
        valuesMouse = firstHalf.get(2);

        valuesDeer = firstHalf.get(3);
        //GENOTYPE B
        valuesGrass = secondHalf.get(1);
        valuesVulture = secondHalf.get(2);
        valuesTiger = secondHalf.get(0);
        valuesSim = secondHalf.get(3);

        setHalves();
        run();

    }
    private void setHalves(){
        //ADD TO HALVES
        firstHalf.add(valuesFox);
        firstHalf.add(valuesRabbit);
        firstHalf.add(valuesMouse);
        firstHalf.add(valuesDeer);
        secondHalf.add(valuesTiger);
        secondHalf.add(valuesGrass);
        secondHalf.add(valuesVulture);
        secondHalf.add(valuesSim);
    }
    private void run() throws Exception {
        Simulator sim = new Simulator(firstHalf,secondHalf);
        sim.reset();
       fitness = sim.simulate(4000);

    }

    public int getFitness() {
        return fitness;
    }

    public ArrayList<Object[]> getFirstHalf() {
        return firstHalf;
    }

    public ArrayList<Object[]> getSecondHalf() {
        return secondHalf;
    }

    @Override
    public String toString() {
        return  "ID: "+id+" Fitness: "+fitness;
    }
    public String getFullInfo(){
        String data = "ID: "+id+" Fitness: "+fitness+"\n";
        for (Object o: firstHalf){
            for (Object p: (Object[])o)
                data+=p+"\n";
        }for (Object o: secondHalf){
            for (Object p: (Object[])o)
                data+=p+"\n";
        }
        data +="\n\n";
        return data;
    }
    public ArrayList<Genotype> newGenes(Genotype parent) throws Exception {
        ArrayList<Genotype> genotypes = new ArrayList<>();
        genotypes.add(new Genotype(firstHalf,assignRandom(secondHalf)));
        genotypes.add(new Genotype(assignRandom(firstHalf),secondHalf));
        genotypes.add(new Genotype(assignRandom(firstHalf),assignRandom(secondHalf)));
        return genotypes;

    }
    private ArrayList<Object[]> assignRandom( ArrayList<Object[]> genesArray){
        ArrayList<Object[]> half = new ArrayList<>();

            for (int i = 0; i<genesArray.size();i++) {
                Object[] objects = genesArray.get(i);
                Object[] newGenes = new Object[objects.length];
                for (int j = 0; j < objects.length; j++) {
                    if (objects[j].getClass() == Integer.class){
                            newGenes[j] = new Random().nextInt((int) objects[j]+1);

                    }
                    else if (objects[j].getClass() == Double.class)
                        newGenes[j] = new Random().nextDouble()*(double)objects[j];

                }

                half.add(newGenes);
            }

        return half;
    }


}
