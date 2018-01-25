package string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;

public class NFA {
	private char[] re; // match transitions
	private Digraph G; // epsilon transition digraph
	private int M; // number of states

	public NFA(String regexp) {
		M = regexp.length();
		re = regexp.toCharArray();
		G = buildEpsilonTransitionDigraph();
	}

	public boolean recognizes(String txt) {
		Bag<Integer> pc = new Bag<Integer>();
		DirectedDFS dfs = new DirectedDFS(G, 0);
		for (int v = 0; v < G.V(); v++)
			if (dfs.marked(v))
				pc.add(v);
		// find the states reachable after reading character i from the text
		for (int i = 0; i < txt.length(); i++) {
			Bag<Integer> match = new Bag<Integer>();
			for (int v : pc) {
				if (v == M)
					continue;
				if ((re[v] == txt.charAt(i)) || re[v] == '.')
					match.add(v + 1);
			}

			dfs = new DirectedDFS(G, match);
			pc = new Bag<Integer>();
			for (int v = 0; v < G.V(); v++)
				if (dfs.marked(v))
					pc.add(v);
		}
		for (int v : pc)
			if (v == M)
				return true;
		return false;
	}

	public Digraph buildEpsilonTransitionDigraph() {
		Digraph G = new Digraph(M + 1);
		Stack<Integer> ops = new Stack<Integer>();
		for (int i = 0; i < M; i++) {
			int lp = i;
			// left parentheses and |
			if (re[i] == '(' || re[i] == '|')
				ops.push(i);

			else if (re[i] == ')') {
				Stack<Integer> ors = new Stack<>();
				int or = ops.pop();

				// n-way or
				while (re[or] == '|') {
					ors.push(or);
					G.addEdge(or, i);
					or = ops.pop();
				}
				lp = or;
				while (!ors.isEmpty()) {
					or = ors.pop();
					G.addEdge(lp, or + 1);
				}
			}

			// closure (needs 1-character lookahead)
			if (i < M - 1 && re[i + 1] == '*') {
				G.addEdge(lp, i + 1);
				G.addEdge(i + 1, lp);
			} else if (i < M - 1 && re[i + 1] == '+') {
				G.addEdge(i + 1, lp);
			}

			// meta-symbols
			if (re[i] == '(' || re[i] == '*' || re[i] == '+' || re[i] == ')')
				G.addEdge(i, i + 1);
		}
		return G;
	}

	public static void main(String[] args) {
		String regex1 = "(((A*B|AC|B+)|.AB)D)";
		NFA nfa = new NFA(regex1);
		System.out.println(nfa.G.toString());

		while (!StdIn.isEmpty()) {
			String input = StdIn.readString();
			Pattern p = Pattern.compile(regex1);
			Matcher m = p.matcher(input);
			System.out.println("Matcher: "+m.matches()+" NFA: "+nfa.recognizes(input));
		}
	}
}
