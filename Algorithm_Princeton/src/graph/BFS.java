package graph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;

public class BFS {
	private Graph g;
	private int s;
	private int furthest;
	private boolean[] marked;
	private int[] comeFrom;
	
	public BFS(Graph g, int s){
		this.g = g;
		this.s = s;
		
		this.furthest = bfs(s);
	}
	
	public int getStartVertext(){
		return s;
	}
	
	public int getFurthest(){
		return furthest;
	}
	
	/**distance from the starting point to this point v
	 * 
	 * @param v
	 * @return number of edges from the starting point to this point v
	 */
	public int distanceToStart(int v){
		int dis = 0;
		while(v!=s){
			v = comeFrom[v];
			dis++;
		}
		return dis;
	}
	
	public int[] minPathToStart(int v){
		int[] path = new int[distanceToStart(v)+1];

		for(int i=0; i< path.length; i++){
			path[i] = v;
			v = comeFrom[v];
		}
		
		return path;
	}
	
	private int bfs(int v) {
		marked = new boolean[g.V()];
		comeFrom = new int[g.V()];
		
		Queue<Integer> q = new Queue<>();
		q.enqueue(v);
		marked[v] = true;
		comeFrom[v] = v;
		int last = v;

		while (!q.isEmpty()) {
			last = q.dequeue();
			for (int n : g.adj(last)) {
				if (!marked[n]) {
					marked[n] = true;
					comeFrom[n] = last;
					q.enqueue(n);
				}
			}
		}
		
		return last;
	}
}
