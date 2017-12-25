import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;;

public class Percolation {
	private WeightedQuickUnionUF uFObj;
	private int n;
	private boolean[] siteOpened;
	private int openCnt; // do not count 2 virtual sites

	public Percolation(int n) {
		if (n <= 0)
			throw new IllegalArgumentException();

		this.n = n;
		openCnt = 0;
		siteOpened = new boolean[n * n + 2]; // false = blocked, true = open
		uFObj = new WeightedQuickUnionUF(n * n + 2); // 2 additional virtual
														// sites for top and
		// bottom rows.

		if (n > 1) {
			// connect the top virtual site to all sites in the top row if the
			// grid size > 1
			for (int i = 1; i <= n; i++) {
				uFObj.union(0, i);
			}
			siteOpened[0] = true; // virtual top site is open

			// connect the bottom virtual site to all sites in the bottom row
			int firstBottomIndex = getIndexFromRowCol(n, 1);
			int lastBottomIndex = getIndexFromRowCol(n, n);
			int bottomVirtualIndex = getIndexFromRowCol(n, n) + 1;

			for (int i = firstBottomIndex; i <= lastBottomIndex; i++) {
				uFObj.union(bottomVirtualIndex, i);
			}
			siteOpened[bottomVirtualIndex] = true; // virtual bottom site is
													// open
		}
	}

	public void open(int row, int col) {
		validate(row, col);
		int i = getIndexFromRowCol(row, col);

		if (siteOpened[i] == false) {
			openCnt++;

			siteOpened[i] = true;

			if (n > 1) {
				// if this site is not in top row and the site on top of this
				// site
				// is open, the connect it to this site
				if (i > n && siteOpened[i - n]) {
					uFObj.union(i - n, i);
				}

				// if this site is not in the bottom row and the site at the
				// bottom
				// of this site is open, the connect it to
				// this site
				if (i < n * (n - 1) + 1 && siteOpened[i + n]) {
					uFObj.union(i + n, i);
				}

				// if the site to the left of this site is open, the connect it
				// to
				// this site
				if (i % n != 1 && siteOpened[i - 1]) {
					uFObj.union(i - 1, i);
				}

				// if the site to the right of this site is open, the connect it
				// to
				// this site
				if (i % n != 0 && siteOpened[i + 1]) {
					uFObj.union(i + 1, i);
				}
			}else{
				// if n =1, i.e.there is only one site, the connect it to 2 virtual sites
				uFObj.union(0, 1);
				uFObj.union(2, 1);
			}
		}
	}

	public boolean isOpen(int row, int col) {
		validate(row, col);

		int i = getIndexFromRowCol(row, col);
		return siteOpened[i];
	}

	public boolean isFull(int row, int col) {
		validate(row, col);

		int i = getIndexFromRowCol(row, col);
		// check if the site is open & connected to the virtual top site 
		// (1st row is always connect to top virtual site even when they are not opened yet
		return siteOpened[i] && uFObj.connected(0, i); 
	}

	public int numberOfOpenSites() {
		return openCnt;
	}

	public boolean percolates() {
		int bottomVirtualIndex = getIndexFromRowCol(n, n) + 1;
		return uFObj.connected(0, bottomVirtualIndex);
	}

	private int getIndexFromRowCol(int row, int col) {
		// the first site is the virtual one for the top row
		return (row - 1) * n + (col - 1) + 1;
	}

	private void validate(int row, int col) {
		if (row < 1 || row > n || col < 1 || col > n) {
			throw new IllegalArgumentException("row & column should be between 1 & " + n);
		}
	}

	public static void main(String[] args) {
		int n = StdIn.readInt();
		Percolation p = new Percolation(n);

		while (!StdIn.isEmpty()) {
			int row = StdIn.readInt();
			int col = StdIn.readInt();
			p.open(row, col);
			StdOut.println("No of open sites: " + p.openCnt + ", percolated: " + p.percolates());
		}
	}
}
