package exercises;

import java.util.ArrayList;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;

public class EulerCycle {
	private Graph g;
	
	public EulerCycle(Graph g){
		this.g = g;
	}
	
	public void cycleFromPoint(int v){
		ArrayList<Integer>[] copy = copyEdges();
		cycleFromPoint(v, v,copy);
	}
	
	//copy contains all edges which have not been traversed
	private void cycleFromPoint(int orig, int v, ArrayList<Integer>[] tracker){
		Iterable<Integer> adjV = g.adj(v);
		
		for(int av:adjV){
			if(tracker[v].contains(av)){
				tracker[v].remove(av);
				tracker[av].remove(v);
				cycleFromPoint(orig, av,tracker);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<Integer>[] copyEdges(){
		ArrayList<Integer>[] copy = (ArrayList<Integer>[])new ArrayList[g.V()];
		for(int i=0;i<g.V();i++){
			copy[i] = new ArrayList<Integer>();
			for(int av:g.adj(i)){
				copy[i].add(av);
			}
		}
		
		return copy;
	}
}
