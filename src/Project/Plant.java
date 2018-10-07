package Project;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

public interface Plant {

    default void pollinate(Project.Field field, Project.Location location, Project.CalendarAndWeather time, List<Project.Cell> newPlants) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Project.Location plantBirthPlace = null;
        if (new Random().nextDouble()<=getRandomSpawnProb())
            plantBirthPlace = field.getRandomFreeLocation();
        int randomPlants = new Random().nextInt(getPollenSpread()+1);
        for (int i =0; i< randomPlants; i++){
            if (new Random().nextDouble()<= getPollinationProb())
                plantBirthPlace = field.freeAdjacentLocation(location);
        }
        if (plantBirthPlace == null)
            return;
        newPlants.add((Project.Cell) this.getClass()
                .getDeclaredConstructor(Project.Field.class, Project.Location.class, Project.CalendarAndWeather.class,Object[].class).newInstance(field,plantBirthPlace,time,getStats()));
    }
    Object[] getStats();
    double getPollinationProb();
    double getRandomSpawnProb();
    int getPollenSpread();
}
