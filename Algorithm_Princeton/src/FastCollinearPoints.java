import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.Queue;

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
			Queue<LineSegment> queueSegments = new Queue<>();

			for (int i = 0; i < points.length; i++) {
				Point[] aux = new Point[points.length-i-1];
				for (int j = 0; j < aux.length; j++)
					aux[j] = points[i+1];
				Comparator<Point> c = points[i].slopeOrder();
				// sort other points according to the slope they make with point
				// index i
				Arrays.sort(aux, c);

				// points[i] will be at index 0 of the auxiliary array after
				// sorting
				// because the slope to itself is defined as negative infinity
				for (int j = 1; j < aux.length-1; j++) {
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
						
						for (int k = j + 2; k < aux.length; k++) {
							double slope3 = points[i].slopeTo(aux[k]);
							if (slope2 == slope3) { // found 4 collinear points
								fourthFound=true;
								
								if (min.compareTo(aux[k]) > 0)
									min = aux[j];
								if (max.compareTo(aux[k]) < 0)
									max = aux[j];
							}
						}
						
						if(fourthFound)
							queueSegments.enqueue(new LineSegment(min, max));
					}
				}
			}
			
			arraySegments=new LineSegment[queueSegments.size()];
			for(int i=0; i<arraySegments.length;i++)
				arraySegments[i] = queueSegments.dequeue();
		}

		return arraySegments;
	}
}
