package DerivativeCalculator;

public class FunctionDeterminer {
	
	public static boolean needsRule(String function) {
		
		if(applyProductRule(function)) {
			return true;
		}
		else if(applyQuotientRule(function)) {
			return true;
		}
		else if(applyDifferentiationRule(function)) {
			return true;
		}
		else if(applySinusoidal(function)) {
			return true;
		}
		else if(applyGeneralizedPowerRule(function)) {
			return true;
		}
		else if(applyExponentialRule(function)) {
			return true;
		}
		else if(applyPowerRule(function)) {
			return true;
		}
		
		return false;
	}
	
	public static String applyRule(String function) {
		
		if(applyEtoX(function)) {
			return DerivativeFunctions.EtoX(function);
		}
		else if(applyProductRule(function)) {
			return DerivativeFunctions.ProductRule(function);
		}
		else if(applySimpleQuotientRule(function)) {
			return DerivativeFunctions.SimpleQuotientRule(function);
		}
		else if(applyQuotientRule(function)) {
			return DerivativeFunctions.QuotientRule(function);
		}
		else if(applyDifferentiationRule(function)) {
			return DerivativeFunctions.DifferentiationRule(function);
		}
		else if(applySinusoidal(function)) {
			return DerivativeFunctions.Sinusoidal(function);
		}
		else if(applyGeneralizedPowerRule(function)) {
			return DerivativeFunctions.GeneralizedPowerRule(function);
		}
		else if(applyExponentialRule(function)) {
			return DerivativeFunctions.GeneralizedPowerRule(function);
		}
		else if(applyPowerRule(function)) {
			return DerivativeFunctions.PowerRule(function);
		}
		else if(applyLoneX(function)) {
			return "1";
		}
		else if(applyRemoveX(function)) {
			return DerivativeFunctions.RemoveX(function);
		}
		else if(applyConstant(function)) {
			return "0";
		}
		
		return function;
	}
	
	private static boolean applyEtoX(String function) {
		if(function.equals("e^(x)")) return true;
		return false;
	}
	
	private static boolean applyProductRule(String function) {
		if(function.contains("*")) return true;
		return false;
	}
	
	private static boolean applySimpleQuotientRule(String function) {
		String split[], split2[], split3[];
		String degree1 = "Uh";
		String degree2 = "Oh";
		int Num = 0;
		
		if(function.contains("/")) {
			split = function.split("/");
			
			if(split[0].contains("^")) {
				split2 = split[0].split("^");
				degree1 = split2[1];
			}
			else {
				Num++;
			}
			if(split[1].contains("^")) {
				split3 = split[1].split("^");
				degree2 = split3[1];
			}
			else {
				Num++;
			}
		}
		
		if(Num == 1 || degree1 != degree2) return true;
		
		return false;
	}
	
	private static boolean applyQuotientRule(String function) {
		if(function.contains("/")) return true;
		return false;
	}
	
	private static boolean applyDifferentiationRule(String function) {
		if(function.contains("ln")) return true;
		return false;
	}
	
	private static boolean applySinusoidal(String function) {
		if(function.contains("sin") || function.contains("cos") || function.contains("tan") || function.contains("sec") || function.contains("csc") || function.contains("cot")) return true;
		return false;
	}
	
	private static boolean applyGeneralizedPowerRule(String function) {
		String[] split;  
		
		if(function.contains("^")) {
			  split = function.split("^");
			  
			  if(split[0].contains("x") || split[0].contains("_") && split[1].contains("x") || split[1].contains("_")) {
				  return true;
			  }
		  }
		return false;
	}
	
	private static boolean applyExponentialRule(String function) {
		String split[];  
		
		if(function.contains("^")) {
			  split = function.split("^");
			  
			  if(split[1].contains("x") || split[1].contains("_")) {
				  return true;
			  }
		  }
		return false;
	}
	
	private static boolean applyPowerRule(String function) {
		if(function.contains("^")) return true;
		return false;
	}
	
	private static boolean applyLoneX(String function) {
		if(function == "x") return true;
		return false;
	}
	
	private static boolean applyRemoveX(String function) {
		if(function.contains("x")) return true;
		return false;
	}
	
	private static boolean applyConstant(String function) {
		
		for (char c : function.toCharArray())
		{
			if (!Character.isDigit(c)) return false;
		}
		
		return true;
	}
}
