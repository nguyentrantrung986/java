import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.Bag;

public class FastCollinearPoints2 {
	private final Point[] points;
	private LineSegment[] arraySegments;

	/**
	 * Throw a java.lang.IllegalArgumentException if the argument to the
	 * constructor is null, if any point in the array is null, or if the
	 * argument to the constructor contains a repeated point.
	 * 
	 * This algorithm sort the input array once based on points' natural order, then choose each point
	 * as an origin. The other points are sort based on the slope to the origin in a clone array of the
	 * same size as the input. When finding 3 collinear points, it run forwards until slope value is changed.
	 * At this points, the segment is only added if the original point is the smallest point in the segment.
	 * This check eliminates all subsegments. So this algorithm is fast if there are many subsegments such as
	 * in a grids. But for random points, consider removing the points which has served as origins from the
	 * subsequent arrays to sort by slope order.
	 * @param points:
	 *            arrays of point objects
	 */
	public FastCollinearPoints2(Point[] points) {
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

		this.points = points.clone();
		// sort the array according to natural order of points once
		// if sorting by slope is stable, then the lowest and highest points of
		// segment can be found
		// at lowest and highest indices of all collinear points being
		// considered
		Arrays.sort(this.points);
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
				// auxiliary array contains all points other than points[i]
				Point[] aux = points.clone();
				Comparator<Point> c = points[i].slopeOrder();
				// sort other points according to the slope they make with point
				// index i
				Arrays.sort(aux, c);

				for (int j = 1; j < aux.length - 1; j++) {
					double slope1 = points[i].slopeTo(aux[j]);
					double slope2 = points[i].slopeTo(aux[j + 1]);

					// found 3 collinear points
					if (slope1 == slope2) {
						int maxIndex = j + 1;
						Point min = points[i];
						Point max = null;

						for (int k = j + 2; k < aux.length; k++) {
							// found the next collinear point
							if (slope1 == points[i].slopeTo(aux[k])) {
								// due to natural order, max index holds the
								// largest point of the collinear segment
								max = aux[k];
								maxIndex = k;
							} else {
								break;
							}
						}
						
						// if max == null then the fourth point is not found
						// if points[i] is not the minimum point in the segment, then it's a subsegment
						if (max != null && points[i].compareTo(aux[j]) < 0) {
							bagPointPairs.add(new PointPair(min, max));
						}

						// skip to the index of the max point found
						j = maxIndex - 1; 
					}
				}
			}

			arraySegments = new LineSegment[bagPointPairs.size()];

			int i = 0;
			for (PointPair pp : bagPointPairs) {
				arraySegments[i] = new LineSegment(pp.min, pp.max);
				i++;
			}
		}

		// immutable: prevent the client from modifying the original array of
		// Segment
		return arraySegments.clone();
	}

	private class PointPair {
		Point min;
		Point max;

		public PointPair(Point x, Point y) {
			this.min = x;
			this.max = y;
		}
	}
}
