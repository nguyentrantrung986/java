import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
	// apply Burrows-Wheeler transform, reading from standard input and writing
	// to standard output
	public static void transform() {
		String input = BinaryStdIn.readString();
		CircularSuffixArray csa = new CircularSuffixArray(input);
		char[] output = new char[input.length()];
		int first = 0;
		for (int i = 0; i < input.length(); i++) {
			int suffixIndex = csa.index(i);
			if (suffixIndex == 0)
				first = i;
			// index of the last char of the suffix in the original string input
			int t = ((suffixIndex - 1) >= 0) ? suffixIndex - 1 : input.length() - 1;
			output[i] = input.charAt(t);
		}

		BinaryStdOut.write(first);
		for (int i = 0; i < input.length(); i++) {
			BinaryStdOut.write(output[i]);
		}
		BinaryStdOut.flush();
		BinaryStdOut.close();
	}

	// apply Burrows-Wheeler inverse transform, reading from standard input and
	// writing to standard output
	public static void inverseTransform() {
		int first = BinaryStdIn.readInt();
		String t = BinaryStdIn.readString();
		int[] count = new int[256 + 1];
		int[] next = new int[t.length()];
		char[] firstCol = new char[t.length()];

		// calculate character frequency
		for (int i = 0; i < t.length(); i++)
			count[t.charAt(i) + 1]++;
		// cumulate of character counts
		for (int r = 0; r < 256; r++)
			count[r + 1] += count[r];

		for (int i = 0; i < t.length(); i++) {
			next[count[t.charAt(i)]] = i;
			firstCol[count[t.charAt(i)]++] = t.charAt(i);
		}

		for (int i = 0; i < t.length(); i++) {
			char c = firstCol[first];
			BinaryStdOut.write(c);
			first = next[first];
		}

		BinaryStdOut.flush();
		BinaryStdOut.close();
		// System.out.println(Arrays.toString(next));
	}

	// if args[0] is '-', apply Burrows-Wheeler transform
	// if args[0] is '+', apply Burrows-Wheeler inverse transform
	public static void main(String[] args) {
		if (args[0].equals("-"))
			transform();
		else if (args[0].equals("+"))
			inverseTransform();

	}
}
