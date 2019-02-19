package DerivativeCalculator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class FunctionSimplifier {

    /**
     * Simplifies all functions in key and logs them into the simplified function,
     * should only contain one operator.
     *
     * @param key the key to be referenced.
     */
    public static void simplifyFunction(FunctionKeyList key) {
        main:
        for (int x = 0; x < key.getLength(); x++) {
            String function = key.getFunction(x);

            FunctionType FunctionType = getFunctionType(function);
            if (FunctionType == FunctionType.NONE) {
                key.setSimplifiedFunction(x, function);
                key.getNode(x).upDataType();
                continue main;
            }

            String[] pieces = getLeftAndRight(function);

            //TODO clean this mess up.
            for (int y = 0; y < pieces.length; y++) {
                if (key.containsKey(pieces[y])) {
                    try {
                        if (getFunctionType(key.getSimplifiedFunction(pieces[y])) == FunctionType.NONE) {
                            pieces[y] = key.getSimplifiedFunction(pieces[y]);
                        } else {
                            //TODO Distribution
                            key.setSimplifiedFunction(x, function);
                            key.getNode(x).upDataType();
                            continue main;
                        }
                    } catch (NumberFormatException e) {
                        //TODO Distribution
                        key.setSimplifiedFunction(x, function);
                        key.getNode(x).upDataType();
                        continue main;
                    }
                }
            }

            switch (getFunctionType(function)) {
                case MULT:
                    key.setSimplifiedFunction(x, (multiply(pieces[0], pieces[1])));
                    break;
                case DIVI:
                    key.setSimplifiedFunction(x, (divide(pieces[0], pieces[1])));
                    break;
                case EXPO:
                    key.setSimplifiedFunction(x, (exponent(pieces[0], pieces[1])));
                    break;
                case ADD:
                    key.setSimplifiedFunction(x, (add(pieces[0], pieces[1])));
                    break;
                case SUB:
                    key.setSimplifiedFunction(x, (subtract(pieces[0], pieces[1])));
                    break;
            }

            key.getNode(x).upDataType();
        }
    }

    /**
     * Simplifies all derivatives in key and logs them into the simplified derivative,
     * should only contain one operator.
     *
     * @param key the key to be referenced.
     */
    public static void simplifyDerivative(FunctionKeyList key) {
        main:
        for (int x = 0; x < key.getLength(); x++) {
            String function = key.getDerivaitve(x);

            FunctionType FunctionType = getFunctionType(function);
            if (FunctionType == FunctionType.NONE) {
                key.setSimplifiedDerivative(x, function);
                key.getNode(x).upDataType();
                continue main;
            }

            String[] pieces = getLeftAndRight(function);

            //TODO clean this mess up.
            for (int y = 0; y < pieces.length; y++) {
                if (key.containsKey(pieces[y])) {
                    try {
                        if (getFunctionType(key.getSimplifiedDerivative(pieces[y])) == FunctionType.NONE) {
                            pieces[y] = key.getSimplifiedDerivative(pieces[y]);
                        } else {
                            //TODO Distribution
                            key.setSimplifiedDerivative(x, function);
                            key.getNode(x).upDataType();
                            continue main;
                        }
                    } catch (NumberFormatException e) {
                        //TODO Distribution
                        key.setSimplifiedDerivative(x, function);
                        key.getNode(x).upDataType();
                        continue main;
                    }
                }
            }

            switch (getFunctionType(function)) {
                case MULT:
                    key.setSimplifiedDerivative(x, (multiply(pieces[0], pieces[1])));
                    break;
                case DIVI:
                    key.setSimplifiedDerivative(x, (divide(pieces[0], pieces[1])));
                    break;
                case EXPO:
                    key.setSimplifiedDerivative(x, (exponent(pieces[0], pieces[1])));
                    break;
                case ADD:
                    key.setSimplifiedDerivative(x, (add(pieces[0], pieces[1])));
                    break;
                case SUB:
                    key.setSimplifiedDerivative(x, (subtract(pieces[0], pieces[1])));
                    break;
            }

            key.getNode(x).upDataType();
        }
    }

    /**
     * Multiplies the left by the right out if possible.
     * Used if, and only if, left and right are only numbers.
     * Can be multiples of X.
     *
     * @param left  the left multiple.
     * @param right the right multiple.
     * @return The product of the left and the right.
     */
    private static String multiply(String left, String right) {

        if (left.contains("x") && right.contains("x")) {
            return formatNumber(stringToDouble(left) * stringToDouble(right)) + "x^|2";
        } else if (left.contains("x") || right.contains("x")) {
            return formatNumber(stringToDouble(left) * stringToDouble(right)) + "x";
        } else {
            return formatNumber(stringToDouble(left) * stringToDouble(right));
        }
    }

    /**
     * Divides the left by the right if possible.
     * Used if, and only if, left and right are only numbers.
     * Can be multiples of X.
     *
     * @param left  the numerator.
     * @param right the denominator.
     * @return the quotient of the left and the right.
     */
    private static String divide(String left, String right) {

        if (left == right) {
            return "1";
        } else {
            if (left.contains("x") && right.contains("x")) {
                return formatNumber(stringToDouble(left) / stringToDouble(right));
            } else if (left.contains("x")) {
                return formatNumber(stringToDouble(left) / stringToDouble(right)) + "x";
            } else if (right.contains("x")) {
                if (stringToDouble(left) >= stringToDouble(right)) {
                    return "(" + formatNumber(stringToDouble(left) / stringToDouble(right)) + "/|x)";
                } else {
                    return "(1/|" + formatNumber(stringToDouble(right) / stringToDouble(left)) + "x)";
                }
            } else {
                return formatNumber(stringToDouble(left) / stringToDouble(right));
            }
        }
    }

    /**
     * Raises the left to the power of the right if possible.
     * Used if, and only if, left and right are only numbers.
     * Can be multiples of X.
     *
     * @param left  the base.
     * @param right the exponent.
     * @return If possible, the left raised to the power of the right. If not the original function.
     */
    private static String exponent(String left, String right) {

        if (left.contains("x") || right.contains("x")) {
            return left + "^|" + right;
        } else {
            return formatNumber(Math.pow(stringToDouble(left), stringToDouble(right)));
        }
    }

    /**
     * Adds the left to the right if possible.
     * Used if, and only if, left and right are only numbers.
     * Can be multiples of X.
     *
     * @param left  the left number.
     * @param right the right number.
     * @return If possible, the sum of the left and the right. If not the original function.
     */
    private static String add(String left, String right) {

        if (left.contains("x") || right.contains("x")) {
            return formatNumber(stringToDouble(left)) + "x+|" + formatNumber(stringToDouble(right)) + "x";
        } else if (left.contains("x")) {
            return formatNumber(stringToDouble(left)) + "x+|" + formatNumber(stringToDouble(right));
        } else if (right.contains("x")) {
            return formatNumber(stringToDouble(left)) + "+|" + formatNumber(stringToDouble(right)) + "x";
        } else {
            return formatNumber(stringToDouble(left) + stringToDouble(right));
        }
    }

    /**
     * Subtracts the left from the right if possible.
     * Used if, and only if, left and right are only numbers.
     * Can be multiples of X.
     *
     * @param left  the left number.
     * @param right the right number.
     * @return If possible, the difference of the left by the right. If not the original function.
     */
    private static String subtract(String left, String right) {

        if (left.contains("x") || right.contains("x")) {
            return formatNumber(stringToDouble(left)) + "x-|" + formatNumber(stringToDouble(right)) + "x";
        } else if (left.contains("x")) {
            return formatNumber(stringToDouble(left)) + "x-|" + formatNumber(stringToDouble(right));
        } else if (right.contains("x")) {
            return formatNumber(stringToDouble(left)) + "-|" + formatNumber(stringToDouble(right)) + "x";
        } else {
            return formatNumber(stringToDouble(left) - stringToDouble(right));
        }
    }

    /**
     * Formats numbers to have four decimals and no trailing zeros.
     *
     * @param function the function to be formatted.
     * @return the formatted function.
     */
    private static String formatNumber(double function) {
        DecimalFormat foo = new DecimalFormat("0.########");
        BigDecimal bar;

        bar = new BigDecimal(foo.format(function));
        return bar.stripTrailingZeros().toPlainString();
    }

    /**
     * Gets the degree of a variable.
     *
     * @param function the function you want the degree of.
     * @return the degree of the function.
     * TODO functionality is kinda wonky, might need to be reworked completely.
     */
    private static String getDegree(String function) {

        if (function.contains("^")) {
            return function.split("\\^", 2)[1];
        } else {
            return "1";
        }
    }

    /**
     * Creates an array consisting of the right and left of the given function.
     *
     * @param function the function to be split into the right and left.
     * @return an array of size 2 with the left in index 0 and the right in index 1.
     */
    private static String[] getLeftAndRight(String function) {

        if (function.contains("*") || function.contains("/") || function.contains("^") || function.contains("-") || function.contains("+")) {
            return function.split("\\|\\*|\\|/|\\|\\^|\\|-|\\+", 2);
        } else {
            throw new IllegalArgumentException("Function has no left or right");
        }
    }

    /**
     * Converts a string to a double by removing all character that are not a number.
     *
     * @param function the function to have all non-numbers removed.
     * @return the function with the all the non-numbers removed and only the numbers not in a exponent.
     */
    private static double stringToDouble(String function) {
        String toDouble = "";

        if (function.matches(".*\\d+.*")) { //checks if the function has any number in it at all
            for (char i : function.toCharArray()) {
                if (Character.isDigit(i) || i == '.' || i == '-') {
                    toDouble += i;
                } else if (i == '^') {
                    break;
                }
            }

            return Double.parseDouble(toDouble);
        } else {
            throw new IllegalArgumentException("Only characters: " + function);
        }
    }

    /**
     * Used to determine the FunctionType of operation within a key.
     *
     * @param function the function to have it's primary operation looked at.
     * @return the FunctionType of operation as the FunctionType enum.
     */
    public static FunctionType getFunctionType(String function) {

        if (function.contains("|*") || function.contains("*|")) {
            return FunctionType.MULT;
        } else if (function.contains("|/") || function.contains("/|")) {
            return FunctionType.DIVI;
        } else if (function.contains("|^") || function.contains("^|")) {
            return FunctionType.EXPO;
        } else if (function.contains("|+") || function.contains("+|")) {
            return FunctionType.ADD;
        } else if (function.contains("|-") || function.contains("-|")) {
            return FunctionType.SUB;
        }

        return FunctionType.NONE;
    }
}