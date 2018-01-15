package graph;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.KruskalMST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Stack;

public class UpdateMST {
	private Queue<Edge> mst;

	public UpdateMST(EdgeWeightedGraph g) {
		KruskalMST kmst = new KruskalMST(g);
		mst = (Queue<Edge>) kmst.edges();
	}

	/**
	 * Add a new edge to the graph, print out if the new edge replace an edge in
	 * the MST.Running time proportional to v. The algorithm does a DFS in the
	 * original spanning tree to find the path p from one vertex v of the new
	 * edge to the other w. If the new edge weighs less than the max weighted
	 * edge in p, then it replaces the max weighted edge in p in the new MST.
	 * 
	 * @param e
	 * @return
	 */
	public void newMST(Edge ne) {
		Stack<Integer> toProcess = new Stack<>();
		RedBlackBST<Integer, Edge> path = new RedBlackBST<>();
		boolean[] marked = new boolean[mst.size() + 1];
		EdgeWeightedGraph mstg = new EdgeWeightedGraph(mst.size() + 1);
		for (Edge e : mst)
			mstg.addEdge(e);

		int v = ne.either();
		int w = ne.other(v);
		toProcess.push(v);

		while (!toProcess.isEmpty()) {
			int p = toProcess.pop();
			if (p == w)
				break;
			marked[p] = true;

			for (Edge e : mstg.adj(p)) {
				int q = e.other(p);
				if (!marked[q]) {
					path.put(q, e); // trace which edge leads to this vertex q
					marked[q] = true;
					toProcess.push(q);
				}

			}
		}

		// search for the maxEdge in the path from v to w
		Edge maxEdge = path.get(w);
		int p = maxEdge.other(w);
		while (p != v) {
			if (maxEdge.compareTo(path.get(p)) < 0)
				maxEdge = path.get(p);
			p = path.get(p).other(p);
		}

		if (maxEdge.compareTo(ne) > 0)
			System.out.println(ne.toString() + " replaces " + maxEdge.toString() + " in new MST");
		else
			System.out.println(ne.toString() + " does not replace max weighted edge " + maxEdge.toString()
					+ " in path vw in the MST");
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		EdgeWeightedGraph g = new EdgeWeightedGraph(in);
		UpdateMST obj = new UpdateMST(g);
		obj.newMST(new Edge(5, 1, 0.19));
	}
}
