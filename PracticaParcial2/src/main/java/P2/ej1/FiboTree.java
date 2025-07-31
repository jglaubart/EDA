package P2.ej1;

import P2.ej1.*;


public class FiboTree implements BSTreeInterface<Float> {

	private Node root;


	public FiboTree(int N) {
		// COMPLETAR para ejer 1.1
		if(N<=0) throw new IllegalArgumentException("N debe ser mayor o igual a 0");
		this.root = buildTree(N);
	}

	private Node buildTree(int N) {
		if (N <= 1) {
			return new Node(Float.NaN);
		}
		Node node = new Node(Float.NaN);
		node.left = buildTree(N - 1);

		if (N - 2 >= 1) {
			node.right = buildTree(N - 2);
		}
		return node;
	}


	public FiboTree(int N, int lower) {
		this(N); // esto no cambiarlo. es la generacion de la forma, o sea 1.1
		int[] current = new int[1];
		current[0] = lower;
		assignInOrder(root, current);
	}

	private void assignInOrder(Node node, int[] current) {
		if (node == null) return;
		assignInOrder(node.left, current);
		node.data = (float) current[0];
		current[0]++;
		assignInOrder(node.right, current);
	}


	public NodeTreeInterface<Float> getRoot() {
		return root;
	}

	public int getHeight() {
		return getHeight(root);
	}

	private int getHeight(Node aNode) {
		if (aNode== null)
			return -1;

		return 1 + Math.max( getHeight(aNode.left), getHeight(aNode.right) );
	}

	class Node implements NodeTreeInterface<Float> {

		private Float data;
		private Node left;
		private Node right;

		public Node(Float myData) {
			this.data= myData;
		}


		public Float getData() {
			return data;
		}
		public NodeTreeInterface<Float> getLeft() {
			return left;
		}

		public NodeTreeInterface<Float> getRight() {
			return right;
		}

	}

}
