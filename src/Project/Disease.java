package Project;

public class Disease {
    private String name;
    private Symptom symptom;
    private int duration;

    public Disease (String name,int duration, Symptom symptom){
        this.name = name;
        this.symptom = symptom;
        this.duration = duration;
    }

    public Symptom getSymptom() {
        return symptom;
    }
    public void transfer(Cell newVictim){
        newVictim.setDisease(this);
    }

    public int getDuration() {
        return duration;
    }
    public void decreaseDuration(){
        duration--;
    }
}
