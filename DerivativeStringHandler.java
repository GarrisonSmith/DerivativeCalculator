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
	 * @param function The string function you want to format to be workable with the derivative calculator.
	 * @param parts Array-list where the function, split up by +'s and -'s, will be saved.
	 * @param key VariableKey object where i strings and their associated functions will to be saved. 
	 * @return Returns the current state of the function, unsplit, after all modification are done.
	 */
	public static String stringFormater(String function, ArrayList<String> parts, VariableKey key) {

		function = function.replace(" ", ""); //Removes any spaces in the initial function.
		
		function = parenthesisCollapser(function, key); //Collapses any initial parenthesis in the function.
		
		function = parenthesisGrouper(function, key); //Groups together any parts of the function that can be inferred.
		
		function = stringSplitHelper(function); //Sets up the function to be split by +'s and -'s.
		
		parts.addAll(stringSplitter(function)); //Split up the function by the +'s and -'s and adds the parts to parts.
		
		parenthesisGrouper(key); //Groups anything currently in key that can be grouped.
		
		function = function.replaceAll(" ", ""); //Removes any lingering spaces in the function.
		
		return function;
	}
	
	public static String stringFormater(String function, VariableKey key) {

		char first = function.charAt(0);
		
		function = function.substring(1, function.length());
		
		function = function.replace(" ", ""); //Removes any spaces in the initial function.
		
		function = parenthesisCollapser(function, key); //Collapses any initial parenthesis in the function.
		
		function = parenthesisGrouper(function, key); //Groups together any parts of the function that can be inferred.
		
		parenthesisGrouper(key); //Groups anything currently in key that can be grouped.
		
		function = function.replaceAll(" ", ""); //Removes any lingering spaces in the function.
		
		function = first + function;
		
		return function;
	}
	
	/**
	 * Expands all i keys out to be the functions they represent.
	 * @param function The function you want the i keys to be expanded out in.
	 * @param key The key holding the definitions for the i key you want to expand out.
	 * @return the function, now with all i keys expanded.
	 */
	public static String iExpander(String function, VariableKey key) {
		while(function.contains("_")) {
			function = iExpanderHelper(function, key);
		}
		
		return function;
	}
	
	private static String iExpanderHelper(String function, VariableKey key) {
		int start = 0;
		int end = 0;
		
		for(start = 0; start < function.length(); start++) {
				
			if(function.charAt(start) == '_') {
					
				for(end = start+1; end < function.length(); end++) {
						
					if(function.charAt(end) == '_') {
							function = function.substring(0, start) + "(" + key.getFunction(function.substring(start, end+1)) + ")" + function.substring(end+1, function.length());
							return function;
					}
				}
			}
		}
		
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
		
		return parts;
	}
	
	/**
	 * Sets up the function to be split by stringSplitter by adding a space before each - and +.
	 * @param function The Function that is being set up to be split by stringSplitter.
	 * @return The function, now set up to perform undergo stringSplitter.
	 */
	private static String stringSplitHelper(String function) {
		
		if(function.charAt(0) != '+' || function.charAt(0) != '-') {
			function = "+" + function;
		}
		
		for(int x = 0; x < function.length(); x++) {
			if(function.charAt(x) == '+' || function.charAt(x) == '-') {
				if(x == 0) {
					function = " " + function;
					x++;
				}
				else {
					function = function.substring(0, x) + " " + function.substring(x, function.length());
					x++;
				}
			}
		}
		
		return function;
	}
	
	/**
	 * Condenses all the functions in key as much as possible.
	 * @param key The key to have all of it's function condensed down in.
	 */
	private static void parenthesisGrouper(VariableKey key) {
		
		for(int x = 1; x < key.getSize(); x+=2) {
			while(parenthesisGrouperHelperHelper(key.getIndex(x))) {
				 key.setIndex(x, parenthesisGrouperHelper(key.getIndex(x)));
				key.setIndex(x, parenthesisCollapserHelper(key.getIndex(x), key));
			}
		}
	}
	
	/**
	 * Condenses the given function as much as can possibly be done.
	 * @param function The function to be condensed.
	 * @param key key to have the condensed form solutions coded into.
	 * @return the function now condensed.
	 */
	private static String parenthesisGrouper(String function, VariableKey key) {
		
		while(parenthesisGrouperHelperHelper(function)) {
			function = parenthesisGrouperHelper(function);
			function = parenthesisCollapser(function, key);
		}
		
		function = parenthesisGrouperHelper(function);
		function = parenthesisCollapser(function, key);
		
		return function;
	}
	
	/**
	 * Puts parenthesis around the first set of numbers it find, reinforces order of operations.
	 * @param function The function to have parenthesis added in.
	 * @return The function, now with parenthesis added in.
	 */
	private static String parenthesisGrouperHelper(String function) {

		int start = 0; //Tracks where a operator was first found.
		int end = 0; //Tracks where a new operator was found.
		
		for(; start < function.length(); start++) {
				
			if(function.charAt(start) == '*' || function.charAt(start) == '/' || function.charAt(start) == '^') {
			
				for(end = start+1; end < function.length(); end++) {
					
					if(function.charAt(end) == '*' || function.charAt(end) == '/' || function.charAt(end) == '^' || function.charAt(end) == '+' || function.charAt(end) == '-') {
						
						function = "(" + function.substring(0, end) + ")" + function.substring(end, function.length());
						
						return function;
					}
				}
			}
			else if(function.charAt(start) == '+' || function.charAt(start) == '-') {
				function = function.substring(0, start+1) + parenthesisGrouperHelper(function.substring(start+1, function.length()));
				return function;
			}
		}
		
		//function = "(" + function + ")";
		return function;
	}
	
	/**
	 * Method to decided if parenthesis need to be added or not.
	 * @param function The function to be evaluated.
	 * @return True if the function should have parenthesis added, false if it should not.
	 */
	private static boolean parenthesisGrouperHelperHelper(String function) {
		int count = 0;
		
		for(int x = 0; x < function.length(); x++) {
			if(function.charAt(x) == '*' || function.charAt(x) == '/' || function.charAt(x) == '^') {
				count++;
			}
		}
		
		if(count > 1) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Collapses all parenthesis of the given function.
	 * @param function String function that you want to collapse all parenthesis on. 
	 * @param key List that defines what each "i" string translates to.
	 * @return String with "i" strings that equal their corresponding value in the Key list.
	 */
	private static String parenthesisCollapser(String function, VariableKey key) {
		//Collapse the beginning function's highest tier parenthesis. 
		function = parenthesisCollapserHelper(function, key);
		
		//Iterates through the list to collapse parenthesis that were not highest tier in original function.
	
		for(int x = 0; x < key.getSize(); x++) {
			key.setIndex(x, parenthesisCollapserHelper(key.getIndex(x), key));
		}
		
		return function;
	}
	
	/**
	 * @param function String function that you want to collapse the highest tier parenthesis on. 
	 * @param key List that defines what each "i" string translates to.
	 * @return String with "i" strings that equal their corresponding value in the Key list.
	 */
	private static String parenthesisCollapserHelper(String function, VariableKey key){
		
		StringBuffer change = new StringBuffer(function); //Copy of function that allows for easier editing.
		
		int layers = 0; //Tracks number of layer of parenthesis the function is deep in.
		int start = 0; //Tracks the substring index lower bound that will be added to parts.
		int end = 0; //Tracks the substring index higher number bound that will be added to parts.
		
		//Iterates through the function until a ( is found (first if statement).
		for(; end < change.length(); end++) {
			
			if(function.charAt(end) == '(') {
				
				start = end; //Sets start equal to end because this is the index number where the first ( was found.
				
				//Iterates through the function until a matching ) is found for the ( found above.
				for(;; end++) {
					
					//Executes if there is an unbalanced number of )'s compared to ('s.
					if(end == change.length()) {
						function = "Function not valid notation, please enter a proper function";
						return function;
					}
					
					if(change.charAt(end) == '(') {
						layers++;
					}
					else if(change.charAt(end) == ')') {
						layers--;
						if(layers == 0) {
							break; //breaks out of the inner for loop.
						}
					}
					
				}//end of inner for loop
				
				key.addKey(function.substring(start+1, end)); //Adds the grouped () to the key and assigns an i string.
				
				change.replace(start, end+1, key.getKey(function.substring(start+1, end))); //Replaces the () substring with the i string.
				
				function = change.toString();
				
				//Will call the function again if there is another top tier parenthesis further into the string.
				if(function.contains("(")) {
					function = parenthesisCollapserHelper(function, key);
				}
				
				return function;
			}//end of if statement
			
		}//end of first for loop
		
		
		return function; //if the function has no ('s.
	}
	
}
