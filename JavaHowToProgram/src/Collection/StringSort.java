package Collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringSort {
	public static void main(String[] args){
		String[] suits = {"Hearts","Diamonds","Clubs","Spades"};
		
		List<String> listSuits = Arrays.asList(suits);
		System.out.printf("The List before sorting: %s",listSuits);
		System.out.printf("%nThe Array before sorting: [");
		for(int i=0;i<suits.length;i++){
			System.out.printf("%s%s",suits[i],i!=suits.length-1?", ":"]");
		}

		
		Collections.sort(listSuits);
		System.out.printf("%n%nThe List after sorting: %s",listSuits);
		System.out.printf("%nThe Array before sorting: ");
		for(String suit:suits){
			System.out.printf("%s ",suit);
		}
	}
}
 