package graph;

import java.util.Arrays;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

/**
 * A tree is a graph with no cycle, i.e. there is only one single path between 2
 * arbitrary vertices. The distance between 2 vertices is the number of edges on
 * the shortest path between them. The eccentricity of a vertex is the largest
 * distance from it to any other vertices in the graph The diameter of a graph
 * is the value of the greatest eccentricity The radius of a graph is the value
 * of the smallest eccentricity The centers of a graph are the vertices with
 * smallest eccentricity (equal radius of the graph)
 * 
 * @author ennnttg
 *
 */
public class TreeDiameterAndCenter {
	private Graph g;

	public TreeDiameterAndCenter(Graph g) {
		this.g = g;
	}

	/**
	 * To find the diameter, use BFS to get the farthest point from an arbitrary
	 * point s, call this u. BFS the farthest point from u, call this v. The
	 * path (u, v) is the diameter of the graph. Proof: if the first BFS return
	 * a point t other than u or v, then distance from t to s d(t,s) is greater
	 * than d(t,u) and d(t,v). d(t,u) = d(t,x) + d(x,u), d(t,v) = d(t,x) +
	 * d(x,v), either of these will be great than d(u,v), which is a
	 * contradiction. So the first BFS should result in an end of a diameter in
	 * the graph. Hence, the second BFS result in a diameter.
	 * 
	 * @param g
	 * @return an array of all vertices in the diameter 
	 */
	public int[] diameter() {
		int s = StdRandom.uniform(g.V());
		
		BFS bfsFromS = new BFS(g,s);
		int u = bfsFromS.getFurthest();
		BFS bfsFromU = new BFS(g,u);
		int v = bfsFromU.getFurthest();
		
		return bfsFromU.minPathToStart(v);
	}
	
	/**
	 * The center(s) of a tree graph is/are one or two median vertices of its diameter
	 * proof: It's essential to prove that the center(s) must lie on one of the diameters: 
	 * for every vertex p in the graph, the eccentricity of p is the distance from p to either end
	 * of a diameter u & v (see proof for diameter above). If p is not on the diameter, and the tree has no
	 * loop, p cannot connects to u or v without going through the diameter. So point m is where the path
	 * pv or pu starts to merge with the diameter. Then eccentricity of m must be smaller that of p, because
	 * pm + mu = pu, and pv + mv = pv, and either of these are eccentricity of m and p.
	 * @param args
	 */
	public int[] center(){
		int[] diameter = diameter();
		if(diameter.length%2 == 0){
			int[] bicenter = new int[2];
			bicenter[0] = diameter[diameter.length/2];
			bicenter[1] = diameter[diameter.length/2 - 1];
			return bicenter;
		}else{
			int[] center = new int[1];
			center[0] = diameter[diameter.length/2];
			return center;
		}
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

		TreeDiameterAndCenter tdc = new TreeDiameterAndCenter(g);
		int[] diameter = tdc.diameter();

		System.out.println("Diameter length is " + (diameter.length - 1) + ": " + Arrays.toString(diameter));
		System.out.println("Center of the graph: " + Arrays.toString(tdc.center()));
	}
}
