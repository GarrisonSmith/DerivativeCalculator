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
				\n3. Put ALL exponents into parenthesis.
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
		
		function.setInput("5x+(132x/(53x*351x))/5118x*51x-81x+sin(12354x-51x)^(-10x)-e^(x)");
		//function.setInput("5x*4x");
		
		System.out.println(FunctionDeterminer.applyRule("5x/4x^22"));
		
		/*
		test = DerivativeStringHandler.parentheisGrouper(test);
	
		System.out.println(test);
		*/
		
		function.formatFunction();
		/*
		System.out.println("Function:");
		System.out.println(function);
		
		System.out.println("\nValues in key:");
		function.printKey();
		
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
		
		function.takeDerivative();
		
		System.out.println("\nFunction:");
		System.out.println(function);
		System.out.println("Parts Put Together But Not Expanded");
		System.out.println(function.constructFunction(false)); 
		System.out.println("Parts Put Together And Expanded");
		System.out.println(function.constructFunction(true));  
		
		/*
		String functionS = function.getCurrent();
		
		System.out.println(functionS);
		
		int start = 0;
		int end = 0;
		
		while(functionS.contains("_")) {
			
			for(start = 0; start < functionS.length(); start++) {
				
				if(functionS.charAt(start) == '_') {
					
					for(end = start+1; end < functionS.length(); end++) {
						
						if(functionS.charAt(end) == '_') {
							//function = function.substring(0, start) + key.getFunction(function.substring(start, end) + function.substring(end, function.length()));
							System.out.println(functionS.substring(start, end+1));
							functionS = function.getKey().getFunction(functionS.substring(start, end+1));
						}
					}
				}
			}
		}
		
		System.out.println(functionS);
		*/
	}
}
