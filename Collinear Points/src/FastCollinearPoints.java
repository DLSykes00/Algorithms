import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
	
	private LineSegment[] ls;
	
	public FastCollinearPoints(Point[] points) { // Finds all line segments containing 4 or more points
		if (points == null) throw new IllegalArgumentException("Must supply points to constructor.");
		   
		Arrays.sort(points);
	   
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) throw new IllegalArgumentException("Null point in array.");
			if (i > 0) {
				if (points[i].compareTo(points[i - 1]) == 0) throw new IllegalArgumentException("Duplicate points in array.");
			}
		}
		
		ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();
		Point[] slopePoints = points.clone(); // New array to sort points by slope while retaining original order in points array.
		
		// Loop through each point
		for (Point point : points) { 	
			Arrays.sort(slopePoints, point.slopeOrder()); // Sort points w.r.t. to slope to point i;
			
			// Check if 3 or more adjacent points have same slope.
			for (int j = 1; j < slopePoints.length - 2; j++) { 
				int nextPoint = j + 1; // Pointer for next element in slopePoints array.
				
				while (point.slopeTo(slopePoints[j]) == point.slopeTo(slopePoints[nextPoint])) { 
					nextPoint++;	
					if (nextPoint == slopePoints.length) break; // Exit loop if at end of slopePoints.
				}
				nextPoint--; // Step pointer back 1 to get last element with same slope.
				
				if (nextPoint >= j + 2) { // Check at least 3 points with same slope for segment.
					Point[] slopePointsSegment = new Point[nextPoint - j + 2];
					slopePointsSegment[0] = point;		
					for (int p = 0; p < nextPoint - j + 1; p++) {
						slopePointsSegment[p+1] = slopePoints[p + j];
					}
					
					Arrays.sort(slopePointsSegment);
					if (point.compareTo(slopePointsSegment[0]) == 0) { // Check point i is the lowest point in natural order. 
						segmentList.add(new LineSegment(point, slopePointsSegment[slopePointsSegment.length - 1]));
					}
				}
				
				j = nextPoint;
			}	
		}
		
		ls = segmentList.toArray(new LineSegment[segmentList.size()]);
	}
	
	public int numberOfSegments() { // The number of line segments
		return ls.length;
	}
	
	public LineSegment[] segments() { // The line segments
		return ls;
	}
	
	public static void main(String[] args) {
		
	}
}
