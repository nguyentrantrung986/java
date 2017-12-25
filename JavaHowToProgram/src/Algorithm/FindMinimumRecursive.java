package Algorithm;

import java.util.Arrays;

public class FindMinimumRecursive {
	public static int findMin(int[] intArray){
		int length = intArray.length;
		
		//base case, the array has only 1 element
		if(length==1){
			return intArray[0];
		}
		
		//recursive step: compare the last int value with the minimum of the sub-array containing previous int values 
		int[] subIntArray = Arrays.copyOfRange(intArray, 0, length-1);
		int minInSubArray = findMin(subIntArray);
		return minInSubArray<=intArray[length-1]?minInSubArray:intArray[length-1];
	}
	
	public static void main(String[] args){
		int[] array = new int[]{4, 21, 45, 82, 12, 15, 4, 6};
		
		System.out.printf("Min value of the array: %d",findMin(array));
	}
}
