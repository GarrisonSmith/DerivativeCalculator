package DerivativeCalculator;

import java.util.Scanner;
import java.util.ArrayList;

public class DerivativeCalculator {
	
	public static void main (String[] args) {
		
		String function;
		ArrayList<String> parts = new ArrayList<String>();
		VariableKey key = new VariableKey();
		int timesTaken = 1;
		Scanner scan = new Scanner(System.in);	
		
		while(true) {
			
			System.out.println("Enter a function: f(x) = ");
			function = scan.nextLine();
			
			if(function.equals("Directions")) {
				System.out.println("\nDirections will be here I guess\n");
			}
			else {
				break;
			}
			
		}
		
		function = function.replaceAll(" ", "");
		
		//System.out.println("Enter the number of times you want the derivate taken");
		//timesTaken = scan.nextInt();
		System.out.println(" ");
		
		scan.close(); 
		
		function = DerivativeStringHandler.ParenthesisGrouper(function, key);
		
		System.out.println("Function:");
		System.out.println(function);
		
		System.out.println("\nValues in key:");
		for(int x = 0; x < key.getSize(); x++) {
			System.out.println(key.getIndex(x));
		}
		
		parts = DerivativeStringHandler.StringSplitter(function);
		
		System.out.println("\nValues in parts:");
		for(int x = 0; x < parts.size(); x++) {
			System.out.println(parts.get(x));
		}
		
		System.out.println("");
		System.out.println("Function:");
		System.out.println(function);
		
		/*
		System.out.println(" ");
		
		for(int x = 0; x < parts.size(); x++) {
			if(parts.get(x).contains("(")) {
				parts.set(x, (DerivativeStringHandler.StringGrouper(parts.get(x), key)));
			}
		}
		
		for(int x = 0; x < parts.size(); x++) {
			System.out.println(parts.get(x));
		}
		
		System.out.println(" ");
		
		for(int x = 0; x < key.size(); x++) {
			System.out.println(key.get(x));
		}
		*/
	}//
}