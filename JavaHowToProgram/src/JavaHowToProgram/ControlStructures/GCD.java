package JavaHowToProgram.ControlStructures;

import java.util.Scanner;

public class GCD {
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);
		System.out.print("Ënter first integrer: ");
		int a = s.nextInt();
		System.out.println("");
		System.out.print("Ënter second integrer: ");
		int b = s.nextInt();

		System.out.println("The greatest common divisor of " + a + " and " + b + " (using loop method) is: " + gcd(a, b));
		System.out.println("The greatest common divisor of " + a + " and " + b + " (using recusion method) is: " + findGCD(a, b));
		s.close();
	}

	private static int gcd(int a, int b) {
		int r = 0;

		do {
			if (Math.abs(a) > Math.abs(b)) {
				r = a % b;
				if (r != 0) {
					a = b;
					b = r;
				}
			} else {
				r = b % a;
				if (r != 0) {
					b = a;
					a = r;
				}
			}
		} while (r != 0);		

		if (Math.abs(a) > Math.abs(b))
			return Math.abs(b);
		else
			return Math.abs(a);
	}
	
	private static int findGCD(int number1, int number2) { //base case 
		if(number2 == 0){ 
			return number1; 
			}
		
		return findGCD(number2, number1%number2); 		
	}

}
