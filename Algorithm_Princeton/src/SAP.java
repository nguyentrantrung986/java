import java.util.ArrayList;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private final Digraph g;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null)
			throw new java.lang.IllegalArgumentException();
		this.g = new Digraph(G);
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		if (v < 0 || w < 0 || v > g.V() - 1 || w > g.V() - 1)
			throw new java.lang.IllegalArgumentException();
		int[] result = closestCommonAncestor(v, w);

		return result[1];
	}

	// a common ancestor of v and w that participates in a shortest ancestral
	// path; -1 if no such path
	public int ancestor(int v, int w) {
		if (v < 0 || w < 0 || v > g.V() - 1 || w > g.V() - 1)
			throw new java.lang.IllegalArgumentException();
		int[] result = closestCommonAncestor(v, w);

		return result[0];
	}

	// length of shortest ancestral path between any vertex in v and any vertex
	// in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		int[] results = closestCommonAncestor(v, w);
		return results[1];
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no
	// such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		int[] results = closestCommonAncestor(v, w);
		return results[0];
	}

	/**
	 * search for the closest common ancestor of any vertex in v and any vertex
	 * in w.
	 * 
	 * @param v
	 * @param w
	 * @return the ancestor at index 0 and the shortest ancestral distance at 1,
	 *         -1 if no such path
	 */
	public int[] closestCommonAncestor(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null)
			throw new java.lang.IllegalArgumentException();

		int sad = Integer.MAX_VALUE;
		int sa = -1;

		for (int i : v) {
			if (i < 0 || i > g.V() - 1)
				throw new java.lang.IllegalArgumentException();

			for (int j : w) {
				if (j < 0 || j > g.V() - 1)
					throw new java.lang.IllegalArgumentException();

				int[] results = closestCommonAncestor(i, j);
				if (results[1] != -1 && results[1] < sad) {
					sad = results[1];
					sa = results[0];
				}
			}
		}

		int[] results = new int[2];
		if (sa != -1) {
			results[0] = sa;
			results[1] = sad;
		} else {
			results[0] = -1;
			results[1] = -1;
		}

		return results;
	}

	/**
	 * Do 2 breath first search from v and w. Among vertices reachable from both
	 * v & w (common ancestors), look for one with smallest total distance to v
	 * & w.
	 * 
	 * @param v
	 * @param w
	 * @return the ancestor at index 0 and the shortest ancestral distance at 1,
	 *         -1 if no such path
	 */
	private int[] closestCommonAncestor(int v, int w) {
		int sad = Integer.MAX_VALUE;
		int sa = -1;

		// first bfs from v
		boolean[] vMarked = new boolean[g.V()];
		int[] distFromV = new int[g.V()];
		Queue<Integer> q = new Queue<Integer>();
		q.enqueue(v);
		vMarked[v] = true;
		distFromV[v] = 0;

		while (!q.isEmpty()) {
			int last = q.dequeue();
			for (int p : g.adj(last)) {
				if (!vMarked[p]) {
					vMarked[p] = true;
					distFromV[p] = distFromV[last] + 1;
					q.enqueue(p);
				}
			}
		}
		// 2nd bfs from w
		boolean[] wMarked = new boolean[g.V()];
		int[] distFromW = new int[g.V()];

		q.enqueue(w);
		wMarked[w] = true;
		distFromW[w] = 0;

		while (!q.isEmpty()) {
			int last = q.dequeue();
			for (int p : g.adj(last)) {
				if (!wMarked[p]) {
					wMarked[p] = true;
					distFromW[p] = distFromW[last] + 1;
					q.enqueue(p);

					if (vMarked[p] && distFromW[p] + distFromV[p] < sad) {
						sad = distFromW[p] + distFromV[p];
						sa = p;
					}
				}
			}
		}

		int[] results = new int[2];
		if (sa != -1) {
			results[0] = sa;
			results[1] = sad;
		} else {
			results[0] = -1;
			results[1] = -1;
		}

		return results;
	}

	//unit testing
	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		ArrayList<Integer> g1 = new ArrayList<>();
		g1.add(3);
		g1.add(9);
		g1.add(7);
		g1.add(1);
		ArrayList<Integer> g2 = new ArrayList<>();
		g2.add(11);
		g2.add(12);
		g2.add(2);
		g2.add(6);
		int l = sap.length(g1, g2);
		int a = sap.ancestor(g1, g2);
		StdOut.printf("Group length = %d, group ancestor = %d\n", l, a);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}
	}
}
