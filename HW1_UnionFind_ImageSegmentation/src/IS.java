import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
// import java.util.Arrays;
import java.util.List;
// import java.util.stream.IntStream;

class ImageSegmentation2 {

    private int segmentCount;
    private int largestColor;
    private int largestSize;

    private int[] array_new;

    public ImageSegmentation2(int N, int[][] inputImage) {
        // Initialize a N-by-N image
        array_new = new int[N * N];
        WeightedQuickUnionUF singleD = new WeightedQuickUnionUF(N * N);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                array_new[i*N + j] = inputImage[i][j];
            }
        }

        int dismiss = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (array_new[i * N + j] != 0){
                    if (array_new[i * N + j] == array_new[i * N + j + 1]) {
                        singleD.union(i * N + j + 1, i * N + j);
                    }
                }
                // Dismiss background segment
                if (array_new[i * N + j] == 0 && dismiss == 0) {
                    for (int k = 0; k < N*N; k++) {
                        if (array_new[k] == 0) {
                            singleD.union(k, i * N + j);
                        }
                    }
                    dismiss += 1;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (array_new[i + j * N] == array_new[i + (j + 1) * N]) {
                    singleD.union(i + (j + 1) * N, i + j * N);
                }
            }
        }

        segmentCount = singleD.count() - 1;

        List<Integer> id = new ArrayList<>();
        List<Integer> id_num = new ArrayList<>();
        for (int i = 0; i < N*N; i++) {

            if (id.contains(singleD.find(i))) {
                int find = id.indexOf(singleD.find(i));
                int temp = id_num.get(find);
                id_num.set(id.indexOf(singleD.find(i)), temp + 1);
                // System.out.println(id_num.get(find));
            }
            // System.out.println(singleD.find(i));
            if (!(id.contains(singleD.find(i))) && array_new[singleD.find(i)] != 0) {
                id.add(singleD.find(i));
                id_num.add(1);
                // System.out.println(singleD.find(i) + "*" +id_num.get(0));
            }
        }

        int max = 0;
        int max_color = 1;
        for (int i = 0; i < id.size(); i++) {
            // System.out.println(id.get(i)+ "," + array_new[id.get(i)] + "," + id_num.get(i));
            if (id_num.get(i) >= max) {
                if ((array_new[id.get(i)] > max_color) && id_num.get(i) == max) {
                    // System.out.println("skip" + array_new[id.get(i)] + ">" + array_new[max_color]);
                    continue;
                }
                max_color = array_new[id.get(i)];
                max = id_num.get(i);
                // System.out.println(max_color);

            }
            // System.out.println(max_color);
        }
        largestSize = max;
        largestColor = max_color;

        System.out.println(dismiss);


    }

    public int countDistinctSegments() {
        // Count distinct segment

        return segmentCount;
    }

    public int[] findLargestSegment() {

        // Find the largest connected segment and return an array

        // containing the number of pixels and the color of the segment.
        // largestColor = 0;
        // largestSize = 0;

        return new int[]{largestSize, largestColor};
    }

    public static void main(String args[]) {

        // Example 1:
        int[][] inputImage1 = {
            {0, 0, 0},
            {0, 1, 1},
            {0, 0, 1}
        };

        System.out.println("Example 1:");

        ImageSegmentation2 s = new ImageSegmentation2(3, inputImage1);
        System.out.println("Number of Distinct Segments: " + s.countDistinctSegments());

        int[] largest = s.findLargestSegment();
        System.out.println("Size of the Largest Segment: " + largest[0]);
        System.out.println("Color of the Largest Segment: " + largest[1]);

        // Example 2:
        int[][] inputImage2 = {
               {2, 2, 4, 4, 4},
               {1, 2, 3, 3, 4},
               {1, 2, 2, 2, 4},
               {1, 2, 2, 2, 1},
               {1, 1, 1, 1, 1}
        };

        System.out.println("\nExample 2:");

        s = new ImageSegmentation2(5, inputImage2);
        System.out.println("Number of Distinct Segments: " + s.countDistinctSegments());

        largest = s.findLargestSegment();
        System.out.println("Size of the Largest Segment: " + largest[0]);
        System.out.println("Color of the Largest Segment: " + largest[1]);

    }

}