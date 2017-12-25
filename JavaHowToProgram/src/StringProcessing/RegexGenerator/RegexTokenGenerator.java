package StringProcessing.RegexGenerator;

import java.security.SecureRandom;

public class RegexTokenGenerator {
	private static final SecureRandom sr = new SecureRandom();
	private static final int MIN_OCCURRENCE = 2; // used to generate random
	private static final int MAX_OCCURRENCE = 10;// ranged quantifiers
	private static final char[] alphabet;
	
	static{
		// build an char array of the English alphabet in26 lower and 26 upper
		// case letters
		alphabet = new char[52];
		for (int i = 0; i < 26; i++) {
			alphabet[i] = (char) (i + 'a');
			alphabet[i + 26] = (char) (i + 'A');
		}
	}
	
	public static String generateRandomQuantifier() {
		String quantifier = "";
		int quanType = sr.nextInt(6);
		// generate an number of occurrences in range
		int occurs = sr.nextInt(MAX_OCCURRENCE - MIN_OCCURRENCE) + MIN_OCCURRENCE;

		switch (quanType) {
		// quantifier with exact number of occurrences between MIN_OCCURRENCE
		// and MAX_OCCURRENCE, i.e. {n}
		case 0:
			quantifier = "{" + occurs + "}";
			break;

		// quantifier with min number of occurrences, i.e., {n,}
		case 1:
			quantifier = "{" + occurs + ",}";
			break;

		// quantifier {n,m}
		case 2:
			int occurs_m = sr.nextInt(MAX_OCCURRENCE - occurs) + occurs;
			quantifier = "{" + occurs + "," + occurs_m + "}";

			// one of the predefined quantifiers in the quantifierSigns *, +, ?
			// etc.
		default:
			QuantifierSign[] qs = QuantifierSign.values();
			quantifier = qs[sr.nextInt(qs.length)].toString();
			break;
		}

		return quantifier;
	}

	public static String generateRandomEscapeSequence() {
		EscapeSequence[] es = EscapeSequence.values();

		return es[sr.nextInt(es.length)].toString();
	}

	public static String generateRandomLetterSet() {
		StringBuilder charSet = new StringBuilder("[]");

		int numberOfChars = 1 + sr.nextInt(10); // only use maximum of 11 chars

		for (int i = 0; i < numberOfChars; i++) {
			int alphabetIndex = sr.nextInt(52);
			int indexToInsert = charSet.length() - 1;
			charSet.insert(indexToInsert, alphabet[alphabetIndex]);
		}

		return charSet.toString();
	}
	
	//only cover letter from A (unicode 65) to Z (unicode 90) and a (unicode 97) to z (unicode 122)
	public static String generateRandomLetterRange(){
		StringBuilder charRange = new StringBuilder("[-]");
		int letterCase = sr.nextInt(2); //0 = upper, 1 = lower
		int low='A', high='Z';
		
		switch(letterCase){
		case 0:
			low = 'A'+ sr.nextInt(25); //highest low character is Y or y, not Z or z, hence 25
			high = low + sr.nextInt(90-low)+1; //+1 to avoid high = low
			break;
		case 1:
			low = 'a'+ sr.nextInt(25); //highest low character is Y or y, not Z or z, hence 25
			high = low + sr.nextInt(122-low)+1;
			break;
		}
				
		char lowChar = (char)low;
		char highChar = (char)high;
		charRange.insert(1, lowChar);
		charRange.insert(3, highChar);
		
		return charRange.toString();
	}

	public static String generateRegex() {
		String regex = "";
		int numberOfToken = 1 + sr.nextInt(3); // must be greater than 0

		for (int i = 0; i < numberOfToken; i++) {
			// choice between escape sequence(0) or random letter set(1) or letter range (2)
			int sequenceOrSet = sr.nextInt(3);

			switch (sequenceOrSet) {
			case 0:
				regex += generateRandomEscapeSequence();
				break;
			case 1:
				regex += generateRandomLetterSet();
				break;
			case 2:
				regex += generateRandomLetterRange();
				break;
			}
			
			//adding a quantifier after the subexpression (a sequence or a set)
			regex += generateRandomQuantifier();
		}

		return regex;
	}
}
