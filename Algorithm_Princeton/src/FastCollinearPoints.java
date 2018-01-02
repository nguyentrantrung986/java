import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.Bag;

public class FastCollinearPoints {
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
	public FastCollinearPoints(Point[] points) {
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
				// mark if the point has been included in a collinear segment
				// with points[i]
				boolean[] isCollinear = new boolean[points.length - i - 1];
				// auxiliary array contains all points other than points[i]
				Point[] aux = new Point[points.length - i - 1];
				for (int j = 0; j < aux.length; j++)
					aux[j] = points[j + i + 1];
				Comparator<Point> c = points[i].slopeOrder();
				// sort other points according to the slope they make with point
				// index i
				Arrays.sort(aux, c);

				for (int j = 0; j < aux.length - 1; j++) {
					double slope1 = points[i].slopeTo(aux[j]);
					double slope2 = points[i].slopeTo(aux[j + 1]);

					if (slope1 == slope2) {
						// found 3 collinear points, min point point index i in
						// original array
						Point min = points[i];
						Point max = null;

						for (int k = j + 2; k < aux.length; k++) {
							// found the next collinear point
							if (isCollinear[k] == false) {
								Double slope3 = points[i].slopeTo(aux[k]);

								if (slope1 == slope3) {
									// due to natural order, max index holds the
									// largest point of the collinear segment
									max = aux[k];
									isCollinear[k] = true;
								}else
									break;
								
							} 
						}

						// if max == null then the fourth point is not found
						if (max != null) {
							isCollinear[j] = true;
							isCollinear[j + 1] = true;
							// check if this line segment has been added to the
							// bag before
							boolean alreadyAdded = false;

							for (PointPair pp : bagPointPairs) {
								/* Due to natural order of points, all subsequent subsegments
								 * will have the same maximum point with the correct segment, and
								 * the correct segment is always found first.
								 */ 
								if (max == pp.max)
									if (slope1==pp.slope) {
										alreadyAdded = true;
										break;
									}
							}
							if (!alreadyAdded)
								bagPointPairs.add(new PointPair(min, max, slope1));
						}
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
		double slope; //avoid recalculating slope

		public PointPair(Point x, Point y, double slope) {
			this.min = x;
			this.max = y;
			this.slope = slope;
		}
	}

	/**
	 * Test if 4 points are collinear. Take into consideration the equal points.
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @return true if 4 points are collinear, false otherwise
	 */
//	private static boolean areCollinear(Point p1, Point p2, Point p3, Point p4) {
//		double slope1 = p1.slopeTo(p2);
//		double slope2 = p1.slopeTo(p3);
//		double slope3 = p1.slopeTo(p4);
//
//		// if there are 3 equal points, then they must be linear
//		if (slope1 == Double.NEGATIVE_INFINITY && slope2 == Double.NEGATIVE_INFINITY)
//			return true;
//		if (slope1 == Double.NEGATIVE_INFINITY && slope3 == Double.NEGATIVE_INFINITY)
//			return true;
//		if (slope2 == Double.NEGATIVE_INFINITY && slope3 == Double.NEGATIVE_INFINITY)
//			return true;
//		// if there are 2 equal points, one slope will be negative infinity
//		if (slope1 == Double.NEGATIVE_INFINITY && slope2 == slope3)
//			return true;
//		if (slope2 == Double.NEGATIVE_INFINITY && slope1 == slope3)
//			return true;
//		if (slope3 == Double.NEGATIVE_INFINITY && slope1 == slope2)
//			return true;
//		// if the three slopes are equal, the 4 points are collinear.
//		if (slope1 == slope2 && slope1 == slope3)
//			return true;
//
//		return false;
//	}
}