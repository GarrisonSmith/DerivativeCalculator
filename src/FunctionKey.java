package DerivativeCalculator;

import java.util.List;
import java.util.ArrayList;

/**
 *
 *
 * @author Garrison Smith 12/14/18
 */
public class FunctionKey {
    /**
     * List that contains the key and it's contents.
     */
    List<String[]> data;

    /**
     * The char that will be used in the keys.
     */
    char type;

    /**
     * Creates an ArrayList that will hold the key and it's contents.
     * Sets the default key type to be 'i'.
     */
    public FunctionKey(){
        data = new ArrayList();
        this.type = 'i';
    }

    /**
     * Creates an ArrayList that will hold the key and it's contents.
     * @param type the char that will be used inside the keys.
     */
    public FunctionKey(char type){
        data = new ArrayList();
        this.type = type;
    }

    /**
     * Checks to see if the function has already been added, if not then
     * generates a new unique key.
     * @param function the function being added.
     * @return the string representing the function that was given.
     */
    public String addFunction(String function){
        if(containsFunction(function) != null)
            return containsFunction(function);

        String[] array = new String[3];
        array[0] = KeyGenerator();
        array[1] = function;
        data.add(array);
        return array[0];
    }

    /**
     * Returns the original function corresponding with the given key.
     * @param key the key corresponding with the key.
     * @return the targeted function.
     * @throws IllegalArgumentException if the key is unknown.
     */
    public String getFunction(String key){
        for(String[] i : data){
            if(i[0] == key)
                return i[1];
        }

        throw new IllegalArgumentException("Unknown Key");
    }

    /**
     * Returns the derivative corresponding with the given key.
     * @param key the key corresponding to a derivative.
     * @return the targeted derivative.
     * @throws IllegalArgumentException if the key is unknown.
     */
    public String getDerivative(String key){
        for(String[] i : data){
            if(i[0] == key)
                return i[2];
        }

        throw new IllegalArgumentException("Unknown Key");
    }

    /**
     * Sets the derivative of the given key with the given derivative.
     * @param key the key to have the derivative plugged into.
     * @param derivative the derivative being plugged into the key.
     */
    public void setDerivative(String key, String derivative){
        for(String[] i : data){
            if(i[0] == key) {
                i[2] = derivative;
                break;
            }
        }
    }

    /**
     * Checks to see if the given key is already present in data.
     * @param key the key to be checked.
     * @return true if the key is found, false if not.
     */
    private boolean containsKey(String key){
        for(String[] i : data){
            if(i[0] == key)
                return true;
        }

        return false;
    }

    /**
     * Checks to see if the given function is already present in data.
     * @param function the function to be checked.
     * @return the key representing the function, null if the function is not found.
     */
    private String containsFunction(String function){
        for(String[] i : data){
            if(i[1] == function)
                return i[0];
        }

        return null;
    }

    /**
     * Generates a new key for a new function.
     * @return the key representing the function.
     */
    private String KeyGenerator() {
        String key = "_"+type;

        while(containsKey(key+"_")){
            key+=type;
        }

        return key+="_";
    }

    /**
     * Prints data for testing.
     */
    public void toSting(){
        int n = 0;
        for(String[] x : data){
            System.out.print("\nData index:["+n+"]: ");
            for(String y : x){
                System.out.print(y + "|");
            }
            n++;
        }
    }
}
