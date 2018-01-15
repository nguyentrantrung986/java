package graph;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

/**
 * Check if an edge in a weighted undirected graph belongs to any minimum
 * spanning tree
 * 
 * @author ennnttg
 *
 */
public class EdgeInMST {
	private EdgeWeightedGraph g;

	public EdgeInMST(EdgeWeightedGraph g) {
		this.g = g;
	}

	/**
	 * Run the DFS from one vertex of the edge, only consider edges with weights
	 * less than e. If the other vertex can be reached, then e is not in a MST
	 * because there exists a cycle where e is the maximum edge (cycle
	 * property). Otherwise, e belongs to a MST. Proof of this case is that
	 * Kruskal algorithm will surely include e as all smaller edges do not
	 * connect two vertices of e i.e. e would form no loop.
	 * 
	 * @param e
	 * @return whether the edge e is in a minimum spanning tree
	 */
	public boolean inMST(Edge e) {
		boolean[] marked = new boolean[g.V()];
		int v = e.either();
		weightedDFS(v, e.other(v), marked, e.weight());
		
		return !marked[e.other(v)];
	}

	private void weightedDFS(int v, int w, boolean[] marked, double weight) {
		Stack<Integer> toProcess = new Stack<>();
		toProcess.push(v);
		marked[v] = true;

		while (!toProcess.isEmpty()) {
			int p = toProcess.pop();

			for (Edge e : g.adj(p)) {
				int q = e.other(p);
				if (e.weight() < weight && !marked[q]) {
					marked[q] = true;
					if (q == w)
						return;
					toProcess.push(q);
				}
			}
		}
	}
	
	public static void main(String[] args){
		In in = new In(args[0]);
		EdgeWeightedGraph g = new EdgeWeightedGraph(in);
		EdgeInMST eiMST = new EdgeInMST(g);
		Edge e = new Edge(7,5,0.28);
		System.out.println("Edge "+e.toString()+" is in an MST? "+eiMST.inMST(e));
	}
}