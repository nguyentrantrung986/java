package Algorithm;

import java.util.Random;

public class RSelect {
	public int[] rSelect(int[] array, int i, int l, int r){
		//result[0] contains the values of (i+1)th order statistic, result[1] contains the number of comparisons
		int [] results = new int[2];
		int[] tmp = new int[2];
		results[1] = r -l;
		results[0] = 0;
		
		if(r<=l){
			results[0] = array[l];
			return results;
		}
		
		int p = randomPivot(l, r);
//		int p = l;		
		
		p = partition(array, l, r, p);
		
		//lucky case
		if(i==p){ 
			tmp[0]=array[p];
		}
		else if (i < p){
			//recurse on the left half
			tmp = rSelect(array, i, l, p-1);
		}
		else if (i > p){
			//recurse on the right half
			tmp = rSelect(array, i, p+1, r);
		}
		
		results[0] = tmp[0];
		results[1] += tmp[1];
		
		return results;
	}
	
	//partition returns the index of the pivot after partitioning 
	private int partition(int[] array, int l, int r, int p){
		//i keeps track of the first index with value larger than pivot value
		int i=l+1;
		int pValue = array[p];
		
		//swap the pivot with the leftmost element
		int temp = array[p];
		array[p] = array[l];
		array[l] = temp;
		
		for(int j = l+1; j<=r; j++){
			if(array[j]<pValue){
				temp = array[j];
				array[j] = array[i];
				array[i] = temp;
				i++;
			}			
		}
		
		//swap the pivot with the element index (i-1)th, the last one smaller than its value
		temp = array[l];
		array[l] = array[i-1];
		array[i-1] = temp;
		
		return i-1;
	}
	
	private int randomPivot(int l, int r){
		Random ran = new Random();
		int p =ran.nextInt(r -l+1) + l;
		return p;
	}
	
	public static void main(String[] args){
		int [] array = {2148,9058,7742,3153,6324,609,7628,5469,7017,504};
		//int [] array = {2148,9058,7742,3153,6324,609,7628,5469,7017,504,4092,1582,9572,1542,5697,2081,4218,3130,7923,9595,6558,3859,9832,3062,6788,7578,7432,479,8439,9079,7173,2667,2770,2655,972,4264,2014,3171,4715,345,4388,3816,8887,3915,3490,2327,123,4596,4307,8737,4007,6798,6551,1627,1190,4984,2480,3404,2027,4778,2951,2795,5002,8121,8910,9593,5254,448,6237,5565,1816,392,8143,9310,9293,3138,4869,6756,872,6183,3517,3513,1676,5498,9172,5739,6108,7538,7671,5780,8666,540,9771,6837,9341,1590,5689,1605,1103,5859};
		RSelect obj = new RSelect();
		//for example, the fifth order statistics will be at index 4 when returned by rSelect
		int[] results = obj.rSelect(array, 3, 0, array.length-1);
		System.out.println("Result: "+results[0]+" after "+results[1]+" comparisons");		
	}
}
