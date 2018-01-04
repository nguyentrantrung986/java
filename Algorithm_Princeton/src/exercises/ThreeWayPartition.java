package exercises;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class ThreeWayPartition extends ElementarySort {
	@SuppressWarnings({ "rawtypes" })
	public static void sort(Comparable[] a, int lo, int hi) {
		if(lo>=hi)
			return;
		
		int i = lo;
		int lt = lo;
		int gt = hi;
		Comparable v = a[lo];

		while (i<=gt) {
			if (less(a[i], v))
				exchange(a, i++, lt++);
			else if (less(v, a[i]))
				exchange(a, i, gt--);
			else 
				i++;	
		}

		sort(a, lo, lt - 1);
		sort(a, gt+1, hi);
	}

	// unit testing
	public static void main(String[] args) {
//		Integer[] a = { 2148, 9058, 7742, 3153, 6324, 609, 7628, 5469, 7017, 504 };
		Integer[] a = getIntegerTestData("test\\integerRandomOrder.txt");
		Stopwatch timer1 = new Stopwatch();
		ThreeWayPartition.sort(a,0,a.length-1);
		StdOut.println("Three way partitioning runs for " + timer1.elapsedTime() + " seconds");

		for (int i : a)
			System.out.print(i + " - ");
	}
}