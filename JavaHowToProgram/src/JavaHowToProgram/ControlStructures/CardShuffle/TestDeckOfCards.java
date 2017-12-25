package JavaHowToProgram.ControlStructures.CardShuffle;

public class TestDeckOfCards {
	public static void main(String[] args){
		DeckOfCards deck = new DeckOfCards();
		deck.shuffle();
		
		System.out.println();
		System.out.println();
		System.out.println("Deck after shuffling:");
		for(int i=0;i<52;i++){
			if(i%4==0)
				System.out.println();
			
			System.out.printf("%-20s", deck.dealCard());					
		}
	}
}
