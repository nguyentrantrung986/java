package exercises;

import edu.princeton.cs.algs4.StdRandom;

public class PointSetIntersect {
	public static void main(String[] args){
		Point[] a = new Point[10];
		for(int i=0; i<a.length;i++){
			Point p = new Point(StdRandom.uniform(10),StdRandom.uniform(10));
			a[i]= p;
		}
	}
	
	private static class Point implements Comparable<Point>{
		private final int x, y;
		
		public Point(int x, int y){
			this.x = x;
			this.y = y;
		}

		public int compareTo(Point that) {
			if(this.x > that.x) return +1;
			if(this.x < that.x) return -1;
			if(this.y > that.y)	return +1;
			if(this.y < that.y)	return -1;
			
			return 0;
		}
	}
}
