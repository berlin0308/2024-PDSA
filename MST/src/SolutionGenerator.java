import com.google.gson.*;
import java.util.Random;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class OutputFormat{
    LabNetworkCabling l;
    Map<Integer, String> deviceTypes;
    List<int[]> links;

    int cablingCost;
    int serverToRouter;
    int mostPopularPrinter;

    OutputFormat(Map<Integer, String> deviceTypes, List<int[]> links) {
        this.deviceTypes = deviceTypes;
        this.links = links;
        this.l = new LabNetworkCabling(this.deviceTypes, this.links);
        this.cablingCost = l.cablingCost();
        this.serverToRouter = l.serverToRouter();
        this.mostPopularPrinter = l.mostPopularPrinter();
        System.out.println("Total Cabling Cost: "+this.cablingCost);
        System.out.println("Distance between Server and Router: "+this.serverToRouter);
        System.out.println("Most Popular Printer: "+this.mostPopularPrinter);
    }
}
class RandomGenerator {
    public static OutputFormat CustomProblem(int case_id, int test_id){
            if(case_id==1 && test_id==1){ // the example
                Map<Integer, String> deviceTypes = Map.of(
                0, "Router",
                1, "Server",
                2, "Printer",
                3, "Printer",
                4, "Computer",
                5, "Computer",
                6, "Computer"
                );

                List<int[]> links = List.of(
                    new int[]{0, 1, 1},
                    new int[]{1, 2, 2},
                    new int[]{2, 4, 1},
                    new int[]{0, 3, 3},
                    new int[]{1, 3, 8},
                    new int[]{3, 5, 2},
                    new int[]{3, 6, 9},
                    new int[]{0, 6, 1}
                );
                return new OutputFormat(deviceTypes, links);
            }
            if(case_id==1 && test_id==2){ // simple
                Map<Integer, String> deviceTypes = Map.of(
                0, "Router",
                1, "Server",
                2, "Printer",
                3, "Computer"
                );

                List<int[]> links = List.of(
                    new int[]{0, 2, 2},
                    new int[]{1, 2, 4},
                    new int[]{1, 3, 5},
                    new int[]{0, 1, 9},
                    new int[]{0, 3, 12}
                );
                return new OutputFormat(deviceTypes, links);
            }
            if(case_id==1 && test_id==3){
                Map<Integer, String> deviceTypes = Map.of(
                    0, "Router",
                    1, "Server",
                    2, "Printer",
                    3, "Printer",
                    4, "Computer",
                    5, "Computer",
                    6, "Computer"
                );

                List<int[]> links = List.of(
                    new int[]{0, 1, 5},
                    new int[]{0, 4, 3},
                    new int[]{0, 5, 9},
                    new int[]{0, 2, 16},
                    new int[]{4, 2, 2},
                    new int[]{5, 3, 1},
                    new int[]{6, 3, 1},
                    new int[]{6, 2, 6}
                );
                return new OutputFormat(deviceTypes, links);
            }


            if(case_id==2 && test_id==1){ // if two printers are equally close, they choose the one with the lower device index.
                Map<Integer, String> deviceTypes = Map.of(
                    0, "Printer",
                    1, "Printer",
                    2, "Printer",
                    3, "Server",
                    4, "Router",
                    5, "Computer"
                );

                List<int[]> links = List.of(
                    new int[]{0, 5, 2},
                    new int[]{1, 5, 2},
                    new int[]{2, 5, 2},
                    new int[]{0, 3, 4},
                    new int[]{3, 5, 6},
                    new int[]{3, 4, 10},
                    new int[]{4, 5, 3},
                    new int[]{4, 2, 30}
                );
                return new OutputFormat(deviceTypes, links);
            }
            if(case_id==2 && test_id==2){ // If two or more printers have the same amount of usage, return the smallest.
                Map<Integer, String> deviceTypes = Map.of(
                    0, "Router",
                    1, "Server",
                    2, "Printer",
                    3, "Printer",
                    4, "Computer",
                    5, "Computer",
                    6, "Computer",
                    7, "Computer"
                );

                List<int[]> links = List.of(
                    new int[]{4, 2, 1},
                    new int[]{5, 2, 1},
                    new int[]{0, 2, 7},
                    new int[]{0, 6, 2},
                    new int[]{0, 1, 9},
                    new int[]{1, 6, 4},
                    new int[]{6, 3, 1},
                    new int[]{3, 7, 1}
                );
                return new OutputFormat(deviceTypes, links);
            }
            if(case_id==2 && test_id==3){
                Map<Integer, String> deviceTypes = Map.of(
                    0, "Router",
                    1, "Server",
                    2, "Printer",
                    3, "Printer",
                    4, "Printer",
                    5, "Computer"
                );

                List<int[]> links = List.of(
                    new int[]{4, 2, 1},
                    new int[]{3, 2, 1},
                    new int[]{0, 2, 6},
                    new int[]{0, 5, 9},
                    new int[]{5, 1, 7}
                );
                return new OutputFormat(deviceTypes, links);
            }
        
        else return new OutputFormat(null, null);
    }
    public static OutputFormat ProblemGenerator(int P, int C, int L){


        long startTime = System.nanoTime();
        Map<Integer, String> deviceTypes = new HashMap<>();
        deviceTypes.put(0, "Router");
        deviceTypes.put(1, "Server");
        for(int i=2;i<2+P;i++){
            deviceTypes.put(i, "Printer");
        }
        for(int j=2+P;j<2+P+C;j++){
            deviceTypes.put(j, "Computer");
        }
        // deviceTypes.forEach((key, value) -> System.out.println(key + " = " + value));

        
        Random random = new Random();
        int totalDevices = 2 + P + C;

        List<int[]> links = new ArrayList<>();

        // connect all vertice with another to ensure all-connected
        for (int i = 0; i < totalDevices - 1; i++) {
            int cost = random.nextInt(40) + 1;
            links.add(new int[]{i, i + 1, cost});
        }

        // append the remained links
        while (links.size() < L) {
            int a = random.nextInt(totalDevices);
            int b = random.nextInt(totalDevices);
            int cost = random.nextInt(10) + 1;

            if (a != b && links.stream().noneMatch(link -> (link[0] == a && link[1] == b) || (link[0] == b && link[1] == a))) {
                links.add(new int[]{a, b, cost});
            }
        }

        // for (int[] link : links) {
        //     System.out.println("Connect: " + link[0] + " -- " + link[1] + " , cost " + link[2]);
        // }

        LabNetworkCabling l = new LabNetworkCabling(deviceTypes, links);

        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + Long.toString((endTime-startTime)/1000000) + " ms");
        return new OutputFormat(deviceTypes, links);
        }


    public static void main(String[] args) throws Exception {
        Path path = Paths.get("./src/LabNetworkCabling.json");
        Gson myGson = new Gson();

        String output = "[";

        //case 1
        output+="{"+
                "\"case\": "+1+","+
                "\"score\": 20,"+
                "\"data\": ["+
                myGson.toJson(CustomProblem(1,1))+","+
                myGson.toJson(CustomProblem(1,2))+","+
                myGson.toJson(CustomProblem(1,3))+
                "]" +
                "},";
        //case 2
        output+="{"+
                "\"case\": "+2+","+
                "\"score\": 20,"+
                "\"data\": ["+
                myGson.toJson(CustomProblem(2,1))+","+
                myGson.toJson(CustomProblem(2,2))+","+
                myGson.toJson(CustomProblem(2,3))+
                "]" +
                "},";
        // case 3
        output+="{"+
                "\"case\": "+3+","+
                "\"score\": 20,"+
                "\"data\": ["+
                myGson.toJson(ProblemGenerator(5, 2, 20))+","+
                myGson.toJson(ProblemGenerator(10, 5, 30))+","+
                myGson.toJson(ProblemGenerator(10, 20, 80))+","+
                myGson.toJson(ProblemGenerator(20, 20, 60))+
                "]" +
                "},";
        // case 4
        output+="{"+
                "\"case\": "+4+","+
                "\"score\": 20,"+
                "\"data\": ["+
                myGson.toJson(ProblemGenerator(200, 100, 500))+","+
                myGson.toJson(ProblemGenerator(100, 2, 200))+","+
                myGson.toJson(ProblemGenerator(2, 350, 500))+","+
                myGson.toJson(ProblemGenerator(200, 400, 800))+
                "]" +
                "},";
        //case 5
        System.out.println("Case 5\n\n\n");
        output+="{"+
                "\"case\": "+5+","+
                "\"score\": 20,"+
                "\"data\": ["+
                myGson.toJson(ProblemGenerator(200, 100, 500))+","+
                myGson.toJson(ProblemGenerator(100, 2, 200))+","+
                myGson.toJson(ProblemGenerator(2, 350, 500))+","+
                myGson.toJson(ProblemGenerator(200, 400, 800))+
                "]" +
                "}"
                +"]";

        Files.writeString(path, output, StandardCharsets.UTF_8);
        //Files.writeString(path, output,StandardCharsets.UTF_8,StandardOpenOption.APPEND);

        //output = "\n]";

    }

}