import edu.princeton.cs.algs4.WeightedQuickUnionUF;

class ImageSegmentation{
    
    private WeightedQuickUnionUF uf;
    private int size;
    private int segmentCount;
    private int largestColor;
    private int largestSize;

    public ImageSegmentation(int N, int[][] inputImage) {
        size = N;
        uf = new WeightedQuickUnionUF(N * N);
        segmentCount = 0;
        largestColor = 0;
        largestSize = 0;

        int[] segmentSizes = new int[N * N];
        boolean[] hasColor = new boolean[N * N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int color = inputImage[i][j];
                if (color != 0) {
                    int id = i * N + j;
                    hasColor[id] = true;

                    // Merge with adjacent cells
                    if (i > 0 && inputImage[i - 1][j] == color) mergeSegment(i, j, i - 1, j);
                    if (j > 0 && inputImage[i][j - 1] == color) mergeSegment(i, j, i, j - 1);
                }
            }
        }

        for (int i = 0; i < N * N; i++) {
            if (hasColor[i]) {
                int root = uf.find(i);
                segmentSizes[root]++;
                if (segmentSizes[root] > largestSize || 
                    (segmentSizes[root] == largestSize && inputImage[i / N][i % N] < largestColor)) {
                    largestSize = segmentSizes[root];
                    largestColor = inputImage[i / N][i % N];
                }
            }
        }

        for (int i = 0; i < N * N; i++) {
            if (hasColor[i] && uf.find(i) == i) {
                segmentCount++;
            }
        }
    }

    public void mergeSegment(int x1, int y1, int x2, int y2) {
        int id1 = x1 * size + y1;
        int id2 = x2 * size + y2;
        uf.union(id1, id2);
    }

    public int countDistinctSegments() {
        return segmentCount;
    }

    public int[] findLargestSegment() {
        return new int[]{largestSize, largestColor};
    }

    // public static void main(String args[]) {
    //     // Example 1:
    //     int[][] inputImage1 = {
    //         {0, 0, 0},
    //         {0, 1, 1},
    //         {0, 0, 1}
    //     };

    //     System.out.println("Example 1:");

    //     ImageSegmentationGPT s = new ImageSegmentationGPT(3, inputImage1);
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

    //     s = new ImageSegmentationGPT(5, inputImage2);
    //     System.out.println("Distinct Segments Count: " + s.countDistinctSegments());

    //     largest = s.findLargestSegment();
    //     System.out.println("Largest Segment Size: " + largest[0]);
    //     System.out.println("Largest Segment Color: " + largest[1]);
    // }
}
