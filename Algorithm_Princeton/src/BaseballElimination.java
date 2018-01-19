import java.util.Arrays;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;


public class BaseballElimination {
	private final RedBlackBST<String, Integer> teamIndexLookup;
	private final String[] teamNames;
	private final int[] w, l, r;
	private final int[][] g;
	private final int flowNetworkVertices;
	private int query;
	private boolean eliminated;
	private Queue<String> cert;

	public BaseballElimination(String filename) {
		In in = new In(filename);
		query = -1;
		int teams = in.readInt();
		teamIndexLookup = new RedBlackBST<>();
		w = new int[teams];
		l = new int[teams];
		r = new int[teams];
		g = new int[teams][teams];
		teamNames = new String[teams];

		int i = 0;
		while (!in.isEmpty()) {
			// one line example: Atlanta 83 71 8 0 1 6 1
			String s = in.readString();
			teamIndexLookup.put(s, i);
			teamNames[i] = s;
			w[i] = in.readInt();
			l[i] = in.readInt();
			r[i] = in.readInt();
			for (int j = 0; j < teams; j++)
				g[i][j] = in.readInt();
			i++;
		}
		
		flowNetworkVertices = (w.length-1)*(w.length-2)/2 + w.length - 1 + 2;
	}
	
	public int numberOfTeams()  {
		return w.length;
	}

	public Iterable<String> teams() {
		return teamIndexLookup.keys();
	}

	public int wins(String team) {
		if (!teamIndexLookup.contains(team))
			throw new java.lang.IllegalArgumentException("Invalid team name.");
		int i = teamIndexLookup.get(team);
		return w[i];
	}

	public int losses(String team) {
		if (!teamIndexLookup.contains(team))
			throw new java.lang.IllegalArgumentException("Invalid team name.");
		int i = teamIndexLookup.get(team);
		return l[i];
	}

	public int remaining(String team) {
		if (!teamIndexLookup.contains(team))
			throw new java.lang.IllegalArgumentException("Invalid team name.");
		int i = teamIndexLookup.get(team);
		return r[i];
	}

	public int against(String team1, String team2) {
		if (!teamIndexLookup.contains(team1) || !teamIndexLookup.contains(team2))
			throw new java.lang.IllegalArgumentException("Invalid team name.");
		int i = teamIndexLookup.get(team1);
		int j = teamIndexLookup.get(team2);
		return g[i][j];
	}
	
	// is given team eliminated?
	public boolean isEliminated(String team) {
		if (!teamIndexLookup.contains(team))
			throw new java.lang.IllegalArgumentException("Invalid team name.");
		
		FlowNetwork fn = new FlowNetwork(flowNetworkVertices);
		int x = teamIndexLookup.get(team);
		query = x;
		cert = new Queue<>();
		double totalMatchToPlay = 0;
		
		//add flow edges from team vertices to sink vertex
		for(int i=1; i < w.length; i++){
			int teamIndex = teamIndex(x, i);
			int stillCanWin = w[x]+r[x]-w[teamIndex];
			if(stillCanWin < 0){ //trivial elimination
				eliminated = true;
				cert.enqueue(teamNames[teamIndex]);
				return eliminated;
			}
			
			FlowEdge fe = new FlowEdge(i,flowNetworkVertices -1,stillCanWin);
			fn.addEdge(fe);
		}
		
		//add flow edges from source to match vertices to team vertices
		for(int i = w.length; i < flowNetworkVertices -1; i++){
			int[] teamVertices = teamVertices(i);
			int t1 = teamIndex(x, teamVertices[0]);
			int t2 = teamIndex(x, teamVertices[1]);
			int matchToPlay = g[t1][t2];
			totalMatchToPlay += matchToPlay;
			FlowEdge fe = new FlowEdge(0,i,matchToPlay);
			fn.addEdge(fe);
			fe = new FlowEdge(i,teamVertices[0],Double.POSITIVE_INFINITY);
			fn.addEdge(fe);
			fe = new FlowEdge(i,teamVertices[1],Double.POSITIVE_INFINITY);
			fn.addEdge(fe);			
		}
		
		FordFulkerson ff = new FordFulkerson(fn,0,flowNetworkVertices -1);
		//cache result
		eliminated = !(ff.value()==totalMatchToPlay);
		if(eliminated){
			for(int i=0; i<w.length;i++){
				if(i!=x){
					int teamVertex = teamVertex(x, i);
					if(ff.inCut(teamVertex))
						cert.enqueue(teamNames[teamIndex(x, teamVertex)]);
				}
			}
		}
		
		return eliminated;
	}

	public Iterable<String> certificateOfElimination(String team) {
		if (!teamIndexLookup.contains(team))
			throw new java.lang.IllegalArgumentException("Invalid team name.");
		
		int x = teamIndexLookup.get(team);
		if(x != query)
			isEliminated(team);
		if(!eliminated)
			return null;
		
		return cert;
	}
	/*
	 * Calculate which vertex represent the team in the flow map. Source index
	 * is 0 so team index starting from 1, and depends on whether the original
	 * team index is less or greater than x
	 */
	private int teamVertex(int x, int teamIndex) {
		if(teamIndex < x)
			return teamIndex + 1;
		return teamIndex;
	}
	
	private int teamIndex(int x, int teamVertex){
		if(teamVertex <= x)
			return teamVertex -1;
		return teamVertex;
	}
	
	/*	match vertices start from index n, after n-1 team vertices and the source vertex
	 *  consider the 2d array to count the combination, less = row, greater = column
	 *  inputs are team vertices, not original indices of the teams
	 * * 1 2 3 4 
	 * 1 * 0 1 2 	
	 * 2 * * 3 4 
	 * 3 * * * 5 
	 * 4 * * * * 
	 */
	private int matchVertex(int t1, int t2){
		int teams = w.length -1;
		int greater = (t1>t2)?t1:t2;
		int less = (t1>t2)?t2:t1;
		return teams*less - (less+1)*less/2 + greater;
	}
	
	//return team vertices given the match Vertex
	private int[] teamVertices(int matchVertex){
		int[] teamVertices = new int[2]; 
		int t1;
		for(int i=1;; i++){
			int t = i*(w.length -1) - i*(i+1)/2;
			if (t > matchVertex - w.length){
				t1 = i;
				break;
			}
		}
		
		teamVertices[1] = matchVertex - (w.length-1)*t1 + (t1+1)*t1/2;
		teamVertices[0] = t1;
		
		return teamVertices;
	}

	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination(args[0]);
		System.out.println(division.teamVertex(3, 4));
		System.out.println(division.teamIndex(3, 3));
		System.out.println(division.matchVertex(3, 2));
		System.out.println(Arrays.toString(division.teamVertices(10)));
//		division.isEliminated("England");
//		division.certificateOfElimination("England");
		
		for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	                StdOut.print(t + " ");
	            }
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }
	}
}
