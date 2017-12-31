package exercises;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class ElementarySort {
	
	protected static boolean less(Comparable v, Comparable w){
		return v.compareTo(w) < 0;
	}
	
	protected static void exchange(Comparable[] a, int i, int j){
		Comparable swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	
	protected static boolean isSorted(Comparable[] a, int lo, int hi){
		if(hi<lo) throw new IllegalArgumentException("High index must be greater than low index");
		
		for(int i=lo; i<=hi; i++)
			if(!less(a[i],a[i+1])) return false;
		return true;
	}
	
	protected static Integer[] getIntegerTestData(String fileName){
		ArrayList<Integer> ar = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))) {
			String line;
			while ((line = br.readLine()) != null) {
				ar.add(Integer.parseInt(line));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer[] a = new Integer[ar.size()];
		a = ar.toArray(a);
		return a;
	}
}
