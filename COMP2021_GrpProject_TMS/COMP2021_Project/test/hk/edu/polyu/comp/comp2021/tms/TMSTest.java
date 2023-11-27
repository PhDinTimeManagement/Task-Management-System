package hk.edu.polyu.comp.comp2021.tms;

import hk.edu.polyu.comp.comp2021.tms.view.TMS;
import org.junit.Before;
import org.junit.Test;

/**
 * This class is for testing the TMS class.
 */
public class TMSTest {

    private static final String[] taskCommands = {
            "asdoufihaoushe",
            "createsimpletask weriuu",
            "createcompositetask 123 49",
            "createsimpletask t1 t1 4 ,",
            "createsimpletask t2 t2 3 t1",
            "createsimpletask t3 t3 6 t2",
            "createsimpletask t4 t4 -34 ,",
            "changetask t1 prerequisites t3",
            "createcompositetask t4 t4 t1,t2,t3",
            "changetask t4 subtasks t1,t2",
            "createsimpletask t5 t5 10 t2,t4",
            "deletetask t1",
            "deletetask t4",
            "reportearliestfinishtime t5",
            "deletetask t5",
            "changetask t1 name t0",
            "printtask t5",
            "printtask t2",
            "printalltasks",
            "changetask t0 description t0",
            "changetask t0 duration 12",
            "reportduration t4",
            "changetask t3 prerequisites t0",
            "store ../data.bin"
    };

    private static final String[] criterionCommand = {
            "load ../data.bin",
            "definecriterion 1",
            "definebasiccriterion c1 name contains t",
            "definebasiccriterion c1 name contains \"t\"",
            "printcriterion c1",
            "definebasiccriterion c2 duration > 5",
            "definebasiccriterion c3 duration >= 5",
            "definebasiccriterion c4 duration < 3",
            "definebasiccriterion c5 description contains \"t\"",
            "definebasiccriterion c6 prerequisites contains t1",
            "definebasiccriterion c7 subtasks contains t2",
            "search c1",
            "search c2",
            "search c6",
            "search c7",
            "printallcriteria",
            "definebinarycriterion c10 c1 && c2",
            "definebinarycriterion c11 c3 || c4",
            "definenegatedcriterion c12 c5",
            "definebinarycriterion c13 c9 && c10",
            "definebinarycriterion c13 c10 && c11",
            "search c10",
            "search c11",
            "search c12",
            "search IsPrimitive",
            "printallcriteria",
            "quit"
    };

    private static final String[] fileCommand = {
            "load ../data.bin",
            "load ../data.binefhouhiafebjb",
            "load data.bin",
            "store ../data.bin"
    };

    private static final String[] undoRedoCommand = {
            "createsimpletask t1 t1 4 ,",
            "createsimpletask t2 t2 3 t1",
            "undo",
            "printalltasks",
            "redo",
            "printalltasks",
            "definebasiccriterion c1 name contains \"t\"",
            "definebasiccriterion c2 duration > 5",
            "undo",
            "printallcriteria",
            "redo",
            "printallcriteria",
            "undo",
            "definebasiccriterion c3 duration >= 5",
            "redo",
            "undo",
    };

    private TMS tms;

    /**
     * Initialize the TMS before each test.
     */
    @Before
    public void init(){
        tms = new TMS();
    }

    /**
     * Test all the commands.
     */
    @Test
    public void testAll(){
        testTask();
        testCriterion();
        testFile();
        testUndoRedo();
    }

    /**
     * Test the task commands.
     */
    @Test
    public void testTask(){
        tms.testRun(taskCommands);
    }

    /**
     * Test the criterion commands.
     */
    @Test
    public void testCriterion(){
        tms.testRun(criterionCommand);
    }

    /**
     * Test the file commands.
     */
    @Test
    public void testFile(){
        tms.testRun(fileCommand);
    }

    /**
     * Test the undo and redo commands.
     */
    @Test
    public void testUndoRedo(){
        tms.testRun(undoRedoCommand);
    }
}