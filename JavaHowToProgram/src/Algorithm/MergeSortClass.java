package Algorithm;

import java.security.SecureRandom;
import java.util.Arrays;

public class MergeSortClass {
	public static void mergeSort(int[] data){
		sort(data,0,data.length-1);
	}
	
	public static void sort(int[] data, int low, int high){
		//base case: array has one element
		if(high-low<1){
			return;
		}
		
		int middle1 = (high+low)/2;
		int middle2 = middle1 +1;
		
		sort(data,low, middle1);
		sort(data,middle2,high);
		
		merge(data,low,middle1,middle2,high);
	}
	
	public static void merge(int[] data, int low, int middle1, int middle2, int high){
		int[] tmpData = new int[high-low+1];
		int tmpIndex =0;
		int left = low;
		int right = middle2;
		
		//compare value of 2 sorted arrays and insert into the temp array
		while(left<=middle1&&right<=high){			
			if(data[left]<=data[right]){
				tmpData[tmpIndex] = data[left];			
				left++;
			}else{
				tmpData[tmpIndex] = data[right];
				right++;
			}
			tmpIndex++;
		}
		
		//reaching the end of either array
		if(left >= middle2){
			while(right<=high){
				tmpData[tmpIndex] = data[right];
				tmpIndex++;
				right++;
			}
		}else{
			while(left<=middle1){
				tmpData[tmpIndex] = data[left];
				tmpIndex++;
				left++;
			}
		}
		
		//copy temp data to data
		for(int i=low;i<=high;i++){
			data[i] = tmpData[i-low];
		}
	}
	
	public static void main(String[] args){
		//test merge
//		int[] data = {55, 18, 73, 25, 39, 30, 49, 64, 46, 85};		
//		merge(data,0,4,5,8);		
//		System.out.println(Arrays.toString(data));
		
		SecureRandom sr = new SecureRandom();
		int[] data = new int[8];
		
		for(int i=0;i<data.length;i++){
			data[i] = 10+sr.nextInt(90);
		}
		
		System.out.printf("%nArray before sorting: %s",Arrays.toString(data));
		mergeSort(data);
		System.out.printf("%nArray after sorting: %s",Arrays.toString(data));
	}
}
