package string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class RegexCheck {
	/**
	 * Construct the DFA based on 3 state: remainder equals 0, 1 or 2
	 * Include each state of the DFA into the regex. The latter state is inserted into the middle of the previous state:
	 * Include state 1: 0*
	 * Include state 1 & 2: 0*(11)*0*
	 * Include state 1 & 2 & 3: 0*(1(01*0)*1)*0*
	 * Simplify: ((1(01*0)*1|0)*
	 * @author TrungNT
	 *
	 */
	
	private static void divBy3(){
//		String regex = "(0*(11)*0*)*(1(00111)*01*(00)*1*(00)*010*)*";
		String regex = "(1(01*0)*1|0)*";
		Pattern p = Pattern.compile(regex);
		
		for(int i=0; i < 1000; i++){
			String binaryString = Integer.toBinaryString(i);
			Matcher m = p.matcher(binaryString);
			System.out.printf("%-5d%-12s%s%n",i,binaryString,m.matches());
		}
	}
	
	private static void no2Consecutive1s(){
		String regex = "0*(10+)*1?";
		Pattern p = Pattern.compile(regex);
		String[] text = {"0","010","001000","0100101","1001010001","011010","01011"};
		
		for(String s: text){
			Matcher m = p.matcher(s);
			System.out.printf("%-13s%s%n", s,m.matches());
		}
	}
	
	public static void main(String[] args){
		no2Consecutive1s();
	}
}
