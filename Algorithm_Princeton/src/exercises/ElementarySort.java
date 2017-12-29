package exercises;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ElementarySort {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static boolean less(Comparable v, Comparable w){
		return v.compareTo(w) < 0;
	}
	
	@SuppressWarnings("rawtypes")
	protected static void exchange(Comparable[] a, int i, int j){
		Comparable swap = a[i];
		a[i] = a[j];
		a[j] = swap;
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
