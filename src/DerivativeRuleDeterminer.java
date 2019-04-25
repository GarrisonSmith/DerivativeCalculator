package DerivativeCalculator;

public class DerivativeRuleDeterminer {

    /**
     * Determines what derivative rule each simplified function key inside of key should be applied.
     * @param key the key to referenced.
     */
    public static void applyRule(FunctionKeyList key) {

        for (int x = 0; x < key.getLength(); x++) {

            String function = FunctionFormater.cleanUp(key.getSimplifiedFunction(x));

            if (checkTestCase(function)) {
                key.setDerivative(x, "testing");
            }
            else if (checkConstant(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.CONST);
                key.setDerivative(x, "0");
            }
            else if (checkEtoX(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.EX);
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.EtoX(function, key)));
            }
            else if (checkProductRule(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.PRO);
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.productRule(function, key)));
            }
            else if (checkQuotientRule(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.QUO);
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.quotientRule(function, key)));
            }
            else if (checkDifferentiationRule(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.DIFF);
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.differentiationRule(function, key)));
            }
            else if (checkSinusoidal(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.TRIGD);
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.sinusoidalRule(function, key)));
            }
            else if (checkPowerRule(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.POWER);
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.powerRule(function, key)));
            }
            else if (checkLoneX(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.CONST);
                key.setDerivative(x, "1");
            }
            else if (checkScaledX(function)) {
                key.getNodeFromSimplifiedFunction(function).setTypes(2, FunctionType.CONST);
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.scaledX(function, key)));
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
        else if (checkConstant(function)) {
            return "0";
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
        else if (checkScaledX(function)) {
            return ApplyDerivative.scaledX(function, key);
        }

        return function;
    }

    /**
     * Identifies if the function is a constant.
     * @param function the function to be evaluated.
     * @return true if the rule can be applied, false if not.
     */
    private static boolean checkConstant(String function) {
        if (!function.contains("_") && !function.contains("x")) {
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
     * Identifies if the function is a scaled x.
     * @param function the function to be evaluated.
     * @return true if the rule can be applied, false if not.
     */
    private static boolean checkScaledX(String function) {
        if (function.contains("x")) return true;
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