
public class BitonicArraySearch {
	public boolean bitonicSearch(int[] array, int x){
		int low = 0;
		int hi = array.length - 1;
		int mid;
		
		while(low<hi){
			mid= low + (hi-low)/2;
			
			if(array[mid]==x)
				return true;
			else if (array[mid]>x){
				if(array[mid]<array[mid+1])
					hi = mid -1;
				else
					low = mid +1;
			}
			else if (array[mid]<x){
				if(array[mid]<array[mid+1]){
					low=mid+1;
				}else
					hi=mid-1;
			}
		}
		
		return false;
	}
	
	public static void main(String[] args){
		BitonicArraySearch obj = new  BitonicArraySearch();
		int[] array = {1,3,6,9,12,10,8,7,4};
		System.out.println(obj.bitonicSearch(array, 1));
	}
}
