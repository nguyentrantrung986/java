import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stopwatch;

public class KdTree {
	private static final boolean DX = true;
	private static final boolean DY = false;
	private int sz;

	private Node root;

	public KdTree() {
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
			root = new Node(p);
			root.rect = new RectHV(0, 0, 1, 1);
			root.dividedBy = DX;
			sz++;
		} else
			insert(root, new Node(p), 0);
	}

	// depth is the number of edges from root to the node, root depth = 0
	private Node insert(Node parent, Node newNode, int depth) {
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

		// if compare =0, the new node is found, do nothing
		return parent;
	}

	public void draw() {
		draw(root);
	}

	private void draw(Node x) {
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

		return contains(root, new Node(p));
	}

	private boolean contains(Node root, Node x) {
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

		Node nearest = nearest(root, new Node(p), root.p.distanceSquaredTo(p));
		return nearest.p;
	}

	private Node nearest(Node parent, Node query, double minDistance) {
		if (parent == null)
			return null;
		Node nearest = parent;
		double d = parent.p.distanceSquaredTo(query.p);
		if (d < minDistance)
			minDistance = d;
		double dToLeft = (parent.lb == null) ? Double.MAX_VALUE : parent.lb.rect.distanceSquaredTo(query.p);
		double dToRight = (parent.rt == null) ? Double.MAX_VALUE : parent.rt.rect.distanceSquaredTo(query.p);

		int compare = parent.compareTo(query);
		if (compare > 0) {
			if (dToLeft < minDistance) {
				Node nearestLeft = nearest(parent.lb, query, minDistance);
				double minDistanceLeft = nearestLeft.p.distanceSquaredTo(query.p);
				if (minDistanceLeft < minDistance) {
					minDistance = minDistanceLeft;
					nearest = nearestLeft;
				}
			}
			if (dToRight < minDistance) {
				Node nearestRight = nearest(parent.rt, query, minDistance);
				double minDistanceRight = nearestRight.p.distanceSquaredTo(query.p);
				if (minDistanceRight < minDistance) {
					minDistance = minDistanceRight;
					nearest = nearestRight;
				}
			}
		} else if (compare < 0) {
			if (dToRight < minDistance) {
				Node nearestRight = nearest(parent.rt, query, minDistance);
				double minDistanceRight = nearestRight.p.distanceSquaredTo(query.p);
				if (minDistanceRight < minDistance) {
					minDistance = minDistanceRight;
					nearest = nearestRight;
				}
			}

			if (dToLeft < minDistance) {
				Node nearestLeft = nearest(parent.lb, query, minDistance);
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

	private void range(Node parent, RectHV query, Queue<Point2D> result) {
		if (parent != null && query.intersects(parent.rect)) {
			if (query.contains(parent.p))
				result.enqueue(parent.p);

			range(parent.lb, query, result);
			range(parent.rt, query, result);
		}
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
		KdTree kdt = new KdTree();
		Stopwatch sw = new Stopwatch();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			kdt.insert(p);
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
