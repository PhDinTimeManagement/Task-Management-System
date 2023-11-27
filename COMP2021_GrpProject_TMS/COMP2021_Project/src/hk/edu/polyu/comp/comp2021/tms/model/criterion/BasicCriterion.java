package hk.edu.polyu.comp.comp2021.tms.model.criterion;

import hk.edu.polyu.comp.comp2021.tms.model.task.*;

/**
 * Basic Criterion is a criterion that contains a property, an operand and a value.
 * The property can be named, description, duration, prerequisite and subtasks.
 * The operand can be greater, less, greater or equal, less or equal, not equal, contains and is primitive.
 * The value is a string array.
 * The criterion is satisfied if the property of the task satisfies the operand with the value.
 */
public class BasicCriterion extends Criterion{
    private final Property property;
    private final Operand operand;
    private final String[] value;

    /**
     * Constructor of Basic Criterion.
     * @param name name of the criterion
     * @param property property of the criterion
     * @param operand operand of the criterion
     * @param value value of the criterion
     */
    public BasicCriterion(String name, Property property, Operand operand, String[] value){
        super(name);
        this.property = property;
        this.operand = operand;
        this.value = value;
    }

    @Override
    public String toString(){
        StringBuilder strB = new StringBuilder();
        strB.append("\nBasic Criterion: ");
        strB.append(super.toString());
        strB.append("\nProperty: ").append(property.toString());
        strB.append("\nOperand: ").append(operand.toString());
        strB.append("\nValue: ");
        if(property!=Property.PREREQUISITE&&property!=Property.SUBTASKS) strB.append("\"").append(value[0]).append("\"");
        else {
            for(String str : value) strB.append(str).append(",");
        }
        return strB.toString();
    }

    /**
     * The basic checking function.
     * @param task task to be checked
     * @return true if the task satisfies the criterion
     */
    @Override
    public boolean check(Task task){
        if(this.operand==Operand.IS_PRIMITIVE) return task.isPrimitive();
        switch (property){
            case NAME -> {
                return operand.evaluate(new String[]{task.getName()} , value);
            }
            case DURATION -> {
                if(task instanceof PrimitiveTask){
                    return operand.evaluate(new String[]{String.valueOf(((PrimitiveTask) task).getThisDuration())} , value);
                }
                else return false;
            }
            case DESCRIPTION -> {
                return operand.evaluate(new String[]{task.getDescription()} , value);
            }
            case SUBTASKS -> {
                if(task instanceof CompositeTask) return operand.evaluate(task.getNameArray() , value);
                else return false;
            }
            case PREREQUISITE -> {
                if(task instanceof PrimitiveTask) return operand.evaluate(task.getNameArray() , value);
                else return false;
            }
            default -> {
                return false;
            }
        }
    }

}