import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private static final boolean DX = true;
	private static final boolean DY = false;

	private Node root;

	public KdTree() {
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return size(root);
	}
	
	private int size(Node x){
		if(x == null) return 0;
		
		return size(x.lb) + size(x.rt) + 1;
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();

		if(root == null){
			root = new Node(p);
			root.rect = new RectHV(0, 0, 1, 1);
		}else
			insert(root, new Node(p), 0);
	}

	// depth is the number of edges from root to the node, root depth = 0
	private Node insert(Node parent, Node newNode, int depth) {
		if (parent == null) {// not found newNode
			if (depth % 2 == 0)
				newNode.dividedBy = DX;
			else
				newNode.dividedBy = DY;

			return newNode;
		}

		int compare = parent.compareTo(newNode);
		if (compare < 0) {
			parent.lb = insert(parent.lb, newNode, depth + 1);

			if (parent.lb.rect == null) {
				double xmax = (parent.dividedBy == DX) ? parent.p.x() : parent.rect.xmax();
				double ymax = (parent.dividedBy == DX) ? parent.rect.ymax() : parent.p.y();
				parent.lb.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), xmax, ymax);
			}
		} else if (compare > 0) {
			parent.rt = insert(parent.rt, newNode, depth + 1);

			if (parent.rt.rect == null) {
				parent.rt.rect = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
			}
		}

		// if compare =0, the new node is found, do nothing
		return parent;
	}
	
	public void draw() {
		draw(root);
	}
	
	private void draw(Node x){
		if(x == null) return;
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		x.p.draw();
		
		if(x.dividedBy == DX){
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius(0.003);
			StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
		}else{
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius(0.003);
			StdDraw.line(x.rect.xmin(),x.p.y(), x.rect.ymin(), x.p.y());
		}
		draw(x.lb);
		draw(x.rt);
	}

	public boolean contains(Point2D p) {
		return contains(root, new Node(p));
	}

	private boolean contains(Node root, Node x) {
		if (root == null)
			return false;

		int compare = root.compareTo(x);
		if (compare < 0)
			return contains(root.lb, x);
		else if (compare > 0)
			return contains(root.rt, x);
		else
			return true;
	}

	private static class Node implements Comparable<Node> {
		private Point2D p; // the point
		private RectHV rect; // the axis-aligned rectangle corresponding to this
								// node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree
		private boolean dividedBy;

		public Node(Point2D p) {
			this.p = p;
		}

		@Override
		public int compareTo(Node that) {
			if (dividedBy == DX) {
				if (this.p.x() > that.p.x())
					return 1;
				else if (this.p.x() < that.p.x())
					return -1;
			} else {
				if (this.p.y() > that.p.y())
					return 1;
				else if (this.p.y() < that.p.y())
					return -1;
			}

			return 0;
		}
	}

	public static void main(String[] args) {
		KdTree kdt = new KdTree();
		kdt.insert(new Point2D(0.4, 0.3));
		kdt.insert(new Point2D(0.3, 0.7));
		kdt.insert(new Point2D(0.8, 0.6));

		System.out.println(kdt.contains(new Point2D(0.01, 0.7)));
		System.out.println(kdt.size());
		kdt.draw();
		StdDraw.show();
	}
}
