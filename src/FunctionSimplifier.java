package DerivativeCalculator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class FunctionSimplifier {

    /**
     * Simplifies all functions in key and logs them into the simplified function,
     * should only contain one operator.
     * @param key the key to be referenced.
     */
    public static void simplifyFunction(FunctionKeyList key) {
        main:
        for (int x = 0; x < key.getLength(); x++) {
            String function = key.getFunction(x);
            FunctionType FunctionType = getFunctionType(function);

            if (FunctionType == FunctionType.NONE) {
                key.setSimplifiedFunction(x, function);
            }
            else if (FunctionType == FunctionType.LN || FunctionType == FunctionType.TRIG) {

                String inner;
                if (FunctionType == FunctionType.LN) {
                    inner = function.substring(2);
                }
                else {
                    inner = function.substring(3);
                }

                if (key.containsKey(inner)) {
                    inner = key.getSimplifiedFunction(inner);
                }

                if (!inner.contains("x") && !inner.contains("_")) {
                    switch (FunctionType) {
                        case LN:
                            key.setSimplifiedFunction(x, LN(inner));
                            break;
                        case TRIG:
                            key.setSimplifiedFunction(x, trig(function, inner));
                            break;
                    }
                }
                else {
                    key.setSimplifiedFunction(x, function);
                }
            }
            else {
                String[] pieces = getLeftAndRight(function);

                for (int y = 0; y < pieces.length; y++) {
                    if (key.containsKey(pieces[y])) {
                        if (getFunctionType(key.getSimplifiedFunction(pieces[y])) == FunctionType.NONE && key.getSimplifiedFunction(pieces[y]) != null) {
                            pieces[y] = key.getSimplifiedFunction(pieces[y]);
                        }
                        else {
                            //TODO Distribution
                            key.setSimplifiedFunction(x, function);
                            key.getNode(x).upDateDataType();
                            continue main;
                        }
                    }
                }

                if (pieces[0].matches(".*\\d+.*") && pieces[1].matches(".*\\d+.*")) {
                    switch (FunctionType) {
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
                }
                else {
                    System.out.println("Is this needed: " + function);
                    key.setSimplifiedFunction(x, function);
                }
            }
            key.getNode(x).upDateDataType();
        }

    }

    /**
     * Simplifies all derivatives in key and logs them into the simplified derivative,
     * should only contain one operator.
     * @param key the key to be referenced.
     */
    public static void simplifyDerivative(FunctionKeyList key) {
        main:
        for (int x = 0; x < key.getLength(); x++) {
            String function = key.getDerivaitve(x);

            FunctionType FunctionType = getFunctionType(function);
            if (FunctionType == FunctionType.NONE) {
                key.setSimplifiedDerivative(x, function);
                key.getNode(x).upDateDataType();
                continue main;
            }

            String[] pieces = getLeftAndRight(function);

            //TODO clean this mess up.
            for (int y = 0; y < pieces.length; y++) {
                if (key.containsKey(pieces[y])) {
                    try {
                        if (getFunctionType(key.getSimplifiedDerivative(pieces[y])) == FunctionType.NONE) {
                            pieces[y] = key.getSimplifiedDerivative(pieces[y]);
                        }
                        else {
                            //TODO Distribution
                            key.setSimplifiedDerivative(x, function);
                            key.getNode(x).upDateDataType();
                            continue main;
                        }
                    }
                    catch (NumberFormatException e) {
                        //TODO Distribution
                        key.setSimplifiedDerivative(x, function);
                        key.getNode(x).upDateDataType();
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

            key.getNode(x).upDateDataType();
        }
    }

    /**
     * Multiplies the left by the right out if possible.
     * Used if, and only if, left and right are only numbers.
     * Can be multiples of X.
     * @param left  the left multiple.
     * @param right the right multiple.
     * @return The product of the left and the right.
     */
    private static String multiply(String left, String right) {

        System.out.println(left);
        System.out.println(right);

        if(left == "0" || right == "0"){
            return "0";
        }

        if (left.contains("x") && right.contains("x")) {
            return formatNumber(stringToDouble(left) * stringToDouble(right)) + "x^|2";
        }
        else if (left.contains("x") || right.contains("x")) {
            return formatNumber(stringToDouble(left) * stringToDouble(right)) + "x";
        }
        else {
            return formatNumber(stringToDouble(left) * stringToDouble(right));
        }
    }

    /**
     * Divides the left by the right if possible.
     * Used if, and only if, left and right are only numbers.
     * Can be multiples of X.
     * @param left  the numerator.
     * @param right the denominator.
     * @return the quotient of the left and the right.
     */
    private static String divide(String left, String right) {

        if (left == right) {
            return "1";
        }
        else {
            if (left.contains("x") && right.contains("x")) {
                return formatNumber(stringToDouble(left) / stringToDouble(right));
            }
            else if (left.contains("x")) {
                return formatNumber(stringToDouble(left) / stringToDouble(right)) + "x";
            }
            else if (right.contains("x")) {
                if (stringToDouble(left) >= stringToDouble(right)) {
                    return "(" + formatNumber(stringToDouble(left) / stringToDouble(right)) + "/|x)";
                }
                else {
                    return "(1/|" + formatNumber(stringToDouble(right) / stringToDouble(left)) + "x)"; //not always 1
                }
            }
            else {
                return formatNumber(stringToDouble(left) / stringToDouble(right));
            }
        }
    }

    /**
     * Raises the left to the power of the right if possible.
     * Used if, and only if, left and right are only numbers.
     * Can be multiples of X.
     * @param left  the base.
     * @param right the exponent.
     * @return If possible, the left raised to the power of the right. If not the original function.
     */
    private static String exponent(String left, String right) {

        if (left.contains("x") || right.contains("x")) {
            return left + "^|" + right;
        }
        else {
            return formatNumber(Math.pow(stringToDouble(left), stringToDouble(right)));
        }
    }

    /**
     * Adds the left to the right if possible.
     * Used if, and only if, left and right are only numbers.
     * Can be multiples of X.
     * @param left  the left number.
     * @param right the right number.
     * @return If possible, the sum of the left and the right. If not the original function.
     */
    private static String add(String left, String right) {

        if (left.contains("x") && right.contains("x")) {
            return formatNumber(stringToDouble(left)) + "x+|" + formatNumber(stringToDouble(right)) + "x";
        }
        else if (left.contains("x")) {
            return formatNumber(stringToDouble(left)) + "x+|" + formatNumber(stringToDouble(right));
        }
        else if (right.contains("x")) {
            return formatNumber(stringToDouble(left)) + "+|" + formatNumber(stringToDouble(right)) + "x";
        }
        else {
            return formatNumber(stringToDouble(left) + stringToDouble(right));
        }
    }

    /**
     * Subtracts the left from the right if possible.
     * Used if, and only if, left and right are only numbers.
     * Can be multiples of X.
     * @param left  the left number.
     * @param right the right number.
     * @return If possible, the difference of the left by the right. If not the original function.
     */
    private static String subtract(String left, String right) {

        if (left.contains("x") && right.contains("x")) {
            return formatNumber(stringToDouble(left)) + "x-|" + formatNumber(stringToDouble(right)) + "x";
        }
        else if (left.contains("x")) {
            return formatNumber(stringToDouble(left)) + "x-|" + formatNumber(stringToDouble(right));
        }
        else if (right.contains("x")) {
            return formatNumber(stringToDouble(left)) + "-|" + formatNumber(stringToDouble(right)) + "x";
        }
        else {
            return formatNumber(stringToDouble(left) - stringToDouble(right));
        }
    }

    /**
     * Takes the natural log of the given number.
     * Used if, and only if, inner is only numbers.
     * @param inner the string of numbers to have the natural log of.
     * @return the natural log of the given inner.
     */
    private static String LN(String inner) {
        return formatNumber(Math.log(stringToDouble(inner)));
    }

    /**
     * Take the proper trig function of the given inner.
     * @param function the original full function.
     * @param inner the number to be taken.
     * @return the trig function of the given function.
     */
    private static String trig(String function, String inner) {
        String foo = function;
        switch (function.substring(0, 3).toLowerCase()) {
            case "sin":
                foo = formatNumber(Math.sin(stringToDouble(inner)));
                break;
            case "cos":
                foo = formatNumber(Math.cos(stringToDouble(inner)));
                break;
            case "tan":
                foo = formatNumber(Math.tan(stringToDouble(inner)));
                break;
        }
        return foo;
    }

    /**
     * Formats numbers to have four decimals and no trailing zeros.
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
     * @param function the function you want the degree of.
     * @return the degree of the function.
     * TODO functionality is kinda wonky, might need to be reworked completely.
     */
    private static String getDegree(String function) {

        if (function.contains("^")) {
            return function.split("\\^", 2)[1];
        }
        else {
            return "1";
        }
    }

    /**
     * Creates an array consisting of the right and left of the given function.
     * @param function the function to be split into the right and left.
     * @return an array of size 2 with the left in index 0 and the right in index 1.
     * If the array is of size 0, then no left or right could be derived.
     */
    public static String[] getLeftAndRight(String function) {

        if (function.contains("*") || function.contains("/") || function.contains("^") || function.contains("-") || function.contains("+")) {
            return function.split("\\|\\*|\\|/|\\|\\^|\\|-|\\+", 2);
        }
        else {
            throw new IllegalArgumentException("Function has no left or right");
        }
    }

    /**
     * Converts a string to a double by removing all character that are not a number.
     * @param function the function to have all non-numbers removed.
     * @return the function with the all the non-numbers removed and only the numbers not in a exponent.
     */
    public static double stringToDouble(String function) {
        String toDouble = "";

        if (function.matches(".*\\d+.*")) { //checks if the function has any number in it at all
            for (char i : function.toCharArray()) {
                if (Character.isDigit(i) || i == '.' || i == '-') {
                    toDouble += i;
                }
                else if (i == '^') {
                    break;
                }
            }

            return Double.parseDouble(toDouble);
        }
        else {
            throw new IllegalArgumentException("Only characters: " + function);
        }
    }

    /**
     * Used to determine the FunctionType of operation within a key.
     * @param function the function to have it's primary operation looked at.
     * @return the FunctionType of operation as the FunctionType enum.
     */
    public static FunctionType getFunctionType(String function) {

        if (function.contains("|*") || function.contains("*|")) {
            return FunctionType.MULT;
        }
        else if (function.contains("|/") || function.contains("/|")) {
            return FunctionType.DIVI;
        }
        else if (function.contains("|^") || function.contains("^|")) {
            return FunctionType.EXPO;
        }
        else if (function.contains("|+") || function.contains("+|")) {
            return FunctionType.ADD;
        }
        else if (function.contains("|-") || function.contains("-|")) {
            return FunctionType.SUB;
        }
        else if (function.contains("sin") || function.contains("cos") || function.contains("tan") ||
                function.contains("sec") || function.contains("csc") || function.contains("cot")) {
            return FunctionType.TRIG;
        }
        else if (function.contains("ln")) {
            return FunctionType.LN;
        }

        return FunctionType.NONE;
    }
}