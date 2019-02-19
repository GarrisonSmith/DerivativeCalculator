package DerivativeCalculator;

public class FunctionFormater {

    /**
     * Formats the function down to the pieces need for the calculator.
     *
     * @param function the function to be formatted.
     * @param key      the key to be used when condensing the function.
     * @return the function now formatted and condensed.
     */
    public static String condense(String function, FunctionKeyList key) {
        function = function.replaceAll(" ", "");
        function = function.replaceAll("\\|", "");
        function = function.replaceAll("X", "x");
        function = preOperationsFlag(function);
        function = parenthesisCollapser(function, key);

        return function;
    }

    /**
     * Formats the function after having everything done.
     *
     * @param function the function to cleaned up.
     * @return the function after being cleaned up.
     */
    public static String cleanUp(String function) {
        function = function.replaceAll("\\+\\+", "+");
        function = function.replaceAll("--", "");
        function = function.replaceAll("-\\+", "-");
        function = function.replaceAll("\\+-", "-");

        return function;
    }

    /**
     * Splits the function into parts around the +'s and -'s, while still keeping them
     * in their respective parts.
     *
     * @param function the function to be split into parts.
     * @return the function split into parts.
     * @throws IllegalArgumentException if no parts can be derived from the function.
     */
    public static String[] partsSplitter(String function) {
        String[] foo;

        if (function.contains("+") || function.contains("-")) {
            function = function.replace("+", " +");
            function = function.replace("|-", " |-");

            if (function.charAt(0) == ' ') {
                function = function.substring(1);
            }

            foo = function.split(" ");

            return foo;
        }
        throw new IllegalArgumentException("Function has no parts: " + function);
    }

    /**
     * Condenses the function down by adding in parenthesis wherever possible without ruining the order of operations.
     *
     * @param function the function to be condensed.
     * @param key      the key you want the collapsed parenthesis indexed into.
     * @return the function now condensed down as much as possible.
     */
    public static String parenthesisGrouper(String function, FunctionKeyList key) {
        int current, end = 1;
        boolean pass = false;

        if (function.contains("^")) {
            int start = current = end = function.lastIndexOf('^');

            for (; end < function.length(); end++) {
                if ((function.charAt(end) == '/' || function.charAt(end) == '*' || function.charAt(end) == '^') && end != current)
                    break;
            }
            for (; start >= 0; start--) {
                if ((function.charAt(start) == '/' || function.charAt(start) == '*' || function.charAt(start) == '^') && start != current)
                    break;
            }

            if (function.charAt(end - 1) == '|') {
                end--;
            }

            function = function.substring(0, start + 1) + key.addFunction(function.substring(start + 1, end)) + function.substring(end);

            return parenthesisGrouper(function, key);
        }


        for (; end < function.length(); end++) {
            if (function.charAt(end) == '/' || function.charAt(end) == '*') {
                if (pass) {

                    if (function.charAt(end - 1) == '|') {
                        end--;
                    }

                    function = key.addFunction(function.substring(0, end)) + function.substring(end);

                    return parenthesisGrouper(function, key);
                }
                pass = true;
            }
        }

        return function;
    }

    /**
     * Collapses all parenthesis into function keys and indexes the keys with their
     * corresponding functions.
     *
     * @param function the function you want to collapse the parenthesis within.
     * @param key      the key you want to collapsed parenthesis to be indexed into.
     * @return the function with all parenthesis replaced with function keys.
     */
    private static String parenthesisCollapser(String function, FunctionKeyList key) {
        int start = 0, end = 0;

        if (function.contains("("))
            for (int x = 0; x < function.length(); x++) {
                switch (function.charAt(x)) {
                    case '(':
                        start = x;

                        break;

                    case ')':
                        end = x;
                }

                if (end != 0)
                    return parenthesisCollapser(function.substring(0, start)
                            + key.addFunction(parenthesisGrouper(function.substring(start + 1, end), key), true)
                            + function.substring(end + 1), key);
            }

        return function;
    }

    /**
     * Replaces all keys with the functions they represent.
     *
     * @param function the function to be expanded out.
     * @param key      the key to be referenced for the keys and their functions.
     * @return the input function will keys replaced with the functions they represent.
     */
    public static String functionKeyExpander(String function, FunctionKeyList key) {
        int start = -1, end = 0;

        if (function.contains("_"))
            for (int x = 0; x < function.length(); x++) {
                if (function.charAt(x) == '_' && start == -1) {
                    start = x;
                } else if (function.charAt(x) == '_') {
                    end = x;
                }

                if (end != 0)
                    return functionKeyExpander(function.substring(0, start)
                            + key.getNode(function.substring(start, end+1)).getData(1)
                            + function.substring(end+1), key);
            }

        return function;
    }

    /**
     * Replaces all keys with the derivatives they represent.
     * @param function the function to be expanded out.
     * @param key the key to be referenced for the keys and their derivatives.
     * @return the input function will keys replaced with the derivatives they represent.
     */
    public static String derivativeKeyExpander(String function, FunctionKeyList key){
        int start=-1, end=0;

        if(function.contains("_"))
            for(int x=0; x<function.length(); x++){
                if(function.charAt(x) == '_' && start == -1){
                    start=x;
                }
                else if(function.charAt(x) == '_'){
                    end=x;
                }

                if(end!=0)
                    return derivativeKeyExpander(function.substring(0, start)
                            + key.getNode(function.substring(start, end+1)).getData(3)
                            + function.substring(end+1), key);
            }

        return function;
    }

    /**
     * Adds a '|' in front of each '^', '*', '/' within the function.
     * Used to signify that an operative has not been attempted to evaluate.
     *
     * @param function the function to have '|'s added in where appropriate.
     * @return the function with '|'s added in.
     */
    public static String preOperationsFlag(String function) {

        //Chunk used to define the difference between subtraction and a negative.
        //Subtraction Here
        function = function.replaceAll("-", "- ");
        function = function.replace(")- ", ")|-");
        function = function.replace("0- ", "0|-");
        function = function.replace("1- ", "1|-");
        function = function.replace("2- ", "2|-");
        function = function.replace("3- ", "3|-");
        function = function.replace("4- ", "4|-");
        function = function.replace("5- ", "5|-");
        function = function.replace("6- ", "6|-");
        function = function.replace("7- ", "7|-");
        function = function.replace("8- ", "8|-");
        function = function.replace("9- ", "9|-");
        function = function.replaceAll(" ", "");
        //Every - without a | is a negative.

        function = function.replaceAll("\\^", "|^");
        function = function.replaceAll("\\*", "|*");
        function = function.replaceAll("/", "|/");
        function = function.replaceAll("\\+", "|+");

        return function;
    }

    /**
     * Moves any '|'s from in front of each '^', '*', '/' to behind each one within the function.
     * Used to signify that an operative has been attempted to evaluate.
     *
     * @param function the function with the '|'s moved to the front.
     * @return the function with '|'s moved behind each '^', '*', '/'.
     */
    public static String postOperationsFlag(String function) {

        function = function.replaceAll("\\|\\^", "^|");
        function = function.replaceAll("\\|\\*", "*|");
        function = function.replaceAll("\\|/", "/|");
        function = function.replaceAll("\\|\\+", "+|");
        function = function.replaceAll("\\|-", "-|");

        return function;
    }

}