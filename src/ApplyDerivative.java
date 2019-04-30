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

            return FunctionFormater.derivativeFlagConverter(DerivativeRuleDeterminer.applyRule(split[1], key) + "*" + function);
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
     * @param key      the key to be referenced.
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
     * Applies differentiation rules to the given function.
     * @param function the function to have differentiation rules applied to.
     * @param key      the key to be referenced.
     * @return the derivative of the function.
     */
    public static String differentiationRule(String function, FunctionKeyList key) {

        String inner = function.substring(2);

        if (inner.contains("x") || inner.contains("_")) {
            if (inner == "x") {
                return "1/" + inner;
            }
            else {
                return FunctionFormater.derivativeFlagConverter(inner) + "/" + inner;
            }
        }
        else {
            return "0";
        }
    }

    /**
     * Applies sinusoidal derivative rules to the given function.
     * @param function the function have sinusoidal rules applied to.
     * @param key      the key to be referenced.
     * @return the derivative of the function
     * @throws IllegalArgumentException when the function isn't supported.
     */
    public static String sinusoidalRule(String function, FunctionKeyList key) {

        String func = function.substring(0, 3);
        String inner = function.substring(3);

        switch (func.toLowerCase()) {
            case "sin":
                func = "cos";
                break;
            case "cos":
                func = "sin";
                break;
            case "tan":
                func = "sec^2";
                break;
            default:
                throw new IllegalArgumentException("Not acceptable trig function");
        }

        if (inner.contains("x") || inner.contains("_")) {
            if (inner == "x") {
                return func + inner;
            }
            else {
                return FunctionFormater.derivativeFlagConverter(inner) + "*" + func + inner;
            }
        }
        else {
            return "0";
        }
    }

    /**
     * @param function
     * @param key
     * @return
     */
    public static String powerRule(String function, FunctionKeyList key) {

        String[] split = function.split("\\^");

        if ((split[0].contains("x") || split[0].contains("_")) && (split[1].contains("x") || split[1].contains("_"))) {
            return function + "*(" + DerivativeRuleDeterminer.applyRule(split[1], key) + "*lnx+" + DerivativeRuleDeterminer.applyRule(split[1], key) + ")";
        }
        else if (split[1].contains("x") || split[1].contains("_")) {
            if (split[1] == "x") {
                return function + "*ln" + split[0];
            }
            else {
                return FunctionFormater.derivativeFlagConverter(split[1]) + "*" + function + "*ln" + split[0];
            }
        }
        else if (split[0].contains("x") || split[0].contains("_")) {
            if ((FunctionSimplifier.stringToDouble(split[1]) - 1) == 1) {
                return split[1] + "*" + split[0];
            }
            else {
                return split[1] + "*" + split[0] + "^" + (FunctionSimplifier.stringToDouble(split[1]) - 1);
            }
        }
        else {
            return "na";
        }
    }
}