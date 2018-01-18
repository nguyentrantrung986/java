package graph;

import edu.princeton.cs.algs4.*;
/**
 * This works for DAG only, loops will make the algorithm runs incorrectly.
 * Each edge may be relaxed more than once.
 * @author ennnttg
 *
 */
public class MonotonicSP {
	private DistanceEdge[] pathTo;
	private MinPQ<DistanceEdge> pq;
	private EdgeWeightedDigraph g;
	private int relaxCount;

	public MonotonicSP(EdgeWeightedDigraph g, int s) {
		pathTo = new DistanceEdge[g.V()];
		pq = new MinPQ<>();
		this.g = g;
		relaxCount =0;

		for (int i = 0; i < pathTo.length; i++) {
			pathTo[i] = new DistanceEdge(null, null, Double.POSITIVE_INFINITY);
		}
		
		pathTo[s] = new DistanceEdge(null, null, 0);
		for (DirectedEdge e : g.adj(s)) {
			int t = e.to();
			DistanceEdge de = new DistanceEdge(e, null, e.weight());
			pathTo[t] = de;
			pq.insert(de);
		}

		while (!pq.isEmpty()) {
			DistanceEdge de = pq.delMin();
			descRelax(de);
			relaxCount++;
		}
	}

	/*precondition: there is already a monotonic descending path to head vertex w, i.e.
	 * When an edge is added to queue, there must already have been a descending path to its head*/
	private void descRelax(DistanceEdge ie) {
		 int w = ie.edge.to();
//		 int v = ie.edge.from()
		// update the shortest descending path to w
//		if (pathTo[w].distanceToTail > pathTo[v].distanceToTail + ie.edge.weight()
//				&& ie.edge.weight() < pathTo[v].edge.weight()) {
//			DistanceEdge de = new DistanceEdge(ie.edge, pathTo[v], pathTo[v].distanceToTail + ie.edge.weight());
//			pathTo[w] = de;
//		}
		
		// look for new descending paths from w
		for (DirectedEdge oe : g.adj(w)) {
			if (oe.weight() < ie.edge.weight()) {
				int x = oe.to();
				DistanceEdge de = new DistanceEdge(oe, ie, ie.distanceToTail + oe.weight());
				pq.insert(de);
				if(pathTo[x].distanceToTail > de.distanceToTail)
					pathTo[x] = de;
			}
		}
	}

	public Iterable<DirectedEdge> descPathTo(int v) {
		if (pathTo[v].edge == null)
			return null;

		Stack<DirectedEdge> path = new Stack<>();
		DistanceEdge de = pathTo[v];
		while (de != null) {
			path.push(de.edge);
			de = de.prev;
		}

		return path;
	}

	private class DistanceEdge implements Comparable<DistanceEdge> {
		private DirectedEdge edge;
		private DistanceEdge prev;
		private double distanceToTail;

		public DistanceEdge(DirectedEdge edge, DistanceEdge prev, double distanceToTail) {
			this.edge = edge;
			this.prev = prev;
			this.distanceToTail = distanceToTail;
		}

		@Override
		public int compareTo(DistanceEdge that) {
			if (this.distanceToTail > that.distanceToTail)
				return 1;
			if (this.distanceToTail < that.distanceToTail)
				return -1;
			return 0;
		}
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		EdgeWeightedDigraph g = new EdgeWeightedDigraph(in);
		MonotonicSP msp = new MonotonicSP(g, 0);
		System.out.println("Relax edges "+msp.relaxCount+ " times.");

		in = new In();
		while (!in.isEmpty()) {
			int t = in.readInt();
			System.out.println(msp.descPathTo(t));
		}
	}
}
