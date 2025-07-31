package P2.ej1;


public interface NodeTreeInterface<T extends Comparable<? super T>> {

	T getData();

	NodeTreeInterface<T> getLeft();

	NodeTreeInterface<T> getRight();

}