
public class BitonicArraySearch {
	public boolean bitonicSearch(int[] array, int x) {
		int top= findIndexTop(array);
		System.out.println("Top:"+top);
		boolean found = binarySearchAscending(array, 0, top, x) || 
				binarySearchDecending(array, top+1, array.length-1, x);
		
		return found;
	}
	
	private int findIndexTop(int[] array){
		int low = 0;
		int hi = array.length - 1;
		int mid;
		int indexTop=-1;

		// looking for the index of the top/highest integer
		while (low <= hi) {
				mid = low + (hi - low) / 2;

				if (array[mid] < array[mid + 1]) {
					low = mid + 1;
				} else if(array[mid] > array[mid-1])
					return mid;
				else {
					hi = mid;
				}
		}
		
		return indexTop;
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
		BitonicArraySearch obj = new BitonicArraySearch();
		int[] array = { 1, 3, 4, 6, 9, 14, 11, 7, 2, -4, -9 };
		System.out.println(obj.bitonicSearch(array, 1));
	}
}
