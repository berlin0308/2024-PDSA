import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.Point2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import edu.princeton.cs.algs4.GrahamScan;
import com.google.gson.*;


class ObservationStationAnalysis {

    private Point2D[] sites;
    private List<Point2D> hullPoints;
    private ArrayList<Point2D> stationSites;

    public ObservationStationAnalysis(ArrayList<Point2D> stations) {

        stationSites = stations;

        int N = stations.size();
        sites = new Point2D[N];
        for (int i = 0; i < stations.size(); i++) {
            Point2D point = stations.get(i);
            sites[i] = point;
        }
        
        // Find convex hull point
        GrahamScan Graham = new GrahamScan(sites);
        Iterable<Point2D> hull = Graham.hull();
        // System.out.println("Convex Hull:"+hull);

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
                if (dist >= maxDist) {
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

        // If new stations are added into previous updated hullpoints
        hullPoints.add(newStation);
        int N = hullPoints.size();
        sites = new Point2D[N];
        for (int i = 0; i < hullPoints.size(); i++) {
            Point2D point = hullPoints.get(i);
            sites[i] = point;
        }

        // If new stations are added into the original station sites
        // stationSites.add(newStation);
        // int N = stationSites.size();
        // sites = new Point2D[N];
        // for (int i = 0; i < stationSites.size(); i++) {
        //     Point2D point = stationSites.get(i);
        //     sites[i] = point;
        // }
        
        // Find convex hull point
        GrahamScan Graham = new GrahamScan(sites);
        Iterable<Point2D> hull = Graham.hull();
        // System.out.println("Convex Hull:"+hull);

        // Convert Iterable to ArrayList
        hullPoints = new ArrayList<>();
        for (Point2D point : hull) {
            hullPoints.add(point);
        }
    }
}

class OutputFormat{
    ArrayList<Point2D> stations;
    ObservationStationAnalysis OSA;
    Point2D[] farthest;
    double area;
    Point2D[] farthestNew;
    double areaNew;
    ArrayList<Point2D> newStations;
}

class TestCase {
    int Case;
    int score;
    ArrayList<OutputFormat> data;
}


class test_ObservationStationAnalysis{
    public static void main(String[] args)
    {
        Gson gson = new Gson();
        int num_ac = 0;
        int i = 1;

        try {
            // TestCase[] testCases = gson.fromJson(new FileReader(args[0]), TestCase[].class);
            TestCase[] testCases = gson.fromJson(new FileReader("src/ObservationStationAnalysis_testcase.json"), TestCase[].class);
            for (TestCase testCase : testCases) {
                System.out.println("Sample"+i+": ");
                i++;
                for (OutputFormat data : testCase.data) {
                    ObservationStationAnalysis OSA = new ObservationStationAnalysis(data.stations);
                    Point2D[] farthest;
                    double area;
                    Point2D[] farthestNew;
                    double areaNew;

                    farthest = OSA.findFarthestStations();
                    area = OSA.coverageArea();


                    if(data.newStations!=null){
                        for(Point2D newStation: data.newStations){
                            OSA.addNewStation(newStation);
                        }
                        farthestNew = OSA.findFarthestStations();
                        areaNew = OSA.coverageArea();
                    }else{
                        farthestNew = farthest;
                        areaNew = area;
                    }

                    
                    if(farthest[0].equals(data.farthest[0]) && farthest[1].equals(data.farthest[1]) &&  Math.abs(area-data.area) < 0.0001 
                    && farthestNew[0].equals(data.farthestNew[0]) && farthestNew[1].equals(data.farthestNew[1]) && Math.abs(areaNew-data.areaNew) < 0.0001)
                    {
                        System.out.println("AC");
                        num_ac++;
                    }
                    else
                    {
                        System.out.println("WA");
                        System.out.println("Ans-farthest: " + Arrays.toString(data.farthest));
                        System.out.println("Your-farthest:  " + Arrays.toString(farthest));
                        System.out.println("Ans-area:  " + data.area);
                        System.out.println("Your-area:  " + area);

                        System.out.println("Ans-farthestNew: " + Arrays.toString(data.farthestNew));
                        System.out.println("Your-farthestNew:  " + Arrays.toString(farthestNew));
                        System.out.println("Ans-areaNew:  " + data.areaNew);
                        System.out.println("Your-areaNew:  " + areaNew);
                        System.out.println("");
                    }
                }
                System.out.println("Score: "+num_ac+"/ 8");
                }
            
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }
}