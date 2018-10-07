package Project;

import java.util.List;

/**
 * Created by k1763934 on 16/02/18.
 */
public class DeadCell extends Project.Cell {

    //private Project.Disease[] diseases;
    private int length;
    public DeadCell(Project.Field field, Project.Location location, Project.CalendarAndWeather time){
        super(field,location,time);
        length = 15;

    }

    public void act(List<Project.Cell>newCells) throws Exception{
       // nutritionalValue--;
        length --;
        if (length == 0)
            this.setEaten();
       /** if (nutritionalValue == 0)
            this.setEaten(); */
    }



}
