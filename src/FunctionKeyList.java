package DerivativeCalculator;

import java.util.InputMismatchException;

public class FunctionKeyList {

    /**
     * The head of the list.
     */
    private FunctionNode head, tail;

    /**
     * Tracks the length of the list.
     */
    int length;

    /**
     * The char that will be used in the keys.
     */
    private char keyType;

    /**
     * Creates a new FunctionKey.
     *
     * @param keyType the char used in the function keys.
     */
    public FunctionKeyList(char keyType) {
        length = 0;
        this.keyType = keyType;
        head = tail = new FunctionNode();
    }

    /**
     * Creates a new FunctionKey.
     */
    public FunctionKeyList() {
        length = 0;
        keyType = 'i';
        head = tail = new FunctionNode();
    }

    /**
     * Adds a new function to the list.
     *
     * @param function the function being added.
     * @return the function key corresponding with the given function.
     */
    public String addFunction(String function) {
        if (head.getFunction() == null) {
            head.setFunction(function);
            head.setFunctionKey(keyGenerator());
            head.setIndex(length);
            tail = head;
            length++;
        } else if (containsFunction(function) != null) {
            //do nothing
        } else {
            FunctionNode foo = new FunctionNode(function, keyGenerator());
            foo.setIndex(length);
            tail.setNext(foo);
            foo.setPrev(tail);
            tail = foo;
            length++;
        }
        return getFunctionKey(function);
    }

    /**
     * Generates a new key for a new function.
     *
     * @return the key representing the function.
     */
    private String keyGenerator() {
        String key = "_" + keyType;

        while (containsKey(key + "_")) {
            key += keyType;
        }

        return key += "_";
    }

    /**
     * Checks whether the function key list contains the given function or not.
     *
     * @param function the function to be checked.
     * @return if the function key list already contains the function returns the
     * corresponding function key, null if not.
     */
    private String containsFunction(String function) {
        for (int x = 0; x < length; x++) {
            if (function.equals(getNode(x).getFunction())) {
                return getNode(x).getFunctionKey();
            }
        }
        return null;
    }

    /**
     * Check whether the function key list contains the given key or not.
     *
     * @param functionKey the key to be checked.
     * @return true if the key is found, false if it is not.
     */
    public boolean containsKey(String functionKey) {
        for (int x = 0; x < length; x++) {
            if (functionKey.equals(getNode(x).getFunctionKey())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints the all info inside of the function key list.
     */
    public void print() {
        for (int x = 0; x < length; x++) {
            getNode(x).print();
        }
    }

    /**
     * Gets the first function node in the list.
     *
     * @return the first function node in the list.
     */
    public FunctionNode getHead() {
        return head;
    }

    /**
     * Gets the FunctionNode with the given function key.
     *
     * @param functionKey the function key to be found.
     * @return the FunctionNode with the given function key.
     * @throws IllegalArgumentException when the given function key is invalid.
     */
    public FunctionNode getNode(String functionKey) {
        FunctionNode current = head;

        try {
            while (!current.getFunctionKey().equals(functionKey)) {
                current = current.getNext();
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Unknown function key: " + functionKey);
        }
        return current;
    }

    /**
     * Gets the FunctionNode with the given function.
     *
     * @param function the function to be found.
     * @return the FunctionNode with the given function key.
     * @throws IllegalArgumentException when the given function is not valid.
     */
    public FunctionNode getNodeFromFunction(String function) {
        FunctionNode current = head;

        try {
            while (!current.getFunction().equals(function)) {
                current = current.getNext();
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Unknown function: " + function);
        }
        return current;
    }

    /**
     * Gets the FunctionNode with the given index.
     *
     * @param index the index to be found.
     * @return the FunctionNode with the given index.
     * @throws IndexOutOfBoundsException when the given index is not valid.
     */
    public FunctionNode getNode(int index) {
        FunctionNode current = head;

        try {
            while (current.getIndex() != index) {
                current = current.getNext();
            }
        } catch (NullPointerException e) {
            throw new IndexOutOfBoundsException("Invalid Index: " + index);
        }
        return current;
    }

    /**
     * Returns the current length of the list.
     *
     * @return the length of the list.
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns the function at the index.
     *
     * @param functionKey the function key to get the function from.
     * @return the function from the index.
     */
    public String getFunction(String functionKey) {
        return getNode(functionKey).getFunction();
    }

    /**
     * Returns the function at the index.
     *
     * @param index the index to get the function from.
     * @return the function from the index.
     */
    public String getFunction(int index) {
        return getNode(index).getFunction();
    }

    /**
     * Returns the simplified function at the index.
     *
     * @param functionKey the function key to get the simplified function from.
     * @return the simplified function at the index.
     */
    public String getSimplifiedFunction(String functionKey) {
        return getNode(functionKey).getSimplifiedFunction();
    }

    /**
     * Returns the simplified function at the index.
     *
     * @param index the index to get the simplified function from.
     * @return the simplified function at the index.
     */
    public String getSimplifiedFunction(int index) {
        return getNode(index).getSimplifiedFunction();
    }

    /**
     * Returns the derivative corresponding with the function key.
     *
     * @param functionKey the function key to get the derivative from.
     * @return the derivative at the index.
     */
    public String getDerivaitve(String functionKey) {
        return getNode(functionKey).getDerivative();
    }

    /**
     * Returns the derivative at the index.
     *
     * @param index the index to get the derivative from.
     * @return the derivative at the index.
     */
    public String getDerivaitve(int index) {
        return getNode(index).getDerivative();
    }

    /**
     * Returns the simplified derivative corresponding with the function key.
     *
     * @param functionKey the function key to get the simplified derivative from.
     * @return the simplified derivative at the index.
     */
    public String getSimplifiedDerivative(String functionKey) {
        return getNode(functionKey).getSimplifiedDerivative();
    }

    /**
     * Returns the simplified derivative at the index.
     *
     * @param index the index to get the simplified derivative from.
     * @return the simplified derivative at the index.
     */
    public String getSimplifiedDerivative(int index) {
        return getNode(index).getSimplifiedDerivative();
    }

    /**
     * Returns the function key corresponding with the given function.
     *
     * @param function the function to get the function key from.
     * @return the function key at the index.
     */
    public String getFunctionKey(String function) {
        return getNodeFromFunction(function).getFunctionKey();
    }

    /**
     * Returns the function key at the index.
     *
     * @param index the index to get the function key from.
     * @return the function key at the index.
     */
    public String getFunctionKey(int index) {
        return getNode(index).getFunctionKey();
    }

    /**
     * Sets the given function key's function to be the given function.
     *
     * @param functionKey the function key to be set.
     * @param function    the simplified function to be set.
     * @param grouped     determines if the simplified derivative needs parenthesis or not.
     */
    public void setFunction(String functionKey, String function, boolean grouped) {
        getNode(functionKey).setFunction(function, grouped);
    }

    /**
     * Sets the given index's function to be the given function.
     *
     * @param index    the index to be set.
     * @param function the function to be set.
     * @param grouped  determines if the function needs parenthesis or not.
     */
    public void setFunction(int index, String function, boolean grouped) {
        getNode(index).setFunction(function, grouped);
    }

    /**
     * Sets the given function key's function to be the given function.
     *
     * @param functionKey to be set.
     * @param function    the function to be set.
     */
    public void setFunction(String functionKey, String function) {
        getNode(functionKey).setFunction(function);
    }

    /**
     * Sets the given index's function to be the given function.
     *
     * @param index    the index to be set.
     * @param function the function to be set.
     */
    public void setFunction(int index, String function) {
        getNode(index).setFunction(function);
    }

    /**
     * Sets the given function key's simplified function to be the given function.
     *
     * @param functionKey        the function key to be set.
     * @param simplifiedFunction the simplified function to be set.
     * @param grouped            determines if the simplified derivative needs parenthesis or not.
     */
    public void setSimplifiedFunction(String functionKey, String simplifiedFunction, boolean grouped) {
        getNode(functionKey).setSimplifiedFunction(simplifiedFunction, grouped);
    }

    /**
     * Sets the given index's simplified function to be the given function.
     *
     * @param index              the index to be set.
     * @param simplifiedFunction the simplified function to be set.
     * @param grouped            determines if the simplified function needs parenthesis or not.
     */
    public void setSimplifiedFunction(int index, String simplifiedFunction, boolean grouped) {
        getNode(index).setSimplifiedFunction(simplifiedFunction, grouped);
    }

    /**
     * Sets the given function key's simplified function to be the given function.
     *
     * @param functionKey        the function key to be set.
     * @param simplifiedFunction the simplified function to be set.
     */
    public void setSimplifiedFunction(String functionKey, String simplifiedFunction) {
        getNode(functionKey).setSimplifiedFunction(simplifiedFunction);
    }

    /**
     * Sets the given index's simplified function to be the given function.
     *
     * @param index              the index to be set.
     * @param simplifiedFunction the simplified function to be set.
     */
    public void setSimplifiedFunction(int index, String simplifiedFunction) {
        getNode(index).setSimplifiedFunction(simplifiedFunction);
    }

    /**
     * Sets the given function key's derivative to be the given derivative.
     *
     * @param functionKey the function key to be set.
     * @param derivative  the derivative to be set.
     * @param grouped     determines if the simplified derivative needs parenthesis or not.
     */
    public void setDerivative(String functionKey, String derivative, boolean grouped) {
        getNode(functionKey).setDerivative(derivative, grouped);
    }

    /**
     * Sets the given index's derivative to be the given derivative.
     *
     * @param index      the index to be set.
     * @param derivative the derivative to be set.
     * @param grouped    determines if the derivative needs parenthesis or not.
     */
    public void setDerivative(int index, String derivative, boolean grouped) {
        getNode(index).setDerivative(derivative, grouped);
    }

    /**
     * Sets the given function key's derivative to be the given derivative.
     *
     * @param functionKey the functionKey to be set.
     * @param derivative  the derivative to be set.
     */
    public void setDerivative(String functionKey, String derivative) {
        getNode(functionKey).setDerivative(derivative);
    }

    /**
     * Sets the given index's derivative to be the given derivative.
     *
     * @param index      the index to be set.
     * @param derivative the derivative to be set.
     */
    public void setDerivative(int index, String derivative) {
        getNode(index).setDerivative(derivative);
    }

    /**
     * Sets the given function key's simplified derivative to be the given derivative.
     *
     * @param functionKey          the function key to be set.
     * @param simplifiedDerivative the simplified derivative to be set.
     * @param grouped              determines if the simplified derivative needs parenthesis or not.
     */
    public void setSimplifiedDerivative(String functionKey, String simplifiedDerivative, boolean grouped) {
        getNode(functionKey).setSimplifiedDerivative(simplifiedDerivative, grouped);
    }

    /**
     * Sets the given index's simplified derivative to be the given derivative.
     *
     * @param index                the index to be set.
     * @param simplifiedDerivative the simplified derivative to be set.
     * @param grouped              determines if the simplified derivative needs parenthesis or not.
     */
    public void setSimplifiedDerivative(int index, String simplifiedDerivative, boolean grouped) {
        getNode(index).setSimplifiedDerivative(simplifiedDerivative, grouped);
    }

    /**
     * Sets the given function key's simplified derivative to be the given derivative.
     *
     * @param functionKey          the function key to be set.
     * @param simplifiedDerivative the simplified derivative to be set.
     */
    public void setSimplifiedDerivative(String functionKey, String simplifiedDerivative) {
        getNode(functionKey).setSimplifiedDerivative(simplifiedDerivative);
    }

    /**
     * Sets the given index's simplified derivative to be the given derivative.
     *
     * @param index                the index to be set.
     * @param simplifiedDerivative the simplified derivative to be set.
     */
    public void setSimplifiedDerivative(int index, String simplifiedDerivative) {
        getNode(index).setSimplifiedDerivative(simplifiedDerivative);
    }

    /**
     * Sets the given index's function key to be the given key.
     *
     * @param index       the index to be set.
     * @param functionKey the function key to be set.
     */
    public void setKey(int index, String functionKey) {
        getNode(index).setFunction(functionKey);
    }

    /**
     * Used to contain everything for each element inside of a FunctionKey.
     *
     * @author Garrison Smith 2/11/2019
     */
    public class FunctionNode {
        /**
         * Next acts as the next FunctionNode in the list, Prev acts as the previous.
         */
        private FunctionNode next, prev;

        /**
         * String array containing the following info corresponding
         * to index : {function, simplifiedFunction, derivative, simplifiedDerivative, FunctionKey}
         */
        private String[] data = {null, null, null, null, null};

        /**
         * Tracks the FunctionNodes index within a list.
         */
        private int index;

        /**
         * Boolean array that signifies if the corresponding data index needs parenthesis placed around it.
         * {function, simplifiedFunction, derivative, simplifiedDerivative}
         */
        private boolean[] grouped;

        /**
         * Tracks the type of each equation in data.
         */
        private FunctionType[] types;

        /**
         * Used to create a new FunctionNode.
         *
         * @param prev        sets the previous FunctionNode in the list.
         * @param function    sets the input function for this FunctionNode.
         * @param functionKey sets the key for the corresponding function.
         * @param grouped     sets whether the input function should be grouped or not.
         */
        public FunctionNode(FunctionNode prev, String function, String functionKey, boolean grouped) {
            next = null;
            this.prev = prev;
            //data = new String[5];
            data[0] = function;
            data[5] = functionKey;
            index = prev.getIndex() + 1;
            this.grouped = new boolean[4];
            this.grouped[0] = grouped;
            types = new FunctionType[5];
        }

        /**
         * Used to create a new FunctionNode.
         *
         * @param prev        sets the previous FunctionNode in the list.
         * @param function    sets the input function for this FunctionNode.
         * @param functionKey sets the key for the corresponding function.
         */
        public FunctionNode(FunctionNode prev, String function, String functionKey) {
            next = null;
            this.prev = prev;
            //data = new String[5];
            data[0] = function;
            data[5] = functionKey;
            index = prev.getIndex() + 1;
            this.grouped = new boolean[4];
            this.grouped[0] = true;
            types = new FunctionType[5];
        }

        /**
         * Used to create a new FunctionNode.
         *
         * @param function    sets the input function for this FunctionNode.
         * @param grouped     sets whether the input function should be grouped or not.
         * @param functionKey sets the key for the corresponding function.
         */
        public FunctionNode(String function, String functionKey, boolean grouped) {
            next = prev = null;
            //data = new String[5];
            data[0] = function;
            data[5] = functionKey;
            this.index = 0;
            this.grouped = new boolean[4];
            this.grouped[0] = grouped;
            types = new FunctionType[5];
        }

        /**
         * Used to create a new FunctionNode.
         *
         * @param function    sets the input function for this FunctionNode.
         * @param functionKey sets the key for the corresponding function.
         */
        public FunctionNode(String function, String functionKey) {
            next = prev = null;
            //data = new String[5];
            data[0] = function;
            data[4] = functionKey;
            this.index = 0;
            this.grouped = new boolean[4];
            this.grouped[0] = true;
            types = new FunctionType[5];
        }

        /**
         * Used to create a new FunctionNode.
         */
        public FunctionNode() {
            next = prev = null;
            //data = new String[5];
            //data[4] = null;
            this.index = 0;
            this.grouped = new boolean[4];
            this.grouped[0] = true;
            types = new FunctionType[5];
        }

        /**
         * Gets the current node set as previous.
         *
         * @return the previous node in the list.
         */
        public FunctionNode getPrev() {
            return prev;
        }

        /**
         * Gets the current node set as next.
         *
         * @return the next node in the list.
         */
        public FunctionNode getNext() {
            return next;
        }

        /**
         * Gets the specified index from data.
         * {function, simplifiedFunction, derivative, simplifiedDerivative}
         *
         * @param index the index to be returned.
         * @return the value in data corresponding with the given index.
         */
        public String getData(int index) {
            if (grouped[index]) {
                return "(" + data[index] + ")";
            }
            return data[index];
        }

        /**
         * Gets the current function for this FunctionNode.
         *
         * @return the function saved for this FunctionNode.
         */
        public String getFunction() {
            return data[0];
        }

        /**
         * Gets the current simplified function for this FunctionNode.
         *
         * @return the simplified function saved for this FunctionNode.
         */
        public String getSimplifiedFunction() {
            return data[1];
        }

        /**
         * Gets the current derivative for this FunctionNode.
         *
         * @return the derivative saved for this FunctionNode.
         */
        public String getDerivative() {
            return data[2];
        }

        /**
         * Gets the current simplified Derivative for this FunctionNode.
         *
         * @return the simplified derivative saved for this FunctionNode.
         */
        public String getSimplifiedDerivative() {
            return data[3];
        }

        /**
         * Gets the current function key saved for this FunctionNode.
         *
         * @return the function key saved for this FunctionNode.
         */
        public String getFunctionKey() {
            return data[4];
        }

        /**
         * Gets the current index for this FunctionNode.
         *
         * @return the current value of the index.
         */
        public int getIndex() {
            return index;
        }

        /**
         * Gets the current grouping status for a function at a index.
         *
         * @param index the index to get the grouping status of.
         * @return the grouping status of a function at the given index.
         */
        public boolean getGrouping(int index) {
            return grouped[index];
        }

        /**
         * Sets this FunctionNodes previous FunctionNode in a list.
         *
         * @param prev the previous FunctionNode in the list.
         */
        public void setPrev(FunctionNode prev) {
            this.prev = prev;
            upDateIndexes();
        }

        /**
         * Sets this FunctionNodes next FunctionNode in a list.
         *
         * @param next the next FunctionNode in the list.
         */
        public void setNext(FunctionNode next) {
            this.next = next;
            next.upDateIndexes();
        }

        /**
         * Sets this FunctionNode's function.
         *
         * @param function the function being set.
         * @param grouped  determines if the function should have parenthesis around it.
         */
        public void setFunction(String function, boolean grouped) {
            data[0] = function;
            this.grouped[0] = grouped;
        }

        /**
         * Sets this FunctionNode's function.
         *
         * @param function the function being set.
         */
        public void setFunction(String function) {
            data[0] = function;
            grouped[0] = true;
        }

        /**
         * Sets this FunctionNode's simplified function.
         *
         * @param function the function being set.
         * @param grouped  determines if the function should have parenthesis around it.
         */
        public void setSimplifiedFunction(String function, boolean grouped) {
            data[1] = function;
            this.grouped[1] = grouped;
        }

        /**
         * Sets this FunctionNode's simplified function.
         *
         * @param function the function being set.
         */
        public void setSimplifiedFunction(String function) {
            data[1] = function;
            grouped[1] = true;
        }

        /**
         * Sets this FunctionNode's derivative.
         *
         * @param derivative the derivative being set.
         * @param grouped    determines if the derivative should have parenthesis around it.
         */
        public void setDerivative(String derivative, boolean grouped) {
            data[2] = derivative;
            this.grouped[2] = grouped;
        }

        /**
         * Sets this FunctionNode's derivative.
         *
         * @param derivative the derivative being set.
         */
        public void setDerivative(String derivative) {
            data[2] = derivative;
            grouped[2] = true;
        }

        /**
         * Sets this FunctionNode's simplified derivative.
         *
         * @param derivative the derivative being set.
         * @param grouped    determines if the derivative should have parenthesis around it.
         */
        public void setSimplifiedDerivative(String derivative, boolean grouped) {
            data[3] = derivative;
            this.grouped[3] = grouped;
        }

        /**
         * Sets this FunctionNode's simplified derivative.
         *
         * @param derivative the derivative being set.
         */
        public void setSimplifiedDerivative(String derivative) {
            data[3] = derivative;
            grouped[3] = true;
        }

        /**
         * Sets this FunctionNode's function key.
         *
         * @param functionKey the function key being set.
         */
        public void setFunctionKey(String functionKey) {
            data[4] = functionKey;
        }

        /**
         * Set this FunctionNodes data values.
         *
         * @param function the input into data.
         * @param grouped  True indicates that the corresponding index needs parenthesis, false
         *                 that it does not.
         * @param index    the index to be updated.
         */
        public void setData(String function, boolean grouped, int index) {
            data[index] = function;
            if (index != 4) {
                setGrouping(grouped, index);
            }
        }

        /**
         * Set this FunctionNodes data values.
         *
         * @param function the input into data.
         * @param index    the index to be updated.
         */
        public void setData(String function, int index) {
            data[index] = function;
            if (index != 4) {
                setGrouping(true, index);
            }
        }

        /**
         * Sets this FunctionNodes index.
         *
         * @param index the new index.
         */
        private void setIndex(int index) {
            this.index = index;
        }

        /**
         * Sets this FunctionNodes grouping values.
         *
         * @param grouped True indicates that the corresponding index needs parenthesis, false
         *                that it does not.
         * @param index   the index to be changed.
         */
        private void setGrouping(boolean grouped, int index) {
            this.grouped[index] = grouped;
        }

        /**
         * Checks to see if there is a previous node in the list.
         *
         * @return True if there's a previous node, False if there is not.
         */
        public boolean hasPrevious() {
            if (prev != null)
                return true;
            return false;
        }

        /**
         * Checks to see if there is a next node in the list.
         *
         * @return True if there's a next node, False if there is not.
         */
        public boolean hasNext() {
            if (next != null)
                return true;
            return false;
        }

        /**
         * Ensure that this node and all following nodes have the correct index, according
         * to this nodes previous node.
         */
        private void upDateIndexes() {
            if (!hasPrevious()) {
                setIndex(0);
            } else {
                index = prev.getIndex() + 1;
            }

            if (next != null) {
                next.upDateIndexes();
            }
        }

        /**
         * Prints out all info in the FunctionNode.
         */
        public void print() {
            System.out.print("Index[" + index + "]:{" + data[4] + "}{" + data[0] + " " + grouped[0] + "}{" +
                    data[1] + " " + grouped[1] + "}{" + data[2] + " " + grouped[2] + "}{" + data[3] + " " + grouped[3] + "}\n");
        }
    }
}