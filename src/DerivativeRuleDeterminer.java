package DerivativeCalculator;

public class DerivativeRuleDeterminer {

    /**
     * Determines what derivative rule each simplified function key inside of key should be applied.
     * @param key the key to referenced.
     */
    public static void applyRule(FunctionKeyList key) {

        for (int x = 0; x < key.getLength(); x++) {

            String function = key.getSimplifiedFunction(x);

            if (checkTestCase(function)) {
                key.setDerivative(x, "testing");
            }
            else if (nonassociated(function)) {
                String split[] = FunctionSimplifier.getLeftAndRight(function);
                if (function.contains("-")) {
                    key.setDerivative(x, (applyRule(split[0], key) + "-" + applyRule(split[1], key)));
                }
                else {
                    key.setDerivative(x, (applyRule(split[0], key) + "-" + applyRule(split[1], key)));
                }
            }
            else if (checkConstant(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.CONST);
                if (function.contains("x")) {
                    key.setDerivative(x, FunctionFormater.cleanUp(function.replaceAll("x", "")));
                }
                else {
                    key.setDerivative(x, "0");
                }
            }
            else if (checkEtoX(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.EX);
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.EtoX(FunctionFormater.cleanUp(function), key)));
            }
            else if (checkProductRule(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.PRO);
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.productRule(FunctionFormater.cleanUp(function), key)));
            }
            else if (checkQuotientRule(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.QUO);
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.quotientRule(FunctionFormater.cleanUp(function), key)));
            }
            else if (checkDifferentiationRule(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.DIFF);
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.differentiationRule(FunctionFormater.cleanUp(function), key)));
            }
            else if (checkSinusoidal(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.TRIGD);
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.sinusoidalRule(FunctionFormater.cleanUp(function), key)));
            }
            else if (checkPowerRule(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.POWER);
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.powerRule(FunctionFormater.cleanUp(function), key)));
            }
            else if (checkLoneX(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.CONST);
                key.setDerivative(x, "1");
            }
            else {
                key.setDerivative(x, "Error");
            }

        }
    }

    /**
     * Determines what rule should be applied to the given function.
     * @param function the function to have a derivative rule applied to.
     * @param key      the key to be referenced.
     * @return the derivative of the function.
     */
    public static String applyRule(String function, FunctionKeyList key) {

        if (checkTestCase(function)) {
            return "testing";
        }
        else if (nonassociated(function)) {
            String split[] = FunctionSimplifier.getLeftAndRight(function);
            if (function.contains("-")) {
                return applyRule(split[0], key) + "-" + applyRule(split[1], key);
            }
            else {
                return applyRule(split[0], key) + "-" + applyRule(split[1], key);
            }
        }
        else if (checkConstant(function)) {
            if (function.contains("x")) {
                return function.replaceAll("x", "");
            }
            else {
                return "0";
            }
        }
        else if (checkEtoX(function)) {
            return ApplyDerivative.EtoX(function, key);
        }
        else if (checkProductRule(function)) {
            return ApplyDerivative.productRule(function, key);
        }
        else if (checkQuotientRule(function)) {
            return ApplyDerivative.quotientRule(function, key);
        }
        else if (checkDifferentiationRule(function)) {
            return ApplyDerivative.differentiationRule(function, key);
        }
        else if (checkSinusoidal(function)) {
            return ApplyDerivative.sinusoidalRule(function, key);
        }
        else if (checkPowerRule(function)) {
            return ApplyDerivative.powerRule(function, key);
        }
        else if (checkLoneX(function)) {
            return "1";
        }

        return function;
    }

    /**
     * Used to identify when two pieces of the function wont effect one another's derivative.
     * @param function the function to be evaluated.
     * @return the derivative of the function.
     */
    private static boolean nonassociated(String function) {
        if (function.contains("|+") || function.contains("|-") || function.contains("+|") || function.contains("-|")) {
            return true;
        }
        return false;
    }

    /**
     * Identifies if the function is a constant.
     * @param function the function to be evaluated.
     * @return true if the rule can be applied, false if not.
     */
    private static boolean checkConstant(String function) {
        if (!function.contains("_") && !function.contains("*") && !function.contains("/") && !function.contains("^")) {
            return true;
        }
        return false;
    }

    /**
     * Identifies if the function is e^x.
     * @param function the function to be evaluated.
     * @return true if the rule can be applied, false if not.
     */
    private static boolean checkEtoX(String function) {
        if (function.contains("e^")) return true;
        return false;
    }

    /**
     * Identifies if the function can have the product rule applied.
     * @param function the function to be evaluated.
     * @return true if the rule can be applied, false if not.
     */
    private static boolean checkProductRule(String function) {
        if (function.contains("*")) return true;
        return false;
    }

    /**
     * Identifies if the function can have the quotient rule applied.
     * @param function the function to be evaluated.
     * @return true if the rule can be applied, false if not.
     */
    private static boolean checkQuotientRule(String function) {
        if (function.contains("/")) return true;
        return false;
    }

    /**
     * Identifies if the function can have the differentiation rule applied.
     * @param function the function to be evaluated.
     * @return true if the rule can be applied, false if not.
     */
    private static boolean checkDifferentiationRule(String function) {
        if (function.contains("ln")) return true;
        return false;
    }

    /**
     * Identifies if the function is Sinusoidal.
     * @param function the function to be evaluated.
     * @return true if the rule can be applied, false if not.
     */
    private static boolean checkSinusoidal(String function) {
        if (function.contains("sin") || function.contains("cos") || function.contains("tan") || function.contains("sec")
                || function.contains("csc") || function.contains("cot")) {
            return true;
        }
        return false;
    }

    /**
     * Identifies if the function can have the power rule applied.
     * @param function the function to be evaluated.
     * @return true if the rule can be applied, false if not.
     */
    private static boolean checkPowerRule(String function) {
        if (function.contains("^")) return true;
        return false;
    }

    /**
     * Identifies if the function is a lone x.
     * @param function the function to be evaluated.
     * @return true if the rule can be applied, false if not.
     */
    private static boolean checkLoneX(String function) {
        if (function.equals("x")) return true;
        return false;
    }

    /**
     * A non-sense case for testing. function = testing.
     * @param function the function to be evaluated.
     * @return true if the rule can be applied, false if not.
     */
    private static boolean checkTestCase(String function) {
        if (function.contains("testing")) {
            return true;
        }
        return false;
    }
}