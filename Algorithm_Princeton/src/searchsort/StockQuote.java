package searchsort;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class StockQuote {
	public static void main(String[] args) {
		String name = "https://finance.yahoo.com/q?s=";
		In in = new In(name + args[0]);
		String text = in.readAll();
		int start = text.indexOf("Last Price:", 0);
		int from = text.indexOf("<b>", start);
		int to = text.indexOf("</b>", from);
		String price = text.substring(from + 3, to);
		StdOut.println(price);
	}
}