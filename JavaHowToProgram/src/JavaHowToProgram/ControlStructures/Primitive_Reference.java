package JavaHowToProgram.ControlStructures;

import java.util.ArrayList;
import java.util.Arrays;

public class Primitive_Reference {
	public static void main(String[] args){
		/*Java is pass by value, not by reference. Changing the reference inside a method won't be reflected 
		into the passed-in reference in the calling method.
		Integer is immutable. There's no such method like Integer#set(i). You could otherwise just make use of it.*/
		
		int i = 10;
		Integer j = 10;
		modifyInt(i, j);		
		
		System.out.println(i);
		System.out.println(j);
		System.out.println("j in main: "+j.toString());
		
//		int[][] b = new int[i][]; // create 2 rows
//		b[0] = new int[5]; // create 5 columns for row 0
//		b[1] = new int[3]; // create 3 columns for row 1
		//changing references to arrays
	    String[] strings = new String[] { "foo", "bar" };
	    changeReference(strings);
	    System.out.println(Arrays.toString(strings)); // still [foo, bar]
	    changeValue(strings);
	    System.out.println(Arrays.toString(strings)); // [foo, foo]
		
		//String equals method is different from array equals
		String a = "love";
		String b = "love";
		System.out.println(a.equals(b));
		
		int [] array1 = {1,2};
		int [] array2 = {1,2};
		System.out.println(array1.equals(array2));
		System.out.println(array1);
		System.out.println(array2);		
		
		ArrayList<Integer> al = new ArrayList<Integer>();
	}
	
	private static void modifyInt(int i, Integer j){
		i = 1;
		j = new Integer(1);
		System.out.println("j inside method: "+ j.toString());;
	}
	
	public static void changeReference(String[] strings) {
	    strings = new String[] { "foo", "foo" };
	}
	public static void changeValue(String[] strings) {
	    strings[1] = "foo";
	}
}
