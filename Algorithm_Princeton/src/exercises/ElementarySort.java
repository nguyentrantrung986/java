package exercises;

public abstract class ElementarySort {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static boolean less(Comparable v, Comparable w){
		return v.compareTo(w) < 0;
	}
	
	@SuppressWarnings("rawtypes")
	protected static void exchange(Comparable[] a, int i, int j){
		Comparable swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
}
