package DerivativeCalculator;

public class FunctionSimplifier {

    /**
     *
     * @param key
     * @return
     */
    public static void simplify(FunctionKey key){

        for(int x=0; x < key.length(); x++){
              key.setFunction(simplify(key.getElement(x)[1], key), x);
        }

    }

    /**
     *
     * @param function
     * @return
     */
    public static String simplify(String function, FunctionKey key){
        String[] pieces;
        try {
            pieces = getLeftAndRight(function);
        }
        catch(IllegalArgumentException e){
            return function;
        }

        Type type = getType(function);
        if(type == Type.NONE){
            return function;
        }

        if(key.containsKey(pieces[0]) && key.containsKey(pieces[1])){ //left and right is a key for a function: Case 1
            switch(getType(function)){
                case MULT: return FunctionFormater.postOperationsFlag(function);
                case DIVI: return FunctionFormater.postOperationsFlag(function);
                case EXPO: return FunctionFormater.postOperationsFlag(function);
                case ADD: return FunctionFormater.postOperationsFlag(function);
                case SUB: return FunctionFormater.postOperationsFlag(function);
            }
        }
        else if(key.containsKey(pieces[0])){ //left is a key for a function: Case 2
            switch(getType(function)){
                case MULT: return FunctionFormater.postOperationsFlag(function);
                case DIVI: return FunctionFormater.postOperationsFlag(function);
                case EXPO: return FunctionFormater.postOperationsFlag(function);
                case ADD: return FunctionFormater.postOperationsFlag(function);
                case SUB: return FunctionFormater.postOperationsFlag(function);
            }
        }
        else if(key.containsKey(pieces[1])){ //right is a key for a function: Case 3
            switch(getType(function)){
                case MULT: return FunctionFormater.postOperationsFlag(function);
                case DIVI: return FunctionFormater.postOperationsFlag(function);
                case EXPO: return FunctionFormater.postOperationsFlag(function);
                case ADD: return FunctionFormater.postOperationsFlag(function);
                case SUB: return FunctionFormater.postOperationsFlag(function);
            }
        }
        else{ //neither left or right are keys for a function: Case 4
            switch(getType(function)){
                case MULT: return FunctionFormater.postOperationsFlag(multiplyCase4(pieces[0], pieces[1]));
                case DIVI: return FunctionFormater.postOperationsFlag(function);
                case EXPO: return FunctionFormater.postOperationsFlag(exponentCase4(pieces[0], pieces[1]));
                case ADD: return FunctionFormater.postOperationsFlag(function);
                case SUB: return FunctionFormater.postOperationsFlag(function);
            }
        }

        return function;
    }

    /**
     * Multiplies the function out if possible.
     * Used if, and only if, left and right are only numbers.
     * @param left
     * @param right
     * @return
     */
    public static String multiplyCase4(String left, String right){

        if(left.contains("x") && right.contains("x")){
            return (stringToDouble(left)*stringToDouble(right))+"x^|2";
        }
        else if(left.contains("x") || right.contains("x")){
            return (stringToDouble(left)*stringToDouble(right))+"x";
        }
        else{
            return Double.toString(stringToDouble(left)*stringToDouble(right));
        }
    }

    /**
     *
     * @param left
     * @param right
     * @return
     */
    public static String exponentCase4(String left, String right){
        if(left.contains("x") || right.contains("x")){
            return left+"^|"+right;
        }
        else {
            return Double.toString(Math.pow(stringToDouble(left), stringToDouble(right)));
        }
    }

    /**
     * Gets the degree of a variable.
     * @param function the function you want the degree of.
     * @return the degree of the function.
     * TODO functionality is kinda wonky, might need to be reworked completely.
     */
    private static String getDegree(String function){

        if(function.contains("^")){
            return function.split("\\^", 2)[1];
        }
        else{
            return "1";
        }
    }

    /**
     * Creates an array consisting of the right and left of the given function.
     * @param function the function to be split into the right and left.
     * @return an array of size 2 with the left in index 0 and the right in index 1.
     */
    private static String[] getLeftAndRight(String function){

        if(function.contains("*") || function.contains("/") || function.contains("^")) {
            return function.split("\\|\\*|\\|/|\\|\\^", 2);
        }
        else{
            throw new IllegalArgumentException("Function has no left or right");
        }
    }

    /**
     * Converts a string to a double by removing all character that are not a number.
     * @param function the function to have all non-numbers removed.
     * @return the function with the all the non-numbers removed and only the numbers not in a exponent.
     */
    private static double stringToDouble(String function){
        String toDouble = "";

        if(function.matches(".*\\d+.*")) { //checks if the function has any number in it at all
            for (char i : function.toCharArray()) {
                if (Character.isDigit(i) || i == '.' || i == 'e') {
                    toDouble += i;
                }
                else if(i == '^'){
                    break;
                }
            }
            return Double.parseDouble(toDouble);
        }
        else {
            throw new IllegalArgumentException("Only characters");
        }
    }

    /**
     * Used to determine the type of operation within a key.
     * @param function the function to have it's primary operation looked at.
     * @return the type of operation as the type enum.
     */
    public static Type getType(String function){

        if(function.contains("|*")){
            return Type.MULT;
        }
        else if(function.contains("|/")){
            return Type.DIVI;
        }
        else if(function.contains("|^")){
            return Type.EXPO;
        }
        else if(function.contains("|+")){
            return Type.ADD;
        }
        else if(function.contains("|-")){
            return Type.SUB;
        }

        return Type.NONE;
    }

    /**
     * Used to track the type of operation in each key.
     */
    enum Type{
        MULT, DIVI, EXPO, ADD, SUB, NONE;
    }

}