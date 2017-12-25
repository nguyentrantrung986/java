package Algorithm;

import java.util.Arrays;
import java.util.Random;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class QuickSortClass {
	//quickSort method will return the count of comparisons performed.
	public int quickSort(int[] array, int l, int r){
		int comp = r - l;
		if(array.length <=1 || l >=r){
			return 0;
		}		
		
		int p = partition(array, l, r);
		
		//recursive calls on 2 partitions of the arrays
		comp+=quickSort(array,l, p-1);
		comp+=quickSort(array,p+1, r);
		
		return comp;
	}
	
	private int choosePivotNaiveFirst(int[] array, int l, int r){
		return l;
	}
	
	private int choosePivotNaiveLast(int[] array, int l, int r){
		return r;
	}
	
	private int choosePivotMedianOf3(int[] array, int l, int r){
		int midIndex = l + (r-l)/2;
		int median = 0;
		
		if(array[l] >= array[r])
			if(array[r]>=array[midIndex])
				median= r;
			else
				median = (array[l]>=array[midIndex])?midIndex:l;
		else
			if(array[l]>=array[midIndex])
				median = l;
			else 
				median = (array[r]>=array[midIndex])?midIndex:r; 
		
		return median;
	}
	
	private int randomPivot(int[] array, int l, int r){		
		Random rand = new Random();
		int p = l + rand.nextInt(r-l+1);
				
		return p;
	}
	
	private int partition(int[] array, int l, int r){
//		int p = choosePivotMedianOf3(array, l, r);
		int p = randomPivot(array, l, r);
		int pivot = array[p];
		int temp;
		int i = l;
		
		//swap pivot with the left most considered element, i.e. index l		
		array[p] = array[l];
		array[l] = pivot;
		
		for(int j=l+1; j <=r; j++)
		{
			if(array[j] < pivot ){ //swap this with the array[i+1] and increase i by 1 to keep all elements from l to i less than pivot
				temp = array[j];
				array[j] = array[i+1];
				array[i+1] = temp;
				i++;
			}
		}
		
		//swap the pivot at l with the array[i] element, i.e. the last element less than pivot
		temp = array[l];
		array[l] = array[i];
		array[i] = temp;
		
		//index of the pivot is now returned
		p = i;		
		return p;
	}
	
	public static void main(String[] args){
		int [] array = {2148,9058,7742,3153,6324,609,7628,5469,7017,504};
		QuickSortClass obj = new QuickSortClass();
		int comp = obj.quickSort(array, 0, array.length-1);
		
		System.out.println(Arrays.toString(array));
		System.out.println("Number of comparisons performed: "+comp);
	}
}
 