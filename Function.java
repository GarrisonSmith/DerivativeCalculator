package DerivativeCalculator;

import java.util.ArrayList;

public class Function {
	
	private String input;
	private String current;
	private String output = "Not Found Yet";
	private VariableKey key = new VariableKey();
	private ArrayList<String> parts;
	private ArrayList<String> derivativeParts; //Parts but with the derivative taken
	
	public Function(String input) {
		this.input = input;
		current = input;
	} 
	
	public String getDerivative() {
		
		return output;
	}
	
	public String formatFunction() {
		current = DerivativeStringFormater.stringFormater(current, key, parts);
		return current;
	}
	
	public String getInput() {
		return input;
	}
	
	public String getCurrent() {
		return current;
	}
	
	public String getOutput() {
		return output;
	}
	
	public ArrayList<String> getKey(){
		return key;
	}
	
	public ArrayList<String> getParts(){
		return parts;
	}

	public void printKey() {
		for(int x = 0; x < key.size(); x++) {
			System.out.println(key.get(x));
		}
	}
	
	public void printParts() {
		for(int x = 0; x < parts.size(); x++) {
			System.out.println(parts.get(x));
		}
	}
	
	public String toString() {
		return "Input Function: "+input+" Current Function: "+current+" Output Function: "+output;
	}
}
