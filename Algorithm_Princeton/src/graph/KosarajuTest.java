package graph;

import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.KosarajuSharirSCC;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * running Kosaraju Sharir algorithm on a DAG of v vertices will result in v separate strong component
 * the reverse post order returned by the first DFS ensures each vertex has no where to go except for
 * those vertices marked by previous calls of dfs method.
 * @author TrungNT
 *
 */
public class KosarajuTest {
	   @SuppressWarnings("unchecked")
	public static void main(String[] args) {
	        In in = new In(args[0]);
	        Digraph G = new Digraph(in);
	        KosarajuSharirSCC scc = new KosarajuSharirSCC(G);

	        // number of connected components
	        int m = scc.count();
	        StdOut.println(m + " strong components");

	        // compute list of vertices in each strong component
	        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
	        for (int i = 0; i < m; i++) {
	            components[i] = new Queue<Integer>();
	        }
	        for (int v = 0; v < G.V(); v++) {
	            components[scc.id(v)].enqueue(v);
	        }

	        // print results
	        for (int i = 0; i < m; i++) {
	            for (int v : components[i]) {
	                StdOut.print(v + " ");
	            }
	            StdOut.println();
	        }
	        
	        DepthFirstOrder dfo = new DepthFirstOrder(G);
	        System.out.println(dfo.reversePost());
	    }
}
