package DerivativeCalculator;

import java.util.ArrayList;

public class Function {
    /**
     * Function key for contains condensed pieces of the function.
     */
    private FunctionKey key;

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
     *  The symbol that will appear within the data key.
     */
    private char type;

    /**
     * Constructs a new Function with a FunctionKey and take an input function.
     * @param function the input function.
     */
    public Function(String function){
        input=current=function;
        key=new FunctionKey();
        type = 'i';
    }

    /**
     * Constructs a new Function with a FunctionKey and take an input function.
     * @param function the input function.
     * @param type the symbol that will appear within the data key.
     */
    public Function(String function, char type){
        input=current=function;
        key=new FunctionKey(type);
        this.type = type;
    }

    /**
     * Constructs a new Function with the given FunctionKey and input function.
     * @param function the input function.
     * @param key the key to be used when referencing function keys.
     */
    public Function(String function, FunctionKey key){
        input=current=function;
        this.key=key;
    }

    /**
     * Returns the input function.
     * @return the input function.
     */
    public String getInput(){
            return input;
    }

    /**
     * Returns the output derivative, if not yet calculated returns "Output Not found Yet".
     * @return the output derivative, if not yet calculated returns "Output Not found Yet".
     */
    public String getOutput(){
        try {
            return output;
        }
        catch(NullPointerException e) {
            return "Output Not Calculated Yet";
        }
    }

    /**
     * Does all needed steps for taking the derivative of the input function.
     * @return the derivative of the input function.
     */
    public String calculateDerivative(){
        stages.add("Input Function: "); stages.add(input);
        formatInput();
        stages.add("Function after Formatting: "); stages.add(current);
        constructParts();
        stages.add("Function after being assembled from parts: "); stages.add(current);
        key.print();
        simplify();
        stages.add("Key now simplified");


        return null;
    }

    /**
     * Simplifies the function keys down in key.
     */
    private void simplify(){
       FunctionSimplifier.simplify(key);
       for(int x=0; x<parts.length; x++){
           parts[x] = FunctionSimplifier.simplify(parts[x], key);
       }
    }

    /**
     * Does all needed formatting for the function, also gets parts.
     * TODO Figure out why x != 0 in the if statement, probably covered the case somewhere else and forgot?.
     */
    private void formatInput(){
        String temp="";

        current=FunctionFormater.condense(current, key);
        try {
            parts = FunctionFormater.partsSplitter(current);
            for(int x=0; x<parts.length; x++) {
                if((parts[x].charAt(0) == '+' || parts[x].charAt(0) == '-') && x != 0){
                    temp += parts[x].charAt(0);
                    parts[x] = parts[x].substring(1);
                }
                parts[x] = FunctionFormater.parenthesisGrouper(parts[x], key);
                parts[x] = temp+parts[x];
            }
            formatKey();
        }
        catch(IllegalArgumentException NoParts){}
    }

    /**
     * Formats each element in the key for simplification.
     */
    private void formatKey(){
       for(int x=0; x<key.length(); x++){
            key.setFunction(FunctionFormater.preOperationsFlag(key.getElement(x)[1]), x);
       }
    }

    /**
     * Constructs the function from parts and keys.
     */
    private void constructParts(){
        String temp="";
        try {
            for (String i : parts) {
                temp += i;
            }
            current=temp;
        }
        catch(NullPointerException e){}
    }

    /**
     * Prints the process for testing.
     */
    public void showSteps(){
        System.out.println("\n\n----Stages----");
        for(String i : stages) {
            System.out.println(i);
        }
        System.out.println("-----End-----");
        System.out.print("Key: ");
        key.print();
        System.out.println("\nParts: ");
        try {
            for (String i : parts) {
                System.out.println(i);
            }
        }
        catch (NullPointerException e){}
        System.out.println( "Function Expanded\n" +
                FunctionFormater.functionKeyExpander(current, key).replace("|", ""));
    }
}