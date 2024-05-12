// import com.google.gson.*;
// import java.util.Random;
// import java.nio.file.Files;
// import java.nio.charset.StandardCharsets;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import edu.princeton.cs.algs4.Point2D;
// import edu.princeton.cs.algs4.GrahamScan;


// class ObservationStationAnalysis {

//     private Point2D[] sites;
//     private List<Point2D> hullPoints;
//     private ArrayList<Point2D> stationSites;

//     public ObservationStationAnalysis(ArrayList<Point2D> stations) {

//         stationSites = stations;

//         int N = stations.size();
//         sites = new Point2D[N];
//         for (int i = 0; i < stations.size(); i++) {
//             Point2D point = stations.get(i);
//             sites[i] = point;
//         }
        
//         // Find convex hull point
//         GrahamScan Graham = new GrahamScan(sites);
//         Iterable<Point2D> hull = Graham.hull();
//         // System.out.println("Convex Hull:"+hull);

//         // Convert Iterable to ArrayList
//         hullPoints = new ArrayList<>();
//         for (Point2D point : hull) {
//             hullPoints.add(point);
//         }
//     }

//     public boolean sameFarthestPairs(){

//         int n = hullPoints.size();

//         Point2D p1 = null, p2 = null;
//         double maxDist = 0.0;

//         for (int i = 0; i < n; i++) {
//             for (int j = i + 1; j < n; j++) {
//                 double dist = hullPoints.get(i).distanceTo(hullPoints.get(j));
//                 if (dist >= maxDist) {
//                     maxDist = dist;
//                     p1 = hullPoints.get(i);
//                     p2 = hullPoints.get(j);
//                 }
//             }
//         }

//         int count = 0;
//         for (int i = 0; i < n; i++) {
//             for (int j = i + 1; j < n; j++) {
//                 double dist = hullPoints.get(i).distanceTo(hullPoints.get(j));
//                 if (dist == maxDist) {
//                     count ++;
//                 }
//             }
//         }

//         if(count==1) return false;
//         else return true;
//     }


//     public Point2D[] findFarthestStations() {
//         int n = hullPoints.size();
//         if (n < 2) return null;

//         Point2D p1 = null, p2 = null;
//         double maxDist = 0.0;

//         for (int i = 0; i < n; i++) {
//             for (int j = i + 1; j < n; j++) {
//                 double dist = hullPoints.get(i).distanceTo(hullPoints.get(j));
//                 if (dist >= maxDist) {
//                     maxDist = dist;
//                     p1 = hullPoints.get(i);
//                     p2 = hullPoints.get(j);
//                 }
//             }
//         }

//         Point2D[] farthest = new Point2D[]{p1, p2};
//         Arrays.sort(farthest, Point2D.R_ORDER);

//         return farthest;
//     }

//     public double coverageArea() {
        
//         double area = 0.0;
//         Point2D origin = new Point2D(hullPoints.get(0).x(), hullPoints.get(0).y());

//         for (int i = 1; i < hullPoints.size(); i++) {
//             Point2D p1 = hullPoints.get(i);
//             Point2D p2 = hullPoints.get((i + 1) % (hullPoints.size()));
//             area += triangleArea(origin, p1, p2);
//         }

//         return area;
//     }

//     private double triangleArea(Point2D a, Point2D b, Point2D c) {
//         double area = ((b.x() - a.x()) * (c.y() - a.y()) - (b.y() - a.y()) * (c.x() - a.x())) / 2.0;
//         return Math.abs(area);
//     }

//     public void addNewStation(Point2D newStation) {

//         // If new stations are added into previous updated hullpoints
//         hullPoints.add(newStation);
//         int N = hullPoints.size();
//         sites = new Point2D[N];
//         for (int i = 0; i < hullPoints.size(); i++) {
//             Point2D point = hullPoints.get(i);
//             sites[i] = point;
//         }

//         // If new stations are added into the original station sites
//         // stationSites.add(newStation);
//         // int N = stationSites.size();
//         // sites = new Point2D[N];
//         // for (int i = 0; i < stationSites.size(); i++) {
//         //     Point2D point = stationSites.get(i);
//         //     sites[i] = point;
//         // }
        
//         // Find convex hull point
//         GrahamScan Graham = new GrahamScan(sites);
//         Iterable<Point2D> hull = Graham.hull();
//         // System.out.println("Convex Hull:"+hull);

//         // Convert Iterable to ArrayList
//         hullPoints = new ArrayList<>();
//         for (Point2D point : hull) {
//             hullPoints.add(point);
//         }
//     }
// }


// class OutputFormat{
//     ArrayList<Point2D> stations;
//     ObservationStationAnalysis OSA;
//     Point2D[] farthest;
//     double area;
//     Point2D[] farthestNew;
//     double areaNew;
//     ArrayList<Point2D> newStations;
//     public OutputFormat(ArrayList<Point2D> stations, ArrayList<Point2D> newStations) {
//         this.stations = stations;
//         this.OSA = new ObservationStationAnalysis(stations);
//         this.farthest = OSA.findFarthestStations();
//         this.area = OSA.coverageArea();
//         this.newStations = newStations;
        
//         if(newStations!=null){
//             for(Point2D newStation: this.newStations){
//                 OSA.addNewStation(newStation);
//             }
//             this.farthestNew = OSA.findFarthestStations();
//             this.areaNew = OSA.coverageArea();
//         }else{
//             this.farthestNew = this.farthest;
//             this.areaNew = this.area;
//         }
//     }
// }



// class RandomGenerator {
//     public static OutputFormat CustomProblem(int case_id, int test_id){
//         if(case_id==1 && test_id==1){ // without any new station
//             ArrayList<Point2D> stations = new ArrayList<>(List.of( new Point2D(0, 0),
//                                                         new Point2D(2, 0),
//                                                         new Point2D(3, 2),
//                                                         new Point2D(2, 6),
//                                                         new Point2D(0, 4),
//                                                         new Point2D(1, 1),
//                                                         new Point2D(2, 2)));
//             return new OutputFormat(stations, null);
//         }
//         else if(case_id==1 && test_id==2){ // with a new station
//             ArrayList<Point2D> stations = new ArrayList<>(List.of( new Point2D(0, 0),
//                                                         new Point2D(2, 0),
//                                                         new Point2D(3, 2),
//                                                         new Point2D(2, 6),
//                                                         new Point2D(0, 4),
//                                                         new Point2D(1, 1),
//                                                         new Point2D(2, 2)));

//             ArrayList<Point2D> newStations = new ArrayList<>(List.of(new Point2D(10, 3)));

//             return new OutputFormat(stations, newStations);
//         }
//         else if(case_id==1 && test_id==3){ // add a new station in the hull
//             ArrayList<Point2D> stations = new ArrayList<>(List.of( new Point2D(0, 0),
//                                                         new Point2D(10, 0),
//                                                         new Point2D(0, 10),
//                                                         new Point2D(10, 14),
//                                                         new Point2D(0, 4),
//                                                         new Point2D(1, 1),
//                                                         new Point2D(2, 2)));

//             ArrayList<Point2D> newStations = new ArrayList<>(List.of(new Point2D(6, 7)));
//             return new OutputFormat(stations, newStations);
//         }
//         else if(case_id==1 && test_id==4){ // add multiple new stations
//             ArrayList<Point2D> stations = new ArrayList<>(List.of( new Point2D(0, 0),
//                                                         new Point2D(5, 0),
//                                                         new Point2D(0, 5),
//                                                         new Point2D(7, 5),
//                                                         new Point2D(0, 4),
//                                                         new Point2D(1, 1),
//                                                         new Point2D(2, 2)));

//             ArrayList<Point2D> newStations = new ArrayList<>(List.of(new Point2D(6, 7), new Point2D(10, 0), new Point2D(3, 3)));
//             return new OutputFormat(stations, newStations);
//         }
//         else return new OutputFormat(null, null);
//     }
//     public static OutputFormat ProblemGenerator(int N, int M, int A){

//         Random random = new Random();
//         while (true) {
//             long startTime = System.nanoTime();

//             ArrayList<Point2D> stations = new ArrayList<>();
//             for (int i = 0; i < N; i++) {
//                 int x = random.nextInt(M + 1);
//                 int y = random.nextInt(M + 1);
//                 stations.add(new Point2D(x, y));
//             }

//             ArrayList<Point2D> newStations = new ArrayList<>();
//             for (int i = 0; i < A; i++) {
//                 int x = random.nextInt(M + 1);
//                 int y = random.nextInt(M + 1);
//                 newStations.add(new Point2D(x, y));
//             }

            
//             ObservationStationAnalysis OSA = new ObservationStationAnalysis(stations);
//             ObservationStationAnalysis OSA_Add = new ObservationStationAnalysis(stations);
//             for(Point2D newStation : newStations){
//                 OSA_Add.addNewStation(newStation);
//             }
            
//             if(OSA.sameFarthestPairs() && OSA_Add.sameFarthestPairs()){
//                 System.out.println("Same farthest pairs");
                
//             }else{
//                 System.out.println("No same farthest pairs");
//                 // System.out.println(stations);
//                 // System.out.println(newStations);

//                 OutputFormat OF = new OutputFormat(stations, newStations);
//                 long endTime = System.nanoTime();
//                 System.out.println("Execution Time: " + Long.toString((endTime-startTime)/1000000) + " ms");
//                 return OF;
//             }
//         }


//     }
//     public static void main(String[] args) throws Exception {
//         Path path = Paths.get("./src/ObservationStationAnalysis_testcase.json");
//         Gson myGson = new Gson();

//         String output = "[";

//         //case 1
//         output+="{"+
//                 "\"case\": "+1+","+
//                 "\"score\": 20,"+
//                 "\"data\": ["+
//                 myGson.toJson(CustomProblem(1,1))+","+
//                 myGson.toJson(CustomProblem(1,2))+","+
//                 myGson.toJson(CustomProblem(1,3))+","+
//                 myGson.toJson(CustomProblem(1,4))+
//                 "]" +
//                 "},";

//         //case 2 - N <= 10, M <= 100, A <= 5
//         output+="{"+
//                 "\"case\": "+2+","+
//                 "\"score\": 20,"+
//                 "\"data\": ["+
//                 myGson.toJson(ProblemGenerator(6, 20, 3))+","+
//                 myGson.toJson(ProblemGenerator(8, 50, 2))+","+
//                 myGson.toJson(ProblemGenerator(10, 80, 1))+","+
//                 myGson.toJson(ProblemGenerator(10, 100, 5))+
//                 "]" +
//                 "}]";
//         //case 3 - N <= 200, M <= 20, A <= 10
//         // output+="{"+
//         //         "\"case\": "+3+","+
//         //         "\"score\": 20,"+
//         //         "\"data\": ["+
//         //         myGson.toJson(ProblemGenerator(100, 20, 3))+","+
//         //         myGson.toJson(ProblemGenerator(200, 20, 10))+","+
//         //         myGson.toJson(ProblemGenerator(180, 16, 10))+","+
//         //         myGson.toJson(ProblemGenerator(200, 20, 10))+
//         //         "]" +
//         //         "},";
//         //case 4 - N <= 50000, M <= 400, A <= 1000
//         // output+="{"+
//         //         "\"case\": "+4+","+
//         //         "\"score\": 20,"+
//         //         "\"data\": ["+
//         //         myGson.toJson(ProblemGenerator(200, 400, 1000))+","+
//         //         myGson.toJson(ProblemGenerator(400, 400, 1000))+","+
//         //         myGson.toJson(ProblemGenerator(800, 400, 1000))+","+
//         //         myGson.toJson(ProblemGenerator(50000, 400, 1000))+
//         //         "]" +
//         //         "},";
//         // case 5 - N <= 80000, M <= 1000, A <= 50
//         // output+="{"+
//         //         "\"case\": "+5+","+
//         //         "\"score\": 20,"+
//         //         "\"data\": ["+
//         //         myGson.toJson(ProblemGenerator(40000, 300, 40))+","+
//         //         myGson.toJson(ProblemGenerator(50000, 400, 50))+","+
//         //         myGson.toJson(ProblemGenerator(60000, 1000, 30))+
//         //         "]" +
//         //         "}"
//         //         +"]";

//         Files.writeString(path, output, StandardCharsets.UTF_8);
//         //Files.writeString(path, output,StandardCharsets.UTF_8,StandardOpenOption.APPEND);

//         //output = "\n]";

//     }

// }