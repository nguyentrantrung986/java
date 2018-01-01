import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.Bag;

public class FastCollinearPoints {
	private Point[] points;
	private LineSegment[] arraySegments;

	/**
	 * Throw a java.lang.IllegalArgumentException if the argument to the
	 * constructor is null, if any point in the array is null, or if the
	 * argument to the constructor contains a repeated point.
	 * 
	 * @param points:
	 *            arrays of point objects
	 */
	public FastCollinearPoints(Point[] points) {
		if (points == null)
			throw new IllegalArgumentException();
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null)
				throw new IllegalArgumentException();

			for (int j = i + 1; j < points.length; j++)
				if (points[i].compareTo(points[j]) == 0)
					throw new IllegalArgumentException();
		}

		this.points = points;
	}

	public int numberOfSegments() {
		if (arraySegments == null)
			segments();

		return arraySegments.length;
	}

	public LineSegment[] segments() {
		if (arraySegments == null) {
			Bag<PointPair> bagPointPairs = new Bag<>();

			for (int i = 0; i < points.length; i++) {
				//mark if the point has been included in a collinear segment or not
				boolean[] isCollinear = new boolean[points.length-i-1]; 
				//auxiliary array contains all points other than points[i]
				Point[] aux = new Point[points.length-i-1];
				for (int j = 0; j < aux.length; j++)
					aux[j] = points[j+i+1];
				Comparator<Point> c = points[i].slopeOrder();
				// sort other points according to the slope they make with point
				// index i
				Arrays.sort(aux, c);

				for (int j = 0; j < aux.length-1;j++) {
					double slope1 = points[i].slopeTo(aux[j]);
					double slope2 = points[i].slopeTo(aux[j + 1]);

					if (slope1 == slope2) {	//found 3 collinear points
						Point min = points[i];
						Point max = points[i];
						boolean fourthFound = false;
						if (min.compareTo(aux[j]) > 0)
							min = aux[j];
						if (max.compareTo(aux[j]) < 0)
							max = aux[j];
						
						if (min.compareTo(aux[j+1]) > 0)
							min = aux[j+1];
						if (max.compareTo(aux[j+1]) < 0)
							max = aux[j+1];
						
						for (int k = j + 2; k < aux.length; k++) {
							// found the 4th collinear point which has not been included into any 
							// collinear segments before
							if (isCollinear[k]==false && isCollinear(points[i], aux[j], aux[j+1], aux[k])) { 
								fourthFound=true;
								isCollinear[k]=true;
								
								if (min.compareTo(aux[k]) > 0)
									min = aux[k];
								if (max.compareTo(aux[k]) < 0)
									max = aux[k];
							}
						}
						
						if(fourthFound){
							isCollinear[j]=true;
							isCollinear[j+1]=true;
							
							//check if this line segment has been added to the bag before
							boolean alreadyAdded = false;

							for(PointPair pp: bagPointPairs){
								if(isCollinear(min, max, pp.x, pp.y))
									alreadyAdded = true;
							}
							
							if(!alreadyAdded)
								bagPointPairs.add(new PointPair(min, max));
						}
					}
				}
			}
			
			arraySegments=new LineSegment[bagPointPairs.size()];
			
			int i=0;
			for(PointPair pp: bagPointPairs){
				arraySegments[i] = new LineSegment(pp.x, pp.y);
				i++;
			}
		}

		return arraySegments;
	}
	
	private class PointPair{
		Point x;
		Point y;
		
		public PointPair(Point x, Point y){
			this.x = x;
			this.y = y;
		}
	}
	
	/**Test if 4 points are collinear.
	 * Take into consideration the equal points.
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @return true if 4 points are collinear, false otherwise
	 */
	private boolean isCollinear(Point p1, Point p2, Point p3, Point p4){
		double slope1 = p1.slopeTo(p2);
		double slope2 = p1.slopeTo(p3);
		double slope3 = p1.slopeTo(p4);
		
		//if there are 3 equal points, then they must be linear
		if(slope1==Double.NEGATIVE_INFINITY && slope2==Double.NEGATIVE_INFINITY) return true;
		if(slope1==Double.NEGATIVE_INFINITY && slope3==Double.NEGATIVE_INFINITY) return true;
		if(slope2==Double.NEGATIVE_INFINITY && slope3==Double.NEGATIVE_INFINITY) return true;
		//if there are 2 equal points, one slope will be negative infinity
		if(slope1==Double.NEGATIVE_INFINITY && slope2==slope3) return true;
		if(slope2==Double.NEGATIVE_INFINITY && slope1==slope3) return true;
		if(slope3==Double.NEGATIVE_INFINITY && slope1==slope2) return true;
		//if the three slopes are equal, the 4 points are collinear.
		if(slope1==slope2 && slope1==slope3) return true;
		
		return false;
	}
}
