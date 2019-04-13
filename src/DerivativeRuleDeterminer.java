package DerivativeCalculator;

public class DerivativeRuleDeterminer {

    /**
     * Determines what derivative rule each simplified function key inside of key should be applied.
     * @param key the key to referenced.
     */
    public static void applyRule(FunctionKeyList key) {

        for (int x = 0; x < key.getLength(); x++) {

            String function = key.getSimplifiedFunction(x);

            if (applyTestCase(function)) {
                key.setDerivative(x, "testing");
            }
            else if (applyConstant(function)) {
                key.setDerivative(x, "0");
            }
            else if (applyEtoX(function)) {
                key.setDerivative(x, FunctionFormater.preOperationsFlag(function));
            }
            else if (applyProductRule(function)) {
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.ProductRule(function, key)));
            }
            else if (applyQuotientRule(function)) {
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.QuotientRule(function, key)));
            }
            else if (applySimpleDifferentiationRule(function)) {
                key.setDerivative(x, FunctionFormater.preOperationsFlag("(1/x)"));
            }
            else if (applyDifferentiationRule(function)) {
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.DifferentiationRule(function, key)));
            }
            else if (applySimpleSinusoidal(function)) {
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.SimpleSinusoidal(function, key)));
            }
            else if (applySinusoidal(function)) {
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.Sinusoidal(function, key)));
            }
            else if (applyGeneralizedPowerRule(function)) {
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.GeneralizedPowerRule(function, key)));
            }
            else if (applySimpleGeneralizedPowerRule(function)) {
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.SimpleGeneralizedPowerRule(function, key)));
            }
            else if (applyPowerRule(function)) {
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.PowerRule(function, key)));
            }
            else if (applyLoneX(function)) {
                key.setDerivative(x, "1");
            }
            else if (applyRemoveX(function)) {
                key.setDerivative(x, FunctionFormater.preOperationsFlag(ApplyDerivative.RemoveX(function, key)));
            }
            else {
                key.setDerivative(x, "Error");
            }

        }
    }

    /**
     * Determines what rule should be applied to the given function.
     * @param function the function to have a derivative rule applied to.
     * @param key the key to be referenced.
     * @return the derivative of the function.
     */
    public static String applyRule(String function, FunctionKeyList key) {

        if (applyTestCase(function)) {
            return "testing";
        }
        else if (applyConstant(function)) {
            return "(0)";
        }
        else if (applyEtoX(function)) {
            return function;
        }
        else if (applyProductRule(function)) {
            return ApplyDerivative.ProductRule(function, key);
        }
        else if (applyQuotientRule(function)) {
            return ApplyDerivative.QuotientRule(function, key);
        }
        else if (applySimpleDifferentiationRule(function)) {
            return "(1/x)";
        }
        else if (applyDifferentiationRule(function)) {
            return ApplyDerivative.DifferentiationRule(function, key);
        }
        else if (applySimpleSinusoidal(function)) {
            return ApplyDerivative.SimpleSinusoidal(function, key);
        }
        else if (applySinusoidal(function)) {
            return ApplyDerivative.Sinusoidal(function, key);
        }
        else if (applyGeneralizedPowerRule(function)) {
            return ApplyDerivative.GeneralizedPowerRule(function, key);
        }
        else if (applySimpleGeneralizedPowerRule(function)) {
            return ApplyDerivative.SimpleGeneralizedPowerRule(function, key);
        }
        else if (applyPowerRule(function)) {
            return ApplyDerivative.PowerRule(function, key);
        }
        else if (applyLoneX(function)) {
            return "(1)";
        }
        else if (applyRemoveX(function)) {
            return ApplyDerivative.RemoveX(function, key);
        }

        return function;
    }

    /**
     * Identifies if the function is e^x.
     * @param function the function to be evaluted.
     * @return the derivative of e^x.
     */
    private static boolean applyEtoX(String function) {
        if (function.equals("e^x") || function.equals("-e^x")) return true;
        return false;
    }

    /**
     *
     * @param function
     * @return
     */
    private static boolean applyProductRule(String function) {
        if (function.contains("*")) return true;
        return false;
    }

    /**
     *
     * @param function
     * @return
     */
    private static boolean applyQuotientRule(String function) {
        if (function.contains("/")) return true;
        return false;
    }

    /**
     *
     * @param function
     * @return
     */
    private static boolean applySimpleDifferentiationRule(String function) {
        if (function.equals("lnx") || function.equals("(lnx)")) return true;
        return false;
    }

    /**
     *
     * @param function
     * @return
     */
    private static boolean applyDifferentiationRule(String function) {
        if (function.contains("ln")) return true;
        return false;
    }

    /**
     *
     * @param function
     * @return
     */
    private static boolean applySimpleSinusoidal(String function) {
        if (function.contains("sinx") || function.contains("cosx") || function.contains("tanx") || function.contains("secx") || function.contains("cscx") || function.contains("cotx")
                || function.contains("(sinx)") || function.contains("(cosx)") || function.contains("(tanx)") || function.contains("(secx)") || function.contains("(cscx)") || function.contains("(cotx)"))
            return true;
        return false;
    }

    /**
     *
     * @param function
     * @return
     */
    private static boolean applySinusoidal(String function) {
        if (function.contains("sin") || function.contains("cos") || function.contains("tan") || function.contains("sec") || function.contains("csc") || function.contains("cot"))
            return true;
        return false;
    }

    /**
     *
     * @param function
     * @return
     */
    private static boolean applyGeneralizedPowerRule(String function) {
        String split[];

        if (function.contains("^")) {
            split = function.split("\\^");

            if ((split[0].contains("x") || split[0].contains("_")) && (split[1].contains("x") || split[1].contains("_"))) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param function
     * @return
     */
    private static boolean applySimpleGeneralizedPowerRule(String function) {
        String[] split;

        if (function.contains("^")) {
            split = function.split("\\^");

            if (split[1].contains("x") || split[1].contains("_")) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param function
     * @return
     */
    private static boolean applyPowerRule(String function) {
        String[] split;

        if (function.contains("^")) {
            split = function.split("\\^");

            if (split[0].contains("x") || split[0].contains("_")) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param function
     * @return
     */
    private static boolean applyLoneX(String function) {
        if (function.equals("x")) return true;
        return false;
    }

    /**
     *
     * @param function
     * @return
     */
    private static boolean applyRemoveX(String function) {
        if (function.contains("x")) return true;
        return false;
    }

    /**
     *
     * @param function
     * @return
     */
    private static boolean applyConstant(String function) {
        if (!function.contains("_") && !function.contains("x")) {
            return true;
        }

        return false;
    }

    /**
     *
     * @param function
     * @return
     */
    private static boolean applyTestCase(String function) {
        if (function.contains("testing")) {
            return true;
        }
        return false;
    }
}