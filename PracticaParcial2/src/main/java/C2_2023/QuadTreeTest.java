package C2_2023;

import java.util.Arrays;

public class QuadTreeTest {
    public static void main(String[] args) {
        // Test Case 1: Simple 4x4 matrix with four distinct quadrants
        System.out.println("=== Test Case 1: Simple 4x4 matrix ===");
        Integer[][] testMatrix1 = {
            {1, 1, 2, 2},
            {1, 1, 2, 2},
            {3, 3, 4, 4},
            {3, 3, 4, 4}
        };

        testQuadTree(testMatrix1, "Simple 4x4 Matrix");

        // Test Case 2: Complex 4x4 matrix with some variation
        System.out.println("\n=== Test Case 2: Complex 4x4 matrix ===");
        Integer[][] testMatrix2 = {
            {1, 1, 2, 2},
            {1, 3, 2, 2},
            {3, 3, 4, 4},
            {3, 3, 4, 5}
        };

        testQuadTree(testMatrix2, "Complex 4x4 Matrix");

        // Test Case 3: 4x4 matrix with all the same values (should result in a single leaf node)
        System.out.println("\n=== Test Case 3: Uniform 4x4 matrix ===");
        Integer[][] testMatrix3 = {
            {7, 7, 7, 7},
            {7, 7, 7, 7},
            {7, 7, 7, 7},
            {7, 7, 7, 7}
        };

        testQuadTree(testMatrix3, "Uniform 4x4 Matrix");

        // Test Case 4: 8x8 matrix with distinct quadrants
        System.out.println("\n=== Test Case 4: 8x8 matrix ===");
        Integer[][] testMatrix4 = {
            {1, 1, 1, 1, 2, 2, 2, 2},
            {1, 1, 1, 1, 2, 2, 2, 2},
            {1, 1, 1, 1, 2, 2, 2, 2},
            {1, 1, 1, 1, 2, 2, 2, 2},
            {3, 3, 3, 3, 4, 4, 4, 4},
            {3, 3, 3, 3, 4, 4, 4, 4},
            {3, 3, 3, 3, 4, 4, 4, 4},
            {3, 3, 3, 3, 4, 4, 4, 4}
        };

        testQuadTree(testMatrix4, "8x8 Matrix");

        // Test Case 5: 4x4 matrix with all different values (should result in a full tree)
        System.out.println("\n=== Test Case 5: All different values 4x4 matrix ===");
        Integer[][] testMatrix5 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        };

        testQuadTree(testMatrix5, "All Different Values 4x4 Matrix");
    }

    private static void testQuadTree(Integer[][] matrix, String testName) {
        System.out.println("Original " + testName + ":");
        printMatrix(matrix);

        // Create a QuadTree from the matrix
        QuadTree quadTree = new QuadTree(matrix);

        // Convert the QuadTree back to a matrix
        Integer[][] resultMatrix = quadTree.toMatrix();

        System.out.println("\nResult " + testName + ":");
        printMatrix(resultMatrix);

        // Check if the result matches the original
        boolean matches = matricesEqual(matrix, resultMatrix);
        System.out.println("\n" + testName + " matrices match: " + matches);
    }

    private static void printMatrix(Integer[][] matrix) {
        for (Integer[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    private static boolean matricesEqual(Integer[][] matrix1, Integer[][] matrix2) {
        if (matrix1.length != matrix2.length) {
            return false;
        }

        for (int i = 0; i < matrix1.length; i++) {
            if (matrix1[i].length != matrix2[i].length) {
                return false;
            }

            for (int j = 0; j < matrix1[i].length; j++) {
                if (!matrix1[i][j].equals(matrix2[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }
}
