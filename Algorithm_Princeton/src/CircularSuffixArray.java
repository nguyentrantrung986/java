import java.util.Arrays;

public class CircularSuffixArray {
	private final String s;
	// index that indicates which character is the beginning of the suffix
	private int[] index;
	private final int W;
	
	// circular suffix array of s
	public CircularSuffixArray(String s){
		if(s==null) throw new java.lang.IllegalArgumentException();
		
		this.s = s;
		W = s.length();
		index = new int[W];	
		for(int i=0; i<W; i++)
			index[i] = i;
		//circularSuffixLSDSort();
		threeWaySuffixSort(s, 0, W-1, 0);
	}
	
	public int length() {
		return W;
	}
	
	// returns index of ith sorted suffix
	public int index(int i) {
		if(i>W-1||i<0) throw new java.lang.IllegalArgumentException();
		return index[i];
	}
	
	private void circularSuffixLSDSort(){
		int[] count = new int[256+1];
		int[] aux = new int[W];
		//only calculate frequency once since all circular suffix have the same characters
		for(int i=0; i<W; i++)
			count[s.charAt(i)+1]++;
		
		for(int r=0; r<256; r++)
			count[r+1] += count[r];
		
		for(int d=W-1; d>=0; d--){
			int[] count2 = Arrays.copyOf(count, count.length);
			for(int i=0; i < W; i++){
				char c = circularSuffixCharAt(i,d);
//				System.out.println(c+" "+i+" "+d+" "+count2[c]);
				aux[count2[c]++] = index[i];
			}

			for (int i = 0; i < W; i++) {
				index[i] = aux[i];
			}
		}
	}
	
	private void threeWaySuffixSort(String s, int lo, int hi, int d) {
		if(lo>=hi) return;
		int lt = lo;
		int gt = hi;
		int i = lo + 1;
		char v = circularSuffixCharAt(lo, d);
		while(i <= gt){
			char c = circularSuffixCharAt(i, d);
			if(c < v)
				exchange(index, lt++, i++);
			else if(c > v)
				exchange(index, i, gt--);
			else
				i++;
		}
		
		threeWaySuffixSort(s, lo, lt-1, d);
		if(d < s.length()) threeWaySuffixSort(s, lt, gt, d+1);
		threeWaySuffixSort(s, gt+1, hi, d);
	}
	
	private void exchange(int[] a, int i, int j){
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	//return the dth character of the ith suffix in array index[i]
	private char circularSuffixCharAt(int i, int d){
		if(d+index[i]<W)
			return s.charAt(d+index[i]);
		else
			return s.charAt(d+index[i]-W);
	}
	
	// unit testing (required)
	public static void main(String[] args) {
		CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
		for(int i=0; i < csa.length(); i++)
			System.out.print(csa.index(i)+ " ");
	}
}
