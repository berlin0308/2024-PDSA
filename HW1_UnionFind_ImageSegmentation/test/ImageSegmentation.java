import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class ImageSegmentation {

    private WeightedQuickUnionUF uf;
    private int segmentCount;
    private int largestColor;
    private int largestSize;

	public ImageSegmentation(int N, int[][] inputImage) {
        // Initialize a N-by-N segmented image
    }

    public int countDistinctSegments() {
        // Count the number of distinct segments in the image.
        return segmentCount;
    }

    public int[] findLargestSegment() {
        // Find the largest connected segment and return an array
        // containing the number of pixels and the color of the segment.
        return new int[]{largestSize, largestColor};
    }

    public void mergeSegment(int x1, int y1, int x2, int y2) {
        // Implement the function to merge two adjacent segments if necessary.
    }
	
    public static void test(String[] args){
        ImageSegmentation s;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(args[0])){
            JSONArray all = (JSONArray) jsonParser.parse(reader);
            int count = 0;
            for(Object CaseInList : all){
                count++;
                JSONObject aCase = (JSONObject) CaseInList;
                JSONArray dataArray = (JSONArray) aCase.get("data");

                // JSONObject data = (JSONObject) aCase.get("data");
                // JSONArray dataArray = (JSONArray) data.get("data");

                int testSize = 0; int waSize = 0;
                System.out.print("Case ");
                System.out.println(count);
                for (Object dataObject : dataArray) {
                    JSONObject dataDetails = (JSONObject) dataObject;
                    int N = ((Long) dataDetails.get("N")).intValue();
                    JSONArray imageArray = (JSONArray) dataDetails.get("image");

                    int[][] image = new int[imageArray.size()][];
                    for (int i = 0; i < imageArray.size(); i++) {
                        JSONArray row = (JSONArray) imageArray.get(i);
                        image[i] = new int[row.size()];
                        for (int j = 0; j < row.size(); j++) {
                            image[i][j] = ((Long) row.get(j)).intValue();
                        }
                    }
                    // System.out.println("N: " + N);
                    // System.out.println("Image: " + Arrays.deepToString(image));

                    s = new ImageSegmentation(N, image);

                    int distinctSegments = ((Long) dataDetails.get("DistinctSegments")).intValue();

                    JSONArray largestSegmentArray = (JSONArray) dataDetails.get("LargestSegment");
                    int largestColor = ((Long) largestSegmentArray.get(0)).intValue();
                    int largestSize = ((Long) largestSegmentArray.get(1)).intValue();

                    int ans1 = s.countDistinctSegments();
                    int ans2 = s.findLargestSegment()[0];
                    int ans3 = s.findLargestSegment()[1];

                    testSize++;
                    if(ans1==distinctSegments && ans2==largestColor && ans3==largestSize){
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

    public static void main(String[] args) {
        test(args);
        // test(new String[]{"src/test.json"});
    }
}