package DerivativeCalculator;

public class ApplyDerivative {

    public static String ProductRule(String inFunction, FunctionKeyList key) {
        String[] split;
        String function;

        if(inFunction.charAt(0) == '(' && inFunction.charAt(inFunction.length()-1) == ')') {
            function = inFunction.substring(1, inFunction.length()-1);
        }
        else {
            function = inFunction;
        }
        split = function.split("\\*", 2);
        for(int x = 0; x<2; x++) {
            if(split[x].charAt(0) == '(' && split[x].charAt(split[x].length()-1) == ')') {
                split[x] = split[x].substring(1, split[x].length()-1);
            }
        }

        if(key.containsKey(split[0]) && key.containsKey(split[1])) {
            return "(" + split[0] + "*(" + key.getFunction(split[1]) + ")+("  + key.getFunction(split[0]) + ")*" + split[1] + ")";
        }
        else if(key.containsKey(split[0])) {
            return "(" + split[0] + "*(" + key.getFunction(split[1]) + ")+"  + split[0] + "*" + DerivativeRuleDeterminer.applyRule(split[1], key) + ")";
        }
        else if(key.containsKey(split[1])) {
            return "(" + DerivativeRuleDeterminer.applyRule(split[0], key) + "*" + split[1] + "+"  + split[0] + "*(" + key.getFunction(split[1]) + "))";
        }
        else {
            return "(" + DerivativeRuleDeterminer.applyRule(split[0], key) + "*" + split[1] + "+"  + split[0] + "*" + DerivativeRuleDeterminer.applyRule(split[1], key) + ")";
        }
    }

    public static String QuotientRule(String inFunction, FunctionKeyList key) {
        String[] split;
        String function;

        if(inFunction.charAt(0) == '(' && inFunction.charAt(inFunction.length()-1) == ')') {
            function = inFunction.substring(1, inFunction.length()-1);
        }
        else {
            function = inFunction;
        }
        split = function.split("/", 2);
        for(int x = 0; x<2; x++) {
            if(split[x].charAt(0) == '(' && split[x].charAt(split[x].length()-1) == ')') {
                split[x] = split[x].substring(1, split[x].length()-1);
            }
        }

        if(samePowers(split[0], split[1], key)) {
            return "(0)";
        }

        if(key.containsKey(split[0]) && key.containsKey(split[1])) {
            return "((" + split[1] + "*(" + key.getFunction(split[0]) + ")-" + split[0] + "*(" + key.getFunction(split[1]) + "))/(" + split[1] + ")^2)";
        }
        else if(key.containsKey(split[0])) {
            return "((" + split[1] + "*(" + key.getFunction(split[0]) + ")-" + split[0] + "*" + DerivativeRuleDeterminer.applyRule(split[1], key) + ")/(" + split[1] + ")^2)";
        }
        else if(key.containsKey(split[1])) {
            return "((" + split[1] + "*" + DerivativeRuleDeterminer.applyRule(split[0], key) + "-" + split[0] + "*(" + key.getFunction(split[1]) + "))/(" + split[1] + ")^2)";
        }
        else {
            return "((" + split[1] + "*" + DerivativeRuleDeterminer.applyRule(split[0], key) + "-" + split[0] + "*" + DerivativeRuleDeterminer.applyRule(split[1], key) + ")/(" + split[1] + ")^2)";
        }
    }

    public static String DifferentiationRule(String inFunction, FunctionKeyList key) {
        String function;

        if(inFunction.charAt(0) == '(' && inFunction.charAt(inFunction.length()-1) == ')') {
            function = inFunction.substring(1, inFunction.length()-1);
        }
        else {
            function = inFunction;
        }
        function = function.substring(2, function.length());

        if(key.containsKey(function)) {
            return "(" + function + "(1/(" + key.getFunction(function) + ")))";
        }
        else {
            return "(" + DerivativeRuleDeterminer.applyRule(function, key) + "(1/" + function + "))";
        }
    }

    public static String SimpleSinusoidal(String inFunction, FunctionKeyList key) {
        String function;

        if(inFunction.charAt(0) == '(' && inFunction.charAt(inFunction.length()-1) == ')') {
            function = inFunction.substring(1, inFunction.length()-1);
        }
        else {
            function = inFunction;
        }

        switch(function) {

            case "sinx" : return "(consx)";
            case "cosx" : return "(-(sinx))";
            case "tanx" : return "((secx)^2)";
            case "secx" : return "((secx)*(tanx))";
            case "cscx" : return "(-((cscx)*(cotx)))";
            case "cotx" : return "(-((cscx)^2))";
            default : return "Oh No";

        }
    }

    public static String Sinusoidal(String inFunction, FunctionKeyList key) {
        String function;
        String header;

        if(inFunction.charAt(0) == '(' && inFunction.charAt(inFunction.length()-1) == ')') {
            function = inFunction.substring(1, inFunction.length()-1);
        }
        else {
            function = inFunction;
        }

        header = function.substring(0, 3);
        function = function.substring(3, function.length());

        switch(header) {

            case "sin" : return "((" + function + ")*(cos(" + FunctionFormater.functionKeyExpander(function, key)  +")))";
            case "cos" : return "((" + function + ")*(-(sin(" + FunctionFormater.functionKeyExpander(function, key)  +"))))";
            case "tan" : return "((" + function + ")*((sec(" + FunctionFormater.functionKeyExpander(function, key)  +"))^2))";
            case "sec" : return "((" + function + ")*((sec(" + FunctionFormater.functionKeyExpander(function, key)  +"))*"+"((tan(" + FunctionFormater.functionKeyExpander(function, key) + "))))";
            case "csc" : return "((" + function + ")*(-((csc(" + FunctionFormater.functionKeyExpander(function, key)  +")))*"+"((cot(" + FunctionFormater.functionKeyExpander(function, key) + "))))";
            case "cot" : return "((" + function + ")*(-((csc(" + FunctionFormater.functionKeyExpander(function, key)  +"))^2)))";
            default : return "Oh No";

        }
    }

    public static String GeneralizedPowerRule(String inFunction, FunctionKeyList key) {
        String[] split;
        String function;

        if(inFunction.charAt(0) == '(' && inFunction.charAt(inFunction.length()-1) == ')') {
            function = inFunction.substring(1, inFunction.length()-1);
        }
        else {
            function = inFunction;
        }

        split = function.split("\\^", 2);
        for(int x = 0; x<2; x++) {
            if(split[x].charAt(0) == '(' && split[x].charAt(split[x].length()-1) == ')') {
                split[x] = split[x].substring(1, split[x].length()-1);
            }
        }

        if(key.containsKey(split[0]) && key.containsKey(split[1])) {
            return "((" + FunctionFormater.functionKeyExpander(inFunction, key) + ")*" + DerivativeRuleDeterminer.applyRule("(ln("+key.getFunction(split[0])+")*"+ key.getFunction(split[1])+")", key) + ")";
        }
        else if(key.containsKey(split[0])) {
            return "((" + FunctionFormater.functionKeyExpander(inFunction, key) + ")*" + DerivativeRuleDeterminer.applyRule("(ln("+key.getFunction(split[0])+")*"+split[1]+")", key) + ")";
        }
        else if(key.containsKey(split[1])) {
            return "((" + FunctionFormater.functionKeyExpander(inFunction, key) + ")*" + DerivativeRuleDeterminer.applyRule("(ln("+split[0]+")*"+ key.getFunction(split[1]) +")", key) + ")";
        }
        else {
            return "((" + FunctionFormater.functionKeyExpander(inFunction, key) + ")*" + DerivativeRuleDeterminer.applyRule("(ln("+split[0]+")*"+split[1]+")", key) + ")";
        }
    }

    public static String SimpleGeneralizedPowerRule(String inFunction, FunctionKeyList key) {
        String[] split;
        String function;

        if(inFunction.charAt(0) == '(' && inFunction.charAt(inFunction.length()-1) == ')') {
            function = inFunction.substring(1, inFunction.length()-1);
        }
        else {
            function = inFunction;
        }

        split = function.split("\\^", 2);
        for(int x = 0; x<2; x++) {
            if(split[x].charAt(0) == '(' && split[x].charAt(split[x].length()-1) == ')') {
                split[x] = split[x].substring(1, split[x].length()-1);
            }
        }

        return "(ln(" + split[0] + ")*" + inFunction + "*" + DerivativeRuleDeterminer.applyRule(split[1], key) + ")";
    }

    public static String PowerRule(String inFunction, FunctionKeyList key) {
        String[] split;
        String function;

        System.out.println(inFunction);

        if(inFunction.charAt(0) == '(' && inFunction.charAt(inFunction.length()-1) == ')') {
            function = inFunction.substring(1, inFunction.length()-1);
        }
        else {
            function = inFunction;
        }

        split = function.split("\\^", 2);
        for(int x = 0; x<2; x++) {
            if(split[x].charAt(0) == '(' && split[x].charAt(split[x].length()-1) == ')') {
                split[x] = split[x].substring(1, split[x].length()-1);
            }
        }

        int expo = Integer.parseInt(split[1]);

        return "(("+expo+")"+split[0]+")^"+"("+(expo-1)+")";
    }

    public static String RemoveX(String inFunction, FunctionKeyList key) {
        return inFunction.replace("x", "");
    }

    private static boolean samePowers(String function1, String function2, FunctionKeyList key) {
        String split1[], split2[];
        String degree1 = "Uh";
        String degree2 = "Oh";
        int Num = 0;

        if(key.containsKey(function1)) {
            function1 = key.getFunction(function1);
        }
        if(key.containsKey(function2)) {
            function2 = key.getFunction(function2);
        }

        if(function1.contains("^")) {
            split1 = function1.split("\\^");
            degree1 = split1[1];
        }
        else {
            Num++;
        }

        if(function2.contains("^")) {
            split2 = function2.split("\\^");
            degree2 = split2[1];
        }
        else {
            Num++;
        }

        if(Num != 0 || degree1.equals(degree2)) return true;

        return false;
    }

}