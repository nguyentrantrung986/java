package searchsort;

import java.util.Comparator;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class MergeSortComparator {
	public static void sort(Object[] a, Comparator c){
		Object[] aux = new Object[a.length];
		/*copy all elements to the auxiliary array, 
		 *then pass it as if it were the input array to the outer call to sort
		*/
		for(int i=0;i<a.length;i++)
			aux[i]=a[i];
		
		sort(c, aux, a, 0, a.length-1);
	}
	
	/*Eliminate the copy to the auxiliary array. Save time (but not space)
	 *by switching the role of the input and auxiliary array in each recursive call.
	 */
	private static void sort(Comparator c, Object[]a, Object[] aux, int l, int r){
		if(l>=r)
			return;
		int mid = l + (r-l)/2;
		sort(c, aux, a, l, mid);
		sort(c, aux, a, mid+1, r);
		
	/*if(!less(a[mid],a[mid+1])) //check if a has not already been sorted
	 * cannot be done for optimized version because 2 arrays switch role every recursive call
	 * so always have to call merged to copy data from one to the other
	 */
		merge(c, a, aux, l, mid, r);
	}
	
	private static void merge(Comparator c, Object[]a, Object[] aux, int l, int mid, int r){
		int i = l, j = mid+1;
		for(int k =l; k<=r; k++){
			if(i>mid) 						aux[k]=a[j++];
			else if(j>r) 					aux[k]=a[i++];
			/*stable code, if there is 2 equal keys, the code insert the item from the left half first
			 * therefore, preserving the relative orders before sorting.
			 */
			else if(less(c,a[j],a[i]))		aux[k]=a[j++];
			else 							aux[k]=a[i++];
		}
	}
	
	protected static boolean less(Comparator c, Object v, Object w){
			return c.compare(v,w) < 0;
		}
		
	protected static void exchange(Object[] a, int i, int j){
			Object swap = a[i];
			a[i] = a[j];
			a[j] = swap;
		}	
}
