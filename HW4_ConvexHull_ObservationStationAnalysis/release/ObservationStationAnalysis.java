
import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    
    public static void main(String[] args) {
        test(args);
        // test(new String[]{"test.json"});
    }
    
    public static void test(String[] args){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(args[0])){
            JSONArray all = (JSONArray) jsonParser.parse(reader);
            int count = 0;
            for(Object CaseInList : all){
                count++;
                JSONObject aCase = (JSONObject) CaseInList;
                JSONArray dataArray = (JSONArray) aCase.get("data");

                int testSize = 0; int waSize = 0;
                System.out.print("Case ");
                System.out.println(count);
                for (Object dataObject : dataArray) {
                    JSONObject dataDetails = (JSONObject) dataObject;
                    JSONArray stationsArray = (JSONArray) dataDetails.get("stations");
                    
                    // parse stations
                    ArrayList<Point2D> stations = new ArrayList<>();
                    for (Object stationObj : stationsArray) {
                        JSONObject station = (JSONObject) stationObj;
                        double x = (double) station.get("x");
                        double y = (double) station.get("y");
                        stations.add(new Point2D(x, y));
                    }
                    
                    ArrayList<Point2D> newStations = new ArrayList<>();
                    try{
                        JSONArray newStationsArray = (JSONArray) dataDetails.get("newStations");
                        for (Object stationObj : newStationsArray) {
                            JSONObject station = (JSONObject) stationObj;
                            double x = (double) station.get("x");
                            double y = (double) station.get("y");
                            newStations.add(new Point2D(x, y));
                        }
                    }catch(Exception e){
                        newStations = null;
                    }
                    
                    Point2D[] farthest;
                    double area;
                    Point2D[] farthestNew;
                    double areaNew;

                    ObservationStationAnalysis OSA = new ObservationStationAnalysis(stations);
                    farthest = OSA.findFarthestStations();
                    area = OSA.coverageArea();
                    if(newStations!=null){
                        for(Point2D newStation: newStations){
                            OSA.addNewStation(newStation);
                        }
                        farthestNew = OSA.findFarthestStations();
                        areaNew = OSA.coverageArea();
                    }else{
                        farthestNew = farthest;
                        areaNew = area;
                    }

                    int countAC = 0;

                    JSONArray AnsArray = (JSONArray) dataDetails.get("farthest");
                    Object point1 = AnsArray.get(0);
                    JSONObject station = (JSONObject) point1;
                    double x1 = (double) station.get("x");
                    double y1 = (double) station.get("y");

                    Object point2 = AnsArray.get(1);
                    station = (JSONObject) point2;
                    double x2 = (double) station.get("x");
                    double y2 = (double) station.get("y");
                    if(x1==farthest[0].x() && y1==farthest[0].y() && x2==farthest[1].x() && y2==farthest[1].y()){
                        countAC++;
                        // System.out.println("farthest: AC");
                    }

                    AnsArray = (JSONArray) dataDetails.get("farthestNew");
                    point1 = AnsArray.get(0);
                    station = (JSONObject) point1;
                    x1 = (double) station.get("x");
                    y1 = (double) station.get("y");

                    point2 = AnsArray.get(1);
                    station = (JSONObject) point2;
                    x2 = (double) station.get("x");
                    y2 = (double) station.get("y");

                    if(x1==farthestNew[0].x() && y1==farthestNew[0].y() && x2==farthestNew[1].x() && y2==farthestNew[1].y()){
                        countAC++;
                        // System.out.println("farthestNew: AC");
                    }
                    

                    if(Math.abs((Double) dataDetails.get("area")-area)<0.0001){
                        countAC++;
                        // System.out.println("area: AC");
                    }
                    
                    if(Math.abs((Double) dataDetails.get("areaNew")-areaNew)<0.0001){
                        countAC++;
                        // System.out.println("areaNew: AC");
                    }

                    testSize++;
                    if(countAC==4){
                        // System.out.println("AC");

                    }else{
                        waSize++;
                        // System.out.println("WA");
                    }
                }
                System.out.println("Score: " + (testSize-waSize) + " / " + testSize + " ");

            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static int[] JSONArraytoIntArray(JSONArray x){
        int sizeLim = x.size();
        int MyInt[] = new int[sizeLim];
        for(int i=0;i<sizeLim;i++){
            MyInt[i]= Integer.parseInt(x.get(i).toString());
        }
        return MyInt;
    }

}
