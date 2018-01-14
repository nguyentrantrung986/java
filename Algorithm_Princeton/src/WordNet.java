import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdIn;

public class WordNet {
	// find IDs of syn-sets containing specific words
	private final RedBlackBST<String, SET<Integer>> synDict;
	// find the syn-set given its ID
	private final RedBlackBST<Integer, String> synSetDict;
	private final SAP grapHypernyms;

	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		synDict = new RedBlackBST<>();
		synSetDict = new RedBlackBST<>();
		int setCount = readSynsets(synsets);

		Digraph g = new Digraph(setCount);
		readHypernyms(hypernyms, g);
		grapHypernyms = new SAP(g);
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return synDict.keys();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		return synDict.contains(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		if (!synDict.contains(nounB) || !synDict.contains(nounA))
			throw new java.lang.IllegalArgumentException();
		return grapHypernyms.length(synDict.get(nounA), synDict.get(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of
	// nounA and nounB in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		if (!synDict.contains(nounB) || !synDict.contains(nounA))
			throw new java.lang.IllegalArgumentException();
		int ancestor = grapHypernyms.ancestor(synDict.get(nounA), synDict.get(nounB));

		return synSetDict.get(ancestor);

	}

	private int readSynsets(String synsetsFile) {
		In in = new In(synsetsFile);
		int lineCount = 0;
		while (!in.isEmpty()) {
			String line = in.readLine();
			lineCount++;
			String[] tokens = line.split(",");
			int synsetId = Integer.parseInt(tokens[0]);
			synSetDict.put(synsetId, tokens[1]);
			String[] words = tokens[1].split(" ");
			for (String word : words) {
				if (synDict.contains(word)) {
					SET<Integer> synsets = synDict.get(word);
					synsets.add(synsetId);
				} else {
					SET<Integer> synsets = new SET<>();
					synsets.add(synsetId);
					synDict.put(word, synsets);
				}
			}
		}

		in.close();
		return lineCount;
	}

	private void readHypernyms(String hypernyms, Digraph g) {
		In in = new In(hypernyms);
		while (!in.isEmpty()) {
			String line = in.readLine();
			String[] tokens = line.split(",");
			int thisSetId = Integer.parseInt(tokens[0]);
			for (int i = 1; i < tokens.length; i++) {
				int parentSetId = Integer.parseInt(tokens[i]);
				g.addEdge(thisSetId, parentSetId);
			}
		}
	}

	// do unit testing of this class
	public static void main(String[] args) {
		WordNet wn = new WordNet(args[0], args[1]);
		System.out.println(wn.nouns());
		while (!StdIn.isEmpty()) {
			String test1 = StdIn.readString();
			String test2 = StdIn.readString();
			System.out.println("Wordnet contains " + test1 + "? " + wn.isNoun(test1));
			System.out.println("Wordnet contains " + test2 + "? " + wn.isNoun(test2));
			System.out.println("Distance " + test1 + " and " + test2 + ": " + wn.distance(test1, test2));
			System.out.println("Closest common ancestor :" + wn.sap(test1, test2));
		}
	}
}
