package Algorithm;

import java.util.Arrays;

public class MergeSortClass2 {
	public int[] mergeSort(int[] array)
	{
		int length = array.length;
		
		int[] arrFirst= Arrays.copyOfRange(array, 0, length/2);
		int[] arrSecond= Arrays.copyOfRange(array, length/2, length);
		
		if(arrFirst.length>1){
			arrFirst=mergeSort(arrFirst);
		}
		
		if(arrSecond.length>1){
			arrSecond=mergeSort(arrSecond);
		}
		
		array = merge(arrFirst,arrSecond);
		return array;
	}
	
	public int[] merge(int[] arrFirst, int[] arrSecond){
		int length = arrFirst.length + arrSecond.length;
		int[] arrResult= new int[length];
		int j=0;
		int i=0;
		
		for(int k=0;k<length;k++){
			if(i < arrFirst.length && j < arrSecond.length){
				if(arrFirst[i]<arrSecond[j]){
					arrResult[k]=arrFirst[i];
					i++;
				}else{
					arrResult[k]=arrSecond[j];
					j++;
				}
			}else if( i >= arrFirst.length && j< arrSecond.length ){
				arrResult[k]=arrSecond[j];
				j++;
			}else if( i < arrFirst.length && j >= arrSecond.length ){
				arrResult[k]=arrFirst[i];
				i++;
			}
		}
		
		return arrResult;
	}
	
	public static void main(String args[]){
		int[] array = {5, 20, 12, 17, 21, 6, 9, 17, 12};
		MergeSortClass2 obj = new MergeSortClass2();
		array = obj.mergeSort(array);
		System.out.println(Arrays.toString(array));
	}
}
