package Collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class ArrayList2Array {
	public static void main(String[] args){
		Color[] arrayColors = new Color[]{new Color("black"),new Color("blue"),new Color("yellow")};
		LinkedList linkedListColors = new LinkedList(Arrays.asList(arrayColors));
		List<Color> listColors2 = Arrays.asList(arrayColors);
		
		ListIterator i = linkedListColors.listIterator();
		ListIterator i2 = listColors2.listIterator();
		
		System.out.printf("linkedListColors before modifying the backing array: ");
		while(i.hasNext()){
			System.out.printf("%s ", i.next());
		}
		
		System.out.printf("%nListColors before modifying the backing array: ");
		while(i2.hasNext()){
			System.out.printf("%s ",i2.next());
		}
		
		//modifying the backing array
		arrayColors[2] = new Color("orange");
		i = linkedListColors.listIterator();
		i2 = listColors2.listIterator();
				
		System.out.printf("%n%nlinkedListColors after modifying the backing array: ");
		while(i.hasNext()){
			System.out.printf("%s ", i.next());
		}
		
		System.out.printf("%nListColors after modifying the backing array: ");
		while(i2.hasNext()){
			System.out.printf("%s ",i2.next());
		}
		
		System.out.printf("%n%nArray Content: %s",Arrays.deepToString(arrayColors));
		
	}
}
