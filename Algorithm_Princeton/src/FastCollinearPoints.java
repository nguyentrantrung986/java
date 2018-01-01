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
			Bag<LineSegment> bagSegments = new Bag<>();

			for (int i = 0; i < points.length; i++) {
				//mark if the point has been included in a collinear segment or not
				boolean[] isCollinear = new boolean[points.length-i-1]; 
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
							double slope3 = points[i].slopeTo(aux[k]);
							// found the 4th collinear point which has not been included into any 
							// collinear segments before
							if (slope2 == slope3 && isCollinear[k]==false) { 
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
							LineSegment ls = new LineSegment(min, max);
							for(LineSegment l: bagSegments){
								if(l.toString().equals(ls.toString()))
									alreadyAdded = true;
							}
							
							if(!alreadyAdded)
								bagSegments.add(ls);
						}
					}
				}
			}
			
			arraySegments=new LineSegment[bagSegments.size()];
			
			int i=0;
			for(LineSegment l: bagSegments){
				arraySegments[i] = l;
				i++;
			}
		}

		return arraySegments;
	}
}
