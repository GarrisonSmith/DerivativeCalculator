package DerivativeCalculator;

public class DerivativeCalculator {

    public static void main (String[] args) {
        String function="-1415*14/1125*14^2^(13*125)*123-24";
        calculateDerivative(function);
        //System.out.println(FunctionFormater.leftAndRight("12")[0]);
        //System.out.println(FunctionSimplifier.multiply("123x^141*1243x^141"));

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
