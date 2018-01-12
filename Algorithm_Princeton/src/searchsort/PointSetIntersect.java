package searchsort;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PointSetIntersect {
	public static void main(String[] args){
		int n = StdRandom.uniform(15);
		Point[] a = new Point[n];
		Point[] b = new Point[n];
		for(int i=0; i<a.length;i++){
			Point p = new Point(StdRandom.uniform(n),StdRandom.uniform(n));
			a[i]= p;
			p = new Point(StdRandom.uniform(n),StdRandom.uniform(n));
			b[i]= p;
		}
		
		StdOut.println("2 arrays sorted:");
		ShellSort.sort(a);
		for(Point p:a) StdOut.print(p);
		StdOut.println();
		ShellSort.sort(b);
		for(Point p:b) StdOut.print(p);		
		StdOut.println();
		
		StdOut.println("Common Points:");
		for(Point p:a){
			for(Point q:b){
				if(p.compareTo(q)==0){
					StdOut.print(p);	
				}else if(p.compareTo(q)<0)
					break; //all points after this point is greater than p, since arrays are sorted
			}
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
		
		public String toString(){
			return "["+x+","+y+"] ";
		}
	}
}
