package StringProcessing.RegexGenerator;

public enum EscapeSequence {
	DIGIT("\\d"),
	NON_DIGIT("\\D"),
	WORD_CHARACTER("\\w"),
	NON_WORD_CHARACTER("\\W"),
	SPACE("\\s"),
	NON_SPACE("\\S"),
	ANY_CHAR(".");
		
	private final String representation;
	
	EscapeSequence(String representation){
		this.representation = representation;
	}
	
	@Override
	public String toString() {
		return representation;
	}
}
