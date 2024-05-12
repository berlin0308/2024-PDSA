// import java.util.List;
// import java.util.Map;
// import java.util.ArrayList;
// import java.util.HashMap;

// // import java.io.FileNotFoundException;
// // import java.io.FileReader;
// // import java.io.IOException;

// // import org.json.simple.JSONArray;
// // import org.json.simple.JSONObject;
// // import org.json.simple.parser.JSONParser;
// // import org.json.simple.parser.ParseException;



// class LabNetworkCabling {

//     public LabNetworkCabling(Map<Integer, String> deviceTypes, List<int[]> links){
//         // parse the params and create a MST
//     };
    
//     public int cablingCost() {
//         int cost = 0;
//         // calculate the total cost
//         return cost;
//     }

//     public int serverToRouter(){
//         int srDistance = 0;
//         // find the path distance between the server and the router
//         return srDistance;
//     }

//     public int mostPopularPrinter(){
//         int printerIndex = 0;
//         // find the most used printer and return the index
//         return printerIndex;
//     }

//     // public static void test(String[] args){
//     //     LabNetworkCabling l;
//     //     JSONParser jsonParser = new JSONParser();
//     //     try (FileReader reader = new FileReader(args[0])){
//     //         JSONArray all = (JSONArray) jsonParser.parse(reader);
//     //         int count = 0;
//     //         for(Object CaseInList : all){
//     //             count++;
//     //             JSONObject aCase = (JSONObject) CaseInList;
//     //             JSONArray dataArray = (JSONArray) aCase.get("data");

//     //             // JSONObject data = (JSONObject) aCase.get("data");
//     //             // JSONArray dataArray = (JSONArray) data.get("data");

//     //             int testSize = 0; int waSize = 0;
//     //             System.out.print("Case ");
//     //             System.out.println(count);

//     //             for (Object dataObj : dataArray) {
//     //                 JSONObject data = (JSONObject) dataObj;

//     //                 // Extracting device types
//     //                 JSONObject jsonDeviceTypes = (JSONObject) data.get("deviceTypes");
//     //                 Map<Integer, String> deviceTypes = new HashMap<>();
//     //                 for (Object key : jsonDeviceTypes.keySet()) {
//     //                     String keyStr = (String) key;
//     //                     String value = (String) jsonDeviceTypes.get(keyStr);
//     //                     deviceTypes.put(Integer.parseInt(keyStr), value);
//     //                 }

//     //                 // Extracting links
//     //                 JSONArray jsonLinks = (JSONArray) data.get("links");
//     //                 List<int[]> links = new ArrayList<>();
//     //                 for (Object linkObj : jsonLinks) {
//     //                     JSONArray linkArr = (JSONArray) linkObj;
//     //                     int[] link = new int[linkArr.size()];
//     //                     for (int i = 0; i < linkArr.size(); i++) {
//     //                         link[i] = ((Long) linkArr.get(i)).intValue();
//     //                     }
//     //                     links.add(link);
//     //                 }

//     //                 long cablingCost = (long) data.get("cablingCost");
//     //                 long serverToRouter = (long) data.get("serverToRouter");
//     //                 long mostPopularPrinter = (long) data.get("mostPopularPrinter");

//     //                 l = new LabNetworkCabling(deviceTypes, links);

//     //                 int ans1 = l.cablingCost();
//     //                 int ans2 = l.serverToRouter();
//     //                 int ans3 = l.mostPopularPrinter();

//     //                 testSize++;
//     //                 if(ans1==cablingCost && ans2==serverToRouter && ans3==mostPopularPrinter){
//     //                     // System.out.println("AC");

//     //                 }else{
//     //                     waSize++;
//     //                     // System.out.println("WA");
//     //                 }
//     //             }
//     //             System.out.println("Score: " + (testSize-waSize) + " / " + testSize + " ");

//     //         }
//     //     }catch (FileNotFoundException e) {
//     //         e.printStackTrace();
//     //     } catch (IOException e) {
//     //         e.printStackTrace();
//     //     } catch (ParseException e) {
//     //         e.printStackTrace();
//     //     }
//     // }

//     // public static void main(String[] args) {
//     //     // test(args);
//     //     test(new String[]{"test/test.json"});
//     // }
// }