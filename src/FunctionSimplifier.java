package DerivativeCalculator;

public class FunctionSimplifier {

    /**
     *
     * @param function
     * @return
     */
    public static String simplify(String function){

        return null;
    }

    /**
     * Multiplies the function out if possible.
     * @param function
     * @return
     */
    public static String multiply(String function){

        String[] pieces = leftAndRight(function);
        String leftDegree = getDegree(pieces[0]);
        String rightDegree = getDegree(pieces[1]);

        if(leftDegree ==  rightDegree){
            return String.valueOf(Double.parseDouble(stringToDouble(pieces[0]))*Double.parseDouble(stringToDouble(pieces[1])));
        }

        return "na";
    }

    /**
     * Gets the degree of a variable.
     * @param function the function you want the degree of.
     * @return the degree of the function.
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
    private static String[] leftAndRight(String function){

        if(function.contains("*") || function.contains("/") || function.contains("^")) {
            return function.split("\\*|\\/|\\^", 2);
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
    private static String stringToDouble(String function){

        String toDouble = "";

        if(function.matches(".*\\d+.*")) {
            for (char i : function.toCharArray()) {
                if (Character.isDigit(i) || i == '.') {
                    toDouble += i;
                }
                else if(i == '^'){
                    break;
                }
            }
            return toDouble;
        }
        else {
            throw new IllegalArgumentException("Only characters");
        }
    }

}