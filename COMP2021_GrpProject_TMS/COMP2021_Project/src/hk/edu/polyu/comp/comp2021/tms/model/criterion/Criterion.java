package hk.edu.polyu.comp.comp2021.tms.model.criterion;

import hk.edu.polyu.comp.comp2021.tms.model.task.Task;

import java.io.Serializable;

/**
 * This class represents a criterion.
 * A criterion is a condition that a task must satisfy.
 */
public abstract class Criterion implements Serializable {

    /**
     * Name of the criterion.
     */
    protected final String name;

    /**
     * Constructor of Criterion.
     * @param name name of the criterion
     */
    public Criterion(String name){
        this.name = name;
    }

    /**
     * Get the name of the criterion.
     * @return name of the criterion
     */
    public String getName(){return name;}

    @Override
    public String toString(){ return "\nName: " + this.name; }

    /**
     * Check if the task satisfies the criterion.
     * @param task task to be checked
     * @return true if the task satisfies the criterion
     */
    public abstract boolean check(Task task);

}