package searchsort;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class SelectionSort extends ElementarySort {
	public static <T> void sort(Comparable<T>[] a){
		for(int i=0; i<a.length; i++){
			int min = i;
			for(int j=i+1; j<a.length; j++){
				if(less(a[j],a[min]))
					min=j;
			}
			exchange(a, i, min);
		}
	}
	
	public static void main(String[] args){
		Integer[] a = getIntegerTestData("test\\integerReversedOrder.txt");

		Stopwatch timer1 = new Stopwatch();
		SelectionSort.sort(a);
		StdOut.println("Selection sort runs for "+timer1.elapsedTime() + " seconds");
		
		for (int i : a) System.out.print(i+" - ");
	}
}
