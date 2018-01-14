import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private final Digraph g;
	// first 2 indices cache the last 2 vertices, common ancestor at index 2,
	// sad at index 3
	private int[] cache;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null)
			throw new java.lang.IllegalArgumentException();
		this.g = new Digraph(G);
		cache = new int[4];
		for (int i = 0; i < 4; i++)
			cache[i] = -9;
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		if (v == cache[0] && w == cache[1] || v == cache[1] && w == cache[0])
			return cache[3];

		int[] result = closestCommonAncestor(v, w);
		cacheResults(v, w, result[0], result[1]);
		return result[1];
	}

	// a common ancestor of v and w that participates in a shortest ancestral
	// path; -1 if no such path
	public int ancestor(int v, int w) {
		if (v == cache[0] && w == cache[1] || v == cache[1] && w == cache[0])
			return cache[2];

		int[] result = closestCommonAncestor(v, w);
		cacheResults(v, w, result[0], result[1]);
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
	 * Search for the closest common ancestor of any vertex in v and any vertex
	 * in w. Do 2 breath first search from all vertices in v and w. The distance
	 * from a vertex p to set w or set v is the closest distance from p to any
	 * vertex in the set. Among vertices reachable from both v & w (common
	 * ancestors), look for one with smallest total distance to v.
	 * 
	 * @param v
	 * @param w
	 * @return the ancestor at index 0 and the shortest ancestral distance at 1,
	 *         -1 if no such path
	 */
	private int[] closestCommonAncestor(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null)
			throw new java.lang.IllegalArgumentException();
		
		for(int i:v){
			if(i<0 || i>g.V()-1)
				throw new java.lang.IllegalArgumentException();
		}
		
		for(int i:w){
			if(i<0 || i>g.V()-1)
				throw new java.lang.IllegalArgumentException();
		}

		int sad = Integer.MAX_VALUE;
		int sa = -1;

		// first search from set of vertices v
		boolean[] vMarked = new boolean[g.V()];
		int[] distFromV = new int[g.V()];
		Queue<Integer> q = new Queue<Integer>();
		for (int vi : v) {
			q.enqueue(vi);
			vMarked[vi] = true;
			distFromV[vi] = 0;
		}

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

		// second search from set of vertices w
		boolean[] wMarked = new boolean[g.V()];
		int[] distFromW = new int[g.V()];
		for (int wi : w) {
			q.enqueue(wi);
			wMarked[wi] = true;
			distFromW[wi] = 0;

			// check if any vertex in w is reachable from vertices in v, then it
			// is a common ancestor
			if (vMarked[wi] && distFromV[wi] < sad) {
				sad = distFromV[wi];
				sa = wi;
			}
		}

		while (!q.isEmpty()) {
			int last = q.dequeue();
			if (distFromW[last] > sad)
				break; // no closer common ancestor can be found

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
		if (v < 0 || w < 0 || v > g.V() - 1 || w > g.V() - 1)
			throw new java.lang.IllegalArgumentException();
		
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

		// check if w is reachable from v, then w is a common ancestor
		if (vMarked[w] && distFromV[w] < sad) {
			sad = distFromV[w];
			sa = w;
		}

		while (!q.isEmpty()) {
			int last = q.dequeue();
			if (distFromW[last] > sad)
				break; // no closer common ancestor can be found

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

	private void cacheResults(int v, int w, int ancestor, int sad) {
		cache[0] = v;
		cache[1] = w;
		cache[2] = ancestor;
		cache[3] = sad;
	}

	// unit testing
	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		Queue<Integer> g1 = new Queue<>();
		g1.enqueue(10);
		g1.enqueue(9);
		g1.enqueue(7);
		g1.enqueue(1);
		Queue<Integer> g2 = new Queue<>();
		g2.enqueue(12);
		g2.enqueue(12);
		g2.enqueue(2);
		g2.enqueue(6);
		int length = sap.length(g1, g2);
		int a = sap.ancestor(g1, g2);
		StdOut.printf("Group length = %d, group ancestor = %d\n", length, a);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}
	}
}
