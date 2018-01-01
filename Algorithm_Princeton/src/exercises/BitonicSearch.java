package exercises;
/* ~2lgN Time algorithm
 * 	
The recursive formula f(L) = f(L/2) + log(L/2) + c doesn't lead to f(L) = O(log(N)) but leads to f(L) = O((log(N))^2) !

Indeed, assume k = log(L), then log(2^(k-1)) + log(2^(k-2)) + ... + log(2^1) = log(2)*(k-1 + k-2 + ... + 1) = O(k^2). Hence, log(L/2) + log(L/4) + ... + log(2) = O((log(L)^2)).

The right way to solve the problem in time ~ 2log(N) is to proceed as follows (assuming the array is first in ascending order and then in descending order):

Take the middle of the array
Compare the middle element with one of its neighbor to see if the max is on the right or on the left
Compare the middle element with the desired value
If the middle element is smaller than the desired value AND the max is on the left side, then do bitonic search on the left subarray (we are sure that the value is not in the right subarray)
If the middle element is smaller than the desired value AND the max is on the right side, then do bitonic search on the right subarray
If the middle element is bigger than the desired value, then do descending binary search on the right subarray and ascending binary search on the left subarray.
In the last case, it might be surprising to do a binary search on a subarray that may be 
bitonic but it actually works because we know that the elements that are not in the good order 
are all bigger than the desired value. For instance, doing an ascending binary search for the 
value 5 in the array [2, 4, 5, 6, 9, 8, 7] will work because 7 and 8 are bigger than the desired
value 5.
 */
public class BitonicSearch {
	public boolean bitonicSearch(int[] array, int low, int hi, int x){
		if(low <= hi){
			int mid = low + (hi - low)/2;
			
			if(array[mid] < array[mid+1] && array[mid] < x){
				return bitonicSearch(array, mid+1, hi, x);
			}else if(array[mid] > array[mid+1] && array[mid] < x){
				return bitonicSearch(array, low, mid-1, x);
			}else if(array[mid]>x){
				return binarySearchAscending(array, low, mid-1, x) ||
						binarySearchDecending(array, mid+1, hi, x);
			}
				
		}
		
		return false;
	}
	
	private boolean binarySearchAscending(int[] array, int low, int hi, int x){		
		if(hi >= low){			
			int mid = low + (hi - low) / 2;
			
			if(array[mid]==x)
				return true;
			else if(array[mid]>x)
				return binarySearchAscending(array, low, mid-1, x);
			else if(array[mid]<x)
				return binarySearchAscending(array, mid+1, hi, x);
				
		}
		
		return false;
	}
	
	private boolean binarySearchDecending(int[] array, int low, int hi, int x){	
			if(hi >= low){			
			int mid = low + (hi - low) / 2;
			
			if(array[mid]==x)
				return true;
			else if(array[mid]<x)
				return binarySearchAscending(array, low, mid-1, x);
			else if(array[mid]>x)
				return binarySearchAscending(array, mid+1, hi, x);				
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		BitonicSearch obj = new BitonicSearch();
		int[] array = { 1, 3, 4, 6, 9, 14, 11, 7, 2, -4, -9 };
		System.out.println(obj.bitonicSearch(array,0,array.length-1, 19));
	}
}
