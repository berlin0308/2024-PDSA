import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class ImageSegmentation {

	private WeightedQuickUnionUF uf;
    private int size;
    private int segmentCount;
    private int largestColor;
    private int largestSize = 0;
	
	public ImageSegmentation(int N, int[][] inputImage) {
	    // Initialize a N-by-N segmented image
        size = N;
        uf = new WeightedQuickUnionUF(size*size);

        int zeroCount = 0;

        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                int color = inputImage[i][j];
                if(color != 0){
                    try{
                        if(inputImage[i-1][j] == color) mergeSegment(i, j, i-1, j);
                    }catch(Exception ArrayIndexOutOfBoundsException){} //System.out.println("ArrayIndexOutOfBoundsException");}

                    try{
                        if(inputImage[i+1][j] == color) mergeSegment(i, j, i+1, j);
                    }catch(Exception ArrayIndexOutOfBoundsException){} //System.out.println("ArrayIndexOutOfBoundsException");}
                    
                    try{
                        if(inputImage[i][j-1] == color) mergeSegment(i, j, i, j-1);
                    }catch(Exception ArrayIndexOutOfBoundsException){} //System.out.println("ArrayIndexOutOfBoundsException");}

                    try{
                        if(inputImage[i][j+1] == color) mergeSegment(i, j, i, j+1);
                    }catch(Exception ArrayIndexOutOfBoundsException){} //System.out.println("ArrayIndexOutOfBoundsException");}
                    
                }else{
                    zeroCount++ ;
                }
            }
        }
        
        // Number of distinct segments in the image
        segmentCount = uf.count()-zeroCount;

        // Find the largest connected segment and return an array
        int[] segmentSizes = new int[size*size];

        for(int i=0; i<size*size; i++){
            if(inputImage[i/size][i%size]!=0){
                int root = uf.find(i);
                // System.out.println(root);
                segmentSizes[root] ++;
                // System.out.println(segmentSizes[root]);
                if(segmentSizes[root] > largestSize || (segmentSizes[root] == largestSize && inputImage[i/size][i%size] < largestColor)){
                    largestSize = segmentSizes[root];
                    largestColor = inputImage[root/size][root%size];
                }
            }
        }
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
        uf.union(x1*size+y1, x2*size+y2);
	}
	
	// public static void main(String args[]) {

	//     // Example 1:
	//     int[][] inputImage1 = {
	//         {0, 0, 0},
	//         {0, 1, 1},
	//         {0, 0, 1}
	//     };
        
    //     System.out.println("Example 1:");

	//     ImageSegmentation s = new ImageSegmentation(3, inputImage1);
	//     System.out.println("Distinct Segments Count: " + s.countDistinctSegments());
        
	//     int[] largest = s.findLargestSegment();
	//     System.out.println("Largest Segment Size: " + largest[0]);
	//     System.out.println("Largest Segment Color: " + largest[1]);
	    
	
	//     // Example 2:
	//     int[][] inputImage2 = {
    //            {0, 0, 0, 3, 0},
    //            {0, 2, 3, 3, 0},
    //            {1, 2, 2, 0, 0},
    //            {1, 2, 2, 1, 1},
    //            {0, 0, 1, 1, 1}
	//     };
        
	//     System.out.println("\nExample 2:");
        
	//     s = new ImageSegmentation(5, inputImage2);
	//     System.out.println("Distinct Segments Count: " + s.countDistinctSegments());
        
	//     largest = s.findLargestSegment();
	//     System.out.println("Largest Segment Size: " + largest[0]);
	//     System.out.println("Largest Segment Color: " + largest[1]);
	    
	// }


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
        // test(args);
        test(new String[]{"src/test.json"});
    }
}