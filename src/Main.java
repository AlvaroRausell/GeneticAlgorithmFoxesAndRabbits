import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    private static final int OBJECTIVE = 3500;
    static ArrayList<Genotype> genotypes = new ArrayList<>();
    static int amount = 50;
    static ArrayList<Genotype> fittest = new ArrayList<>();
    private static HashMap<Integer, ArrayList<Genotype>> generations;
    private static int generation;

    private static int fittestAmount;

    public static void main(String[] args) throws Exception {
        generations = new HashMap<>();
        generation = 1;
        populate();
        generations.put(generation, genotypes);


        while (!check(fittest)) {
            if (genotypes.size() <= 2) {
                System.out.println("Repopulating: "+amount);
                amount = genotypes.size()+100;
                populate();
            }
            fittestAmount = genotypes.size() / 5;
            System.out.println("Reducing the list: "+fittestAmount);

            getFittest();
            nextGen();
            printInfo(printGeneration());

        }


    }
    private static void printInfo(String toPrint) throws IOException {
        File file = new File("Generation.txt");
        if (!file.exists())
            file.createNewFile();
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.print(toPrint);
        printWriter.close();
    }
    public static String printGeneration() {
        String data = "";
        for (Genotype g : genotypes) {
            data += g.getFullInfo();
        }
        return data;
    }

    public static void populate() throws Exception {
        for (int i = 0; i < amount; i++) {
            Genotype genotype = new Genotype();
            genotypes.add(genotype);
            check(genotypes);
            System.out.println("Progress: " + (i+1) + "/" + amount + " (" + ((i+1) * 100.0 / (amount * 1.0) + "%)"));

        }
    }

    public static void getFittest() throws IOException {

        sort(genotypes);
        fittest = new ArrayList<>(genotypes.subList(0, fittestAmount - 1));
        int i = 0;
        System.out.println("GEN "+generation+" FITTEST:");
        for (Genotype gene : fittest) {
            i++;
            System.out.println("Number: " + i + " " + gene);
        }
        printInfo(fittest.get(0).getFullInfo());

    }


    public static void nextGen() throws Exception {
        generation++;
        genotypes = new ArrayList<>();
        for (int i = 0; i < fittest.size(); i++) {
            ArrayList<Genotype> babyFittest = fittest.get(i).newGenes(fittest.get(i));
                genotypes.add(new Genotype());
                genotypes.add(new Genotype());
                genotypes.add(fittest.get(i));
            for (Genotype genotype : babyFittest) {
                genotypes.add(genotype);
            }



        }
        System.out.println("GENERATION " + generation + "  WITH: " + genotypes.size() + " GENOTYPES");
        sort(genotypes);
        for (Genotype g : genotypes)
            System.out.println(g);
        if (genotypes.equals(generations.get(generation - 1))) {
            System.out.println("COLLUSION ENDING PROGRAM");
            System.exit(0);
        }

    }
    public static boolean check (ArrayList < Genotype > genotypes) {
        for (Genotype genotype : genotypes) {
            if (genotype.getFitness() >= OBJECTIVE)
                return true;
        }
        return false;
    }
    public static void sort(ArrayList<Genotype> genotypes){
        Collections.sort(genotypes, new Comparator<Genotype>() {
            @Override
            public int compare(Genotype o1, Genotype o2) {
                if (o1.getFitness() > o2.getFitness())
                    return -1;
                if (o1.getFitness() < o2.getFitness())
                    return 1;
                return 0;
            }
        });
    }
}
