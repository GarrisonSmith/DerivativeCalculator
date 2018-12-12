package DerivativeCalculator;

public class DerivativeFunctions {

	public static String ProductRule(String inFunction, VariableKey key) {
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
		
		if(key.isKey(split[0]) && key.isKey(split[1])) {
			return "(" + split[0] + "*(" + key.getFunction(split[1]) + ")+("  + key.getFunction(split[0]) + ")*" + split[1] + ")";
		}
		else if(key.isKey(split[0])) {
			return "(" + split[0] + "*(" + key.getFunction(split[1]) + ")+"  + split[0] + "*" + FunctionDeterminer.applyRule(split[1], key) + ")";
		}
		else if(key.isKey(split[1])) {
			return "(" + FunctionDeterminer.applyRule(split[0], key) + "*" + split[1] + "+"  + split[0] + "*(" + key.getFunction(split[1]) + "))";
		}
		else {
			return "(" + FunctionDeterminer.applyRule(split[0], key) + "*" + split[1] + "+"  + split[0] + "*" + FunctionDeterminer.applyRule(split[1], key) + ")";
		}
	}
	
	public static String QuotientRule(String inFunction, VariableKey key) {
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
		
		if(key.isKey(split[0]) && key.isKey(split[1])) {
			return "((" + split[1] + "*(" + key.getFunction(split[0]) + ")-" + split[0] + "*(" + key.getFunction(split[1]) + "))/(" + split[1] + ")^2)";
		}
		else if(key.isKey(split[0])) {
			return "((" + split[1] + "*(" + key.getFunction(split[0]) + ")-" + split[0] + "*" + FunctionDeterminer.applyRule(split[1], key) + ")/(" + split[1] + ")^2)";
		}
		else if(key.isKey(split[1])) {
			return "((" + split[1] + "*" + FunctionDeterminer.applyRule(split[0], key) + "-" + split[0] + "*(" + key.getFunction(split[1]) + "))/(" + split[1] + ")^2)";
		}
		else {
			return "((" + split[1] + "*" + FunctionDeterminer.applyRule(split[0], key) + "-" + split[0] + "*" + FunctionDeterminer.applyRule(split[1], key) + ")/(" + split[1] + ")^2)";
		}
	}
	
	public static String DifferentiationRule(String inFunction, VariableKey key) {
		String function;
		
		if(inFunction.charAt(0) == '(' && inFunction.charAt(inFunction.length()-1) == ')') {
			function = inFunction.substring(1, inFunction.length()-1);
		}
		else {
			function = inFunction;
		}
		function = function.substring(2, function.length());
	
		if(key.isKey(function)) {
			return "(" + function + "(1/(" + key.getFunction(function) + ")))";
		}
		else {
			return "(" + FunctionDeterminer.applyRule(function, key) + "(1/" + function + "))";
		}
	}
	
	public static String SimpleSinusoidal(String inFunction, VariableKey key) {
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
	
	public static String Sinusoidal(String inFunction, VariableKey key) {
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
		
		case "sin" : return "((" + function + ")*(cos(" + DerivativeStringHandler.iExpander(function, key)  +")))";
		case "cos" : return "((" + function + ")*(-(sin(" + DerivativeStringHandler.iExpander(function, key)  +"))))";
		case "tan" : return "((" + function + ")*((sec(" + DerivativeStringHandler.iExpander(function, key)  +"))^2))";
		case "sec" : return "((" + function + ")*((sec(" + DerivativeStringHandler.iExpander(function, key)  +"))*"+"((tan(" + DerivativeStringHandler.iExpander(function, key) + "))))";
		case "csc" : return "((" + function + ")*(-((csc(" + DerivativeStringHandler.iExpander(function, key)  +")))*"+"((cot(" + DerivativeStringHandler.iExpander(function, key) + "))))";
		case "cot" : return "((" + function + ")*(-((csc(" + DerivativeStringHandler.iExpander(function, key)  +"))^2)))";
		default : return "Oh No";
		
		}
	}
	
	public static String GeneralizedPowerRule(String inFunction, VariableKey key) {
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
		
		if(key.isKey(split[0]) && key.isKey(split[1])) {
			return "((" + DerivativeStringHandler.iExpander(inFunction, key) + ")*" + FunctionDeterminer.applyRule("(ln("+key.getFunction(split[0])+")*"+ key.getFunction(split[1])+")", key) + ")";
		}
		else if(key.isKey(split[0])) {
			return "((" + DerivativeStringHandler.iExpander(inFunction, key) + ")*" + FunctionDeterminer.applyRule("(ln("+key.getFunction(split[0])+")*"+split[1]+")", key) + ")";
		}
		else if(key.isKey(split[1])) {
			return "((" + DerivativeStringHandler.iExpander(inFunction, key) + ")*" + FunctionDeterminer.applyRule("(ln("+split[0]+")*"+ key.getFunction(split[1]) +")", key) + ")";
		}
		else {
			return "((" + DerivativeStringHandler.iExpander(inFunction, key) + ")*" + FunctionDeterminer.applyRule("(ln("+split[0]+")*"+split[1]+")", key) + ")";
		}
	}
	
	public static String SimpleGeneralizedPowerRule(String inFunction, VariableKey key) {
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
		
		return "(ln(" + split[0] + ")*" + inFunction + "*" + FunctionDeterminer.applyRule(split[1], key) + ")";
	}
	
	public static String PowerRule(String inFunction, VariableKey key) {
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
		
		int expo = Integer.parseInt(split[1]);
		
		return "(("+expo+")"+split[0]+")^"+"("+(expo-1)+")";
	}
	
	public static String RemoveX(String inFunction, VariableKey key) {
		return inFunction.replace("x", "");
	}
	
	private static boolean samePowers(String function1, String function2, VariableKey key) {
		String split1[], split2[];
		String degree1 = "Uh";
		String degree2 = "Oh";
		int Num = 0;
		
		if(key.isKey(function1)) {
			function1 = key.getFunction(function1);
		}
		if(key.isKey(function2)) {
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
