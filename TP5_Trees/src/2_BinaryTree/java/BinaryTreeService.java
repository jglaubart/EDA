import java.io.IOException;

public interface BinaryTreeService<T> {
	
	void preorder();

	void postorder();

	void printHierarchy();

	void printHierarchy(String initial, BinaryTree.Node<T> current);

	void toFile(String name) throws IOException;
}