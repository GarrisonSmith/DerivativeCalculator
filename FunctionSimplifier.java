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


            if(FunctionType == FunctionType.MULT){
                String[] pieces = getLeftAndRight(function);
                if(pieces[0].equals("0") || pieces[1].equals("0")){
                    key.setSimplifiedFunction(x, "0");
                    continue;
                }
            }

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
                    //System.out.println("Is this needed: " + function);
                    key.setSimplifiedFunction(x, function);
                }
            }
            key.getNode(x).upDateDataType();
        }

    }

    /**
     * Adds an array of polynomials together
     * @param parts the array to be referenced.
     * @param key the key to be referenced.
     * @return the new array with everything simplified.
     */
    public static String[] addParts(String[] parts, FunctionKeyList key) {

        for(String j : parts){
            System.out.println(j);
        }
        System.out.println();

        for (int i = 0; i < parts.length - 1; i++) {
            String current = key.getSimplifiedFunction(parts[i].replaceAll("-|\\||\\+", ""));
            char currentSign = getSign(parts[i]);
            for (int ii = i + 1; ii < parts.length; ii++) {
                String next = key.getSimplifiedFunction(parts[ii].replaceAll("-|\\||\\+", ""));
                char nextSign = getSign(parts[ii]);
                if (getDegree(current).equals(getDegree(next)) && !key.containsKey(current) && !key.containsKey(next) &&
                        ((getFunctionType(current) == FunctionType.EXPO && getFunctionType(next) == FunctionType.EXPO) ||
                                (getFunctionType(current) == FunctionType.NONE && getFunctionType(next) == FunctionType.NONE))) {
                    String degree = getDegree(current);
                    if (degree == "0" || degree == "0.0") {
                        if (currentSign == '-') {
                            parts[i] = "0";
                            parts[ii] = "-1";
                            break;
                        }
                        else {
                            parts[i] = "0";
                            parts[ii] = "-1";
                            break;
                        }
                    }
                    else if (degree == "1" || degree == "1.0") {
                        if (currentSign == '-') {
                            if (nextSign == '-') { //negative first - second
                                parts[i] = "0";
                                if (((0 - stringToDouble(current)) - stringToDouble(next)) < 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction(String.valueOf((0 - stringToDouble(current)) - stringToDouble(next)).substring(1));
                                }
                                else {
                                    parts[ii] = "|+" + key.addSimplifiedFunction(String.valueOf((0 - stringToDouble(current)) - stringToDouble(next)));
                                }
                                break;
                            }
                            else { //negative first + second
                                parts[i] = "0";
                                if (((0 - stringToDouble(current)) + stringToDouble(next)) < 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction(String.valueOf((0 - stringToDouble(current)) + stringToDouble(next)).substring(1));
                                }
                                else {
                                    parts[ii] = "|+" + key.addSimplifiedFunction(String.valueOf((0 - stringToDouble(current)) + stringToDouble(next)));
                                }
                                break;
                            }
                        }
                        else {
                            System.out.println(currentSign);
                            if (nextSign == '-') { //positive first - second
                                parts[i] = "0";
                                if (((stringToDouble(current)) - stringToDouble(next)) < 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction(String.valueOf((stringToDouble(current)) - stringToDouble(next)).substring(1));
                                }
                                else {
                                    parts[ii] = "|+" + key.addSimplifiedFunction(String.valueOf((stringToDouble(current)) - stringToDouble(next)));
                                }
                                break;
                            }
                            else { //positive first + second
                                parts[i] = "0";
                                if (((stringToDouble(current)) + stringToDouble(next)) < 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction(String.valueOf((stringToDouble(current)) + stringToDouble(next)).substring(1));
                                }
                                else {
                                    parts[ii] = "|+" + key.addSimplifiedFunction(String.valueOf((stringToDouble(current)) + stringToDouble(next)));
                                }
                                break;
                            }
                        }
                    }
                    else if (degree.equals("Lone x")) {
                        if (currentSign == '-') {
                            if (nextSign == '-') { //negative first - second
                                parts[i] = "0";
                                if (((0 - stringToDouble(current)) - stringToDouble(next)) == 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction("0");
                                }
                                else if (((0 - stringToDouble(current)) - stringToDouble(next)) < 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction(String.valueOf((0 - stringToDouble(current)) - stringToDouble(next)).substring(1) + "x");
                                }
                                else {
                                    parts[ii] = "|+" + key.addSimplifiedFunction(String.valueOf((0 - stringToDouble(current)) - stringToDouble(next)) + "x");
                                }
                                break;
                            }
                            else { //negative first + second
                                parts[i] = "0";
                                if (((0 - stringToDouble(current)) + stringToDouble(next)) == 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction("0");
                                }
                                else if (((0 - stringToDouble(current)) + stringToDouble(next)) < 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction(String.valueOf((0 - stringToDouble(current)) + stringToDouble(next)).substring(1) + "x");
                                }
                                else {
                                    parts[ii] = "|+" + key.addSimplifiedFunction(String.valueOf((0 - stringToDouble(current)) + stringToDouble(next)) + "x");
                                }
                                break;
                            }
                        }
                        else {
                            if (nextSign == '-') { //positive first - second
                                parts[i] = "0";
                                if (((stringToDouble(current)) - stringToDouble(next)) == 0) {
                                    parts[ii] = "|+" + key.addSimplifiedFunction("0");
                                }
                                else if (((stringToDouble(current)) - stringToDouble(next)) < 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction(String.valueOf((stringToDouble(current)) - stringToDouble(next)).substring(1) + "x");
                                }
                                else {
                                    parts[ii] = "|+" + key.addSimplifiedFunction(String.valueOf((stringToDouble(current)) - stringToDouble(next)) + "x");
                                }
                                break;
                            }
                            else { //positive first + second
                                parts[i] = "0";
                                if (((stringToDouble(current)) + stringToDouble(next)) == 0) {
                                    parts[ii] = "|+" + key.addSimplifiedFunction("0");
                                }
                                else if (((stringToDouble(current)) + stringToDouble(next)) < 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction(String.valueOf((stringToDouble(current)) + stringToDouble(next)).substring(1) + "x");
                                }
                                else {
                                    parts[ii] = "|+" + key.addSimplifiedFunction(String.valueOf((stringToDouble(current)) + stringToDouble(next)) + "x");
                                }
                                break;
                            }
                        }
                    }
                    else {
                        if (currentSign == '-') {
                            if (nextSign == '-') { //negative first - second
                                parts[i] = "0";
                                if (((0 - stringToDouble(current)) - stringToDouble(next)) == 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction("0");
                                }
                                else if (((0 - stringToDouble(current)) - stringToDouble(next)) < 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction(String.valueOf((0 - stringToDouble(current)) - stringToDouble(next)).substring(1) + "x^|" + degree);
                                }
                                else {
                                    parts[ii] = "|+" + key.addSimplifiedFunction(String.valueOf((0 - stringToDouble(current)) - stringToDouble(next)) + "x^|" + degree);
                                }
                                break;
                            }
                            else { //negative first + second
                                parts[i] = "0";
                                if (((0 - stringToDouble(current)) + stringToDouble(next)) == 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction("0");
                                }
                                else if (((0 - stringToDouble(current)) + stringToDouble(next)) < 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction(String.valueOf((0 - stringToDouble(current)) + stringToDouble(next)).substring(1) + "x^|" + degree);
                                }
                                else {
                                    parts[ii] = "|+" + key.addSimplifiedFunction(String.valueOf((0 - stringToDouble(current)) + stringToDouble(next)) + "x^|" + degree);
                                }
                                break;
                            }
                        }
                        else {
                            if (nextSign == '-') { //positive first - second
                                parts[i] = "0";
                                if (((stringToDouble(current)) - stringToDouble(next)) == 0) {
                                    parts[ii] = "|+" + key.addSimplifiedFunction("0");
                                }
                                else if (((stringToDouble(current)) - stringToDouble(next)) < 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction(String.valueOf((stringToDouble(current)) - stringToDouble(next)).substring(1) + "x^|" + degree);
                                }
                                else {
                                    parts[ii] = "|+" + key.addSimplifiedFunction(String.valueOf((stringToDouble(current)) - stringToDouble(next)) + "x^|" + degree);
                                }
                                break;
                            }
                            else { //positive first + second
                                parts[i] = "0";
                                if (((stringToDouble(current)) + stringToDouble(next)) == 0) {
                                    parts[ii] = "|+" + key.addSimplifiedFunction("0");
                                }
                                else if (((stringToDouble(current)) + stringToDouble(next)) < 0) {
                                    parts[ii] = "|-" + key.addSimplifiedFunction(String.valueOf((stringToDouble(current)) + stringToDouble(next)).substring(1) + "x^|" + degree);
                                }
                                else {
                                    parts[ii] = "|+" + key.addSimplifiedFunction(String.valueOf((stringToDouble(current)) + stringToDouble(next)) + "x^|" + degree);
                                }
                                break;
                            }
                        }
                    }
                }
            }

            for(String j : parts){
                System.out.println(j);
            }
            System.out.println();

        }


        return parts;
    }

    /**
     * Gets the sign of the function, either a - or +.
     * @param function the function to be referenced.
     * @return the sign of the function.
     */
    public static char getSign(String function) {
        System.out.println("aL:" + function);
        char foo = '+';
        for (char i : function.toCharArray()) {
            if (i == '-') {
                foo = '-';
            }
        }
        return foo;
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

        if (left == "0" || right == "0") {
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
     * @param inner    the number to be taken.
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
     */
    public static String getDegree(String function) {

        if (function.contains("^")) {
            return function.split("\\^", 2)[1].replaceAll("\\|| ", "");
        }
        else if (function.contains("x")) {
            return "Lone x";
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

        if (function.equals("x")) {
            return 1;
        }
        else if (function.matches(".*\\d+.*")) { //checks if the function has any number in it at all

            if(function.charAt(0) == 'x'){
                toDouble = "1";
            }
            else {
                for (char i : function.toCharArray()) {
                    if (Character.isDigit(i) || i == '.' || i == '-') {
                        toDouble += i;
                    }
                    else if (i == '^') {
                        break;
                    }
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