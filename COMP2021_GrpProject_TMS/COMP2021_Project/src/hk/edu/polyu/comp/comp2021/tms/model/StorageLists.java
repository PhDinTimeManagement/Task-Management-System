package hk.edu.polyu.comp.comp2021.tms.model;

import hk.edu.polyu.comp.comp2021.tms.model.task.*;
import hk.edu.polyu.comp.comp2021.tms.model.criterion.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the storage lists.
 * The storage lists consist of the task list and the criterion list.
 */
public
class StorageLists implements Serializable{

    private final ArrayList<Task> taskList;
    private final ArrayList<Criterion> criterionList;

    /**
     * Constructor of Storage Lists.
     */
    public StorageLists(){
        taskList = new ArrayList<>();
        criterionList = new ArrayList<>();
        defineBasicCriterion("IsPrimitive",Property.IS_PRIMITIVE,Operand.IS_PRIMITIVE,
                new String[]{"Only for checking whether a task is primitive or not."});
    }

    @Override
    public String toString(){
        return taskListString() + criterionListString();
    }

    /* ********************************************************* */
    /* Task List Operations */

    /**
     * Get the string representation of the task list.
     * @return string representation of the task list
     */
    public ArrayList<Task> getTaskList(){ return taskList; }

    /**
     * Get the string representation of the task list.
     * @return string representation of the task list
     */
    public ArrayList<Criterion> getCriterionList() { return criterionList; }

    /**
     * Search a task in the list by task name.
     * Return null if the task is not found.
     * @param name task name
     * @return task
     */
    public Task searchTaskList(String name){
        for(Task t : taskList) if(t.getName().equals(name)) return t;
        return null;
    }

    /**
     * Create new Primitive Task
     * @param name task name
     * @param description task description
     * @param duration task duration
     * @param prerequisites task prerequisites
     */
    public void createNewPrimitiveTask(String name, String description,
                                       double duration, ArrayList<Task> prerequisites) {
        PrimitiveTask newPrimitiveTask = new PrimitiveTask(name, description,duration);
        setPrerequisites(newPrimitiveTask,prerequisites);
        taskList.add(newPrimitiveTask);
    }

    /**
     * Create new Composite Task
     * @param name task name
     * @param description task description
     * @param subtaskList task subtask list
     */
    public void createNewCompositeTask(String name, String description,
                                       ArrayList<Task> subtaskList) {
        CompositeTask newCompositeTask = new CompositeTask(name, description);
        setSubTaskList(newCompositeTask, subtaskList);
        taskList.add(newCompositeTask);
    }

    /**
     * Check if the prerequisite tasks of a primitive task exists in the task list
     * @param task primitive task
     * @param prerequisites prerequisite tasks
     */
    public void setPrerequisites(PrimitiveTask task, ArrayList<Task> prerequisites) {
        if(prerequisites==null|| prerequisites.isEmpty()) return;
        for (Task t : prerequisites){
            if(t.isContained(task)) continue;
            task.addPrerequisites(t);
        }
    }

    /**
     * Check if the tasks in subTask List exists in the task list
     * @param task composite task
     * @param subTaskList subtask list
     */
    public void setSubTaskList(CompositeTask task, ArrayList<Task> subTaskList) {
        if(subTaskList==null|| subTaskList.isEmpty()) return;
        for (Task t : subTaskList){
            if(t.isContained(task)) continue;
            task.addTask(t);
        }
    }

    /**
     * Delete a task from the task list
     * @param task task to be deleted
     */
    public void deleteTask(Task task){
        for(Task t : taskList) if(t instanceof CompositeTask) t.getList().remove(task);
        taskList.remove(task);
    }

    /**
     * Print the task list
     * @return task list string
     */
    public String taskListString(){
        StringBuilder strB = new StringBuilder();
        for(Task t : this.taskList){
            strB.append(t.toString()).append("\n");
        }
        return strB.toString();
    }

    /* ************************************************************* */
    /* Criterion List operations*/

    /**
     * Search a specific Criterion by name
     * @param name criterion name
     * @return criterion
     */
    public Criterion searchCriterionList(String name){
        for(Criterion c : criterionList) if(c.getName().equals(name)) return c;
        return null;
    }

    /**
     * Define a basic Criterion
     * @param name criterion name
     * @param property property
     * @param operand operand
     * @param value value
     */
    public void defineBasicCriterion(String name, Property property, Operand operand, String[] value){
        BasicCriterion newBasicCriterion = new BasicCriterion(name, property, operand, value);
        criterionList.add(newBasicCriterion);
    }

    /**
     * Define a negated criterion
     * @param name criterion name
     * @param criterion criterion
     */
    public void defineNegatedCriterion(String name, Criterion criterion){
        NegatedCriterion newNegatedCriterion = new NegatedCriterion(name, criterion);
        criterionList.add(newNegatedCriterion);
    }

    /**
     * Define a binary criterion
     * @param name criterion name
     * @param criterion1 criterion 1
     * @param logicOp logic operator
     * @param criterion2 criterion 2
     */

    public void defineBinaryCriterion(String name, Criterion criterion1, LogicOp logicOp, Criterion criterion2){
        BinaryCriterion newBinaryCriterion = new BinaryCriterion(name, criterion1, logicOp, criterion2);
        criterionList.add(newBinaryCriterion);
    }

    /**
     * Search through the Task List based on a criterion.
     * Return a list of tasks that meet the criterion.
     * @param criterion criterion
     * @return list of tasks
     */

    public ArrayList<Task> search(Criterion criterion){
        ArrayList<Task> result = new ArrayList<>();
        for(Task t : taskList){
            if(criterion.check(t)) result.add(t);
        }
        return result;
    }

    /**
     * Print the criterion list
     * @return criterion list string
     */
    public String criterionListString(){
        StringBuilder strB = new StringBuilder();
        for(Criterion c : this.criterionList){
            strB.append(c.toString()).append("\n");
        }
        return strB.toString();
    }
}