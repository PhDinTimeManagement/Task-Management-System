package hk.edu.polyu.comp.comp2021.tms.model.criterion;

/**
 * This enum represents an operand.
 * An operand is an operator that operates on two values.
 */
public enum Operand {

    /**
     * Greater than operator.
     */
    GREATER (">"){
        @Override
        public boolean evaluate(String[] value1, String[] value2){
            return Double.parseDouble(value1[0]) > Double.parseDouble(value2[0]);
        }
    },
    /**
     * Less than operator.
     */
    LESS ("<"){
        @Override
        public boolean evaluate(String[] value1, String[] value2){
            return Double.parseDouble(value1[0]) < Double.parseDouble(value2[0]);
        }
    },
    /**
     * Equal operator.
     */
    GREATER_OR_EQUAL (">="){
        @Override
        public boolean evaluate(String[] value1, String[] value2){
            return Double.parseDouble(value1[0]) >= Double.parseDouble(value2[0]);
        }
    },
    /**
     * Not equal operator.
     */
    LESS_OR_EQUAL ("<="){
        @Override
        public boolean evaluate(String[] value1, String[] value2){
            return Double.parseDouble(value1[0]) <= Double.parseDouble(value2[0]);
        }
    },
    /**
     * Not equal operator.
     */
    NOT_EQUAL ("!="){
        @Override
        public boolean evaluate(String[] value1, String[] value2){
            return Double.parseDouble(value1[0]) != Double.parseDouble(value2[0]);
        }
    },
    /**
     * Equal operator.
     */
    CONTAINS ("contains"){
        @Override
        /*
        Value 1 is the value of the task.
        Value 2 is the value of the criterion.
         */
        public boolean evaluate(String[] value1, String[] value2){
            if(value1.length==1&&value2.length==1) return value1[0].contains(value2[0]);
            for(String str2 : value2){
                for(String str1 : value1){
                    if(str1.contains(str2)) return true;
                }
            }
            return false;
        }
    },
    /**
     * Equal operator.
     */
    IS_PRIMITIVE ("IsPrimitive"){
        @Override
        public boolean evaluate(String[] value1, String[] value2){
            return false;
        }
    };

    private final String name;

    Operand(String name){ this.name = name;}

    public String toString() { return this.name;}

    /**
     * Get the operand by its name.
     * @param operand operand name
     * @return operand
     */
    public static Operand getOperand(String operand){
        return switch (operand) {
            case ">" -> GREATER;
            case "<" -> LESS;
            case ">=" -> GREATER_OR_EQUAL;
            case "<=" -> LESS_OR_EQUAL;
            case "!=" -> NOT_EQUAL;
            case "contains" -> CONTAINS;
            default -> null;
        };
    }

    /**
     * This method takes in two String arrays as input.
     * The first input is supposed to be the value of task.
     * The second input is supposed to be the value of criterion.
     * @param value1 First input in String array.
     * @param value2 Second input in String array.
     * @return The result of the evaluation.
     */
    public abstract boolean evaluate(String[] value1, String[] value2);
}