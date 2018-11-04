package DerivativeCalculator;

import java.util.Scanner;

public class DerivativeCalculator {
	
	public static void main (String[] args) {
		
		
		Function function = new Function();
		//int timesTaken = 1;
		Scanner scan = new Scanner(System.in);	
		
		while(true) {
			
			System.out.println("Enter a function: f(x) = ");
			function.setInput(scan.nextLine());
			
			if(function.getInput().equals("Directions")) {
				System.out.println("\nDirections will be here I guess\n");
			}
			else {
				break;
			}
			
		}
		
		//System.out.println("Enter the number of times you want the derivative  taken");
		//timesTaken = scan.nextInt();
		System.out.println(" ");
		
		scan.close(); 
		
		/*
		String test = "7*84/7*1512"
				+ ""
				+ "";
		
		test = DerivativeStringHandler.parentheisGrouper(test);
	
		System.out.println(test);
		*/
		
		function.formatFunction();
		
		System.out.println("Function:");
		System.out.println(function);
		
		System.out.println("\nValues in key:");
		function.printKey();
		
		System.out.println("\nValues in parts:");
		function.printParts();
		
		System.out.println("");
		System.out.println("Function:");
		System.out.println(function);  
		
		
	}
}
