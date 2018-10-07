package Project;

import java.util.List;

/**
 * Created by k1763934 on 16/02/18.
 */
public abstract class Cell {
    protected boolean alive;
    protected Project.Location location;
    protected Project.Field field;
    protected List<Cell> newCells;
    protected boolean eaten = false;
    protected Project.Disease disease;
    private Project.CalendarAndWeather time;
    protected String DEBUG_DEATH_CAUSE = null;
    protected int foodLevel;
    /**
     * Remove from the field. Sets it to dead.
     */
    public Cell(Project.Field field, Project.Location location, Project.CalendarAndWeather time){
        disease = null;
        this.field = field;
        this.time = time;
        setLocation(location);

    }
    public boolean isSick(){
        return getDisease() != null;
    }


    public void setEaten(){
        alive = false;
        eaten = true;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }


    public void setDead(List<Cell> newCells){
        if (alive && newCells !=null){
        Project.Location loc = location;
        Project.Field field = this.field;
        setEaten();
        newCells.add(new Project.DeadCell(field,loc,time));

        }

    }
    public abstract void act(List<Cell> newCells) throws Exception;

    public boolean isEaten() {
        return eaten;
    }
    public void setDisease(Project.Disease disease){
        this.disease = disease;
    }

    public Project.Disease getDisease() {
        return disease;
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */

    public Project.Location getLocation()
    {
        return location;
    }
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    public void setLocation(Project.Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }


    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Project.Field getField()
    {
        return field;
    }

    protected String getDayTime(){
        return time.getDayTime();
    }
    protected Project.Season getCurrentSeason(){
        return time.getCurrentSeason();
    }

    public Project.CalendarAndWeather getTime() {
        return time;
    }
}
