package C2_2023;

public class QuadTree {private QTNode root;

    public QuadTree( Integer[][] matrix ){
        if (!checkDimIsSquareAndEven(matrix) )
            throw new RuntimeException("Invalid Dim");
        buildTree(matrix);
    }
    private void buildTree(Integer[][] matrix) {
        root = buildTreeRecursive(matrix);
    }

    private QTNode buildTreeRecursive(Integer[][] matrix) {
        QTNode node = new QTNode();
        node.dimension = matrix.length;

        boolean allSame = true;
        Integer firstValue = matrix[0][0];

        for (int i = 0; i < matrix.length && allSame; i++) {
            for (int j = 0; j < matrix.length && allSame; j++) {
                if (!firstValue.equals(matrix[i][j])) {
                    allSame = false;
                }
            }
        }

        if (allSame) {
            node.data = firstValue;
            return node;
        }

        int newDim = matrix.length / 2;

        Integer[][] upperLeftMatrix = new Integer[newDim][newDim];
        Integer[][] upperRightMatrix = new Integer[newDim][newDim];
        Integer[][] lowerLeftMatrix = new Integer[newDim][newDim];
        Integer[][] lowerRightMatrix = new Integer[newDim][newDim];

        for (int i = 0; i < newDim; i++) {
            for (int j = 0; j < newDim; j++) {
                upperLeftMatrix[i][j] = matrix[i][j];
                upperRightMatrix[i][j] = matrix[i][j + newDim];
                lowerLeftMatrix[i][j] = matrix[i + newDim][j];
                lowerRightMatrix[i][j] = matrix[i + newDim][j + newDim];
            }
        }

        node.upperLeft = buildTreeRecursive(upperLeftMatrix);
        node.upperRight = buildTreeRecursive(upperRightMatrix);
        node.lowerLeft = buildTreeRecursive(lowerLeftMatrix);
        node.lowerRight = buildTreeRecursive(lowerRightMatrix);

        return node;
    }

    private boolean checkDimIsSquareAndEven(Integer[][] matrix){
        return matrix.length % 2 == 0 && matrix.length == matrix[0].length;
    }

    public Integer[][] toMatrix() {
        if (root == null) {
            return new Integer[0][0];
        }

        int dimension = root.getDim();
        Integer[][] matrix = new Integer[dimension][dimension];
        fillMatrix(matrix, root, 0, 0, dimension);
        return matrix;
    }

    private void fillMatrix(Integer[][] matrix, QTNode node, int rowStart, int colStart, int size) {
        if (node.data != null) {
            for (int i = rowStart; i < rowStart + size; i++) {
                for (int j = colStart; j < colStart + size; j++) {
                    matrix[i][j] = node.data;
                }
            }
        } else {
            int newSize = size / 2;

            fillMatrix(matrix, node.upperLeft, rowStart, colStart, newSize);

            fillMatrix(matrix, node.upperRight, rowStart, colStart + newSize, newSize);

            fillMatrix(matrix, node.lowerLeft, rowStart + newSize, colStart, newSize);

            fillMatrix(matrix, node.lowerRight, rowStart + newSize, colStart + newSize, newSize);
        }
    }

    private class QTNode{
        private Integer data;
        private int dimension;
        public int getDim(){ return dimension; }

        private QTNode upperLeft;
        private QTNode upperRight;
        private QTNode lowerLeft;
        private QTNode lowerRight;
    }
}
