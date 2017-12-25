package JavaHowToProgram.ControlStructures;

import java.util.Scanner;

public class TriangleSides {
	public void testTriangleSides(){
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the length of the first side (must be greater than 0): ");
		int a= scanner.nextInt();
		System.out.print("Enter the length of the second side (must be greater than 0): ");
		int b= scanner.nextInt();
		System.out.print("Enter the length of the third side (must be greater than 0): ");
		int c= scanner.nextInt();
		
		if(a+b>c){
			if(a+c>b){
				if(b+c>a){
					System.out.println("These can represent the lengths of 3 sides of a triangle.");
					scanner.close();
					return;
				}
			}
		}
		
		scanner.close();
		System.out.println("These cannot represent the lengths of 3 sides of a triangle.");
	}
	
	public static void main(String args[]){
		TriangleSides obj = new TriangleSides();
		obj.testTriangleSides();
		
//		for (int i = 1; i <= 5; i++)
//		{
//		for (int j = 1; j <= 3; j++)
//		{
//		for (int k = 1; k <= 4; k++)
//		System.out.print('*');
//		System.out.println();
//		} // end inner for
//		System.out.println();
//		} // end outer for
	}
}
