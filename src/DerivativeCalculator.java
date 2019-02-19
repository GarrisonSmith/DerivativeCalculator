package DerivativeCalculator;

public class DerivativeCalculator {

    public static void main (String[] args) {
        String function = "-(1415x*14x/-1125x)*14^2^(135/-125)*123-(24x^2)";
        //String function = "14x^2^(135/-125)";
        calculateDerivative(function);
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