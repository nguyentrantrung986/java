/**
 * Choose any starting vertex v, and follow a trail of edges from that vertex
 * until returning to v. It is not possible to get stuck at any vertex other
 * than v, because the even degree of all vertices ensures that, when the trail
 * enters another vertex w there must be an unused edge leaving w. The tour
 * formed in this way is a closed tour, but may not cover all the vertices and
 * edges of the initial graph.
 * 
 * As long as there exists a vertex u that belongs to the current tour but that
 * has adjacent edges not part of the tour, start another trail from u,
 * following unused edges until returning to u, and join the tour formed in this
 * way to the previous tour.
 **/
package graph;

import java.util.ArrayList;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class EulerCycle {
	private final Graph g;

	public EulerCycle(Graph g) {
		this.g = g;
	}

	public boolean isEulerian() {
		for (int i = 0; i < g.V(); i++) {
			if (g.degree(i) % 2 != 0)
				return false;
		}

		return true;
	}

	public Iterable<Integer> cycleFromPoint(int v) {
		if (isEulerian()) {
			ArrayList<Integer>[] tracker = copyEdges();
			Queue<Integer> cycle = new Queue<>();
			subtourFromPoint(v, tracker, cycle);
			return cycle;
		}

		return null;
	}

	// copy contains all edges which have not been traversed
	private void subtourFromPoint(int orig, ArrayList<Integer>[] tracker, Queue<Integer> cycle) {
		cycle.enqueue(orig);
		Stack<Integer> subtour = new Stack<>();
		subtour.push(orig);
		int current = tracker[orig].get(0);

		// remove the visited edge from the tracker
		tracker[current].remove(new Integer(orig));
		tracker[orig].remove(new Integer(current));

		// move until returning to the original point
		while (current != orig) {
			// keep track of the visited points in order of visit
			subtour.push(current);
			int next = tracker[current].get(0);
			tracker[current].remove(new Integer(next));
			tracker[next].remove(new Integer(current));
			current = next;
		}

		// backtracking once sub-tour is formed
		while (!subtour.isEmpty()) {
			current = subtour.pop();
			// if the point has no unvisited edge, add it to the result cycle
			if (tracker[current].isEmpty())
				cycle.enqueue(current);
			/*
			 * if the point has some unvisited edge, form a subtour from that
			 * point the recursive call will merge that subtour to the cycle
			 * from that point
			 */
			else
				subtourFromPoint(current, tracker, cycle);
		}
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Integer>[] copyEdges() {
		ArrayList<Integer>[] copy = (ArrayList<Integer>[]) new ArrayList[g.V()];
		for (int i = 0; i < g.V(); i++) {
			copy[i] = new ArrayList<Integer>();
			for (int av : g.adj(i)) {
				copy[i].add(av);
			}
		}

		return copy;
	}

	public static void main(String[] args) {
		String filename = args[0];
		In in = new In(filename);
		int v = in.readInt();

		Graph g = new Graph(v);
		while (!in.isEmpty()) {
			int v1 = in.readInt();
			int v2 = in.readInt();
			g.addEdge(v1, v2);
		}
		// g.addEdge(0, 1);
		// g.addEdge(0, 5);
		// g.addEdge(1, 4);
		// g.addEdge(1, 3);
		// g.addEdge(1, 2);
		// g.addEdge(2, 3);
		// g.addEdge(4, 5);

		EulerCycle ec = new EulerCycle(g);

		System.out.println("Is Eulerian? " + ec.isEulerian());
		System.out.println("Eulerian cycle: " + ec.cycleFromPoint(3));
	}
}
