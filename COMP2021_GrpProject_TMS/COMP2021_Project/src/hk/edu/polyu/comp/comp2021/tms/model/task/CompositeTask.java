package hk.edu.polyu.comp.comp2021.tms.model.task;

import java.util.ArrayList;

/**
 * This class represents a composite task.
 * A composite task is a task that is composed of other tasks.
 */
public class CompositeTask extends Task{

    private final ArrayList<Task> subTaskList;

    /**
     * Constructor of CompositeTask.
     * @param name name of the composite task
     * @param description description of the composite task
     */
    public CompositeTask(String name, String description){
        super(name, description);
        this.subTaskList = new ArrayList<>();
        isPrimitive = false;
    }

    /**
     * Add a subtask to the composite task.
     * @param task subtask to be added
     */
    public void addTask(Task task){subTaskList.add(task);}

    @Override
    public ArrayList<Task> getList(){return subTaskList;}

    @Override
    public String[] getNameArray(){
        String[] result = new String[subTaskList.size()];
        for(int i = 0;i< result.length;i++) result[i] = subTaskList.get(i).getName();
        return result;
    }

    /**
     * This method return the max duration of its subtasks.
     * @return the max duration of its subtasks.
     */
    @Override
    public double getDuration() {
        double duration = 0;
        for(Task t : subTaskList) {
            double subTaskDuration = t.getDuration();
            if (duration < subTaskDuration) duration = subTaskDuration;
        }
        return duration;
    }

    @Override
    public String toString(){
        StringBuilder strB = new StringBuilder();
        strB.append("\nComposite Task: ");
        strB.append(super.toString());
        strB.append("\nSubtasks: ");
        if(subTaskList.isEmpty()) strB.append("No Subtasks.");
        for(Task t : subTaskList){
            strB.append(t.getName()).append(",");
        }
        if(strB.charAt(strB.length()-1) == ',') strB.delete(strB.length()-1,strB.length());
        return strB.toString();
    }
}
