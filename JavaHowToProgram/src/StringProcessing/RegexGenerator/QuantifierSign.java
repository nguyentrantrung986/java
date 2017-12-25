package StringProcessing.RegexGenerator;

public enum QuantifierSign {
	NO_QUANTIFIER(""),
	ZERO_OR_MORE_GREEDY("*"),
	ONE_OR_MORE_GREEDY("+"),
	ZERO_OR_ONE_GREEDY("?"),
	ZERO_OR_MORE_RELUCTANT("*?"),
	ONE_OR_MORE_RELUCTANT("+?"),
	ZERO_OR_ONE_RELUCTANT("??"),
	ZERO_OR_MORE_POSSESSIVE("*+"),
	ONE_OR_MORE_POSSESSIVE("++"),
	ZERO_OR_ONE_POSSESSIVE("?+");
	
	private final String representation;	
	QuantifierSign(String representation){
		this.representation = representation;
	}
	
	@Override
	public String toString() {
		return representation;
	}
}
