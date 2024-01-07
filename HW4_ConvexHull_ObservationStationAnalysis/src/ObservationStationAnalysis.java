import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import edu.princeton.cs.algs4.GrahamScan;
import edu.princeton.cs.algs4.Point2D;

public class ObservationStationAnalysis {

    private Point2D[] sites;
    private List<Point2D> hullPoints;

    public ObservationStationAnalysis(ArrayList<Point2D> stations) {

        int N = stations.size();
        sites = new Point2D[N];
        for (int i = 0; i < stations.size(); i++) {
            Point2D point = stations.get(i);
            sites[i] = point;
        }
        
        // Find convex hull point
        GrahamScan Graham = new GrahamScan(sites);
        Iterable<Point2D> hull = Graham.hull();
        System.out.println("Convex Hull:"+hull);

        // Convert Iterable to ArrayList
        hullPoints = new ArrayList<>();
        for (Point2D point : hull) {
            hullPoints.add(point);
        }
    }

    public Point2D[] findFarthestStations() {
        int n = hullPoints.size();
        if (n < 2) return null;

        Point2D p1 = null, p2 = null;
        double maxDist = 0.0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dist = hullPoints.get(i).distanceTo(hullPoints.get(j));
                if (dist > maxDist) {
                    maxDist = dist;
                    p1 = hullPoints.get(i);
                    p2 = hullPoints.get(j);
                }
            }
        }

        Point2D[] farthest = new Point2D[]{p1, p2};
        Arrays.sort(farthest, Point2D.R_ORDER);

        return farthest;
    }

    public double coverageArea() {
        
        double area = 0.0;
        Point2D origin = new Point2D(hullPoints.get(0).x(), hullPoints.get(0).y());

        for (int i = 1; i < hullPoints.size(); i++) {
            Point2D p1 = hullPoints.get(i);
            Point2D p2 = hullPoints.get((i + 1) % (hullPoints.size()));
            area += triangleArea(origin, p1, p2);
        }

        return area;
    }

    private double triangleArea(Point2D a, Point2D b, Point2D c) {
        double area = ((b.x() - a.x()) * (c.y() - a.y()) - (b.y() - a.y()) * (c.x() - a.x())) / 2.0;
        return Math.abs(area);
    }

    public void addNewStation(Point2D newStation) {
        hullPoints.add(newStation);
        int N = hullPoints.size();
        sites = new Point2D[N];
        for (int i = 0; i < hullPoints.size(); i++) {
            Point2D point = hullPoints.get(i);
            sites[i] = point;
        }
        
        // Find convex hull point
        GrahamScan Graham = new GrahamScan(sites);
        Iterable<Point2D> hull = Graham.hull();
        System.out.println("Convex Hull:"+hull);

        // Convert Iterable to ArrayList
        hullPoints = new ArrayList<>();
        for (Point2D point : hull) {
            hullPoints.add(point);
        }
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        ArrayList<Point2D> stationCoordinates = new ArrayList<>();
        stationCoordinates.add(new Point2D(0, 0));
        stationCoordinates.add(new Point2D(2, 0));
        stationCoordinates.add(new Point2D(3, 2));
        stationCoordinates.add(new Point2D(2, 6));
        stationCoordinates.add(new Point2D(0, 4));
        stationCoordinates.add(new Point2D(1, 1));
        stationCoordinates.add(new Point2D(2, 2));

        ObservationStationAnalysis Analysis = new ObservationStationAnalysis(stationCoordinates);
        System.out.println("Farest Station A: "+Analysis.findFarthestStations()[0]);
        System.out.println("Farest Station B: "+Analysis.findFarthestStations()[1]);
        System.out.println("Converage Area: "+Analysis.coverageArea());
        
        System.out.println("Add Station (-1, -1): ");
        Analysis.addNewStation(new Point2D(-1, -1));
        
        System.out.println("Farest Station A: "+Analysis.findFarthestStations()[0]);
        System.out.println("Farest Station B: "+Analysis.findFarthestStations()[1]);
        System.out.println("Converage Area: "+Analysis.coverageArea());


    }
}
