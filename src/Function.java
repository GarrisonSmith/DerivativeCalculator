package DerivativeCalculator;

import java.util.ArrayList;

/**
 * Used to hold everything we need to in order to take the derivative of a function, also neatly provides ways
 * to carry out derivative operations.
 */
public class Function {
	
	private String input = "Nothing has been Inputted"; //The function we start with
	private String current = "Nothing In Progress"; //Function after currently applied operations
	private String output = "Not Found Yet"; //What we end up with.
	private VariableKey key = new VariableKey(); //Tracks the i keys and the functions they represent
	private VariableKey derivativeKey = new VariableKey(); //Key but with the corresponding derivatives taken
	private ArrayList<String> parts = new ArrayList<String>(); //Contains the function split up by the +'s and -'s
	
	/**
	 * Creates a function object with no initial input.
	 */
	public Function() {
		
	} 
	
	/**
	 * Creates a function object with a initial input.
	 * @param input Input function.
	 */
	public Function(String input) {
		this.input = input;
		current = input;
	} 
	
	/**
	 * Takes the derivative of everything in key.
	 */
	public void fillDerivativeKey() {
		for(int x = 1; x <= key.getSize(); x+=2) {
			derivativeKey.forceAddKey(FunctionDeterminer.applyRule(key.getIndex(x), key));
			//derivativeKey.addKey(key.getIndex(x));
		}
	}
	
	/**
	 * Takes the derivative of the input function and sets it equal to output.
	 */
	public void takeDerivative() {
		output = constructDerivative(true);
	}
	
	/**
	 * Formats the function to something we can work with.
	 */
	public void formatFunction() {
		current = DerivativeStringHandler.stringFormater(input, parts, key);
	}
	
	/**
	 * Sets the input of the function.
	 * @param function String to be made the input.
	 */
	public void setInput(String function) {
		input = function;
	}
	
	/**
	 * Method to return the current input.
	 * @return the input.
	 */
	public String getInput() {
		return input;
	}
	
	/**
	 * Method to return the current state of the function.
	 * @return the current state of the function.
	 */
	public String getCurrent() {
		return current;
	}
	
	/**
	 * Method to return the output of the function.
	 * @return the output of the function.
	 */
	public String getOutput() {
		return output;
	}
	
	/**
	 * Method to return the key for the i strings.
	 * @return the key for the i strings.
	 */
	public VariableKey getKey(){
		return key;
	}
	
	/**
	 * Method to return the derivative key for the i strings.
	 * @return the derivative key for the i strings.
	 */
	public VariableKey getDerivativeKey(){
		return derivativeKey;
	}
	
	/**
	 * Method to return the parts for the split up function.
	 * @return parts of the split up function.
	 */
	public ArrayList<String> getParts(){
		return parts;
	}
	
	/**
	 * Method to print out the contents of the i key.
	 */
	public void printKey() {
		key.printKey();
	}
	
	/**
	 * Method to print out the contents of the derivative i key.
	 */
	public void printDerivativeKey() {
		derivativeKey.printKey();
	}
	
	/**
	 * Method to print out the contents of parts.
	 */
	public void printParts() {
		for(String x : parts) {
			System.out.println(x);
		}
	}
	
	/**
	 * Puts the function back together based off what is found in parts.
	 * @param expand Boolean for if you want the i keys to be expanded or not.
	 * @return the function reconstructed based off the contents of parts.
	 */
	public String constructFunction(boolean expand) {
		String function = "";
		
		for(String x : parts) {
			function += x;
		}
		
		if(expand) {
			function = DerivativeStringHandler.iExpander(function, key);
		}
		
		return function;
	}
	
	/**
	 * Puts the derivative function together based off what is found in derivative parts.
	 * @param expand Boolean for if you want the i keys to be expanded or not.
	 * @return the derivative reconstructed based off the contents of derivative parts.
	 */
	public String constructDerivative(boolean expand) {
		String function = "";
		
		for(String x : parts) {
			function += x;
		}
		
		if(expand) {
			function = DerivativeStringHandler.iExpander(function, derivativeKey);
		}
		
		return function;
	}
	
	public void expandParts() {
		for(int x = 0; x < parts.size(); x++) {
			parts.set(x, DerivativeStringHandler.iExpander(parts.get(x), key));
		}
		key.clearKey();
	}
	
	public void collapseParts() {
		for(int x = 0; x < parts.size(); x++) {
			parts.set(x, DerivativeStringHandler.stringFormater(parts.get(x), key));
		}
	}
	
	/**
	 * Method to get a string information on the current states of everything.
	 */
	public String toString() {
		takeDerivative();
		return "Input Function: "+input+" Current Function: "+current+" Output Function: "+output;
	}
}
