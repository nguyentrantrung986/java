package searchsort;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class InsertionSort extends ElementarySort {
	public static <T> void sort(Comparable<T>[] a){
		for(int i=1; i<a.length; i++){
			for(int j=i; j >0; j--){
				if(less(a[j], a[j-1]))
					exchange(a, j, j-1);
				else
					break;
			}
		}
	}
	
	public static void main(String[] args){
		Integer[] a = getIntegerTestData("test\\integerRandomOrder.txt");

		Stopwatch timer1 = new Stopwatch();
		InsertionSort.sort(a);
		StdOut.println("Insertion sort runs for "+timer1.elapsedTime() + " seconds");
		
		for (int i : a) System.out.print(i+" - ");
	}
}
