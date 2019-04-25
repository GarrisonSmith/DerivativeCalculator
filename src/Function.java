package DerivativeCalculator;

import java.util.ArrayList;

public class Function {

    /**
     * Function key for contains condensed pieces of the function.
     */
    private FunctionKeyList key;

    /**
     * String array for containing the parts of the function, split by the
     * +'s and -'s.
     */
    private String[] parts;

    /**
     * String for containing the input, current state, and the output.
     */
    private String input, current, output;

    /**
     * ArrayList for containing the stages and progress points in taking the derivative.
     */
    private ArrayList<String> stages = new ArrayList<>();

    /**
     * The symbol that will appear within the data key.
     */
    private char type;

    /**
     * Constructs a new Function with a FunctionKey and take an input function.
     * @param function the input function.
     */
    public Function(String function) {
        input = current = function;
        key = new FunctionKeyList();
        type = 'i';
    }

    /**
     * Constructs a new Function with a FunctionKey and take an input function.
     * @param function the input function.
     * @param type     the symbol that will appear within the data key.
     */
    public Function(String function, char type) {
        input = current = function;
        key = new FunctionKeyList(type);
        this.type = type;
    }

    /**
     * Constructs a new Function with the given FunctionKey and input function.
     * @param function the input function.
     * @param key      the key to be used when referencing function keys.
     */
    public Function(String function, FunctionKeyList key) {
        input = current = function;
        this.key = key;
    }

    /**
     * Returns the input function.
     * @return the input function.
     */
    public String getInput() {
        return input;
    }

    /**
     * Returns the output derivative, if not yet calculated returns "Output Not found Yet".
     * @return the output derivative, if not yet calculated returns "Output Not found Yet".
     */
    public String getOutput() {
        try {
            return output;
        }
        catch (NullPointerException e) {
            return "Output Not Calculated Yet";
        }
    }

    /**
     * Does all needed steps for taking the derivative of the input function.
     * @return the derivative of the input function.
     */
    public String calculateDerivative() {
        stages.add("Input Function: ");
        stages.add(input);
        formatInput();
        stages.add("Function after Formatting: ");
        stages.add(current);
        simplifyFunction();
        stages.add("Key now simplified");
        constructParts();
        stages.add("Function after being assembled from parts: ");
        stages.add(current);
        DerivativeRuleDeterminer.applyRule(key);

        return null;
    }

    /**
     * Simplifies the function keys down in key.
     */
    private void simplifyFunction() {
        FunctionSimplifier.simplifyFunction(key);
    }

    /**
     * Does all needed formatting for the function, also gets parts.
     */
    private void formatInput() {
        String foo = "";

        current = FunctionFormater.condense(current, key);
        try {
            parts = FunctionFormater.partsSplitter(current);
        }
        catch (IllegalArgumentException NoParts) {
            parts = new String[1];
            current = FunctionFormater.parenthesisGrouper(current, key);
            parts[0] = key.addFunction(current);
        }

        for (int x = 0; x < parts.length; x++) {
            if ((parts[x].charAt(0) == '+' || parts[x].charAt(0) == '-')) {
                foo += parts[x].charAt(0);
                parts[x] = parts[x].substring(1);
            }
            else if ((parts[x].charAt(0) == '|')) {
                foo += parts[x].substring(0, 2);
                parts[x] = parts[x].substring(2);
            }
            parts[x] = FunctionFormater.parenthesisGrouper(parts[x], key);
            parts[x] = key.addFunction(parts[x]);
            parts[x] = foo + parts[x];
            foo = "";
        }
    }

    /**
     * Constructs the function from parts and keys and then cleans it up.
     */
    private void constructParts() {
        String temp = "";
        try {
            for (String i : parts) {
                temp += i;
            }
            current = temp;
        }
        catch (NullPointerException e) {
        }

        current = FunctionFormater.cleanUp(FunctionFormater.functionKeyExpander(current, key));
    }

    /**
     * Prints the process for testing.
     */
    public void showSteps() {
        System.out.println("----Stages----");
        for (String i : stages) {
            System.out.println(i);
        }
        System.out.println("-----End-----");
        System.out.print("Key: \n");
        key.print();
        System.out.println("\nParts: ");
        try {
            for (String i : parts) {
                System.out.println(i);
            }
        }
        catch (NullPointerException e) {
        }
        System.out.println("Function Expanded\n" + current);
    }
}