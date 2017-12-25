package Collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ListInsertPosition {
	public static void main(String[] args){
		String[] colors = {"red", "black", "blue", "green"};
		LinkedList<String> lstColors = new LinkedList(Arrays.asList(colors));
		
		Collections.sort(lstColors);
		System.out.printf("lstColors %s gray at index %s%n",
				lstColors.contains("gray")?"contains":"does not contain",Collections.binarySearch(lstColors, "gray"));
		
		lstColors.add("gray");
		
		for(String c:lstColors){
			System.out.printf("%s ",c);
		}
	}
}
