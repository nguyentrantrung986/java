package JavaHowToProgram.ControlStructures.CardShuffle;

public class Card {
	private final Face face;
	private final Suit suit;
	
	public Card(Face face, Suit suit){
		this.face = face;
		this.suit = suit;		
	}
	
	public String toString(){
		return face + " of " + suit;
	}
}
