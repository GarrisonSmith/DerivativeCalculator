package DerivativeCalculator;

import java.util.ArrayList;
import java.util.List;

public class DerivativeStringHandler {
	
	public static String StringGrouper(String function, VariableKey key) {
		//This function will do all other functions so we don't have to call each one.
		
		return "dear god";
	}
	
	/**
	 * @param function Function to be split up by the +'s and -'s.
	 * @return List containing pieces of the function split up by +'s and -'s.
	 */
	public static ArrayList<String> StringSplitter(String function) { 
		
		ArrayList<String> parts = new ArrayList<String>();
		
		String[] a = function.split("[-+]");
		
		for(int x = 0; x < a.length; x++) {
			parts.add(a[x]);
		}
		
		return parts;
	}
	
	/**
	 * Collapses all parenthesis of the given function.
	 * @param function String function that you want to collapse all parenthesis on. 
	 * @param key List that defines what each "i" string translates to.
	 * @return String with "i" strings that equal their corresponding value in the Key list.
	 */
	public static String ParenthesisGrouper(String function, VariableKey key) {
		//Collapse the beginning function's highest tier parenthesis. 
		function = DerivativeStringHandler.ParenthesisGrouperLogic(function, key);
		
		//Iterates through the list to collapse parenthesis that were not highest tier in original function.
		for(int x = 0; x < key.getSize(); x++) {
			key.setIndex(x, DerivativeStringHandler.ParenthesisGrouperLogic(key.getIndex(x), key));
		}
		
		return function;
	}//end of method
	
	/**
	 * @param function String function that you want to collapse the highest tier parenthesis on. 
	 * @param key List that defines what each "i" string translates to.
	 * @return String with "i" strings that equal their corresponding value in the Key list.
	 */
	private static String ParenthesisGrouperLogic(String function, VariableKey key){
		
		StringBuffer parts = new StringBuffer(function); //Copy of function that allows for easier editing.
		
		int layers = 0; //Tracks number of layer of parenthesis the function is deep in.
		int start = 0; //Tracks the substring index lower bound that will be added to parts.
		int end = 0; //Tracks the substring index higher number bound that will be added to parts.
		
		//Iterates through the string until a ( is found (first if statement).
		for(; end < parts.length(); end++) {
			
			if(function.charAt(end) == '(') {
				
				start = end; //Sets start equal to part because this is the index number where the first ( was found.
				
				//Iterates through the number string until a matching ) is found for the ( found above.
				for(;layers != -1; end++) {
					
					//Executes if there is an unbalanced number of )'s compared to ('s.
					if(end == parts.length()) {
						function = "Function not valid notation, please enter a proper function";
						return function;
					}
					
					if(parts.charAt(end) == '(') {
						layers++;
					}
					else if(parts.charAt(end) == ')') {
						layers--;
						if(layers == 0) {
							layers = -1; //Escape condition, I don't know why I can't just put a break statement here?.
						}
					}
					
				}//end of inner for loop
				
				key.addKey(function.substring(start+1, end-1)); //Adds the grouped () to the key and assigns an i string.
				
				parts.replace(start, end, key.getKey(function.substring(start+1, end -1))); //Replaces the () substring with the i string.
				
				function = parts.toString();
				
				//Will call the function again if there is another top tier parenthesis further into the string.
				if(function.contains("(")) {
					function = ParenthesisGrouper(function, key);
				}
				
				return function;
				
			}//end of if statement
			
		}//end of first for loop
		
		
		return function;
	}//end of method
	
}//end of class