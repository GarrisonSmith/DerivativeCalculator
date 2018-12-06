package DerivativeCalculator;

public class FunctionDeterminer {
	
	public static String applyRule(String function, VariableKey key) {
		
		if(applyTestCase(function)) {
			return "F";
		}
		else if(applyConstant(function)) {
			return "(0)";
		}
		else if(applyEtoX(function)) {
			return function;
		}
		else if(applyProductRule(function)) {
			return DerivativeFunctions.ProductRule(function, key);
		}
		else if(applyQuotientRule(function)) {
			return DerivativeFunctions.QuotientRule(function, key);
		}
		else if(applySimpleDifferentiationRule(function)) {
			return "(1/x)";
		}
		else if(applyDifferentiationRule(function)) {
			return DerivativeFunctions.DifferentiationRule(function, key);
		}
		else if(applySimpleSinusoidal(function)) {
			return DerivativeFunctions.SimpleSinusoidal(function, key);
		}
		else if(applySinusoidal(function)) {
			return DerivativeFunctions.Sinusoidal(function, key);
		}
		else if(applyGeneralizedPowerRule(function)) {
			return DerivativeFunctions.GeneralizedPowerRule(function, key);
		}
		else if(applySimpleGeneralizedPowerRule(function)) {
			return DerivativeFunctions.SimpleGeneralizedPowerRule(function, key);
		}
		else if(applyPowerRule(function)) {
			return DerivativeFunctions.PowerRule(function, key);
		}
		else if(applyLoneX(function)) {
			return "(1)";
		}
		else if(applyRemoveX(function)) {
			return DerivativeFunctions.RemoveX(function, key);
		}
		
		return function;
	}
	
	private static boolean applyEtoX(String function) {
		if(function.equals("e^x") || function.equals("-e^x")) return true;
		return false;
	}
	
	private static boolean applyProductRule(String function) {
		if(function.contains("*")) return true;
		return false;
	}
	
	private static boolean applyQuotientRule(String function) {
		if(function.contains("/")) return true;
		return false;
	}
	
	private static boolean applySimpleDifferentiationRule(String function) {
		if(function.equals("lnx") || function.equals("(lnx)")) return true;
		return false;
	}
	
	private static boolean applyDifferentiationRule(String function) {
		if(function.contains("ln")) return true;
		return false;
	}
	
	private static boolean applySimpleSinusoidal(String function) {
		if(function.contains("sinx") || function.contains("cosx") || function.contains("tanx") || function.contains("secx") || function.contains("cscx") || function.contains("cotx")
		|| function.contains("(sinx)") || function.contains("(cosx)") || function.contains("(tanx)") || function.contains("(secx)") || function.contains("(cscx)") || function.contains("(cotx)")) return true;
		return false;
	}
	
	private static boolean applySinusoidal(String function) {
		if(function.contains("sin") || function.contains("cos") || function.contains("tan") || function.contains("sec") || function.contains("csc") || function.contains("cot")) return true;
		return false;
	}
	
	private static boolean applyGeneralizedPowerRule(String function) {
		String split[];  
		
		if(function.contains("^")) {
			  split = function.split("\\^");
			  
			  if((split[0].contains("x") || split[0].contains("_")) && (split[1].contains("x") || split[1].contains("_"))) {
				  return true;
			  }
		  }
		return false;
	}
	
	private static boolean applySimpleGeneralizedPowerRule(String function) {
		String[] split;  
		
		if(function.contains("^")) {
			  split = function.split("\\^");
			  
			  if(split[1].contains("x") || split[1].contains("_")) {
				  return true;
			  }
		  }
		return false;
	}
	
	private static boolean applyPowerRule(String function) {
		String[] split;  
		
		if(function.contains("^")) {
			  split = function.split("\\^");
			  
			  if(split[0].contains("x") || split[0].contains("_")) {
				  return true;
			  }
		  }
		return false;
	}
	
	private static boolean applyLoneX(String function) {
		if(function.equals("x")) return true;
		return false;
	}
	
	private static boolean applyRemoveX(String function) {
		if(function.contains("x")) return true;
		return false;
	}
	
	private static boolean applyConstant(String function) {
		if(!function.contains("_") && !function.contains("x")) {
			return true;
		}
		
		return false;
	}
	
	private static boolean applyTestCase(String function) {
		if(function.contains("|")) {
			return true;
		}
		return false;
	}
}
