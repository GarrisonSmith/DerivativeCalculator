package DerivativeCalculator;

import java.util.ArrayList;
/**
 * Used to take an input function and complete the needed modification to standardize the syntax so that the derivative
 * calculator functions can be processed.
 * @author Garrison Smith
 */
public class DerivativeStringHandler {
	
	/**
	 * This method completes all needed functions to separate and condense the function into something the calculator can work
	 * with.
	 * @param function The function you want to format to be workable with the derivative calculator.
	 * @param key VariableKey object where i strings and their associated functions will to be saved. 
	 * @param parts Array-list where the function, split up by +'s and -'s, will be saved.
	 * @return Returns the current state of the function, unsplit, after all modification are done.
	 */
	public static String stringFormater(String function, VariableKey key, ArrayList<String> parts) {
		
		function = function.replaceAll(" ", ""); //removes any space present at input.
		
		function = parenthesisGrouper(function, key); //Condenses all parenthesis in the function and assigns i string keys to them. 
		
		function = stringSplitHelper(function); //Sets up the function to be split up by the +'s and -'s.
		
		parts.addAll(stringSplitter(function)); //Splits up the function by the +'s and -'s.
		
		function = function.replaceAll(" ", ""); //removes any lingering spaces after transformations.
		
		return function;
	}
	
	/**
	 * @param function Function to be split up by the +'s and -'s.
	 * @return List containing pieces of the function split up by +'s and -'s.
	 */
	private static ArrayList<String> stringSplitter(String function) { 
		
		ArrayList<String> parts = new ArrayList<String>();
		String[] split;
		
		split = function.split(" ");
		
		for(int x = 1; x < split.length; x++) {
			parts.add(split[x]);
		}
		
		for(int x = 1; x < parts.size(); x+=2) {
			//parts.set(x, stringFormater(parts.get(x)));
		}
		
		return parts;
	}
	
	/**
	 * Works by having the function's input spaces removed, and then adding spaces before each - and +,
	 * then splitting up by the spaces.
	 * @param function Function to be split up by the +'s and -'s.
	 * @return List containing pieces of the function split up by +'s and -'s.
	 */
	private static ArrayList<String> stringSplitter(String function) { 
		
		ArrayList<String> parts = new ArrayList<String>();
		String[] split;
		
		split = function.split(" ");
		
		for(int x = 1; x < split.length; x++) {
			parts.add(split[x]);
		}
		
		for(int x = 1; x < parts.size(); x+=2) {
			//parts.set(x, stringFormater(parts.get(x)));
		}
		
		return parts;
	}
	
	/**
	 * Collapses all parenthesis of the given function.
	 * @param function String function that you want to collapse all parenthesis on. 
	 * @param key List that defines what each "i" string translates to.
	 * @return String with "i" strings that equal their corresponding value in the Key list.
	 */
	private static String parenthesisGrouper(String function, VariableKey key) {
		//Collapse the beginning function's highest tier parenthesis. 
		function = parenthesisGrouperLogic(function, key);
		
		//Iterates through the list to collapse parenthesis that were not highest tier in original function.
		for(int x = 0; x < key.getSize(); x++) {
			key.setIndex(x, parenthesisGrouperLogic(key.getIndex(x), key));
		}
		
		return function;
	}
	
	/**
	 * @param function String function that you want to collapse the highest tier parenthesis on. 
	 * @param key List that defines what each "i" string translates to.
	 * @return String with "i" strings that equal their corresponding value in the Key list.
	 */
	private static String parenthesisGrouperLogic(String function, VariableKey key){
		
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
					function = parenthesisGrouper(function, key);
				}
				
				return function;
				
			}//end of if statement
			
		}//end of first for loop
		
		
		return function; //if the function has no ('s.
	}
	
}//end of class
