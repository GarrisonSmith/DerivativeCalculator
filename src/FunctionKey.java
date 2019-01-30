package DerivativeCalculator;

import java.util.List;
import java.util.ArrayList;

/**
 * Data structure for storing keys that represent string functions.
 * Each one is stored into a string array order as {key, function, derivative}.
 * Uses an ArrayList of String[] as the backbone structure.
 *
 * @author Garrison Smith 12/15/18
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
        data=new ArrayList();
        this.type = 'i';
    }

    /**
     * Creates an ArrayList that will hold the key and it's contents.
     * @param type the char that will be used inside the keys.
     */
    public FunctionKey(char type){
        data=new ArrayList();
        this.type = type;
    }

    /**
     * Checks to see if the function has already been added, if not then
     * generates a new unique key.
     * @param function the function being added.
     * @return the string representing the function that was given.
     */
    public String addFunction(String function) {
        if (containsFunction(function) != null)
            return containsFunction(function);

        String[] array = new String[3];
        array[0] = keyGenerator();
        array[1] = function;
        data.add(array);

        removeRedundancy();

        try {
            return getFunctionKey(function);
        }
        catch(IllegalArgumentException e){
            return function;
        }
    }

    /**
     * Returns the key corresponding with the given function.
     * @param function the function corresponding with the key.
     * @return the targeted key.
     * @throws IllegalArgumentException if the function has no corresponding key.
     */
    public String getFunctionKey(String function){
        for(String[] i : data){
            if(i[1].equals(function)){
                return i[0];
            }
        }

        throw new IllegalArgumentException("Unknown Function: "+function);
    }

    /**
     * Returns the original function corresponding with the given key.
     * @param key the key corresponding with the function.
     * @return the targeted function.
     * @throws IllegalArgumentException if the key is unknown.
     */
    public String getFunction(String key){
        for(String[] i : data){
            if(i[0].equals(key))
                return i[1];
        }

        throw new IllegalArgumentException("Unknown Key: "+key);
    }

    /**
     * Returns the derivative corresponding with the given key.
     * @param key the key corresponding to a derivative.
     * @return the targeted derivative.
     * @throws IllegalArgumentException if the key is unknown.
     */
    public String getDerivative(String key){
        for(String[] i : data){
            if(i[0].equals(key))
                return i[2];
        }

        throw new IllegalArgumentException("Unknown Key: "+key);
    }

    /**
     * Sets the derivative of the given key with the given derivative.
     * @param key the key to have the derivative plugged into.
     * @param derivative the derivative being plugged into the key.
     */
    public void setDerivative(String key, String derivative){
        for(String[] i : data){
            if(i[0].equals(key)) {
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
            if(i[0].equals(key))
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
            if(i[1].equals(function))
                return i[0];
        }

        return null;
    }

    /**
     * Generates a new key for a new function.
     * @return the key representing the function.
     */
    private String keyGenerator() {
        String key="_"+type;

        while(containsKey(key+"_")){
            key+=type;
        }

        return key+="_";
    }

    /**
     * Removes redundant keys from data.
     * Redundant keys are keys that only represent other keys.
     */
    private void removeRedundancy(){
        String key="_"+type;

        for(int x=0; x < data.size(); x++){
            if(containsKey(data.get(x)[1])){
                data.remove(x);
            }
        }

        for(String[] i : data){
            i[0]=key+"_";
            key+=type;
        }
    }

    /**
     * Prints data's contents for testing.
     */
    public void print(){
        int n=0;
        for(String[] x : data){
            System.out.print("\nData index:["+n+"]:|");
            for(String y : x){
                System.out.print(y + "|");
            }
            n++;
        }
    }
}
