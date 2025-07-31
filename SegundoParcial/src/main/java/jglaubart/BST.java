package jglaubart;

import java.util.ArrayList;
import java.util.Collections;


public class BST<T extends Comparable<? super T>> {

        private Node root;

        public void insert(T myData){
            if ( root == null ) {
                root = new Node( myData );
                return;
            }
            root.insert( myData );
        }

        private class Node {
            private T data;
            private Node left;
            private Node right;

            Node( T d ){ data = d; }

            public void insert(T myData){
                if ( data.compareTo(myData) >= 0 ){
                    if ( left == null )
                        left = new Node( myData );
                    else
                        left.insert( myData );
                } else {
                    if ( right == null )
                        right = new Node( myData );
                    else
                        right.insert( myData );
                }
            }
        }


    int getDiameter() {
        int[] max = new int[1];
        height(root, max);
        return max[0];
    }

    private int height(Node node, int[] max) {
        if (node == null) return -1;

        int left = height(node.left, max);
        int right = height(node.right, max);

        int localDiameter = left + right + 2;
        max[0] = Math.max(max[0], localDiameter);

        return 1 + Math.max(left, right);
    }


    ArrayList<T> getDiameterPath() {
        if (root == null) return new ArrayList<>();   //si el arbol esta vacio

        Result result = new Result();
        calculateDiameter(root, result);

        ArrayList<T> diameterPath = new ArrayList<>();

        Collections.reverse(result.rightPath);  //invierto paraque pueda seguir el orden del camino y no empiece de la hoja
        diameterPath.addAll(result.leftPath);   //camino izquierdo desde la hoja
        diameterPath.add(result.common.data);   //nodo comun
        diameterPath.addAll(result.rightPath);  // camino derecho desde arriba (para eso lo inverti)

        // No deberia ser necesario. Por si quedó al revés, lo arreglo para que siempre empiece con el menor
        if (diameterPath.get(0).compareTo(diameterPath.get(diameterPath.size() - 1)) > 0) {
            Collections.reverse(diameterPath);
        }

        return diameterPath;
    }

    private class Result {
        int maxDiameter = -1;
        Node common = null;
        ArrayList<T> leftPath = new ArrayList<>();
        ArrayList<T> rightPath = new ArrayList<>();
    }

    private int calculateDiameter(Node node, Result result) {
        if (node == null) return -1;

        ArrayList<T> leftPath = new ArrayList<>();
        ArrayList<T> rightPath = new ArrayList<>();

        int leftHeight = findDepth(node.left, leftPath);
        int rightHeight = findDepth(node.right, rightPath);

        int diameter = leftHeight + rightHeight + 2;
        if (diameter > result.maxDiameter) {
            result.maxDiameter = diameter;
            result.common = node;
            result.leftPath = new ArrayList<>(leftPath);
            result.rightPath = new ArrayList<>(rightPath);
        }

        calculateDiameter(node.left, result);
        calculateDiameter(node.right, result);

        return 1 + Math.max(leftHeight, rightHeight);
    }

    private int findDepth(Node node, ArrayList<T> path) {
        if (node == null) return -1;

        ArrayList<T> leftPath = new ArrayList<>();
        ArrayList<T> rightPath = new ArrayList<>();

        int leftDepth = findDepth(node.left, leftPath);
        int rightDepth = findDepth(node.right, rightPath);

        if (leftDepth > rightDepth) {
            path.addAll(leftPath);
        } else {
            path.addAll(rightPath);
        }

        path.add(node.data);
        return 1 + Math.max(leftDepth, rightDepth);
    }



    public static void test1(){
        BST<Integer> myTree = new BST<>();
        myTree.insert(50);
        myTree.insert(60);
        myTree.insert(80);
        myTree.insert(20);
        myTree.insert(70);
        myTree.insert(40);
        myTree.insert(44);
        myTree.insert(10);

        System.out.println( myTree.getDiameter() );

        ArrayList<Integer> path = myTree.getDiameterPath();
        System.out.println( "Path" );
        for( int i : path )
            System.out.println( i );
    }

    public static void test2(){
        BST<Integer> myTree = new BST<>();
        myTree.insert(50);
        myTree.insert(60);
        myTree.insert(80);
        myTree.insert(20);
        myTree.insert(40);
        myTree.insert(44);
        myTree.insert(10);
        myTree.insert(5);
        myTree.insert(8);
        myTree.insert(7);
        myTree.insert(42);
        myTree.insert(43);

        System.out.println( myTree.getDiameter() );

        ArrayList<Integer> path = myTree.getDiameterPath();
        System.out.println( "Path" );
        for( int i : path )
            System.out.println( i );
    }

    public static void main(String[] args) {
        System.out.println( "Test1" );
        test1();
        System.out.println( "\nTest2" );
        test2();
    }
}
