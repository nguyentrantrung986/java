package graph;

import java.util.Iterator;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMaxPQ;
import edu.princeton.cs.algs4.Stack;

/**
 * Fattest path. Given an edge-weighted digraph and two vertices s and t, design
 * an ElogE algorithm to find a fattest path from s to t. The bottleneck
 * capacity of a path is the minimum weight of an edge on the path. A fattest
 * path is a path such that no other path has a higher bottleneck capacity.
 * 
 * @author TrungNT
 *
 */
public class FattestPath {
	double[] pathWeight;
	DirectedEdge[] edgeTo;
	int source;

	public FattestPath(EdgeWeightedDigraph g, int s) {
		this.source = s;
		pathWeight = new double[g.V()];
		edgeTo = new DirectedEdge[g.V()];
		IndexMaxPQ<Double> pq = new IndexMaxPQ<Double>(g.V());

		for (int i = 0; i < g.V(); i++) {
			pathWeight[i] = Double.NEGATIVE_INFINITY;
		}
		pathWeight[s] = Double.POSITIVE_INFINITY;
		pq.insert(s, pathWeight[s]);

		while (!pq.isEmpty()) {
			int v = pq.delMax();
			System.out.print("deleting  " + v);
			printPQ(pq);

			for (DirectedEdge e : g.adj(v))
				relax(pq, e);
		}
	}

	private void relax(IndexMaxPQ<Double> pq, DirectedEdge edge) {
		int v = edge.from();
		int w = edge.to();
		/*
		 * when vertex v got deleted from the queue, its fattest path has been
		 * established, so this conditions will prevents v from ever being
		 * inserted again into the queue
		 */
		if (pathWeight[w] < Math.min(pathWeight[v], edge.weight())) {
			pathWeight[w] = Math.min(pathWeight[v], edge.weight());
			edgeTo[w] = edge;

			if (pq.contains(w))
				pq.increaseKey(w, pathWeight[w]);
			else {
				pq.insert(w, pathWeight[w]);
				System.out.print("inserting " + w);
				printPQ(pq);
			}
		}
	}

	private void printPQ(IndexMaxPQ<Double> pq) {
		System.out.print(" pq contains:");
		Iterator<Integer> iter = pq.iterator();
		while (iter.hasNext())
			System.out.print(" " + iter.next());
		System.out.println();
	}

	public Iterable<DirectedEdge> fattestPathTo(int v) {
		if (pathWeight[v] == Double.NEGATIVE_INFINITY)
			return null;
		Stack<DirectedEdge> path = new Stack<>();

		DirectedEdge e = edgeTo[v];
		while (e != null) {
			path.push(e);
			e = edgeTo[e.from()];
		}
		return path;
	}

	public double fattestPathWeightTo(int v) {
		return pathWeight[v];
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		EdgeWeightedDigraph g = new EdgeWeightedDigraph(in);
		FattestPath fp = new FattestPath(g, 0);
		in = new In();
		while (!in.isEmpty()) {
			int v = in.readInt();
			System.out.println(
					"Fattest path to " + v + " weighs " + fp.fattestPathWeightTo(v) + ": " + fp.fattestPathTo(v));
		}
	}
}
