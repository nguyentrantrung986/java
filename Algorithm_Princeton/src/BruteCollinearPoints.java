import edu.princeton.cs.algs4.Bag;

public class BruteCollinearPoints {
	private Point[] points;
	private LineSegment[] arraySegments;

	public BruteCollinearPoints(Point[] points) {
		this.points = points;
	}

	public int numberOfSegments() {
		if (arraySegments == null)
			segments();

		return arraySegments.length;
	}

	public LineSegment[] segments() {
		if (arraySegments == null) {
			Bag<LineSegment> lineSegments = new Bag<>();

			for (int i = 0; i < points.length; i++) {
				for (int j = i + 1; j < points.length; j++) {
					for (int k = j + 1; k < points.length; k++) {
						for (int l = k + 1; l < points.length; l++) {
							if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
									&& points[i].slopeTo(points[k]) == points[i].slopeTo(points[l])) {
								Point min = points[i];
								Point max = points[i];
								if (min.compareTo(points[j]) < 0)
									max = points[j];
								if (max.compareTo(points[j]) < 0)
									min = points[j];

								if (min.compareTo(points[k]) < 0)
									max = points[k];
								if (max.compareTo(points[k]) < 0)
									min = points[k];

								if (min.compareTo(points[l]) < 0)
									max = points[l];
								if (max.compareTo(points[l]) < 0)
									min = points[l];

								lineSegments.add(new LineSegment(min, max));
							}
						}
					}
				}
			}

			LineSegment[] arraySegments = new LineSegment[lineSegments.size()];
			int i = 0;
			for (LineSegment ls : lineSegments) {
				arraySegments[i++] = ls;
			}

			this.arraySegments = arraySegments;
		}
		
		return arraySegments;
	}
}
