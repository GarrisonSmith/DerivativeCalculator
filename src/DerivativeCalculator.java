package DerivativeCalculator;

public class DerivativeCalculator {

    public static void main(String[] args) {
        //String function = "ln(1415x*14x/-1125x)*14^2^(135/-125)-123*sin(24x^2)";
        //String function = "ln(pi-4x)^2^sin(ln135/-125)";
        //String function = "(((((2*19810x)*-1125x1125*(19810x^2))/(-1125x)^2)/((19810x^2)/-1125x))*3.48458925+ln((19810x^2)/-1125x)*(0))-(0*sin(24x^2)+123*sin(24x^2))";
        //String function = "(((((-445769572500x^2)*(19810x^2))/(-1125x^2))/((19810x^2)/-1125x))*3.48458925+ln((19810x^2)/-1125x)*0)-(0*sin(24x^2))";
        double temp[][] = getPoints(0, 10, 11, "2*x");
        for(double[] i : temp){
            for(double ii : i){
                System.out.println(ii+" ");
            }
            System.out.println();
        }
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

    /**
     * Simplifies the given function as much as possible.
     * @param input the function to be simplified.
     * @return the simplified function.
     */
    public static String simplify(String input){
        String next, current = input;
        do {
            next = current;
            Function function = new Function(current);
            current = function.simplify();
            //function.showSteps();
            System.out.println();
        }
        while(!next.equals(current));
        return current;
    }

    /**
     * Returns the value of the given function with the given x value plugged in.
     * @param x the value to be plugged in.
     * @param function the function to be used.
     * @return the evaluation of the function with the given x value.
     */
    public static double[] getPoint(double x, String function){
        return new double[]{x, Double.valueOf(simplify(function.replaceAll("x", String.valueOf(x))))};
    }

    /**
     * Returns an array containing the specified number of points within the xMin to xMax range.
     * If numOfPoints is less then 2, then it still returns 2 points.
     * Always returns a value at xMin and xMax.
     * @param xMin the minimum value to have its range returned.
     * @param xMax the maximum value to have its range returned.
     * @param numOfPoints the number of points to be returned.
     * @param function the function to be used.
     * @return a two dimensional array containing x values and their corresponding y values.
     */
    public static double[][] getPoints(double xMin, double xMax, int numOfPoints, String function) {
        double[][] foo = new double[numOfPoints][2];

        if(numOfPoints <= 2){
            foo[0] = getPoint(xMin, function);
            foo[1] = getPoint(xMax, function);
        }
        else if(numOfPoints > 2) {
            double increment = ((xMax - xMin) / (numOfPoints-1))-.0000000001; //off sets bug from adding repeating rational numbers
            int x = 0;
            for (double i = xMin; i <= xMax; i += increment) {
                foo[x] = getPoint(i, function);
                x++;
            }

        }

        return foo;
    }
}