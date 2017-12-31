package exercises;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

@SuppressWarnings("rawtypes")
public class MergeSortOptimized extends ElementarySort{
	public static void sort(Comparable[] a){
		Comparable[] aux = new Comparable[a.length];
		/*copy all elements to the auxiliary array, 
		 *then pass it as if it were the input array to the outer call to sort
		*/
		for(int i=0;i<a.length;i++)
			aux[i]=a[i];
		
		sort(aux, a, 0, a.length-1);
	}
	
	/*Eliminate the copy to the auxiliary array. Save time (but not space)
	 *by switching the role of the input and auxiliary array in each recursive call.
	 */
	private static void sort(Comparable[]a, Comparable[] aux, int l, int r){
		if(l>=r)
			return;
		int mid = l + (r-l)/2;
		sort(aux, a, l, mid);
		sort(aux, a, mid+1, r);
		
	/*if(!less(a[mid],a[mid+1])) //check if a has not already been sorted
	 * cannot be done for optimized version because 2 arrays switch role every recursive call
	 * so always have to call merged to copy data from one to the other
	 */
		merge(a, aux, l, mid, r);
	}
	
	private static void merge(Comparable[]a, Comparable[] aux, int l, int mid, int r){
		assert isSorted(a,l,mid);	// precondition: a[lo..mid] sorted
		assert isSorted(a,mid+1,r);	// precondition: a[mid+1..hi] sorted
//		for(int k =l; k<=r; k++){
//			System.out.print(a[k]+" - ");
//			if(k==mid) System.out.print("|");
//		}
//		System.out.println();
		
		int i = l, j = mid+1;
		for(int k =l; k<=r; k++){
			if(i>mid) 						aux[k]=a[j++];
			else if(j>r) 					aux[k]=a[i++];
			else if(less(a[i],a[j])) 		aux[k]=a[i++];
			else 							aux[k]=a[j++];
			
//			System.out.print(aux[k]+" - ");
		}
//		System.out.println();
		assert isSorted(aux,l,r); // postcondition: aux[lo..hi] sorted
	}
	
	//unit testing
	public static void main(String[] args){
		Integer[] a = getIntegerTestData("test\\integerRandomOrder.txt");
//		Integer[] a = {2148,9058,7742,3153,6324,609,7628,5469,7017,504};
		
		Stopwatch timer1 = new Stopwatch();
		MergeSortOptimized.sort(a);
		StdOut.println("Optimized merge sort runs for "+timer1.elapsedTime() + " seconds");
		
		for (int i : a) System.out.print(i+" - ");
	}
}
