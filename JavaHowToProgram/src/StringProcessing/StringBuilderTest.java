package StringProcessing;

public class StringBuilderTest {
	public static void main(String[] args){
		StringBuilder sb = new StringBuilder("abcdefghiklmnopqrstxyz");
		System.out.printf("Testing a StringBuilder with original content as \"%s\"",sb.toString());
		System.out.printf("%nCharacter at index 3 is %c, original length is %d", sb.charAt(3),sb.length());
		sb.deleteCharAt(3);
		System.out.printf("%nCharacter at index 3 after deleteCharAt(3) is %c, new lenghth is %d", 
				sb.charAt(3),sb.length());
		
		System.out.printf("%n%nStringBuilder contains this now \"%s\"",sb.toString());
		sb.delete(1, 3);
		System.out.printf("%nStringBuilder contains this after delete(1,3) \"%s\"",sb.toString());
		sb.insert(1, new char[] {'b','c','d'});
		
		System.out.printf("%n%nRestoring StringBuilder by insert(1, new char[] {'b','c','d'}): \"%s\"",sb.toString());
	}
}
