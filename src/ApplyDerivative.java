package DerivativeCalculator;

public class ApplyDerivative {

    /**
     * Applies E to the X rules to the given function.
     * @param function the function to have E to the X applied to.
     * @param key      the key to be referenced.
     * @return the derivative of the function.
     */
    public static String EtoX(String function, FunctionKeyList key) {

        if (function.equals("e^x")) {
            return function;
        }
        else {
            String split[] = function.split("\\^");

            return FunctionFormater.derivativeFlagConverter(split[1]) + "*" + function;
        }
    }

    /**
     * Applies the product rule to the given function.
     * @param function the function to have product rule applied to.
     * @param key      the key to be referenced.
     * @return the derivative of the function.
     */
    public static String productRule(String function, FunctionKeyList key) {

        String[] split = function.split("\\*");

        if (key.containsKey(split[0]) && key.containsKey(split[1])) {
            return FunctionFormater.derivativeFlagConverter(split[0]) + "*" + split[1] + "+" + split[0] + "*" + FunctionFormater.derivativeFlagConverter(split[1]);
        }
        else if (key.containsKey(split[0])) {
            return FunctionFormater.derivativeFlagConverter(split[0]) + "*" + split[1] + "+" + split[0] + "*" + DerivativeRuleDeterminer.applyRule(split[1], key);
        }
        else if (key.containsKey(split[1])) {
            return DerivativeRuleDeterminer.applyRule(split[0], key) + "*" + split[1] + "+" + FunctionFormater.derivativeFlagConverter(split[0]) + "*" + split[1];
        }
        else {
            return DerivativeRuleDeterminer.applyRule(split[0], key) + "*" + split[1] + "+" + split[0] + "*" + DerivativeRuleDeterminer.applyRule(split[1], key);
        }
    }

    /**
     * Applies the quotient rule to the given function.
     * @param function the function to have the quotient rule applied to.
     * @param key the key to be referenced.
     * @return the derivative of the function.
     */
    public static String quotientRule(String function, FunctionKeyList key) {

        String[] split = function.split("/");

        if (key.containsKey(split[0]) && key.containsKey(split[1])) {
            return "(" + FunctionFormater.derivativeFlagConverter(split[0]) + "*" + split[1] + "-" +
                    FunctionFormater.derivativeFlagConverter(split[1]) + "*" + split[0] + ")/(" + split[1] + ")^2";
        }
        else if (key.containsKey(split[0])) {
            return "(" + FunctionFormater.derivativeFlagConverter(split[0]) + "*" + split[1] + "-" +
                    DerivativeRuleDeterminer.applyRule(split[1], key) + "*" + split[0] + ")/(" + split[1] + ")^2";
        }
        else if (key.containsKey(split[1])) {
            return "(" + DerivativeRuleDeterminer.applyRule(split[0], key) + "*" + split[1] + "-" +
                    FunctionFormater.derivativeFlagConverter(split[1]) + "*" + split[0] + ")/(" + split[1] + ")^2";
        }
        else {
            return "(" + DerivativeRuleDeterminer.applyRule(split[0], key) + "*" + split[1] + "-" +
                    DerivativeRuleDeterminer.applyRule(split[1], key) + "*" + split[0] + ")/(" + split[1] + ")^2";
        }
    }

    /**
     *
     * @param function
     * @param key
     * @return
     */
    public static String differentiationRule(String function, FunctionKeyList key) {

        return "0";
    }

    /**
     *
     * @param function
     * @param key
     * @return
     */
    public static String sinusoidalRule(String function, FunctionKeyList key) {

        return "0";
    }

    /**
     *
     * @param function
     * @param key
     * @return
     */
    public static String powerRule(String function, FunctionKeyList key) {

        String[] split = function.split("\\^");

        if((split[0].contains("x") || split[0].contains("_")) && (split[1].contains("x") || split[1].contains("_"))){

        }
        else if (split[1].contains("x") || split[1].contains("_")) {

        }
        else if (split[0].contains("x") || split[0].contains("_")) {

        }

        return "0";
    }

    /**
     *
     * @param function
     * @param key
     * @return
     */
    public static String scaledX(String function, FunctionKeyList key) {

        return "0";
    }
}