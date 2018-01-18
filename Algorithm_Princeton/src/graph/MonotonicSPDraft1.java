package graph;

import edu.princeton.cs.algs4.*;

/**
 * Adding ascending or descending condition to relax method does not work
 * because the viable monotonic path to the next vertex might have already been
 * discarded by the shortest condition. This class is a draft of an incorrect
 * algorithm. It cannot find the descending monotonic path to 3 in the following
 * graph: 0-7 0.16 2-3 0.17 1-7 0.19 0-2 0.26 5-7 0.28 1-3 0.29 1-5 0.32 2-7
 * 0.34 4-5 0.35 1-2 0.36 4-7 0.37 0-4 0.38 6-2 0.40 3-6 0.52 6-0 0.58 6-4 0.93
 * 
 * @author ennnttg
 *
 */
public class MonotonicSPDraft1 {
	private DirectedEdge[] descEdgeTo, ascEdgeTo;
	private double[] descDistTo, ascDistTo;
	private IndexMinPQ<Double> pq;

	public MonotonicSPDraft1(EdgeWeightedDigraph G, int s) {
		descEdgeTo = new DirectedEdge[G.V()];
		ascEdgeTo = new DirectedEdge[G.V()];
		descDistTo = new double[G.V()];
		ascDistTo = new double[G.V()];
		pq = new IndexMinPQ<>(G.V());

		for (int v = 0; v < G.V(); v++) {
			descDistTo[v] = Double.POSITIVE_INFINITY;
			ascDistTo[v] = Double.POSITIVE_INFINITY;
		}
		descDistTo[s] = 0.0;
		ascDistTo[s] = 0.0;

		// search for descending shortest monotonous paths
		pq.insert(s, 0.0);
		while (!pq.isEmpty()) {
			int v = pq.delMin();
			for (DirectedEdge e : G.adj(v)) {
				descRelax(e);
			}
		}

		// search for ascending shortest monotonous paths
		pq.insert(s, 0.0);
		while (!pq.isEmpty()) {
			int v = pq.delMin();
			for (DirectedEdge e : G.adj(v)) {
				ascRelax(e);
			}
		}
	}

	public Iterable<DirectedEdge> shortestMonoDescPathTo(int v) {
		if (descDistTo[v] == Double.POSITIVE_INFINITY)
			return null;

		Stack<DirectedEdge> mdsp = new Stack<>();
		DirectedEdge e = descEdgeTo[v];
		while (e != null) {
			mdsp.push(e);
			e = descEdgeTo[e.from()];
		}

		return mdsp;
	}

	public double shortestMonoDescDistTo(int v) {
		return descDistTo[v];
	}

	public Iterable<DirectedEdge> shortestMonoAscPathTo(int v) {
		if (ascDistTo[v] == Double.POSITIVE_INFINITY)
			return null;

		Stack<DirectedEdge> masp = new Stack<>();
		DirectedEdge e = ascEdgeTo[v];
		while (e != null) {
			masp.push(e);
			e = ascEdgeTo[e.from()];
		}

		return masp;
	}

	public double shortestMonoAscDistTo(int v) {
		return ascDistTo[v];
	}

	public Iterable<DirectedEdge> shortestMonoPathTo(int v) {
		if (descDistTo[v] == Double.POSITIVE_INFINITY && ascDistTo[v] == Double.POSITIVE_INFINITY)
			return null;
		if (descDistTo[v] == Double.POSITIVE_INFINITY && ascDistTo[v] != Double.POSITIVE_INFINITY)
			return shortestMonoAscPathTo(v);
		if (descDistTo[v] != Double.POSITIVE_INFINITY && ascDistTo[v] == Double.POSITIVE_INFINITY)
			return shortestMonoDescPathTo(v);

		if (ascDistTo[v] < descDistTo[v])
			return shortestMonoAscPathTo(v);

		return shortestMonoDescPathTo(v);
	}

	private void descRelax(DirectedEdge e) {
		int v = e.from();
		int w = e.to();
		if (descDistTo[w] > descDistTo[v] + e.weight()
				&& (descEdgeTo[v] == null || descEdgeTo[v].weight() > e.weight())) {
			descDistTo[w] = descDistTo[v] + e.weight();
			descEdgeTo[w] = e;

			if (pq.contains(w))
				pq.decreaseKey(w, descDistTo[w]);
			else
				pq.insert(w, descDistTo[w]);
		}
	}

	private void ascRelax(DirectedEdge e) {
		int v = e.from();
		int w = e.to();
		if (ascDistTo[w] > ascDistTo[v] + e.weight() && (ascEdgeTo[v] == null || ascEdgeTo[v].weight() < e.weight())) {
			ascDistTo[w] = ascDistTo[v] + e.weight();
			ascEdgeTo[w] = e;

			if (pq.contains(w))
				pq.decreaseKey(w, ascDistTo[w]);
			else
				pq.insert(w, ascDistTo[w]);
		}
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		EdgeWeightedDigraph g = new EdgeWeightedDigraph(in);
		MonotonicSPDraft1 msp = new MonotonicSPDraft1(g, 0);
		int t = 3;

		Iterable<DirectedEdge> sMDPTo = msp.shortestMonoDescPathTo(t);
		if (sMDPTo == null)
			System.out.println("Shortest descending path to " + t + " does not exist.");
		else {
			System.out.println("Shortest descending path to " + t + " weighs " + msp.descDistTo + ": ");
			for (DirectedEdge e : sMDPTo)
				System.out.print(e.toString() + "	");
			System.out.println();
		}

		Iterable<DirectedEdge> sMAPTo = msp.shortestMonoAscPathTo(t);
		if (sMAPTo == null)
			System.out.println("Shortest ascending path to " + t + " does not exist.");
		else {
			System.out.println("Shortest ascending path to " + t + " weighs " + msp.shortestMonoAscDistTo(t) + ": ");
			for (DirectedEdge e : sMAPTo)
				System.out.print(e.toString() + "	");
			System.out.println();
		}

		Iterable<DirectedEdge> sMPTo = msp.shortestMonoPathTo(t);
		if (sMPTo == null)
			System.out.println("Shortest monotonous path to " + t + " does not exist.");
		else {
			System.out.println("Shortest monotonous path to " + t + ": ");
			for (DirectedEdge e : sMPTo) {
				System.out.print(e.toString() + "	");
			}
		}
	}
}
