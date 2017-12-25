package Algorithm;

import java.security.SecureRandom;
import java.util.Arrays;

public class InsertionSortClass {
	public static void insertionSort(int[] array){
		for(int next=1;next<array.length;next++){
			int valueToInsert = array[next];
			int posToInsert = next;
			
			//looking for a position to insert the next value
			for(int i=next-1;i>=0&&array[i]>valueToInsert;i--){
				array[i+1] = array[i];
				posToInsert=i;
			}
			
			//inserting the next value to the right position
			array[posToInsert] = valueToInsert;
		}
	}
	
	public static void main(String[] args){
		SecureRandom sr= new SecureRandom();
		int[] data = new int[10];
		
		for(int i=0;i<10;i++){
			data[i] = 10+sr.nextInt(90);
		}
		
		System.out.printf("%nArray before sorting: %s",Arrays.toString(data));
		
		insertionSort(data);
		
		System.out.printf("%nArray after sorting: %s",Arrays.toString(data));
	}
}
