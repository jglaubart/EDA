import java.util.Scanner;


public class ExpTree implements ExpressionService {

	private Node root;

	public ExpTree() {
	    System.out.print("Introduzca la expresion en notacion infija con todos los parentesis y blancos: ");

		// token analyzer
	    Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
	    String line= inputScanner.nextLine();
	    inputScanner.close();

	    buildTree(line);
	}

	public ExpTree(String string) {
		buildTree(string);
	}

	private void buildTree(String line) {	
		  // space separator among tokens
		  Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
		  root= new Node(lineScanner);
		  lineScanner.close();
	}

	@Override
	public void preorder() {
		if(root==null)
			throw new IllegalStateException();
		System.out.println(root.preOrder(new StringBuilder()));
	}

	@Override
	public void inorder() {
		if(root==null)
			throw new IllegalStateException();
		System.out.println(root.inOrder(new StringBuilder()));
	}

	@Override
	public void postorder() {
		if(root==null)
			throw new IllegalStateException();
		System.out.println(root.postOrder(new StringBuilder()));
	}

	@Override
	public double eval() {
		return evalRec(root);
	}

	private double evalRec(Node node) {
		if (node.left == null || node.right == null)
			return Utils.getDoubleConstant(node.data);

		switch (node.data) {
			case "+" -> {
				return evalRec(node.left) + evalRec(node.right);
			}
			case "-" -> {
				return evalRec(node.left) - evalRec(node.right);
			}
			case "*" -> {
				return evalRec(node.left) * evalRec(node.right);
			}
			case "/" -> {
				return evalRec(node.left) / evalRec(node.right);
			}
			case "^" -> {
				return Math.pow(evalRec(node.left), evalRec(node.right));
			}
			default -> throw new IllegalArgumentException("Not a valid operand");
		}
	}

	@Override
	public String toString() {
		return root.toString();
	}


	static final class Node {
		private String data;
		private Node left, right;
		
		private Scanner lineScanner;

		public Node(Scanner theLineScanner) {
			lineScanner= theLineScanner;
			
			Node auxi = buildExpression();
			data = auxi.data;
			left = auxi.left;
			right = auxi.right;
			
			if (lineScanner.hasNext() ) 
				throw new RuntimeException("Bad expression");
		}
		
		private Node() 	{
		}


		private Node buildExpression() {
			Node ans = new Node();
			// 1) E -> ( E op E)
			if(lineScanner.hasNext("\\(")) {
				lineScanner.next();
				ans.left = buildExpression();

				//Operator
				if (!lineScanner.hasNext())
					throw new OperandException("Operands missing");

				ans.data = lineScanner.next();
				if (!Utils.isOperator(ans.data))
					throw new OperandException("Invalid operator");

				//Subexpression
				ans.right = buildExpression();

				// Busco )
				if (!lineScanner.hasNext("\\)")){
					lineScanner.next();
				} else {
					throw new IncorrectParenthesisException("Incorrect parenthesis");
				}

				return ans;
			}

			// 2) E -> cte
			if (!lineScanner.hasNext())
				throw new OperandException("Missing operand");

			ans.data = lineScanner.next();
			if (!Utils.isConstant(ans.data))
				throw new OperandException("Incorrect operand %s".formatted(ans.data));

			return ans;
		}

		private String preOrder(StringBuilder s){
			s.append(data).append(" ");
			if(left != null)
				left.preOrder(s);
			if(right!= null)
				right.preOrder(s);
			return s.toString();
		}

		private String inOrder(StringBuilder s){
			if(left != null) {
				s.append("( ");
				left.inOrder(s);
			}
			s.append(data).append(" ");
			if(right != null){
				right.inOrder(s);
				s.append(")");
			}
			return s.toString();
		}

		private String postOrder(StringBuilder s){
			if(left != null)
				left.postOrder(s);
			if(right!= null)
				right.postOrder(s);
			s.append(data).append(" ");
			return s.toString();
		}

		@Override
		public String toString(){
			StringBuilder buffer = new StringBuilder();
			print(buffer, "", "");
			return buffer.toString();
		}

		private void print(StringBuilder buffer, String prefix, String childrenPrefix){
			buffer.append(prefix);
			buffer.append(data);
			buffer.append('\n');
			if(left!=null)
				left.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
			if(right!=null)
				right.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
		}
	}  // end core.Node class

	
	
	// hasta que armen los testeos
	public static void main(String[] args) {
		ExpressionService myExp = new ExpTree();
		System.out.println(myExp);
		myExp.preorder();
		myExp.inorder();
		myExp.postorder();

		System.out.println("Second Tree Test: ");
		ExpressionService myExp2 = new ExpTree("(  (  2 + 3.5 ) * -10 )\n");
		System.out.println(myExp2.eval());
	
	}

}  // end ExpTree class
