package DerivativeCalculator;

public class DerivativeCalculator {

    public static void main(String[] args) {
        //String function = "ln(1415x*14x/-1125x)*14^2^(135/-125)-123*sin(24x^2)";
        //String function = "ln(pi-4x)^2^sin(ln135/-125)";
        //String function = "(((((2*19810x)*-1125x1125*(19810x^2))/(-1125x)^2)/((19810x^2)/-1125x))*3.48458925+ln((19810x^2)/-1125x)*(0))-(0*sin(24x^2)+123*sin(24x^2))";
        String function = "(((((-445769572500x^2)*(19810x^2))/(-1125x^2))/((19810x^2)/-1125x))*3.48458925+ln((19810x^2)/-1125x)*0)-(0*sin(24x^2))";
        System.out.println(simplify(function));
    }

    /**
     * Takes the derivative of the input function.
     * @param input the function to have the derivative taken of.
     * @return the derivative of the input function.
     */
    public static String calculateDerivative(String input) {
        Function function = new Function(input);
        function.calculateDerivative();
        function.showSteps();
        System.out.println();
        return function.getOutput();
    }

    public static String simplify(String input){
        String next, current = input;
        do {
            next = current;
            Function function = new Function(current);
            current = function.simplify();
            function.showSteps();
            System.out.println();
        }
        while(!next.equals(current));
        return current;
    }
    
        public static double[][] getPoints(double xMin, double xMax, int numOfPoints, String function){
    	double[][] foo = new double[numOfPoints][2];
    	
    	int x = 0;
    	double increment = (xMax-xMin/(double)(numOfPoints-2));
    	for(double i = xMin; i < xMax; i+=increment) {
    		System.out.println("here: "+String.valueOf(i));
    		foo[x] = new double[] {i, Double.valueOf(simplify(function.replaceAll("x", String.valueOf(i))))};
    		x++;
    	}
    	foo[x] = new double[] {x, Double.parseDouble(simplify(function.replaceAll("x", String.valueOf(xMax))))};
    	
    	return foo;
    }
}
}
