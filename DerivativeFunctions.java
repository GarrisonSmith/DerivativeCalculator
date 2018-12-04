package DerivativeCalculator;

public class DerivativeFunctions {

	public static String EtoX(String function) {
		return function; //this one is easy
	}
	
	public static String ProductRule(String function) {
		
		String[] split;
		split = function.split("\\*");
		
		return FunctionDeterminer.applyRule(split[0]) + "*" + split[1] + "+"  + split[0] + "*" + FunctionDeterminer.applyRule(split[1]);
	}
	
	public static String SimpleQuotientRule(String function) {
		return "11";
	}
	
	public static String QuotientRule(String function) {
		
		String[] split;
		split = function.split("/");
		
		return "((" + split[1] + "*" +FunctionDeterminer.applyRule(split[0]) + "-" + split[0] + "*" + FunctionDeterminer.applyRule(split[1]) + ")/(" + split[1] + ")^2)";
	}
	
	public static String DifferentiationRule(String function) {
		return "2";
	}
	
	public static String Sinusoidal(String function) {
		return "3";
	}
	
	public static String GeneralizedPowerRule(String function) {
		return "4";
	}
	
	public static String ExponentialRule(String function) {
		return "5";
	}
	
	public static String PowerRule(String function) {
		return "6";
	}
	
	public static String RemoveX(String function) {
		return function.replace("x", "");
	}
}
