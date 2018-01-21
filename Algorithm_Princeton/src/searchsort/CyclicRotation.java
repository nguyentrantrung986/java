package searchsort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Cyclic rotations. Two strings s and t are cyclic rotations of one another if
 * they have the same length and s consists of a suffix of t followed by a
 * prefix of t. For example, "suffixsort" and "sortsuffix" are cyclic rotations.
 * 
 * Given n distinct strings, each of length L, design an algorithm to determine
 * whether there exists a pair of distinct strings that are cyclic rotations of
 * one another. For example, the following list of n=12 strings of length L=10
 * contains exactly one pair of strings ("suffixsort" and "sortsuffix") that are
 * cyclic rotations of one another.The order of growth of the running time
 * should be nL2 (or better) in the worst case. Assume that the alphabet size R
 * is a small constant.
 * 
 * Signing bonus. Do it in NnL time in the worst case. Hint: define a
 * fingerprint of a string in such a way that two strings are cyclic rotations
 * of one another if and only if they have the same fingerprint.
 * 
 * @author TrungNT
 *
 */
public class CyclicRotation {
	//distinct set of all cyclic rotations of the string
	private Set<String> rotate(String s) {
		HashSet<String> set = new HashSet<>();
		
		for (int i = 0; i < s.length(); i++) {
			String tail = s.substring(i);
			String head = s.substring(0, i);
			set.add(tail+head);
		}

		return set;
	}

	public boolean haveCyclicRotation(String[] a) {
		ArrayList<String> al = new ArrayList<>();
		for (int i = 0; i < a.length; i++) {
			al.addAll(rotate(a[i]));
		}
		String[] allRotations = new String[al.size()];
		allRotations = al.toArray(allRotations);
		Arrays.sort(allRotations);
		System.out.println(Arrays.toString(allRotations));
		
		for (int i = 0; i < allRotations.length - 1; i++) {
			if (allRotations[i].equals(allRotations[i + 1])) {
				System.out.println(allRotations[i]);
				return true;
			}
		}
		
		return false;
	}

	public static void main(String[] args) {
		String[] a = { "algorithms", "polynomial", "sortsuffix", "boyermoore", "structures", "minimumcut", "suffixsort",
				"stackstack","binaryheap", "digraphdfs", "stringsort", "digraphbfs" };

		CyclicRotation cr = new CyclicRotation();
		System.out.println(cr.haveCyclicRotation(a));
	}
}
