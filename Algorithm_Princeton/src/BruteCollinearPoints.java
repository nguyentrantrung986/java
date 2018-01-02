import edu.princeton.cs.algs4.Queue;

public class BruteCollinearPoints {
	private final Point[] points;
	private LineSegment[] arraySegments;

	/**
	 * Throw a java.lang.IllegalArgumentException if the argument to the
	 * constructor is null, if any point in the array is null, or if the
	 * argument to the constructor contains a repeated point.
	 * 
	 * @param points:
	 *            arrays of point objects
	 */
	public BruteCollinearPoints(Point[] points) {
		if (points == null)
			throw new IllegalArgumentException();
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null)
				throw new IllegalArgumentException();
		}

		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++)
				if (points[i].compareTo(points[j]) == 0)
					throw new IllegalArgumentException();
		}
		
		//clone to prevent the client to modify the array inside this object
		this.points = points.clone();
	}

	public int numberOfSegments() {
		if (arraySegments == null)
			segments();

		return arraySegments.length;
	}
	
	/**The method segments() should include each line segment containing 4 points exactly once.  
	 * If 4 points appear on a line segment in the order p->q->r->s, then you should include either the 
	 * line segment p->s or s->p (but not both) and you should not include subsegments such as p->r or q->r
	 * @return an arrays of collinear segments found
	 */
	public LineSegment[] segments() {
		if (arraySegments == null) {
			Queue<PointPair> queuePointPairs = new Queue<>();

			for (int i = 0; i < points.length; i++) {
				for (int j = i + 1; j < points.length; j++) {
					for (int k = j + 1; k < points.length; k++) {
						if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) {
							Point min = points[i];
							Point max = points[i];
							boolean fourthPointFound = false;
							
							//the innermost for loop only runs if 3 collinear points are found
							for (int l = k + 1; l < points.length; l++) {
								if (isCollinear(points[i], points[j], points[k], points[l])) {
									fourthPointFound = true;
									
									if (min.compareTo(points[j]) > 0)
										min = points[j];
									if (max.compareTo(points[j]) < 0)
										max = points[j];

									if (min.compareTo(points[k]) > 0)
										min = points[k];
									if (max.compareTo(points[k]) < 0)
										max = points[k];

									if (min.compareTo(points[l]) > 0)
										min = points[l];
									if (max.compareTo(points[l]) < 0)
										max = points[l];
								}
							}
							
							if(fourthPointFound){	
								//check if this line segment has been added to the bag before
								boolean alreadyAdded = false;

								for(PointPair pp: queuePointPairs){
									if(isCollinear(min, max, pp.x, pp.y))
										alreadyAdded = true;
								}
								
								if(!alreadyAdded)
								queuePointPairs.enqueue(new PointPair(min, max));
							}
						}
					}
				}
			}

			LineSegment[] arraySegments = new LineSegment[queuePointPairs.size()];
			int i = 0;
			for (PointPair pp : queuePointPairs) {
				arraySegments[i++] = new LineSegment(pp.x, pp.y);
			}

			this.arraySegments = arraySegments;
		}

		return arraySegments.clone();
	}
	
	private class PointPair{
		Point x;
		Point y;
		
		public PointPair(Point x, Point y){
			this.x = x;
			this.y = y;
		}
	}
	
	private static boolean isCollinear(Point p1, Point p2, Point p3, Point p4){
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
