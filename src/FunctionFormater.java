package DerivativeCalculator;

import java.util.Arrays;

public class FunctionFormater {

    /**
     * Formats the function down to the pieces need for the calculator.
     * @param function the function to be formatted.
     * @param key the key to be used when condensing the function.
     * @return the function now formatted and condensed.
     */
    public static String condense(String function, FunctionKey key){
        function=function.replace(" ", "");
        function=parenthesisCollapser(function, key);

        return function;
    }

    /**
     * Splits the function into parts around the +'s and -'s, while still keeping them
     * in their respective parts.
     * @param function the function to be split into parts.
     * @return the function split into parts.
     * @throws IllegalArgumentException if no parts can be derived from the function.
     */
    public static String[] partsSplitter(String function){
        String c="", temp[];

        if(function.charAt(0)=='+' || function.charAt(0)=='-') {
            c+=function.charAt(0);
            function = function.substring(1);
        }

        if(function.contains("+") || function.contains("-")) {
            function = function.replace("+", " +");
            function = function.replace("-", " -");
            temp=function.split(" ");
            temp[0]=c+temp[0];
            if(temp[0].charAt(0) != '+' && temp[0].charAt(0) != '-')
                temp[0]="+"+temp[0];
            return temp;
        }
        throw new IllegalArgumentException("Function has no parts: "+function);
    }

    /**
     * Condenses the function down by adding in parenthesis wherever possible without ruining the order of operations.
     * @param function the function to be condensed.
     * @param key the key you want the collapsed parenthesis indexed into.
     * @return the function now condensed down as much as possible.
     */
    public static String parenthesisGrouper(String function, FunctionKey key) {
        int end=1;
        boolean pass=false;

        if(function.contains("^")){
            int start = end = function.indexOf('^');

            for(end++; end < function.length(); end++){
                if(function.charAt(end) == '/' || function.charAt(end) == '*' || function.charAt(end) == '^')
                    break;
            }
            for(start--; start > 0; start--){
                if(function.charAt(start) == '/' || function.charAt(start) == '*' || function.charAt(start) == '^')
                    break;
            }

            function = function.substring(0, start+1) + "(" + function.substring(start+1, end) + ")" + function.substring(end);
            function = parenthesisCollapser(function, key);
            return parenthesisGrouper(function, key);
        }


        for (; end < function.length(); end++) {
            if (function.charAt(end) == '/' || function.charAt(end) == '*') {
                if(pass){
                    function = function.substring(0, 1) + "(" + function.substring(1, end) + ")" + function.substring(end);
                    function = parenthesisCollapser(function, key);
                    return parenthesisGrouper(function, key);
                }
                pass=true;
            }
        }

        return function;
    }

    /**
     * Collapses all parenthesis into function keys and indexes the keys with their
     * corresponding functions.
     * @param function the function you want to collapse the parenthesis within.
     * @param key the key you want to collapsed parenthesis to be indexed into.
     * @return the function with all parenthesis replaced with function keys.
     */
    private static String parenthesisCollapser(String function, FunctionKey key){
        int start=0, end=0;

        if(function.contains("("))
            for(int x=0; x<function.length(); x++){
                switch(function.charAt(x)){
                    case '(':  start=x;

                    break;

                    case ')': end=x;
                }

                if(end!=0)
                    return parenthesisCollapser(function.substring(0, start)
                            + key.addFunction(function.substring(start+1, end))
                            + function.substring(end+1), key);
            }

        return function;
    }

    /**
     * Replaces all keys with the functions they represent.
     * @param function the function to be expanded out.
     * @param key the key to be referenced for the keys and their functions.
     * @return the input function will keys replaced with the functions they represent.
     */
    public static String functionKeyExpander(String function, FunctionKey key){
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
                    return functionKeyExpander(function.substring(0, start)
                            + "(" +key.getFunction(function.substring(start, end+1)) + ")"
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
    public static String derivativeKeyExpander(String function, FunctionKey key){
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
                            + "(" +key.getDerivative(function.substring(start, end+1)) + ")"
                            + function.substring(end+1), key);
            }

        return function;
    }

    /**
     * Adds a '|' in front of each '^', '*', '/' within the function.
     * Used to signify that an operative has not been attempted to evaluate.
     * @param function the function to have '|'s added in where appropriate.
     * @return the function with '|'s added in.
     */
    private static String preOperationsFlag(String function){
        function = function.replaceAll("\\^", "|^");
        function = function.replaceAll("\\*", "|*");
        function = function.replaceAll("/", "|/");

        return function;
    }

    /**
     * Moves any '|'s from in front of each '^', '*', '/' to behind each one within the function.
     * Used to signify that an operative has been attempted to evaluate.
     * @param function the function with the '|'s moved to the front.
     * @return the function with '|'s moved behind each '^', '*', '/'.
     */
    private static String postOperationsFlag(String function){
        function = function.replaceAll("\\|\\^", "^|");
        function = function.replaceAll("\\|\\*", "*|");
        function = function.replaceAll("\\|/", "/|");

        return function;
    }

}