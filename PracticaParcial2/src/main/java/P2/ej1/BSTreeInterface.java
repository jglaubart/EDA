package P2.ej1;


public interface BSTreeInterface<T extends Comparable<? super T>> {

	NodeTreeInterface<T> getRoot();
	
	int getHeight();

}