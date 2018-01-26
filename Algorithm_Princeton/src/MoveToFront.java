import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
	// apply move-to-front encoding, reading from standard input and writing to
	// standard output
	public static void encode() {
		char[] indexOf = new char[256];
		char[] charAtIndex = new char[256];
		for (int i = 0; i < 256; i++) {
			indexOf[i] = (char) i;
			charAtIndex[i] = (char) i;
		}

		while (!BinaryStdIn.isEmpty()) {
			char c = BinaryStdIn.readChar();
			BinaryStdOut.write(indexOf[c]);

			moveToFront(c,indexOf,charAtIndex);
		}

		BinaryStdOut.flush();
		BinaryStdOut.close();
	}

	// apply move-to-front decoding, reading from standard input and writing to
	// standard output
	public static void decode() {
		char[] indexOf = new char[256];
		char[] charAtIndex = new char[256];
		for (int i = 0; i < 256; i++) {
			indexOf[i] = (char) i;
			charAtIndex[i] = (char) i;
		}

		while (!BinaryStdIn.isEmpty()) {
			int i = BinaryStdIn.readInt(8);
			char c = charAtIndex[i];
			BinaryStdOut.write(c);

			moveToFront(c,indexOf,charAtIndex);
		}
		
		BinaryStdOut.flush();
		BinaryStdOut.close();
	}
	
	private static void moveToFront(char c, char[] indexOf, char[] charAtIndex) {
		for (int j = indexOf[c]; j > 0; j--) {
			charAtIndex[j] = charAtIndex[j-1];
			indexOf[charAtIndex[j]]++;
		}
		
		indexOf[c] = 0;
		charAtIndex[0] = c;
	}

	// if args[0] is '-', apply move-to-front encoding
	// if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args) {
		if (args[0].equals("-"))
			encode();
		else if (args[0].equals("+"))
			decode();
	}
}
