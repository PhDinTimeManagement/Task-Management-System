package hk.edu.polyu.comp.comp2021.tms.controller;

import hk.edu.polyu.comp.comp2021.tms.model.criterion.*;
import hk.edu.polyu.comp.comp2021.tms.model.StorageLists;
import hk.edu.polyu.comp.comp2021.tms.model.task.Task;

import java.util.ArrayList;

/**
 * This class represents the operations of criterion.
 * It is consists of static methods.
 */
public class CriterionOperation {

    /**
     * Req 9
     * @param storageList the storageList
     * @param name the name of the criterion
     * @param property the property of the criterion
     * @param operand the operand of the criterion
     * @param value the value of the criterion
     * @throws Exception from the check methods
     * @return the message
     */
    public static String defineBasicCriterion(StorageLists storageList, String name,
                                              String property, String operand, String[] value) throws Exception{
        CheckAvailability.checkName(name);
        CheckAvailability.checkCriterionAlreadyExists(storageList,name);
        Property pro = CheckAvailability.checkProperty(property);
        Operand op = CheckAvailability.checkOperand(operand);
        CheckAvailability.checkPropertyOperandValueMatch(pro,op,value);
        if(pro == Property.NAME || pro == Property.DESCRIPTION) removeDoubleQuote(value);

        StorageListsOperation.transaction();
        storageList.defineBasicCriterion(name,pro,op,value);
        return "Basic criterion \""+name+"\" has been defined successfully.";
    }

    private static void removeDoubleQuote(String[] input){
        input[0] = input[0].replace("\"","");
    }

    /**
     * Req 11_1
     * @param storageLists the storageLists
     * @param name the name of the criterion
     * @param nameOfCriterion the name of the criterion
     * @return the message
     * @throws Exception from the check methods
     */
    public static String defineNegatedCriterion(StorageLists storageLists, String name,
                                                String nameOfCriterion) throws Exception{
        CheckAvailability.checkName(name);
        CheckAvailability.checkCriterionAlreadyExists(storageLists,name);
        Criterion criterion = CheckAvailability.checkCriterionExists(storageLists,nameOfCriterion);

        StorageListsOperation.transaction();
        storageLists.defineNegatedCriterion(name,criterion);
        return "Negated criterion \"" + name + "\" has been defined successfully.";
    }

    /**
     * Req 11_2
     * @param storageLists the storageLists
     * @param name the name of the criterion
     * @param nameOfCriterion1 the name of the criterion
     * @param lOp the logic operator
     * @param nameOfCriterion2 the name of the criterion
     * @return the message
     * @throws Exception from the check methods
     */
    public static String defineBinaryCriterion(StorageLists storageLists, String name, String nameOfCriterion1,
                                               String lOp, String nameOfCriterion2) throws Exception{
        CheckAvailability.checkName(name);
        CheckAvailability.checkCriterionAlreadyExists(storageLists, name);
        Criterion criterion1 = CheckAvailability.checkCriterionExists(storageLists, nameOfCriterion1);
        Criterion criterion2 = CheckAvailability.checkCriterionExists(storageLists, nameOfCriterion2);
        LogicOp logicOp = CheckAvailability.checkLogicOp(lOp);

        StorageListsOperation.transaction();
        storageLists.defineBinaryCriterion(name, criterion1, logicOp, criterion2);
        return "Binary criterion \"" + name + "\" has been defined successfully.";
    }

    /**
     * Req 12
     * @param storageLists the storageLists
     * @return the message
     */

    public static String printAllCriteria(StorageLists storageLists){
        StringBuilder strB = new StringBuilder("\nStart printing all criteria...\n");
        if(storageLists.getCriterionList().isEmpty()) return "There is no criterion currently...";
        strB.append(storageLists.criterionListString());
        return strB.toString();
    }

    /**
     * This is not a requirement, but we implemented it.
     * @param storageLists the storageLists
     * @param criterionName the name of the criterion
     * @return the message
     * @throws Exception from the check methods
     */
    public static String deleteCriteria(StorageLists storageLists, String criterionName) throws Exception{
        Criterion criterion = CheckAvailability.getCriterion(storageLists,criterionName);
        if(criterion == null) throw new Exception( "Criterion does not exist");
        storageLists.getCriterionList().remove(criterion);
        return "criterion " + criterionName + " is deleted";
    }

    /**
     * Req 13
     * @param storageLists the storageLists
     * @param nameOfCriterion the name of the criterion
     * @return the message
     * @throws Exception from the check methods
     */
    public static String search(StorageLists storageLists, String nameOfCriterion) throws Exception{
        Criterion criterion = CheckAvailability.checkCriterionExists(storageLists,nameOfCriterion);
        ArrayList<Task> result = storageLists.search(criterion);
        if(result.isEmpty()) return "Cannot find the corresponding tasks.";
        StringBuilder strB = new StringBuilder();
        strB.append("These tasks meets the criterion ").append(nameOfCriterion).append(" : ");
        for(Task t : result) strB.append(t.getName()).append(",");
        if(strB.charAt(strB.length()-1) == ',') strB.delete(strB.length()-1,strB.length());
        return strB.toString();
    }

}