package Project;

import java.util.List;
import java.util.Random;

/**
 * Created by k1763934 on 16/02/18.
 */
public interface Predator {

    default void attack(Project.Field field, Project.Location location, List<Project.Cell> newCells) {
        if (field == null)
            return;
        List<Project.Location> surroundings = field.adjacentLocations(location);
        for (Project.Location place : surroundings) {
            Project.Cell being = (Project.Cell) field.getObjectAt(place);
            if (being != null && Predator.class.isAssignableFrom(being.getClass()) && this.getClass() != being.getClass()) {
                if (((Predator) being).getFightScore() < this.getFightScore()&&new Random().nextDouble()<=getAttackProb())
                    if (((Predator)being).getFightScore()< new Random().nextInt(getFightScore()))
                        being.setDead(newCells);
                     else
                         this.setDead();
            }
        }

    }
    void setDead();
    int getFightScore();
    double getAttackProb();

}
