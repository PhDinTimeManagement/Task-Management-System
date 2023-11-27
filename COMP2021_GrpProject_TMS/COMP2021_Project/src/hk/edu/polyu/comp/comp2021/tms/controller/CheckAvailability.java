package hk.edu.polyu.comp.comp2021.tms.controller;

import hk.edu.polyu.comp.comp2021.tms.model.criterion.*;
import hk.edu.polyu.comp.comp2021.tms.model.StorageLists;
import hk.edu.polyu.comp.comp2021.tms.model.task.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class checks the availability of task operations.
 * It is consists of static methods.
 * Most of the exceptions are defined in this class as final static variables.
 */
public class CheckAvailability {

    /*Check the availability of task operation.*/
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]{0,7}$");
    private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9-]*$");

    private static final Exception ILLEGAL_NAME = new Exception("The task name is illegal.");
    private static final Exception ILLEGAL_DESCRIPTION = new Exception("The task description is illegal.");
    private static final Exception ILLEGAL_DURATION1 = new Exception ("Duration must be a number.");
    private static final Exception ILLEGAL_DURATION2 = new Exception("Duration must be positive.");
    private static final Exception TASK_NOT_EXIST = new Exception("The required task does not exist.");
    private static final Exception TASK_ALREADY_EXIST = new Exception("This task already exists.");

    private static final Exception TASK_IS_PREREQUISITE = new Exception("Task is the prerequisite of another task.");
    private static final Exception SUBTASK_IS_PREREQUISITE = new Exception("The subtask of this task is required by an other task.");
    private static final Exception IS_PART_OF = new Exception("It is illegal to create a task referencing loop.");

    /**
     * Check if the name of a task is legal.
     * @param name the name of the task
     * @throws Exception if the name is illegal
     */
    public static void checkName(String name) throws Exception{
        Matcher matcher = NAME_PATTERN.matcher(name);
        if(!matcher.matches())throw ILLEGAL_NAME;
    }

    /**
     * Check if the description of a task is legal.
     * @param description the description of the task
     * @throws Exception if the description is illegal
     */
    public static void checkDescription(String description) throws Exception{
        Matcher matcher = DESCRIPTION_PATTERN.matcher(description);
        if(!matcher.matches())throw ILLEGAL_DESCRIPTION;
    }

    /**
     * Check if the duration of a task is legal.
     * @param dur the duration of the task
     * @return the duration of the task
     * @throws Exception if the duration is illegal
     */
    public static double checkDuration(String dur) throws Exception{
        double duration;
        try{
            duration = Double.parseDouble(dur);
        }catch(NumberFormatException e){
            throw ILLEGAL_DURATION1;
        }
        if(!(duration > 0)) throw ILLEGAL_DURATION2;
        return duration;
    }

    /**
     * Check if a task name has corresponding task object in the list.
     * Return the task if it exists in the list.
     * @param storageLists the storageLists object
     * @param name the name of the task
     * @return the task object
     * @throws Exception if the task does not exist
     */
    public static Task checkTaskExists(StorageLists storageLists, String name) throws Exception{
        Task task = storageLists.searchTaskList(name);
        if(task==null) throw TASK_NOT_EXIST;
        return task;
    }

    /**
     * Check if a task is already existing in the list.
     * @param storageLists the storageLists object
     * @param name the name of the task
     * @throws Exception if the task already exists
     */
    protected static void checkTaskAlreadyExists(StorageLists storageLists, String name) throws Exception{
        Task task = storageLists.searchTaskList(name);
        if(task != null) throw TASK_ALREADY_EXIST;
    }

    /**
     * Check if the task is the prerequisite of another task
     * @param storageLists the storageLists object
     * @param task the task object
     * @throws Exception if the task is the prerequisite of another task
     */
    protected static void isPrerequisite(StorageLists storageLists, Task task) throws Exception{
        for(Task t : storageLists.getTaskList()){
            if(task.isContained(t) && t instanceof PrimitiveTask) throw TASK_IS_PREREQUISITE;
        }
    }

    /**
     * Check if the task's subtask is prerequisite of another task
     * @param storageLists the storageLists object
     * @param task the task object
     * @throws Exception if the task's subtask is prerequisite of another task
     */
    protected static void isSubTasksPrerequisite(StorageLists storageLists, Task task) throws Exception{
        for(Task task1 : task.getList()){
            for(Task task2 : storageLists.getTaskList()){
                if((!task.getList().contains(task2)) && task1.isContained(task2) && task2 instanceof PrimitiveTask)
                    throw SUBTASK_IS_PREREQUISITE;
            }
        }
    }

    /**
     * Check if a list of tasks exist in the family tree of a single task.
     * @param task the task object
     * @param taskList the list of tasks
     * @throws Exception if the task is part of the list of tasks
     */
    protected static void isPartOf(Task task, ArrayList<Task> taskList) throws Exception{
        for(Task t : taskList) if( task.isPartOf(t) ) throw IS_PART_OF;
    }

    /******************************************************/
    /*Check the availability of criterion operation.*/

    private static final Exception CRITERION_NOT_EXIST = new Exception("The required criterion does not exist.");
    private static final Exception CRITERION_ALREADY_EXIST = new Exception("This criterion already exists.");

    private static final Exception PROPERTY_OPERAND_EXCEPTION = new Exception("Property and Operand do not match.");
    private static final Exception ILLEGAL_VALUE = new Exception("Too much input for value.");
    private static final Exception DOUBLE_QUOTE = new Exception("The value must be double-quoted.");
    private static final Exception ILLEGAL_PROPERTY_INPUT = new Exception("Invalid property input.");
    private static final Exception ILLEGAL_OPERAND_INPUT = new Exception ("Invalid operand input.");
    private static final Exception ILLEGAL_LOGICAL_OPERAND = new Exception ("Invalid logical operand.");

    /**
     * Check if a criterion name has corresponding criterion object in the list.
     * @param storageLists  the storageLists object
     * @param name the name of the criterion
     * @return the criterion object
     * @throws Exception if the criterion does not exist
     */
    protected static Criterion checkCriterionExists(StorageLists storageLists, String name) throws Exception{
        Criterion criterion = storageLists.searchCriterionList(name);
        if(criterion==null) throw CRITERION_NOT_EXIST;
        return criterion;
    }

    /**
     * Check if a criterion is already existing in the list.
     * @param storageLists the storageLists object
     * @param name the name of the criterion
     * @throws Exception if the criterion already exists
     */
    protected static void checkCriterionAlreadyExists(StorageLists storageLists, String name) throws Exception{
        Criterion criterion = storageLists.searchCriterionList(name);
        if(criterion != null) throw CRITERION_ALREADY_EXIST;
    }

    /**
     * Check if a property exists.
     * Return that property if it exists.
     * @param pro the property
     * @return the property object
     * @throws Exception if the property does not exist
     */
    protected static Property checkProperty(String pro) throws Exception{
        Property property = Property.getProperty(pro.toLowerCase());
        if(property == null) throw ILLEGAL_PROPERTY_INPUT;
        return property;
    }

    /**
     * Check if an operand exists.
     * Return that operand if it exists.
     * @param op the operand
     * @return the operand object
     * @throws Exception if the operand does not exist
     */
    protected static Operand checkOperand(String op) throws Exception{
        Operand operand = Operand.getOperand(op.toLowerCase());
        if(operand == null) throw ILLEGAL_OPERAND_INPUT;
        return operand;
    }

    /**
     * Check if a logical operand exists.
     * Return that logical operand if it exists.
     * @param lOp the logical operand
     * @return the logical operand object
     * @throws Exception if the logical operand does not exist
     */
    protected static LogicOp checkLogicOp(String lOp) throws Exception{
        LogicOp logicOp = LogicOp.getLogicOp(lOp);
        if(logicOp == null) throw ILLEGAL_LOGICAL_OPERAND;
        return logicOp;
    }

    /**
     * Check if the input of basic criterion property, operand, and value are matched.
     * @param property the property
     * @param operand the operand
     * @param value the value
     * @throws Exception if the property, operand, and value are not matched
     */
    protected static void checkPropertyOperandValueMatch(Property property,
                                                         Operand operand, String[] value) throws Exception{
        switch (property){
            case NAME,DESCRIPTION:
                if(value.length>1) throw ILLEGAL_VALUE;
                if(value[0].length()<2||!value[0].startsWith("\"")||!value[0].endsWith("\""))
                    throw DOUBLE_QUOTE;
            case PREREQUISITE,SUBTASKS:
                if(operand != Operand.CONTAINS) throw PROPERTY_OPERAND_EXCEPTION;
                return;

            case DURATION:
                if(operand == Operand.CONTAINS) throw PROPERTY_OPERAND_EXCEPTION;
                CheckAvailability.checkDuration(value[0]);
                if(value.length>1) throw ILLEGAL_VALUE;
        }
    }

    /**
     * Check if the input of logical criterion property, operand, and value are matched.
     * @param storageLists the storageLists object
     * @param criterionName the name of the criterion
     * @return the criterion object
     */
    public static Criterion getCriterion (StorageLists storageLists, String criterionName) {
        for (Criterion criterion : storageLists.getCriterionList()) {
            if(criterion.getName().equals(criterionName)) return criterion;
        }
        return null;
    }
}