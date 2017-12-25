package JavaHowToProgram.ControlStructures;

import java.security.SecureRandom;

public class Craps {
	private enum GameStatus {WON, LOST, CONTINUE};
	private static final int SNAKE_EYES = 2;
	private static final int TREY = 3;
	private static final int SEVEN = 7;
	private static final int YO_LEVEN = 11;
	private static final int BOX_CARS = 12;
	
	private static final SecureRandom sr = new SecureRandom();
	
	public static void main(String args[]){
		int diceSum = rollDice();
		int point = 0;
		GameStatus status;
		
		switch(diceSum){
		case SEVEN:
		case YO_LEVEN:
			System.out.println("The player rolls "+diceSum+" in the first roll and wins");
			status = GameStatus.WON;
			break;
		case SNAKE_EYES:
		case TREY:
		case BOX_CARS:
			System.out.println("The player rolls "+diceSum+" in the first roll and loses");
			status = GameStatus.LOST;
			break;
		default:
			System.out.println("The player's point is "+diceSum+" in the first roll");
			status = GameStatus.CONTINUE;
			point = diceSum;
			break;
		}
		
		while(status==GameStatus.CONTINUE){
			diceSum = rollDice();
			
			if(diceSum == point){
				System.out.println("The player rolls his point "+diceSum+" and wins");
				status = GameStatus.WON;				
			}
			else if(diceSum == SEVEN){
				System.out.println("The player rolls "+diceSum+" and loses");
				status = GameStatus.LOST;
			}
			else{
				System.out.println("The player rolls "+diceSum+" and continues");
			}
		}
	}
	
	private static int rollDice(){
		int roll1 = 1 + sr.nextInt(6);
		int roll2 = 1 + sr.nextInt(6);
		
		System.out.printf("%nThe player rolls %d and %d, which sums up to %d%n",roll1,roll2,roll1+roll2);
		return roll1+roll2;
	}
}
