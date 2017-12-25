package Algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayInversionCount {
	public long countInv(int[] array) {
		int[] arrayLeft = Arrays.copyOfRange(array, 0, array.length / 2);
		int[] arrayRight = Arrays.copyOfRange(array, array.length / 2, array.length);
		long leftInv = 0;
		long rightInv = 0;

		if (arrayLeft.length > 1)
			leftInv = countInv(arrayLeft);

		if (arrayRight.length > 1)
			rightInv = countInv(arrayRight);

		int[] arraySorted = new int[array.length];
		long splitInv = mergeAndCountSplitInv(arrayLeft, arrayRight, arraySorted);

		for (int i = 0; i < array.length; i++) { // copy the sorted array into
													// the input array
			array[i] = arraySorted[i];
		}

		return leftInv + rightInv + splitInv;
	}

	public long mergeAndCountSplitInv(int[] arrayLeft, int[] arrayRight, int[] arraySorted) {
		int splitInvCount = 0;

		for (int i = 0, j = 0, k = 0; k < arraySorted.length; k++) {
			if (i < arrayLeft.length && j < arrayRight.length) {
				if (arrayLeft[i] < arrayRight[j]) {
					arraySorted[k] = arrayLeft[i];
					i++;
				} else {
					arraySorted[k] = arrayRight[j];
					j++;
					splitInvCount += (arrayLeft.length - i);
				}
			} else if (i >= arrayLeft.length) { // copy what's left after one
												// array is exhausted
				arraySorted[k] = arrayRight[j];
				j++;
			} else if (j >= arrayRight.length) {
				arraySorted[k] = arrayLeft[i];
				i++;
			}
		}

		return splitInvCount;
	}

	public static void main(String[] args) {
		// int[] array = {8, 7, 6, 5, 4, 3, 2, 1 };
		ArrayList<String> arrayInt = new ArrayList<String>();
		File file = new File("problem3.5.txt");
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			try {
				String line;
				while ((line = br.readLine()) != null && line.length() > 0) {
					arrayInt.add(line);
				}
			} finally {
				br.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int[] arrayChallenge = new int[arrayInt.size()];
		for (int i = 0; i < arrayInt.size(); i++) {
			arrayChallenge[i] = Integer.parseInt(arrayInt.get(i));
		}

		ArrayInversionCount obj = new ArrayInversionCount();
		long invCount = obj.countInv(arrayChallenge);

		System.out.println(invCount);
	}
}
