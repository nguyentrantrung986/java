import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	private SET<Point2D > pointSet;
	
	public PointSET(){
		pointSet = new SET<>();
	}
	
	public boolean isEmpty(){
		return pointSet.isEmpty();
	}
	
	public int size(){
		return pointSet.size();
	}
	
	public void insert(Point2D p){
		if(p==null) throw new IllegalArgumentException();
		
		pointSet.add(p);
	}
	
	public boolean contains(Point2D p){
		if(p==null) throw new IllegalArgumentException();
		
		return pointSet.contains(p);
	}
	
	public void draw() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		for(Point2D p: pointSet){
			p.draw();
		}
	}
	
	public Iterable<Point2D> range(RectHV rect){
		if(rect==null) throw new IllegalArgumentException();
		Queue<Point2D> q = new Queue<>();
		for(Point2D p: pointSet){
			if(rect.contains(p))
				q.enqueue(p);
		}
		
		return q;
	}
	
	public Point2D nearest(Point2D p){
		if(p==null) throw new IllegalArgumentException();
		if(isEmpty()) return null;
		
		Point2D nearest = null;
		double distance= Double.MAX_VALUE;
		
		for(Point2D p1 : pointSet){
			if(distance > p.distanceSquaredTo(p1)){
				distance = p.distanceSquaredTo(p1);
				nearest = p1;
			}
		}
		
		return nearest;
	}
}
