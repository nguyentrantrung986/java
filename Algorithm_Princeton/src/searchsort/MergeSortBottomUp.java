package searchsort;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

@SuppressWarnings("rawtypes")
public class MergeSortBottomUp extends ElementarySort{
	public static void sort(Comparable[] a){
		Comparable[] aux = new Comparable[a.length];
		
		for(int sz=1; sz <= a.length; sz=sz+sz){
			for(int i=0; i<= a.length - sz; i+=sz+sz){
				merge(a,aux,i,i+sz-1,Math.min(i+sz+sz-1,a.length-1));
			}
		}
	}
	
	private static void merge(Comparable[]a, Comparable[] aux, int l, int mid, int r){
		assert isSorted(a,l,mid);	// precondition: a[lo..mid] sorted
		assert isSorted(a,mid+1,r);	// precondition: a[mid+1..hi] sorted
		
		for(int i=l; i<=r; i++)
			aux[i] = a[i];
		
		int i = l, j = mid+1;
		
		for(int k =l; k<=r; k++){
			if(i>mid) 						a[k]=aux[j++];
			else if(j>r) 					a[k]=aux[i++];
			/*stable code, if there is 2 equal keys, the code insert the item from the left half first
			 * therefore, preserving the relative orders before sorting.
			 */
			else if(less(aux[j],aux[i]))	a[k]=aux[j++];
			else 							a[k]=aux[i++];
			/*the following code would be unstable, because if there are 2 equal keys, the code
			 * insert the item from the right half first
			else if(less(aux[i],aux[j])) 	a[k]=aux[i++];
			else 							a[k]=aux[j++];
			*/
		}
		
		assert isSorted(a,l,r); // postcondition: a[lo..hi] sorted
	}
	
	public static void main(String[] args){
		Integer[] a = getIntegerTestData("test\\integerRandomOrder.txt");
//		Integer[] a = {2148,9058,7742,3153,6324,609,7628,5469,7017,504};
		
		Stopwatch timer1 = new Stopwatch();
		MergeSortBottomUp.sort(a);
		StdOut.println("Merge sort bottom up runs for "+timer1.elapsedTime() + " seconds");
		
		for (int i : a) System.out.print(i+" - ");
	}
}
