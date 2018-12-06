package DerivativeCalculator;

import java.util.Scanner;

public class DerivativeCalculator {
	
	package DerivativeCalculator;

import java.util.Scanner;

public class DerivativeCalculator {
	
	public static void main (String[] args) {
		
		
		Function function = new Function();/*
		//int timesTaken = 1;
		Scanner scan = new Scanner(System.in);	
		
		while(true) {
			
			System.out.println("Enter a function: f(x) = ");
			function.setInput(scan.nextLine());
			
			if(function.getInput().equals("Directions")) {
				System.out.println("\n1. Do not use "_" or "i" at all ever.
				\n2. Only sin, cos, tan, sec, csc, & cot of sinusoidal functions are supported.
				\n3. Put all exponents that are more then 1 character into parenthesis, otherwise dont.
				\n4. If it doesnt work, then you put it in wrong.
				\n");
			}
			else {
				break;
			}
			
		}
		
		//System.out.println("Enter the number of times you want the derivative  taken");
		//timesTaken = scan.nextInt();
		System.out.println(" ");
		
		scan.close(); 
		*/
		
		//Example 1
		//function.setInput("((5x)*(4x))");
		
		//Example 2
		//function.setInput("((5x)^(4x))");
		
		//Example 3
		//function.setInput("(ln(5x))");
		
		//Example 4
		function.setInput("((ln(5x))+((5x)*(4x)))");
		
		//Example 5
		//function.setInput("(cot(12x))");
		
		function.formatFunction();
		function.fillDerivativeKey();
		
		System.out.println("Function:");
		System.out.println(function);
		
		System.out.println("\nValues in key:");
		function.printKey();
		
		System.out.println("\nValues in derivativeKey:");
		function.printDerivativeKey();
		/*
		System.out.println("\nValues in parts:");
		function.printParts();
		
		System.out.println("\nParts Expanded");
		function.expandParts();
		function.printParts();
		System.out.println("\nValues in key:");
		function.printKey();
		
		System.out.println("\nParts Collapsed");
		function.collapseParts();
		function.printParts();
		System.out.println("\nValues in key:");
		function.printKey();
		*/
		//System.out.println(DerivativeStringHandler.iExpander(function.getCurrent(), function.getKey()));
		
		//function.takeDerivative();
		
		System.out.println("\nFunction:");
		System.out.println(function);
		System.out.println("Parts Put Together But Not Expanded");
		System.out.println(function.constructFunction(false)); 
		//System.out.println("Parts Put Together And Expanded");
		//System.out.println(function.constructFunction(true));  
		System.out.println("DerivativeParts Put Together And Not Expanded");
		System.out.println(function.constructDerivative(false));
		System.out.println("DerivativeParts Put Together And Expanded");
		System.out.println(function.constructDerivative(true));
		
	}
}
