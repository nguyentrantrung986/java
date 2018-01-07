package exercises;

import edu.princeton.cs.algs4.Queue;

public class BinarySearchTree<Key extends Comparable<Key>, Value> {
	private Node root;

	public void put(Key key, Value val) {
		root = put(root, key, val);
	}

	private Node put(Node root, Key key, Value val) {
		if (root == null)
			return new Node(key, val);

		int comp = key.compareTo(root.key);
		if (comp < 0)
			root.left = put(root.left, key, val);
		else if (comp > 0)
			root.right = put(root.right, key, val);
		else
			root.val = val;

		return root;
	}

	public Value get(Key key) {
		return get(root, key);
	}

	private Value get(Node root, Key key) {
		if (root == null)
			return null;

		int comp = key.compareTo(root.key);
		if (comp < 0)
			return get(root.left, key);
		else if (comp > 0)
			return get(root.right, key);
		else
			return root.val;
	}

	public String toString() {
		return toString(root);
	}

	private String toString(Node nodeX) {
		StringBuilder builder = new StringBuilder();
		if (nodeX == null)
			return "";

		builder.append(nodeX.toString() + " ");
		builder.append("left: " + nodeX.left + "	");
		builder.append("right: " + nodeX.right);
		builder.append(System.getProperty("line.separator"));

		if (nodeX.left != null)
			builder.append(toString(nodeX.left));
		if (nodeX.right != null)
			builder.append(toString(nodeX.right));

		return builder.toString();
	}

	/**
	 * iterate through the bst in increasing key order
	 * 
	 * @return a queue of keys in increasing order
	 */
	public Iterable<Key> keys() {
		Queue<Key> q = new Queue<>();
		inOrder(root, q);
		return q;
	}

	private void inOrder(Node nodeX, Queue<Key> q) {
		if (nodeX == null)
			return;
		inOrder(nodeX.left, q);
		q.enqueue(nodeX.key);
		inOrder(nodeX.right, q);
	}

	/**
	 * Write a non-recursive traversal of a Binary Search Tree using constant
	 * space and O(n) run time. This algorithm destroys the tree.
	 * 
	 */
	public void traverse() {
		traverse(root.left, root);
	}

	private void traverse(Node current, Node parent) {
		while (current != null) {
			if (parent != null) {
				parent.left = current.right;
				current.right = parent;
			}

			if (current.left != null) {
				parent = current;
				current = current.left;
			} else {
				System.out.print(current+" ");
				current = current.right;
				parent = null;
			}
		}
	}

	private class Node {
		private Key key;
		private Value val;
		private Node left, right;

		public Node(Key key, Value val) {
			this.key = key;
			this.val = val;
		}

		public String toString() {
			return "[" + key + "," + val + "]";
		}
	}

	public static void main(String[] args) {
		BinarySearchTree<String, String> bst = new BinarySearchTree<>();
		bst.put("S", "S");
		bst.put("E", "E");
		bst.put("X", "X");
		bst.put("A", "A");
		bst.put("R", "R");
		bst.put("C", "C");
		bst.put("H", "H");
		bst.put("G", "G");
		bst.put("M", "M");

		System.out.println(bst.toString());

//		Iterable<String> it = bst.keys();
//		for (String s : it)
//			System.out.print(s + " ");
		
		bst.traverse();

		System.out.println();
		System.out.println("After traversing:");
		System.out.println(bst.toString());
	}
}
