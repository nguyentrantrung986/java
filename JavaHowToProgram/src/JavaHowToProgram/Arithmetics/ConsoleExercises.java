package JavaHowToProgram.Arithmetics;

import java.util.Scanner;

public class ConsoleExercises {
	public static void printSquaresCubes() {
		System.out.printf("%s	%s	%s%n", "number", "square", "cube");
		System.out.printf("%d	%d	%d%n", 0, 0 * 0, 0 * 0 * 0);
		System.out.printf("%d	%d	%d%n", 1, 1 * 1, 1 * 1 * 1);
		System.out.printf("%d	%d	%d%n", 2, 2 * 2, 2 * 2 * 2);
	}

	public static void printIntValOfChar() {
		while (true) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter a character (or \"exit\" to quit): ");
			String s = scanner.nextLine();
			if(s.equals("exit")){
				scanner.close();
				break;
			}
			
			char c = s.charAt(0);
			System.out.printf("The character " + c + " has the integer value: %d%n", (int) c);
			System.out.println("");
		}
	}
	
	public static void separateDigits(){
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter a number: ");		
		int n = scanner.nextInt();
		int len = (n+"").length();
		
		double d = n/Math.pow(10,len-1);
		System.out.print((int)d + "   ");
		
		for (int i=len-1;i>0;i--){
			double divisor1 = Math.pow(10,i); 
			double divisor2 = Math.pow(10,i-1);
			d = n%divisor1/divisor2;
			System.out.print((int)d + "   ");
		}
		
//		int a = n/10000;
//		int b = n%10000/1000;
//		int c = n%1000/100;
//		int d = n%100/10;
//		int e = n%10;
//		System.out.printf("%d	%d	%d	%d	%d",a,b,c,d,e);
		scanner.close();
	}
}
