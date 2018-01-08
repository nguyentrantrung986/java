package exercises;

import java.util.ArrayList;

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
		Iterable<Node> nodes = breathFirstIterator(nodeX);
		int depth = 0;
		
		for(Node n: nodes){
			if(depth < depth(n.key)){
				System.out.println();
				depth = depth(n.key);
			}
			System.out.print(n);
		}

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

	public Iterable<Key> breathFirstKeys() {
		Iterable<Node> aln = breathFirstIterator(root);
		Queue<Key> keys = new Queue<>();
		for (Node n : aln) {
			keys.enqueue(n.key);
		}

		return keys;
	}
	
	/*The depth of a node is the number of edges from the root to the node.*/
	public int depth(Key k){
		return depth(root,k,0);
	}
	
	private int depth(Node root, Key k, int depth){
		if(k == null) return -1;
		
		int comp = k.compareTo(root.key);
		if(comp < 0) return depth(root.left, k, depth+1);
		else if(comp > 0) return depth(root.right, k, depth+1);
		else return depth;
	}

	/**
	 * Traverse the tree level by level, left to right
	 * @param x
	 * @return: list of nodes in breath first left to right order
	 */
	private Iterable<Node> breathFirstIterator(Node x) {
		ArrayList<Node> al = new ArrayList<>();
		al.add(x);
		int i = 0; //the node in the current level whose children will be added next 
		int j = 0; //the index of the latest node having been added
		
		int treeSize = size(x);

		while (i < treeSize-1) {
			if (x.left != null) {
				al.add(x.left);
				j++;
			}
			if (x.right != null) {
				al.add(x.right);
				j++;
			}

			if (i < j) {
				i++;
				x = al.get(i);
			}
		}

		return al;
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
				System.out.print(current + " ");
				current = current.right;
				parent = null;
			}
		}
	}

	public int height() {
		return height(root);
	}

	/**
	 * The height of a tree is the length of the path from the root to the
	 * deepest node in the tree. A (rooted) tree with only a node (the root) has
	 * a height of zero.
	 * 
	 * @param x
	 * @return
	 */
	private int height(Node x) {
		if (x == null)
			return -1;

		int lh = height(x.left);
		int rh = height(x.right);

		if (lh > rh)
			return lh + 1;
		else
			return rh + 1;
	}

	public int size() {
		return size(root);
	}

	private int size(Node x) {
		if (x == null)
			return 0;

		int sizeL = size(x.left);
		int sizeR = size(x.right);

		return sizeL + sizeR + 1;
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
		bst.put("M", "M");

		System.out.println(bst.toString());
		System.out.println("Tree height: " + bst.height());
		System.out.println("Tree size: " + bst.size());

		Iterable<String> keys = bst.keys();
		for (String s : keys)
			System.out.print(s + " ");

		System.out.println();

		Iterable<String> bfKeys = bst.breathFirstKeys();
		for (String s : bfKeys){
			System.out.print(s + " ");
		}
		
		System.out.println();
		System.out.println("Depth H: "+ bst.depth("H"));
	}
}
