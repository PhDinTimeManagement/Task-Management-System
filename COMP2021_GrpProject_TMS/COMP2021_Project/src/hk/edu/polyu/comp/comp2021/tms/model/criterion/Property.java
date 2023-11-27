package hk.edu.polyu.comp.comp2021.tms.model.criterion;

/**
 * This enum represents a property.
 * A property is a property of a task.
 */
public enum Property {

    /**
     * Name of the task.
     */
    NAME("Name") ,

    /**
     * Description of the task.
     */
    DESCRIPTION("Description") ,

    /**
     * Duration of the task.
     */
    DURATION("Duration") ,

    /**
     * Prerequisites of the task.
     */
    PREREQUISITE("Prerequisites") ,

    /**
     * Subtasks of the task.
     */
    SUBTASKS("SubTasks") ,

    /**
     * Is the task primitive.
     */
    IS_PRIMITIVE("IsPrimitive");

    final private String name;

    Property(String name){ this.name = name;}

    public String toString(){ return this.name;}

    /**
     * Get the property by its name.
     * @param property property name
     * @return property
     */
    public static Property getProperty(String property){
        return switch (property) {
            case "name" -> NAME;
            case "description" -> DESCRIPTION;
            case "duration" -> DURATION;
            case "prerequisites" -> PREREQUISITE;
            case "subtasks" -> SUBTASKS;
            default -> null;
        };
    }
}