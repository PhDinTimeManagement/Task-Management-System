package hk.edu.polyu.comp.comp2021.tms;
import hk.edu.polyu.comp.comp2021.tms.view.TMS;

/**
 * This is the main class of the program.
 */
public class Application {

    /**
     * The main method of the program.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        TMS tms = new TMS();
        tms.run();
    }
}