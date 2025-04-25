package C1_2023;

public class MinPathFinder {
    public int findMinPath(int[][] weightMatrix){     //---> Programacion dinamica, vou guardando los costos minimos para llegar a cada celda
        int rows = weightMatrix.length;
        int cols = weightMatrix[0].length;

        int[][] dp = new int[rows][cols];

        dp[0][0] = weightMatrix[0][0];

        // Lleno la primera fila y columna
        for (int j = 1; j < cols; j++) {
            dp[0][j] = dp[0][j - 1] + weightMatrix[0][j];
        }
        for (int i = 1; i < rows; i++) {
            dp[i][0] = dp[i - 1][0] + weightMatrix[i][0];
        }

        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + weightMatrix[i][j];
            }
        }
        return dp[rows - 1][cols - 1];
    }

    public static void main(String[] args) {
        MinPathFinder pathFinder = new MinPathFinder();
        int[][] m1 = {
                {2, 8, 32, 30},
                {12, 6, 18, 19},
                {1,2,4,8},
                {1, 31, 1, 16}
        };
        System.out.println("Min Path m1: " + pathFinder.findMinPath(m1)); //38

        int[][] m2 = {
                {2, 8, 32, 30},
                {12, 6, 18, 19},
                {1, 2, 4, 8}
        };
        System.out.println("Min Path m2: " + pathFinder.findMinPath(m2)); //29

        int[][] m3 = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };
        System.out.println("Min Path m3: " + pathFinder.findMinPath(m3)); //7


    }
}
