import java.util.Random;

public class ImageGenerator {

    private static final int[] DX = {-1, 1, 0, 0};
    private static final int[] DY = {0, 0, -1, 1};

    public static int[][] generateSegmentedImage(int N, int C, int seedCount, int expansionSteps) {
        int[][] image = new int[N][N];
        Random random = new Random();

        for (int s = 0; s < seedCount; s++) {
            int x = random.nextInt(N);
            int y = random.nextInt(N);
            int color = 1 + random.nextInt(C); // from 1 to C

            expandFromSeed(image, N, x, y, color, expansionSteps, random);
        }

        return image;
    }

    private static void expandFromSeed(int[][] image, int N, int x, int y, int color, int steps, Random random) {
        if (steps <= 0 || x < 0 || y < 0 || x >= N || y >= N || image[x][y] != 0) return;

        image[x][y] = color;

        // randomly choose a direction
        for (int i = 0; i < steps; i++) {
            int dir = random.nextInt(4);
            int newX = x + DX[dir];
            int newY = y + DY[dir];

            expandFromSeed(image, N, newX, newY, color, steps - 1, random);
        }
    }

    public static void main(String[] args) {
        int N = 10;
        int C = 5;
        int seedCount = 10;
        int expansionSteps = 5;

        int[][] segmentedImage = generateSegmentedImage(N, C, seedCount, expansionSteps);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(segmentedImage[i][j] + " ");
            }
            System.out.println();
        }
    }
}
