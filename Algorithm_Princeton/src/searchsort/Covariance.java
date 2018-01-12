package searchsort;

import java.util.*;

/*
 * Demonstrate that java arrays are covariant but generics are not,i.e. 
 * String[] is a subtype of Object[], but Stack<String> is not a subtype of Stack<Object>
 */
public class Covariance<T> {
	public static void main(String[] args){
		//making generic covariant will break its type safety
		List<Integer> li = new ArrayList<Integer>();
//		List<Number> ln = li; // illegal
//		ln.add(new Float(3.1415));
		
		List<Number> ln = new ArrayList<Number>();
		Integer n = new Integer(100);
		ln.add(n);
		
		Integer[] ai = new Integer[10];
		Number[] an = ai;
		//can compile but create java.lang.ArrayStoreException at run time
//		an[0] = new Float(3.1415);
//		an[0] = new Integer(10);
		
		Object o = new Long[0];
		System.out.println( o.getClass().getName() );
		Long l = new Long(10);
		System.out.println( l.getClass().getName() );
		long [] l2 = new long[10];
		System.out.println( l2.getClass().getName() );
		int [] i = new int[10];
		System.out.println( i.getClass().getName() );
		Covariance[] ac = new Covariance[0];
		System.out.println( ac.getClass().getName() );
	}
	
	public static <T>  void testGeneric(){
		
	}
}
