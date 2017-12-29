package exercises;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class ShellSort extends ElementarySort {
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a) {
		// calculate sort sequence h=3h+1
		int h = 1;
		while (h < a.length / 3) {
			h = 3 * h;
		}

		while (h > 0) {
			for (int i = h; i < a.length; i++) {
				for (int j = i; j >= h; j -= h) {
					if (less(a[j], a[j - h])) {
						exchange(a, j, j - h);
					}
				}
			}
			h = h / 3;
		}
	}

	// unit testing
	public static void main(String[] args) {
		ArrayList<Integer> ar = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(new File("test\\sortingTestData.txt")))) {
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
		// Integer[] a = {2148,9058,7742,3153,6324,609,7628,5469,7017,504};
		// Integer[] a =
		// {2148,9058,7742,3153,6324,609,7628,5469,7017,504,4092,1582,9572,1542,5697,2081,4218,3130,7923,9595,6558,3859,9832,3062,6788,7578,7432,479,8439,9079,7173,2667,2770,2655,972,4264,2014,3171,4715,345,4388,3816,8887,3915,3490,2327,123,4596,4307,8737,4007,6798,6551,1627,1190,4984,2480,3404,2027,4778,2951,2795,5002,8121,8910,9593,5254,448,6237,5565,1816,392,8143,9310,9293,3138,4869,6756,872,6183,3517,3513,1676,5498,9172,5739,6108,7538,7671,5780,8666,540,9771,6837,9341,1590,5689,1605,1103,5859};


		Stopwatch timer1 = new Stopwatch();
		ShellSort.sort(a);
		StdOut.println(timer1.elapsedTime() + " seconds");
		
		for (int i : a) StdOut.print(i + " ");
	}
}
