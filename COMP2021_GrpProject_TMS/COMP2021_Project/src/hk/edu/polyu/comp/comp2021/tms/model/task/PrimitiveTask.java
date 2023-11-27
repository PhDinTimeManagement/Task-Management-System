package hk.edu.polyu.comp.comp2021.tms.model.task;

import java.util.ArrayList;

/**
 * This class represents a primitive task.
 * A primitive task is a task that cannot be decomposed further.
 */
public class PrimitiveTask extends Task {

    private final ArrayList<Task> prerequisites;
    /**
     * This duration field only denote the duration of this task itself.
     */
    private double duration;

    /**
     * Constructor of PrimitiveTask.
     * @param name name of the primitive task
     * @param description description of the primitive task
     * @param duration duration of the primitive task
     */
    public PrimitiveTask(String name, String description, double duration){
        super(name, description);
        prerequisites = new ArrayList<>();
        isPrimitive = true;
        this.duration = duration;
    }

    /**
     * Get the duration of this task itself.
     * @return duration of this task itself
     */
    public double getThisDuration() { return this.duration;}

    /**
     * Set the duration of this task itself.
     * @param duration duration of this task itself
     */
    public void setDuration(double duration) { this.duration = duration; }

    /**
     * Add a prerequisite to the primitive task.
     * @param task prerequisite to be added
     */
    public void addPrerequisites(Task task){
        prerequisites.add(task);
    }

    @Override
    public ArrayList<Task> getList(){return prerequisites;}

    @Override
    public String[] getNameArray(){
        String[] result = new String[prerequisites.size()];
        for(int i = 0;i< result.length;i++) result[i] = prerequisites.get(i).getName();
        return result;
    }

    /**
     * This method return the sum of duration of its prerequisite.
     * Notice that this is different from the field "this.duration".
     * @return the sum of duration of its prerequisite.
     */
    @Override
    public double getDuration() {
        if(prerequisites.isEmpty()) return this.duration;
        double duration = 0;
        for(Task t : prerequisites) {
            double subTaskDuration = t.getDuration();
            if (duration < subTaskDuration) duration = subTaskDuration;
        }
        return (duration + this.duration);
    }

    @Override
    public String toString(){
        StringBuilder strB = new StringBuilder();
        strB.append("\nPrimitive Task: ");
        strB.append(super.toString());
        strB.append("\nDuration: ").append(this.duration).append("h");
        strB.append("\nPrerequisites: ");
        if(prerequisites.isEmpty()) strB.append("No Prerequisites.");
        for(Task t : prerequisites){
            strB.append(t.getName()).append(",");
        }
        if(strB.charAt(strB.length()-1) == ',') strB.delete(strB.length()-1,strB.length());
        return strB.toString();
    }

}
