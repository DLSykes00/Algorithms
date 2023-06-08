import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
	
	private LineSegment[] ls;
	
	public BruteCollinearPoints(Point[] points) { // Finds all line segments containing 4 points
		if (points == null) throw new IllegalArgumentException("Must supply points to constructor.");
	   
		Arrays.sort(points);
	   
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) throw new IllegalArgumentException("Null point in array.");
			if (i > 0) {
				if (points[i].compareTo(points[i - 1]) == 0) throw new IllegalArgumentException("Duplicate points in array.");
			}
		}
        
		ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();
		Point[] slopePoints = new Point[4];
		
		// Brute force algorithm for 4 collinear points.
		for (int i = 0; i < points.length - 3; i++) {
			slopePoints[0] = points[i];
			for (int j = i + 1; j < points.length - 2; j++) {
				slopePoints[1] = points[j];
				for (int a = j + 1; a < points.length - 1; a++) {
					slopePoints[2] = points[a];
					if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[a])) continue;	
					for (int b = a + 1; b < points.length; b++) {
						slopePoints[3] = points[b];
						
						if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[a]) && 
							points[j].slopeTo(points[a]) == points[j].slopeTo(points[b])) { // Check the 4 points are collinear.
																   
							segmentList.add(new LineSegment(slopePoints[0], slopePoints[slopePoints.length - 1]));
						}
					}
				}
			}
		}
		
		ls = segmentList.toArray(new LineSegment[segmentList.size()]);
	}
   
	public int numberOfSegments() { // the number of line segments
		return ls.length;
	}
   
	public LineSegment[] segments() { // the line segments
		return ls;
	}
	
	public static void main(String[] args) {
		
	}
}
