package StringProcessing;

import java.util.Scanner;

public class RegularExpressionTest {
	public static void main(String[] args) {
		String content = "", pattern = "", tmp = "";
		Scanner scanner = new Scanner(System.in);

		while (!pattern.equalsIgnoreCase("exit")) {
			System.out.printf("Enter pattern (\"exit\" to end, or empty to reuse previous pattern): ");
			tmp = scanner.nextLine();

			if (!tmp.equals("exit")) {
				if (!tmp.trim().equals(""))
					pattern = tmp;

				System.out.printf("%nEnter string to match: ");
				content = scanner.nextLine();
				System.out.println();

				if (content.matches(pattern)) {
					System.out.println("The string matches the patern!");
				} else {
					System.out.println("The string does not match the pattern!");
				}
			}else{
				pattern = "exit";
			}
				
		}
	}
}
