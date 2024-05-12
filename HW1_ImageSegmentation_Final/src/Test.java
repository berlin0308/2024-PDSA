import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

class OutputFormat{
    int[][] image;
    int N;
    ImageSegmentation s;
    int DistinctSegments;
    int[] LargestSegment;
    OutputFormat(int N, int[][] image) {
        this.N = N;
        this.image = image;
        this.s = new ImageSegmentation(this.N, this.image);
        this.DistinctSegments = s.countDistinctSegments();
        this.LargestSegment = s.findLargestSegment();
    }

}

class Test{
    public static void main(String[] args)
    {
        Gson gson = new Gson();
        OutputFormat[] datas;
        int num_ac = 0;
        OutputFormat data;

        try {

            datas = gson.fromJson(new FileReader("src/ImageSegmentation.json"), OutputFormat[].class);
            for(int i = 0; i<datas.length;++i)
            {
                data = datas[i];
                ImageSegmentation s = new ImageSegmentation(data.N, data.image);

                // Integer[] L = data.LargestSegment;
                System.out.println(data.LargestSegment[0]);

                System.out.print("Sample"+i+": ");
                if(data.DistinctSegments==s.countDistinctSegments() && Arrays.equals(data.LargestSegment, s.findLargestSegment()))
                {
                    System.out.println("AC");
                    num_ac++;
                }
                else
                {
                    System.out.println("WA");
                    System.out.println("Test Ans - DistinctSegments:  " + data.DistinctSegments);
                    System.out.println("Test Ans - LargestSegment:  " + Arrays.toString(data.LargestSegment));
                    System.out.println("User Ans - DistinctSegments:  " + s.countDistinctSegments());
                    System.out.println("User Ans - LargestSegment:  " + Arrays.toString(s.findLargestSegment()));
                    System.out.println("");
                }
            }
            System.out.println("Score: "+num_ac+"/"+datas.length);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
