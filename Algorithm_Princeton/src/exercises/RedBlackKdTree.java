package exercises;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stopwatch;

/**Not possible to implement because rotation will mess up the rectangle order**/
public class RedBlackKdTree {
	private static final boolean DX = true;
	private static final boolean DY = false;
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	
	private int sz;

	private RBNode root;

	public RedBlackKdTree() {
		sz = 0;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return sz;
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();

		if (root == null) {
			root = new RBNode(p);
			root.rect = new RectHV(0, 0, 1, 1);
			root.dividedBy = DX;
			sz++;
		} else
			insert(root, new RBNode(p), 0);
	}

	// depth is the number of edges from root to the node, root depth = 0
	private RBNode insert(RBNode parent, RBNode newNode, int depth) {
		if (parent == null) {// newNode not found, insert by returning it
			if (depth % 2 == 0)
				newNode.dividedBy = DX;
			else
				newNode.dividedBy = DY;

			sz++;
			return newNode;
		}

		int compare = parent.compareTo(newNode);
		// compare indicates which side contains the query point, search that
		// side first
		if (compare > 0) {
			parent.lb = insert(parent.lb, newNode, depth + 1);

			if (parent.lb.rect == null) {
				double xmax = (parent.dividedBy == DX) ? parent.p.x() : parent.rect.xmax();
				double ymax = (parent.dividedBy == DX) ? parent.rect.ymax() : parent.p.y();
				parent.lb.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), xmax, ymax);
			}
		} else if (compare < 0) {
			parent.rt = insert(parent.rt, newNode, depth + 1);

			if (parent.rt.rect == null) {
				double xmin = (parent.dividedBy == DX) ? parent.p.x() : parent.rect.xmin();
				double ymin = (parent.dividedBy == DX) ? parent.rect.ymin() : parent.p.y();
				parent.rt.rect = new RectHV(xmin, ymin, parent.rect.xmax(), parent.rect.ymax());
			}
		}
		
		if(isRed(parent.rt) && !isRed(parent.lb)) parent = rotateLeft(parent);
		if(isRed(parent.lb) && isRed(parent.lb.lb)) parent = rotateRight(parent);
		if(isRed(parent.lb) && isRed(parent.rt)) flipColors(parent);
		// if compare =0, the new node is found, do nothing
		return parent;
	}

	public void draw() {
		draw(root);
	}

	private void draw(RBNode x) {
		if (x == null)
			return;
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		x.p.draw();

		if (x.dividedBy == DX) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius(0.003);
			StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius(0.003);
			StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
		}
		draw(x.lb);
		draw(x.rt);
	}

	public boolean contains(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();

		return contains(root, new RBNode(p));
	}

	private boolean contains(RBNode root, RBNode x) {
		if (root == null)
			return false;

		int compare = root.compareTo(x);
		if (compare > 0)
			return contains(root.lb, x);
		else if (compare < 0)
			return contains(root.rt, x);
		else
			return true;
	}

	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		if (isEmpty())
			return null;

		RBNode nearest = nearest(root, new RBNode(p), root.p.distanceSquaredTo(p));
		return nearest.p;
	}

	private RBNode nearest(RBNode parent, RBNode query, double minDistance) {
		if (parent == null)
			return null;
		RBNode nearest = parent;
		double d = parent.p.distanceSquaredTo(query.p);
		if (d < minDistance)
			minDistance = d;
		double dToLeft = (parent.lb == null) ? Double.MAX_VALUE : parent.lb.rect.distanceSquaredTo(query.p);
		double dToRight = (parent.rt == null) ? Double.MAX_VALUE : parent.rt.rect.distanceSquaredTo(query.p);

		int compare = parent.compareTo(query);
		if (compare > 0) {
			if (dToLeft < minDistance) {
				RBNode nearestLeft = nearest(parent.lb, query, minDistance);
				double minDistanceLeft = nearestLeft.p.distanceSquaredTo(query.p);
				if (minDistanceLeft < minDistance) {
					minDistance = minDistanceLeft;
					nearest = nearestLeft;
				}
			}
			if (dToRight < minDistance) {
				RBNode nearestRight = nearest(parent.rt, query, minDistance);
				double minDistanceRight = nearestRight.p.distanceSquaredTo(query.p);
				if (minDistanceRight < minDistance) {
					minDistance = minDistanceRight;
					nearest = nearestRight;
				}
			}
		} else if (compare < 0) {
			if (dToRight < minDistance) {
				RBNode nearestRight = nearest(parent.rt, query, minDistance);
				double minDistanceRight = nearestRight.p.distanceSquaredTo(query.p);
				if (minDistanceRight < minDistance) {
					minDistance = minDistanceRight;
					nearest = nearestRight;
				}
			}

			if (dToLeft < minDistance) {
				RBNode nearestLeft = nearest(parent.lb, query, minDistance);
				double minDistanceLeft = nearestLeft.p.distanceSquaredTo(query.p);
				if (minDistanceLeft < minDistance) {
					minDistance = minDistanceLeft;
					nearest = nearestLeft;
				}
			}
		}

		return nearest;
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new IllegalArgumentException();

		Queue<Point2D> result = new Queue<>();
		range(root, rect, result);
		return result;
	}

	private void range(RBNode parent, RectHV query, Queue<Point2D> result) {
		if (parent != null && query.intersects(parent.rect)) {
			if (query.contains(parent.p))
				result.enqueue(parent.p);

			range(parent.lb, query, result);
			range(parent.rt, query, result);
		}
	}
	
	private RBNode rotateLeft(RBNode h){
		assert(isRed(h.rt));
		RBNode x = h.rt;
		h.rt = x.lb;
		x.lb = h;
		x.color = h.color;
		h.color = RED;
		
		return x;
	}
	
	private RBNode rotateRight(RBNode h){
		assert(isRed(h.lb));
		RBNode x = h.lb;
		h.lb = x.rt;
		x.rt = h;
		x.color = h.color;
		h.color = RED;
		
		return x;
	}
	
	private void flipColors(RBNode h){
		assert h.color==BLACK;
		assert isRed(h.lb);
		assert isRed(h.rt);
		
		h.color = !h.color;
		h.lb.color = !h.lb.color;
		h.rt.color = !h.rt.color;
	}
	
	private boolean isRed(RBNode x){
		if(x == null) return false;
		return x.color == RED;
	}
	
	private static class RBNode implements Comparable<RBNode> {
		private Point2D p; // the point
		private RectHV rect; // the axis-aligned rectangle corresponding to this
								// node
		private RBNode lb; // the left/bottom subtree
		private RBNode rt; // the right/top subtree
		private boolean dividedBy;
		private boolean color;

		public RBNode(Point2D p) {
			this.p = p;
			this.color = RED;
		}

		@Override
		public int compareTo(RBNode that) {
			if (this.dividedBy == DX) {
				if (this.p.x() > that.p.x())
					return 1;
				if (this.p.x() < that.p.x())
					return -1;

				if (this.p.y() > that.p.y())
					return 1;
				if (this.p.y() < that.p.y())
					return -1;
			} else {
				if (this.p.y() > that.p.y())
					return 1;
				if (this.p.y() < that.p.y())
					return -1;

				if (this.p.x() > that.p.x())
					return 1;
				if (this.p.x() < that.p.x())
					return -1;
			}

			return 0;
		}
	}

	public static void main(String[] args) {
		String filename = args[0];
		In in = new In(filename);
		RedBlackKdTree kdt = new RedBlackKdTree();
		Stopwatch sw = new Stopwatch();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			try{
			kdt.insert(p);
			kdt.draw();
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(p);
			}
			kdt.size();
			kdt.isEmpty();
		}
		System.out.println("Insertion completed after " + sw.elapsedTime());

		System.out.println(kdt.contains(new Point2D(0.9, 0.6)));
		System.out.println(kdt.size());
		 kdt.draw();
		
		 Point2D query = new Point2D(0.11, 0.4);
		 StdDraw.setPenColor(StdDraw.BLACK);
		 StdDraw.setPenRadius(0.01);
		 query.draw();
		
		 StdDraw.setPenColor(StdDraw.BLUE);
		 StdDraw.setPenRadius(0.03);
		 Point2D nearest = kdt.nearest(query);
		 nearest.draw();
		 StdDraw.show();
	}
}
