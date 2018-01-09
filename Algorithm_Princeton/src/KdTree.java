import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class KdTree {
    private static final boolean dX = true;
    private static final boolean dY = false;
    
	private SET<Node> nodeSet;
	private Node root;
	
	public KdTree(){
		nodeSet = new SET<Node>();
	}
	
	public boolean isEmpty(){
		return nodeSet.isEmpty();
	}
	
	public int size(){
		return nodeSet.size();
	}
	
	public void insert(Point2D p){
		if(p==null) throw new IllegalArgumentException();
		
		root = insert(root, new Node(p), 0);
	}
	
	//depth is the number of edges from root to the node, root depth = 0
	private Node insert(Node root, Node newNode, int depth){
		if(root==null){//not found newNode
			if(depth%2==0) newNode.dividedBy = dX;
			else newNode.dividedBy = dY;
			
			return newNode; 
		}
		
		int compare = root.compareTo(newNode);
		if(compare < 0) 
			root.lb = insert(root.lb, newNode, depth+1);
		else if(compare > 0)
			root.rt = insert(root.rt, newNode, depth+1);
		
		//if the new node is found, do nothing
		return root; 
	}
	
	public boolean contains(Point2D p){
		return contains(root, new Node(p));
	}
	
	private boolean contains(Node root, Node x){
		if(root == null) return false;
		
		int  compare = root.compareTo(x);
		if(compare < 0) return contains(root.lb,x);
		else if (compare > 0) return contains(root.rt,x);
		else return true;
	}

	private static class Node implements Comparable<Node>{
		private Point2D p; // the point
		private RectHV rect; // the axis-aligned rectangle corresponding to this
								// node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree
		private boolean dividedBy;
		
		public Node(Point2D p){
			this.p = p;
		}

		@Override
		public int compareTo(Node that) {
			if(dividedBy == dX){
				if(this.p.x() > that.p.x()) return 1;
				else if(this.p.x() < that.p.x()) return -1;
			}else{
				if(this.p.y() > that.p.y()) return 1;
				else if(this.p.y() < that.p.y()) return -1;
			}
				
			return 0;
		}
	}
	
	public static void main(String[] args){
		KdTree kdt = new KdTree();
		kdt.insert(new Point2D(0.1, 0.3));
		kdt.insert(new Point2D(0.3, 0.7));
		kdt.insert(new Point2D(0.01, 0.7));
		
		System.out.println(kdt.contains(new Point2D(0.01, 0.7)));
	}
}
