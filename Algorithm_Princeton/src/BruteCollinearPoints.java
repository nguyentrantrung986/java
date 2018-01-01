import edu.princeton.cs.algs4.Queue;

public class BruteCollinearPoints {
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
	public BruteCollinearPoints(Point[] points) {
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
	
	/**The method segments() should include each line segment containing 4 points exactly once.  
	 * If 4 points appear on a line segment in the order p->q->r->s, then you should include either the 
	 * line segment p->s or s->p (but not both) and you should not include subsegments such as p->r or q->r
	 * @return an arrays of collinear segments found
	 */
	public LineSegment[] segments() {
		if (arraySegments == null) {
			Queue<LineSegment> queueSegments = new Queue<>();

			for (int i = 0; i < points.length; i++) {
				for (int j = i + 1; j < points.length; j++) {
					for (int k = j + 1; k < points.length; k++) {
						if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) {
							Point min = points[i];
							Point max = points[i];
							boolean fourthPointFound = false;
							
							//the inner most for only runs if 3 collinear points are found
							//only consider points not included in previously found segments
							for (int l = k + 1; l < points.length; l++) {
								if (points[i].slopeTo(points[k]) == points[i].slopeTo(points[l])) {
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
								queueSegments.enqueue(new LineSegment(min, max));
							}
						}
					}
				}
			}

			LineSegment[] arraySegments = new LineSegment[queueSegments.size()];
			int i = 0;
			for (LineSegment ls : queueSegments) {
				arraySegments[i++] = ls;
			}

			this.arraySegments = arraySegments;
		}

		return arraySegments;
	}
}
