import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Function;


public class BinaryTree<T> implements BinaryTreeService<T> {
	
	private Node<T> root;
	private final Scanner inputScanner;
	private int countTokens;

	public BinaryTree(String fileName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, FileNotFoundException {
		 InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

		 if (is == null)
			 throw new FileNotFoundException(fileName);
		 
		 inputScanner = new Scanner(is);
		 inputScanner.useDelimiter("\\s+");

		 buildTree();
		
		 inputScanner.close();
	}
	

	
	private void buildTree() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {	
	
		 Queue<NodeHelper<T>> pendingOps= new LinkedList<>();
		 String token;
		 
		 root= new Node<>();
		 pendingOps.add(new NodeHelper<>(root, (Node<T> n) -> (n)));
		 
		 while(inputScanner.hasNext()) {
			 
			 token= inputScanner.next();

			 NodeHelper<T> aPendingOp = pendingOps.remove();
			 Node<T> currentNode = aPendingOp.getNode();
			 
			 if ( token.equals("?") ) {
				 // no hace falta poner en null al L o R porque ya esta con null
				 
			 // reservar el espacio en Queue aunque NULL no tiene hijos para aparear
				pendingOps.add( new NodeHelper<>(null, (Node<T> n) -> (n)) );  // como si hubiera izq
				pendingOps.add( new NodeHelper<>(null, (Node<T> n) -> (n)) );  // como si hubiera der
			 }
			 else {
				 Function<Node<T>, Node<T>> anAction= aPendingOp.getAction();
				 currentNode = anAction.apply(currentNode);

				 currentNode.data = token;

				 //version lamda para izq
				 pendingOps.add(new NodeHelper<>(currentNode, (Node<T> n) -> (n.setLeftTree(new Node<>()))));
				 //version clase anonima para der
				 pendingOps.add(new NodeHelper<>(currentNode,
						 new Function<Node<T>, Node<T>>(){
							 @Override
							 public Node<T> apply(Node<T> n) {
								 return n.setRightTree(new Node<>());
							 }}
						 )
				 );
			 }
			 countTokens++;
		 }
			
		 if (root.data == null)  // no entre al ciclo jamas 
			 root = null;
		 
	}

	@Override
	public void preorder() {
		if(root == null)
			throw new IllegalStateException();
		System.out.println(root.preorder(new StringBuilder()));
	}

	@Override
	public void postorder() {
		if(root==null)
			throw new IllegalStateException();
		System.out.println(root.postorder(new StringBuilder()));
	}

	@Override
	public void printHierarchy(){
		printHierarchy("", root);
	}

	@Override
	public void printHierarchy(String initial, Node<T> current){
		//Si estamos en un null, imprimimos y no seguimos
		if(current == null){
			System.out.println(initial + "└── " + "null");
			return;
		}
		//Imprimimos el dato
		System.out.println(initial + "└── " + current.data);

		//Si no es hoja, seguimos
		if (!current.isLeaf()) {
			printHierarchy(initial + "    ", current.left);
			printHierarchy(initial + "    ", current.right);
		}
	}

	public String getTree(){
		//Si estamos en un null, imprimimos y no seguimos
		Queue<Node<T>> queue = new LinkedList<>();
		queue.add(root);
		StringBuilder sb = new StringBuilder();

		int count = countTokens;

		while (count != 0) {
			Node<T> current = queue.remove();
			if (current == null) {
				sb.append("?\t");
				queue.add(null);
				queue.add(null);
			} else {
				sb.append(current.data).append("\t");
				queue.add(current.left);
				queue.add(current.right);
			}
			count--;
		}
		return sb.toString();
	}

	public void toFile(String name) throws IOException {
		String path = "/Users/admin/Documents/ITBA/A2_2C/EDA/TP5_Trees/src/B_Binarios/resources/" + name;
		PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8);
		writer.print(getTree());
		writer.close();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof BinaryTree<?> BT))
			return false;
		return getTree().equals(BT.getTree());
	}

	public int getHeight(){
		if(root == null)
			return  -1;
		return getHeightRec(root, 0);
	}

	private int getHeightRec(Node<T> node, int height){
		if (node.isLeaf())
			return height;

		int heightLeft = 0;
		int heightRight = 0;

		if (node.left != null)
			heightLeft = getHeightRec(node.left, height + 1);
		if (node.right != null)
			heightRight = getHeightRec(node.right, height + 1);

		return Math.max(heightLeft, heightRight);
	}

	// hasta el get() no se evalua
    static class Node<T> {
		private String data;
		private Node<T> left;
		private Node<T> right;

		public Node<T> setLeftTree(Node<T> aNode) {
			left= aNode;
			return left;
		}

		public Node<T> setRightTree(Node<T> aNode) {
			right= aNode;
			return right;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (!(o instanceof Node<?> node))
				return false;
			return data.equals(node.data)
					&& (left==null && node.left==null || (left!=null && left.equals(node.left)))
					&& (right==null && node.right==null || (right!=null && right.equals(node.right)));
		}

		private String preorder(StringBuilder s) {
			s.append(data).append(" "); //listar
			if(left!=null)
				left.preorder(s); //preorder izq
			if(right!=null)
				right.preorder(s); //preorder der
			return s.toString();
		}

		private String postorder(StringBuilder s) {
			if(left!=null)
				left.postorder(s);
			if(right!=null)
				right.postorder(s);
			s.append(data).append(" ");
			return s.toString();
		}


		public Node() {
			// TODO Auto-generated constructor stub
		}

		private boolean isLeaf() {
			return left == null && right == null;
		}


	}  // end core.Node class

	
	static class NodeHelper<T> {
		
		//static enum Action { LEFT, RIGHT, CONSUMIR };

		
		private Node<T> aNode;
		//private Action anAction2;
		private Function<Node<T>, Node<T>> anAction;

		public NodeHelper(Node<T> aNode, Function<Node<T>, Node<T>> anAction ) {
			this.aNode= aNode;
			this.anAction= anAction;
		}

		public Node<T> getNode() {
			return aNode;
		}
		
		public Function<Node<T> ,Node<T>> getAction() {
			return anAction;
		}
		
	}
	

	
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		BinaryTreeService<String> rta = new BinaryTree<>("data0_1");
		rta.preorder();
		System.out.println();
		rta.postorder();
		rta.printHierarchy();
		rta.toFile("toFile_data01.txt");   //imprime el arbol en un archivo

			
	}

}  