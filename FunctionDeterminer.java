ppackage DerivativeCalculator;

public class FunctionDeterminer {

	private boolean applyProductRule(String function) {
		if(function.contains("*")) return true;
		return false;
	}
	
	private boolean applyPowerRule(String function) {
		if(function.contains("^")) return true;
		return false;
	}
	
	private boolean applyQuotientRule(String function) {
		if(function.contains("/")) return true;
		return false;
	}
	
}
