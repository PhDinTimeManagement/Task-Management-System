package hk.edu.polyu.comp.comp2021.tms.model.task;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a task.
 * A task is a unit of work to be performed.
 */
public abstract class Task implements Serializable {

    /**
     * Name of the task.
     */
    protected String name;

    /**
     * Description of the task.
     */
    protected String description;

    /**
     * This is a flag to indicate whether this task is a primitive task.
     */
    protected boolean isPrimitive;

    /**
     * Constructor of Task.
     * @param name name of the task
     * @param description description of the task
     */
    public Task(String name, String description){
        this.name = name;
        this.description = description;
    }

    /**
     * Get the duration of this task and all of its prerequisites for PrimitiveTask.
     * Get the duration of the tasklist for CompositeTask.
     * @return duration
     */
    abstract public double getDuration();

    /**
     * Check if this task is contained in the prerequisite or subtasks list of the input task.
     * For example, t1 contains t2, t3 and t4 as its subtask or prerequisite. Then t2.isContained(t1) is true.
     * @param task input task
     * @return true if this task is contained in the input task
     */
    public boolean isContained(Task task){
        for (Task t : task.getList()) {
            if (t.getName().equals(this.name)) return true;
        }
        return false;
    }

    /**
     * Check if this task is contained in the family tree of the input task.
     * For example, t1 contains t2 and t3, t4 contains t1. Then t2 is part of t4.
     * @param task input task
     * @return true if this task is part of the input task
     */
    public boolean isPartOf(Task task){
        if(task.getList().isEmpty()) return false;
        for(Task t : task.getList()) if( this.name.equals(t.getName()) || this.isPartOf(t) ) return true;
        return false;
    }

    /**
     * Return Prerequisites List for primitive task.
     * Return SubTaskList for composite task.
     * @return ArrayList of tasks
     */
    abstract public ArrayList<Task> getList();

    /**
     * Return Prerequisites List for primitive task.
     * Return SubTaskList for composite task.
     * @return String array of task names
     */
    abstract public String[] getNameArray();

    /**
     * Return the name of the task.
     * @return name of the task
     */
    public String getName(){
        return name;
    }

    /**
     * Return the description of the task.
     * @return description of the task
     */
    public String getDescription(){
        return description;
    }

    /**
     * Set the name of the task.
     * @param name name of the task
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Set the description of the task.
     * @param description description of the task
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Check if this task is primitive.
     * @return true if this task is primitive
     */
    public boolean isPrimitive(){ return isPrimitive; }

    @Override
    public String toString(){
        return "\nName: " + this.name +"\nDescription: " + this.description;
    }

}
