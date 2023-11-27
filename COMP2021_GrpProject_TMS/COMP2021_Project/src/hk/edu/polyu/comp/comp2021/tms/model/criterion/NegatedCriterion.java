package hk.edu.polyu.comp.comp2021.tms.model.criterion;

import hk.edu.polyu.comp.comp2021.tms.model.task.Task;

/**
 * This class represents a negated criterion.
 * A negated criterion is a criterion that is composed of another criterion and a negated logic operand.
 */
public class NegatedCriterion extends Criterion{

    private final Criterion criterion;
    private final LogicOp logicOp = LogicOp.Negation;

    /**
     * Constructor of Negated Criterion.
     * @param name name of the criterion
     * @param criterion criterion
     */
    public NegatedCriterion(String name, Criterion criterion){
        super(name);
        this.criterion = criterion;
    }

    public String toString(){
        return "\nNegated Criterion: " +
                super.toString() +
                "\nNegated: " + criterion.getName();
    }

    /**
     * Check if the task satisfies the criterion.
     * @param task task to be checked
     * @return true if the task satisfies the criterion
     */
    @Override
    public boolean check(Task task){
        return getLogicOp().evaluate(criterion.check(task),false);
    }

    /**
     * Get the logic operand of the criterion.
     * @return logic operand of the criterion
     */
    public LogicOp getLogicOp() {
        return logicOp;
    }
}