package hk.edu.polyu.comp.comp2021.tms.view;

import hk.edu.polyu.comp.comp2021.tms.controller.CriterionOperation;
import hk.edu.polyu.comp.comp2021.tms.controller.FileOperation;
import hk.edu.polyu.comp.comp2021.tms.controller.StorageListsOperation;
import hk.edu.polyu.comp.comp2021.tms.controller.TaskOperation;

import java.util.Scanner;

/**
 * This class represents the Task Management System.
 * It is the main class of the program.
 */
public class TMS {
    private static final Exception INVALID_COMMAND = new Exception("Invalid input command.");
    private static final Exception INVALID_PARAMETERS = new Exception("Invalid input parameters");

    /**
     * Constructor of TMS.
     */
    public TMS() {}

    private boolean flag;

    private Scanner scanner;

    /**
     * This is the primary function that should be executed when running the TMS.
     */
    public void run() {
        flag = true;
        while (flag) {
            String[] inputStringArray = input(); //Get the input
            String output;
            //Get the output. Set output as the exception message if exception occurs.
            try {
                output = operation(inputStringArray);
            } catch (Exception e) {
                output = e.getMessage();
            }
            output(output);
        }
        scanner.close();
    }

    /**
     * This is the primary function that should be executed when testing the TMS.
     *
     * @param commandLines The command lines to be executed.
     */
    public void testRun(String[] commandLines) {
        flag = true;
        for (String command : commandLines) {
            String[] inputStringArray = command.split(" "); //Get the input
            String output;
            //Get the output. Set output as the exception message if exception occurs.
            try {
                output = operation(inputStringArray);
            } catch (Exception e) {
                output = e.getMessage();
            }
            output(output);
        }
    }

    /**
     * Taking input from users, returning a String[] split by space.
     *
     * @return String[]
     */
    private String[] input() {
        scanner = new Scanner(System.in);
        System.out.println("Input your command here: ");
        String rawInput = scanner.nextLine();
        return rawInput.split(" ");
    }

    /**
     * The primary switch-case method that decides which operation should be executed.
     * The switch-case follows the order of Reqs.
     *
     * @param inputStringArray The input String[] split by space.
     * @return The output String.
     * @throws Exception If the input command is invalid or the input parameters are invalid.
     */
    protected String operation(String[] inputStringArray) throws Exception {
        switch (inputStringArray[0].toLowerCase()) {
            case "createsimpletask" -> {
                if (inputStringArray.length != 5) throw INVALID_PARAMETERS;
                return TaskOperation.createSimpleTask(StorageListsOperation.getStorageLists(),
                        inputStringArray[1],
                        inputStringArray[2],
                        inputStringArray[3],
                        inputStringArray[4].split(","));
            }
            case "createcompositetask" -> {
                if (inputStringArray.length != 4) throw INVALID_PARAMETERS;
                return TaskOperation.createCompositeTask(StorageListsOperation.getStorageLists(),
                        inputStringArray[1],
                        inputStringArray[2],
                        inputStringArray[3].split(","));
            }
            case "deletetask" -> {
                if (inputStringArray.length != 2) throw INVALID_PARAMETERS;
                return TaskOperation.deleteTask(StorageListsOperation.getStorageLists(),
                        inputStringArray[1]);
            }
            case "changetask" -> {
                if (inputStringArray.length != 4) throw INVALID_PARAMETERS;
                return TaskOperation.setProperty(StorageListsOperation.getStorageLists(),
                        inputStringArray[1],
                        inputStringArray[2],
                        inputStringArray[3].split(","));
            }
            case "printtask" -> {
                if (inputStringArray.length != 2) throw INVALID_PARAMETERS;
                return TaskOperation.printTask(StorageListsOperation.getStorageLists(), inputStringArray[1]);
            }
            case "printalltasks" -> {
                if (inputStringArray.length != 1) throw INVALID_PARAMETERS;
                return TaskOperation.printAllTasks(StorageListsOperation.getStorageLists());
            }
            case "reportduration" -> {
                if (inputStringArray.length != 2) throw INVALID_PARAMETERS;
                return TaskOperation.reportDuration(StorageListsOperation.getStorageLists(),
                        inputStringArray[1]);
            }
            case "reportearliestfinishtime" -> {
                if (inputStringArray.length != 2) throw INVALID_PARAMETERS;
                return TaskOperation.reportEarliestFinishTime(StorageListsOperation.getStorageLists(),
                        inputStringArray[1]);
            }
            case "definebasiccriterion" -> {
                if (inputStringArray.length != 5) throw INVALID_PARAMETERS;
                return CriterionOperation.defineBasicCriterion(StorageListsOperation.getStorageLists(),
                        inputStringArray[1],
                        inputStringArray[2],
                        inputStringArray[3],
                        inputStringArray[4].split(","));
            }
            case "definenegatedcriterion" -> {
                if (inputStringArray.length != 3) throw INVALID_PARAMETERS;
                return CriterionOperation.defineNegatedCriterion(StorageListsOperation.getStorageLists(),
                        inputStringArray[1],
                        inputStringArray[2]);
            }
            case "definebinarycriterion" -> {
                if (inputStringArray.length != 5) throw INVALID_PARAMETERS;
                return CriterionOperation.defineBinaryCriterion(StorageListsOperation.getStorageLists(),
                        inputStringArray[1],
                        inputStringArray[2],
                        inputStringArray[3],
                        inputStringArray[4]);
            }
            case "printallcriteria" -> {
                if (inputStringArray.length != 1) throw INVALID_PARAMETERS;
                return CriterionOperation.printAllCriteria(StorageListsOperation.getStorageLists());
            }
            case "deletecriterion" -> {
                if (inputStringArray.length != 2) throw INVALID_PARAMETERS;
                return CriterionOperation.deleteCriteria(StorageListsOperation.getStorageLists(),
                        inputStringArray[1]);
            }
            case "search" -> {
                if (inputStringArray.length != 2) throw INVALID_PARAMETERS;
                return CriterionOperation.search(StorageListsOperation.getStorageLists(),
                        inputStringArray[1]);
            }
            case "store" -> {
                if (inputStringArray.length != 2) throw INVALID_PARAMETERS;
                return FileOperation.writeFile(StorageListsOperation.getStorageLists(),
                        inputStringArray[1]);
            }
            case "load" -> {
                if (inputStringArray.length != 2) throw INVALID_PARAMETERS;
                return FileOperation.readFile(
                        inputStringArray[1]);
            }
            case "undo" -> {
                return StorageListsOperation.undo();
            }
            case "redo" -> {
                return StorageListsOperation.redo();
            }
            case "gui" -> {
                GUI.run();
                return "Running...";
            }
            case "quit" -> {
                stop();
                return "quit";
            }
            default -> throw INVALID_COMMAND;
        }
    }

    /**
     * Simply prints out the output.
     *
     * @param output the output to be printed.
     */
    private void output(String output) {
        System.out.println(output + "\n");
    }

    /**
     * Stops the TMS.
     */
    protected void stop() {
        flag = false;
    }
}