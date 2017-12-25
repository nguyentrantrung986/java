package StringProcessing.RegexGenerator;

import java.util.Scanner;

public class TestRegexGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String continued = "y";
		Scanner scan = new Scanner(System.in);
		int total=0, correct=0;
		
		while(continued.equalsIgnoreCase("y")){		
			String regex = RegexTokenGenerator.generateRegex();
			System.out.println("The random regex is "+regex);
			System.out.printf("%n%s", "Enter a string that matches the regex: ");
			String input = scan.nextLine();
			if(input.matches(regex)){
				System.out.println("Congratulation, it is a match!");
				correct++;
			}
			else{
				System.out.println("Sorry, it is not a match!");
			}
			
			total++;
			
			System.out.printf("%s%d/%d","You score is: ",correct,total);
			System.out.printf("%n%s", "Continue?(y/n): ");
			continued = scan.nextLine();
			System.out.println();
		}
//		String randomLetterSet = RegexTokenGenerator.generateRandomLetterSet();
//		System.out.println(randomLetterSet);
//		String randomLetterSet = RegexTokenGenerator.generateRandomLetterRange();
//		System.out.println(randomLetterSet);
	}

}
