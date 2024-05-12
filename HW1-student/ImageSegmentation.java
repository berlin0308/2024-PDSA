import java.util.Random;
import edu.princeton.cs.algs4.UF;

//public class ImageSegmentation {
class ImageSegmentation {
    
    private int segmentCount;
    private int largestColor;
    private int largestSize;
    private int[][] temp;
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};  //定義上下左右

    public class Counter {
        //為了讓計數能在dfs累加後傳到外面
        //Java 是傳值呼叫，而不是傳址呼叫，所以count的值在遞迴過程中增加了也不會反映到外面
        public int count;
        public Counter(){
            this.count = 0;
        }
    }

    public ImageSegmentation(int N, int[][] inputImage) {

        segmentCount = 0;
        largestColor = 0;
        largestSize = 0;
        temp = new int[N][N];

        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                if(inputImage[i][j] == 0){
                    temp[i][j] = -1;
                }
                else if(temp[i][j] == -1){
                    //已經處理過了
                    continue;
                }
                else{
                    Counter counter = new Counter();
                    //深度優先搜尋DFS
                    dfs(inputImage, temp, i, j, N, inputImage[i][j], counter);
                    temp[i][j] = counter.count;
                }
            }
        }
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                //記錄共有幾個segment
                if (temp[i][j] != -1) segmentCount++;
                //記錄最大的segment
                if (temp[i][j] > largestSize){
                    largestSize = temp[i][j];
                    largestColor = inputImage[i][j];
                }
                else if (temp[i][j] == largestSize && inputImage[i][j] < largestColor){
                    largestColor = inputImage[i][j];
                }
            }
        }

    }

    public static void dfs(int[][] matrix, int[][] temp, int row, int col, int N, int value, Counter counter){
        //將處理中的component elements設為-1
        temp[row][col] = -1;
        counter.count++;

        //搜索相鄰的節點
        for (int[] dir: DIRECTIONS){
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            //確保新節點在範圍內且未更改過
            if (newRow >= 0 && newRow < N && newCol >= 0 && newCol < N){
                if (matrix[newRow][newCol] == value && temp[newRow][newCol] != -1){
                    dfs(matrix, temp, newRow, newCol, N, value, counter);
                }
            }
        }
    }

    public int countDistinctSegments() {
        return segmentCount;
    }

    public int[] findLargestSegment() {
        return new int[]{largestSize, largestColor};
    }

    public static void main(String args[]) {

        // // Example 1:
        // int[][] inputImage1 = {
        //     {0, 0, 0},
        //     {0, 1, 1},
        //     {0, 0, 1}
        // };

        // System.out.println("Example 1:");

        // ImageSegmentation s = new ImageSegmentation(3, inputImage1);
        // System.out.println("Number of Distinct Segments: " + s.countDistinctSegments());

        // int[] largest = s.findLargestSegment();
        // System.out.println("Size of the Largest Segment: " + largest[0]);
        // System.out.println("Color of the Largest Segment: " + largest[1]);
        // for (int i[]: s.temp){
        //     for (int j: i)
        //     System.out.print(j);
        //     System.out.println();
        // }


        // // Example 2:
        // int[][] inputImage2 = {
        //        {0, 0, 0, 3, 0},
        //        {0, 2, 3, 3, 0},
        //        {1, 2, 2, 0, 0},
        //        {1, 2, 2, 1, 1},
        //        {0, 0, 1, 1, 1}
        // };

        // System.out.println("\nExample 2:");

        // s = new ImageSegmentation(5, inputImage2);
        // System.out.println("Number of Distinct Segments: " + s.countDistinctSegments());

        // largest = s.findLargestSegment();
        // System.out.println("Size of the Largest Segment: " + largest[0]);
        // System.out.println("Color of the Largest Segment: " + largest[1]);
        // for (int i[]: s.temp){
        //     for (int j: i)
        //     System.out.print(j);
        //     System.out.println();
        // }

        // Example 3:
        int[][] inputImage3 = new int[500][500];
        Random random = new Random();
        long time1, time2, time3;
        int overtime = 0;

        // run 10000 tests
        for (int l = 0; l <= 10000; l++){
            for (int i = 0; i < 500; i++){
                for (int j = 0; j < 500; j++){
                    inputImage3[i][j] = random.nextInt(100);
                }
            }
            time1 = System.currentTimeMillis();
            System.out.println("\nExample 3:");
            ImageSegmentation s = new ImageSegmentation(500, inputImage3);
            System.out.println("Number of Distinct Segments: " + s.countDistinctSegments());
            time2 = System.currentTimeMillis();
            //System.out.println("Time = " + (time2-time1));
    
            int[] largest = s.findLargestSegment();
            System.out.println("Size of the Largest Segment: " + largest[0]);
            System.out.println("Color of the Largest Segment: " + largest[1]);
            time3 = System.currentTimeMillis();
            //System.out.println("Time = " + (time3-time2));
            System.out.println("Total Time = " + (time3-time1));
            if ((time3-time1) >= 15) overtime++;
        }
        System.err.println(overtime);
    }
}
