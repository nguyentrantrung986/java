import edu.princeton.cs.algs4.*;

public class BaseballElimination {
	private final RedBlackBST<String, Integer> teamIndex;
	private final int[] w, l, r;
	private final int[][] g;

	public BaseballElimination(String filename) {
		In in = new In(filename);
		int teams = in.readInt();
		teamIndex = new RedBlackBST<>();
		w = new int[teams];
		l = new int[teams];
		r = new int[teams];
		g = new int[teams][teams];

		int i = 0;
		while (!in.isEmpty()) {
			// one line example: Atlanta 83 71 8 0 1 6 1
			String s = in.readString();
			teamIndex.put(s, i);
			w[i] = in.readInt();
			l[i] = in.readInt();
			r[i] = in.readInt();
			for (int j = 0; j < teams; j++)
				g[i][j] = in.readInt();
			i++;
		}
	}

	public Iterable<String> teams() {
		return teamIndex.keys();
	}

	public int wins(String team) {
		if (!teamIndex.contains(team))
			throw new java.lang.IllegalArgumentException("Invalid team name.");
		int i = teamIndex.get(team);
		return w[i];
	}

	public int losses(String team) {
		if (!teamIndex.contains(team))
			throw new java.lang.IllegalArgumentException("Invalid team name.");
		int i = teamIndex.get(team);
		return l[i];
	}

	public int remaining(String team) {
		if (!teamIndex.contains(team))
			throw new java.lang.IllegalArgumentException("Invalid team name.");
		int i = teamIndex.get(team);
		return r[i];
	}

	public int against(String team1, String team2) {
		if (!teamIndex.contains(team1) || !teamIndex.contains(team2))
			throw new java.lang.IllegalArgumentException("Invalid team name.");
		int i = teamIndex.get(team1);
		int j = teamIndex.get(team2);
		return g[i][j];
	}
	
	public static void main(String[] args) {
	    BaseballElimination division = new BaseballElimination(args[0]);
	    for (String team : division.teams()) {
//	        if (division.isEliminated(team)) {
//	            StdOut.print(team + " is eliminated by the subset R = { ");
//	            for (String t : division.certificateOfElimination(team)) {
//	                StdOut.print(t + " ");
//	            }
//	            StdOut.println("}");
//	        }
//	        else {
//	            StdOut.println(team + " is not eliminated");
//	        }
	    }
	}
}
