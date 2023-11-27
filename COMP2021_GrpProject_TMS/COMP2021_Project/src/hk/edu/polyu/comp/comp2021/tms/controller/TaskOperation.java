package hk.edu.polyu.comp.comp2021.tms.controller;

import hk.edu.polyu.comp.comp2021.tms.model.criterion.*;
import hk.edu.polyu.comp.comp2021.tms.model.StorageLists;
import hk.edu.polyu.comp.comp2021.tms.model.task.*;

import java.util.ArrayList;

/**
 * This class represents the operations of task.
 * It is consists of static methods.
 */
public class TaskOperation {
    /**
     * Req 1
     * Create new Primitive Task
     * @param storageLists the storageLists
     * @param name the name of the task
     * @param description the description of the task
     * @param duration the duration of the task
     * @param prerequisites the prerequisites of the task
     * @throws Exception from the check methods
     * @return the message
     */
    public static String createSimpleTask(StorageLists storageLists, String name, String description,
                                          String duration, String[] prerequisites) throws Exception {
        CheckAvailability.checkName(name);
        CheckAvailability.checkTaskAlreadyExists(storageLists, name);
        CheckAvailability.checkDescription(description);
        double dur = CheckAvailability.checkDuration(duration);

        StorageListsOperation.transaction();
        storageLists.createNewPrimitiveTask(name, description, dur, nameListToTaskList(storageLists,prerequisites));
        return "Simple task \""+name+"\" has been created successfully.";
    }

    /**
     * Req 2
     * Create new Composite Task
     * @param storageLists the storageLists
     * @param name the name of the task
     * @param description the description of the task
     * @param subTaskList the subTaskList of the task
     * @throws Exception from the check methods
     * @return the message
     */
    public static String createCompositeTask(StorageLists storageLists, String name, String description,
                                             String[] subTaskList) throws Exception{
        CheckAvailability.checkName(name);
        CheckAvailability.checkTaskAlreadyExists(storageLists, name);
        CheckAvailability.checkDescription(description);

        StorageListsOperation.transaction();
        storageLists.createNewCompositeTask(name, description, nameListToTaskList(storageLists,subTaskList));
        return "Composite task \""+name+"\" has been created successfully.";
    }

    /**
     * Req 3
     * Delete Task
     * @param storageLists the storageLists
     * @param name the name of the task
     * @return the message
     * @throws Exception from the check methods
     */
    public static String deleteTask(StorageLists storageLists, String name) throws Exception{
        Task task = CheckAvailability.checkTaskExists(storageLists,name);
        if(task instanceof PrimitiveTask){
            CheckAvailability.isPrerequisite(storageLists,task);
        }else{ //It is Composite task
            //check if the subtask of this composite task is the prerequisite of other task.
            CheckAvailability.isSubTasksPrerequisite(storageLists,task);
            for(Task t : task.getList()) storageLists.deleteTask(t);
        }
        StorageListsOperation.transaction();
        storageLists.deleteTask(task);
        return "Task \""+name+"\" has been deleted successfully.";
    }

    /**
     * Req 4
     * Change Property
     * @param storageLists the storageLists
     * @param name the name of the task
     * @param property the property of the task
     * @param newValue the new value of the property
     * @throws Exception from the check methods
     * @return the message
     */
    public static String setProperty(StorageLists storageLists, String name,
                                     String property, String[] newValue) throws Exception{
        Task task = CheckAvailability.checkTaskExists(storageLists, name);
        Property pro = CheckAvailability.checkProperty(property.toLowerCase());
        switch (pro) {
            case NAME -> task.setName(newValue[0]);
            case DESCRIPTION -> task.setDescription(newValue[0]);
            case DURATION -> {
                if (!task.isPrimitive()) throw new Exception("Cannot set duration for composite task.");
                PrimitiveTask primitiveTask = (PrimitiveTask) task;
                StorageListsOperation.transaction();
                primitiveTask.setDuration(CheckAvailability.checkDuration(newValue[0]));
            }
            case PREREQUISITE -> {
                if (!task.isPrimitive()) throw new Exception("Cannot set prerequisites for composite task.");
                if(newValue.length==0) break;
                ArrayList<Task> prerequisiteTask = nameListToTaskList(storageLists,newValue);
                if(prerequisiteTask.contains(task)) throw new Exception("A task cannot be the prerequisite of itself.");
                CheckAvailability.isPartOf(task,prerequisiteTask);
                StorageListsOperation.transaction();
                storageLists.setPrerequisites((PrimitiveTask) task, prerequisiteTask);
            }
            case SUBTASKS -> {
                if (task.isPrimitive()) throw new Exception("Cannot set subtasks for primitive task.");
                if(newValue.length==0) break;
                ArrayList<Task> subTask = nameListToTaskList(storageLists,newValue);
                if(subTask.contains(task)) throw new Exception("A task cannot be the subtask of itself.");
                CheckAvailability.isPartOf(task,subTask);
                StorageListsOperation.transaction();
                storageLists.setSubTaskList((CompositeTask) task, subTask);
            }
            default -> throw new Exception("Invalid Property input.");
        }
        return "Task "+name+"'s property "+ property+" has been set successfully.";
    }

    /**
     * Req 5
     * Print the information of a task.
     * @param storageLists the storageLists
     * @param name the name of the task
     * @throws Exception from the check methods
     * @return the message
     */
    public static String printTask(StorageLists storageLists, String name) throws Exception{
        Task task = CheckAvailability.checkTaskExists(storageLists, name);
        return task.toString();
    }

    /**
     * Req 6
     * Print all the tasks
     *
     * @param storageLists the storageLists
     * @return the message
     */
    public static String printAllTasks(StorageLists storageLists){
        StringBuilder strB = new StringBuilder("\nStart printing all tasks...\n");
        if(storageLists.getTaskList().isEmpty()) strB.append("There is no tasks currently...");
        strB.append(storageLists.taskListString());
        return strB.toString();
    }

    /**
     * Req 7
     * Print the duration of a composite task
     * @param storageLists the storageLists
     * @param name the name of the task
     * @return the message
     * @throws Exception from the check methods
     */
    public static String reportDuration(StorageLists storageLists, String name) throws Exception{
        Task task = CheckAvailability.checkTaskExists(storageLists,name);
        if(task.isPrimitive()) throw new Exception("Reporting Duration can only be applied to Composite Task.");
        return "The Duration of the Composite Task \""+task.getName()+"\" is " +task.getDuration()+"h.";
    }

    /**
     * Req 8
     * Print the earliest finish time of a simple task
     * @param storageLists the storageLists
     * @param name the name of the task
     * @return the message
     * @throws Exception from the check methods
     */
    public static String reportEarliestFinishTime(StorageLists storageLists, String name) throws Exception{
        Task task = CheckAvailability.checkTaskExists(storageLists,name);
        if(!task.isPrimitive())
            throw new Exception("Reporting the earliest finish time can only be applied to Simple Task.");
        return "The Earliest finish time of the Simple Task \""+task.getName()+"\" is " +task.getDuration()+"h.";
    }

    /**
     * Change a list of task name to a list of task
     * @param storageLists the storageLists
     * @param name the name of the task
     * @return the message
     * @throws Exception from the check methods
     */
    static ArrayList<Task> nameListToTaskList(StorageLists storageLists, String[] name) throws Exception{
        ArrayList<Task> taskList = new ArrayList<>();
        for(String str : name) taskList.add(CheckAvailability.checkTaskExists(storageLists, str));
        return taskList;
    }
}