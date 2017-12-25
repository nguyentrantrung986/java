package JavaHowToProgram.ControlStructures.CardShuffle;

import java.security.SecureRandom;

public class DeckOfCards {
	private int currentCard;
	private final int NUMBER_OF_CARDS;
	private Card[] cards;
	private final SecureRandom sr;
	
	public DeckOfCards(){
		currentCard =0;
		NUMBER_OF_CARDS = 52;
		cards = new Card[NUMBER_OF_CARDS];
		sr = new SecureRandom();
		Face[] arrayFaces = Face.values();
		Suit[] arraySuits = Suit.values();
		
		System.out.println("Deck before shuffling: ");
		for(int i=0; i<NUMBER_OF_CARDS;i++){
			cards[i] = new Card(arrayFaces[i/arraySuits.length], arraySuits[i%arraySuits.length]);
			
			//print a new line after every face of cards
			if(i%arraySuits.length ==0){
				System.out.println();
			}
			//print all cards of the same face in a line
			System.out.printf("%-20s", cards[i]);
		}					
	}
	
	public void shuffle(){
		currentCard=0;
		int second=0;
		Card temp;
		
		for(int i=0;i<NUMBER_OF_CARDS;i++){
			second=sr.nextInt(NUMBER_OF_CARDS);
			
			// swap current Card with randomly selected Card
			temp = cards[i];
			cards[i] = cards[second];
			cards[second] = temp;
		}
	}
	
	public Card dealCard(){
		if(currentCard<NUMBER_OF_CARDS){
			return cards[currentCard++];
		}
		else
			return null;
	}
}
