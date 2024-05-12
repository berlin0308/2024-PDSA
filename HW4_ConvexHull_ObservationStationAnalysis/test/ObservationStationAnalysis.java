import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import edu.princeton.cs.algs4.Point2D;

class ObservationStationAnalysis {

    public ObservationStationAnalysis(ArrayList<Point2D> stations) {
        
    }

    public Point2D[] findFarthestStations() {
        Point2D[] farthest = new Point2D[]{new Point2D(0,0), new Point2D(1,1)};
        // find the farthest two stations
        return farthest; // should be sorted by polar radius
    }

    public double coverageArea() {
        double area = 0.0;
        // calculate the area by any method
        return area;
    }

    public void addNewStation(Point2D newStation) {
        
    }
    
    public static void main(String[] args) throws Exception {

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
        System.out.println("Coverage Area: "+Analysis.coverageArea());
        
        System.out.println("Add Station (10, 3): ");
        Analysis.addNewStation(new Point2D(10, 3));
        
        System.out.println("Farest Station A: "+Analysis.findFarthestStations()[0]);
        System.out.println("Farest Station B: "+Analysis.findFarthestStations()[1]);
        System.out.println("Coverage Area: "+Analysis.coverageArea());
    }
}