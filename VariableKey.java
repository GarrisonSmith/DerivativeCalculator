package DerivativeCalculator;

import java.util.ArrayList;
/**
 * Used to track the i string equivalence of functions. i strings are stings consisting of
 * _i_ with variable amount of i's that associate with and represent a stored function.
 */
public class VariableKey {
	
	/**
	 * Array-list used to contain the functions and their i string counterparts.
	 */
	private ArrayList<String> key = new ArrayList<String>();
	
	/**
	 * Creates an array-list of type String.
	 */
	public VariableKey() {
		
	}
	
	/**
	 * Searches for any associated i string with the given function, if none are
	 * found then generates a new one and adds the function and key to storage.
	 * @param function The function you want to add to storage and get a key for.
	 * @return the i string that is associated with the given function.
	 */
	public String addKey(String function) {
		
		if(key.contains(function)) {
			return key.get(key.indexOf(function)-1);
		}
		
		key.add(Idecider(function));
		key.add(function);
		return Idecider(function);
	}
	
	/**
	 * Sets the string of the given index equal to the given string.
	 * @param index The index you want to replace the contents of.
	 * @param replacement The string you want the index replaced with.
	 */
	public void setIndex(int index, String replacement) {
		key.set(index, replacement);
	}
	
	/**
	 * 
	 * @param function
	 * @return
	 */
	public String getKey(String function) {
		
		if(key.contains(function)) {
			return key.get(key.indexOf(function)-1);
		}
		
		return "Unknown Function";
		
	}
	
	/**
	 * 
	 * @param Ikey
	 * @return
	 */
	public String getFunction(String Ikey) {
		
		if(key.contains(Ikey)) {
			return key.get(key.indexOf(Ikey)+1);
		}
		
		return "Unknown Key";
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSize() {
		return key.size();
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public String getIndex(int index) {
		return key.get(index);
	}
	
	/**
	 * 
	 * @param function
	 * @return
	 */
	private String Idecider(String function) {
		
		if(key.contains(function)) {
			return key.get(key.indexOf(function)-1);
		}
		else {
			String i = "_i";
			
			while(key.contains(i + "_")) {
				i = i + "i";
			}
			i = i + "_";
			
			return i;
		}
	
	}
	
	
}