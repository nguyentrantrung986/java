package searchsort;

import java.util.Arrays;

import edu.princeton.cs.algs4.Queue;

/**
 * Prefix free codes. In data compression, a set of binary strings is if no
 * string is a prefix of another. For example, {01,10,0010,1111} is prefix free,
 * but {01,10,0010,10100} is not because 10 is a prefix of 10100. Design an
 * efficient algorithm to determine if a set of binary strings is prefix-free.
 * The running time of your algorithm should be proportional the number of bits
 * in all of the binary stings.
 **/
public class PrefixFreeCodes<Value> {
	private static final int R = 2;
	private Node root;

	public void put(String key, Value val) {
		root = put(root, key, val, 0);
	}

	private Node put(Node x, String key, Value val, int d) {
		if (x == null)
			x = new Node();

		if (d < key.length()) {
			char c = key.charAt(d);
			x.next[c - 48] = put(x.next[c - 48], key, val, d + 1);
		} else {
			x.val = val;
		}

		return x;
	}

	/**
	 * Prefixes only count from second character onwards, so we collect all keys
	 * in the tries under each nodes in second level. If there are more than 1
	 * keys in the queue after the collect() returns, then at least 2 strings
	 * share a prefix. If the set has more than 4 strings, it certainly cannot
	 * be prefix-free.
	 * 
	 * @return true if there are no two string with common prefix, false otherwise.
	 */
	public boolean isPrefixFree() {
		if (root == null)
			return true;

		for (int i = 0; i < R; i++) {
			Node x = root.next[i];

			for (int j = 0; j < R; j++) {
				Queue<String> q = new Queue<>();
				char c = (char) (j + 48);
				collect(x.next[j], c + "", q);
				if (q.size() > 1)
					return false;
			}
		}

		return true;
	}

	private void collect(Node x, String prefix, Queue<String> q) {
		if (x == null)
			return;
		if (x.val != null)
			q.enqueue(prefix);
		for (int i = 0; i < R; i++) {
			char c = (char) (i + 48);
			collect(x.next[i], prefix + c, q);
		}
	}

	private static class Node {
		private Object val;
		private Node[] next = new Node[R];
	}

	public static void main(String[] args) {
		// test convert char to integer and vice versa
		char c = '0';
		int i = c - 48;
		System.out.print(i + " ");
		i = 1;
		c = (char) (i + 48);
		System.out.println(c);

		PrefixFreeCodes<String> pfc1 = new PrefixFreeCodes<String>();
		String[] a = { "01", "10", "0010", "1111" };
		for (String s : a)
			pfc1.put(s, s);
		System.out.println(Arrays.toString(a) + " is prefix-free? " + pfc1.isPrefixFree());

		PrefixFreeCodes<String> pfc2 = new PrefixFreeCodes<String>();
		String[] b = { "01", "10", "0010", "10100" };
		for (String s : b)
			pfc2.put(s, s);

		System.out.println(Arrays.toString(b) + " is prefix-free? " + pfc2.isPrefixFree());
	}
}
