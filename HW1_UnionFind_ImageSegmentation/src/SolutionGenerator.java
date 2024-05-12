import com.google.gson.*;
import java.util.Random;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

class OutputFormat{
    int[][] image;
    int N;
    ImageSegmentation s;
    int DistinctSegments;
    Integer[] LargestSegment;
    OutputFormat(int N, int[][] image) {
        this.N = N;
        this.image = image;
        this.s = new ImageSegmentation(this.N, this.image);
        this.DistinctSegments = s.countDistinctSegments();
        this.LargestSegment = convertIntArrayToIntegerArray(s.findLargestSegment());
    }

    private Integer[] convertIntArrayToIntegerArray(int[] intArray) {
        Integer[] result = new Integer[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            result[i] = intArray[i];
        }
        return result;
    }
}
class RandomGenerator {
    public static OutputFormat CustomProblem(int case_id, int test_id){
        if(case_id==1 && test_id==1) return new OutputFormat(2, new int[][]{{0, 1},
                                                                            {0, 1}});
        else if(case_id==1 && test_id==2) return new OutputFormat(3, new int[][]{{0, 0, 0},
                                                                                    {0, 1, 1},
                                                                                    {0, 0, 1}});
        else if(case_id==1 && test_id==3) return new OutputFormat(3, new int[][]{{0, 0, 0},
                                                                                    {2, 2, 2},
                                                                                    {0, 0, 1}});
        else if(case_id==1 && test_id==4) return new OutputFormat(3, new int[][]{{59, 0, 0},
                                                                                    {59, 266, 266},
                                                                                    {0, 0, 72}});

        // Case 2.1: segments with same size -> smallest color index
        else if(case_id==2 && test_id==1) return new OutputFormat(4, new int[][]{{0, 0, 0, 2},
                                                                                    {3, 3, 1, 2},
                                                                                    {3, 1, 1, 2},
                                                                                    {3, 0, 1, 2}});
        // Case 2.2: separate segments with same color -> count as independent segments
        else if(case_id==2 && test_id==2) return new OutputFormat(4, new int[][]{{0, 0, 0, 2},
                                                                                    {4, 3, 3, 2},
                                                                                    {2, 1, 1, 2},
                                                                                    {2, 0, 1, 0}});
        else if(case_id==2 && test_id==3) return new OutputFormat(5, new int[][]{{0, 0, 0, 3, 0},
                                                                                    {0, 2, 3, 3, 0},
                                                                                    {1, 2, 2, 0, 0},
                                                                                    {1, 2, 2, 1, 1},
                                                                                    {0, 0, 1, 1, 1}});
        else if(case_id==2 && test_id==4) return new OutputFormat(5, new int[][]{{0, 0, 5, 5, 0},
                                                                                    {0, 2, 5, 3, 0},
                                                                                    {1, 2, 4, 4, 0},
                                                                                    {1, 2, 4, 1, 1},
                                                                                    {0, 0, 4, 1, 1}});
        else return new OutputFormat(1, new int[][]{{1}});
    }
    public static OutputFormat ProblemGenerator(int N, int C, int seedCount,int expansionSteps){

        long startTime = System.nanoTime();
        int[][] image = ImageGenerator.generateSegmentedImage(N, C, seedCount, expansionSteps, false);
        new ImageSegmentation(N, image);
        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + Long.toString((endTime-startTime)/1000000) + " ms");
        return new OutputFormat(N, image);
    }
    public static OutputFormat ProblemGenerator(int N, int C, int seedCount,int expansionSteps, boolean bg){

        long startTime = System.nanoTime();
        int[][] image = ImageGenerator.generateSegmentedImage(N, C, seedCount, expansionSteps, bg);
        new ImageSegmentation(N, image);
        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + Long.toString((endTime-startTime)/1000000) + " ms");
        return new OutputFormat(N, image);
    }
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("./src/ImageSegmentation.json");
        Gson myGson = new Gson();

        String output = "[";

        //case 1
        output+="{"+
                "\"case\": "+1+","+
                "\"score\": 20,"+
                "\"data\": ["+
                myGson.toJson(CustomProblem(1,1))+","+
                myGson.toJson(CustomProblem(1,2))+","+
                myGson.toJson(CustomProblem(1,3))+","+
                myGson.toJson(CustomProblem(1,4))+
                "]" +
                "},";
        //case 2
        output+="{"+
                "\"case\": "+2+","+
                "\"score\": 20,"+
                "\"data\": ["+
                myGson.toJson(CustomProblem(2,1))+","+
                myGson.toJson(CustomProblem(2,2))+","+
                myGson.toJson(CustomProblem(2,3))+","+
                myGson.toJson(CustomProblem(2,4))+
                "]" +
                "}" + ']';
        // //case 3
        // output+="{"+
        //         "\"case\": "+3+","+
        //         "\"score\": 20,"+
        //         "\"data\": ["+
        //         myGson.toJson(ProblemGenerator(16, 6, 5, 6))+","+
        //         myGson.toJson(ProblemGenerator(20, 10, 10, 5))+","+
        //         myGson.toJson(ProblemGenerator(25, 8, 20, 90))+","+
        //         myGson.toJson(ProblemGenerator(25, 10, 4, 200))+
        //         "]" +
        //         "},";
        // // //case 4
        // output+="{"+
        //         "\"case\": "+4+","+
        //         "\"score\": 20,"+
        //         "\"data\": ["+
        //         myGson.toJson(ProblemGenerator(80, 16, 5, 12))+","+
        //         myGson.toJson(ProblemGenerator(80, 36, 10, 40))+","+
        //         myGson.toJson(ProblemGenerator(100, 8, 20, 500))+","+
        //         myGson.toJson(ProblemGenerator(100, 35, 20, 800))+","+
        //         myGson.toJson(ProblemGenerator(100, 50, 4, 2000))+
        //         "]" +
        //         "},";
        // // //case 5
        // output+="{"+
        //         "\"case\": "+5+","+
        //         "\"score\": 20,"+
        //         "\"data\": ["+
        //         myGson.toJson(ProblemGenerator(200, 16, 5, 1000))+","+
        //         myGson.toJson(ProblemGenerator(400, 10, 6, 2000))+","+
        //         myGson.toJson(ProblemGenerator(500, 10, 8, 30000, true))+","+
        //         myGson.toJson(ProblemGenerator(200, 2, 10, 20000, true))+
        //         "]" +
        //         "}"
        //         +"]";

        Files.writeString(path, output, StandardCharsets.UTF_8);
        //Files.writeString(path, output,StandardCharsets.UTF_8,StandardOpenOption.APPEND);

        //output = "\n]";

    }

}