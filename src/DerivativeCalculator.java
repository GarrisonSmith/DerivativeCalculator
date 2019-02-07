package DerivativeCalculator;

public class DerivativeCalculator {

    public static void main (String[] args) {
        //String function="-1415*14/1125*14^2^(13.05x*125)*123-24^2";
        String function = "-124*3/1-124^124*2";
        calculateDerivative(function);
        //System.out.println(FunctionFormater.leftAndRight("12")[0]);
        //System.out.println("\n"+FunctionSimplifier.getType("12|+3"));

    }

    /**
     * Takes the derivative of the input function.
     * @param input the function to have the derivative taken of.
     * @return the derivative of the input function.
     */
    public static String calculateDerivative(String input){
        Function function = new Function(input);
        function.calculateDerivative();
        function.showSteps();
        return function.getOutput();
    }
}
