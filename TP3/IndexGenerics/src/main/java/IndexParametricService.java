public interface IndexParametricService<T extends Comparable<? super T>>{
    // elements serán los valores del índice, los anteriores se descartan.
    // lanza excepction si elements is null y deja los valores anteriores.
    void initialize(T[] elements);

    // busca una key en el índice, O(log2 N)
    boolean search(T key);

    // inserta el key en pos correcta. Crece automáticamente de a chunks
    void insert(T key);

    // borra el key si lo hay, sino lo ignora.
    // decrece automáticamente de a chunks
    void delete(T key);

    // devuelve la cantidad de apariciones de la clave especificada
    int occurrences(T key);

    // devuelve un nuevo arreglo ordenado con los elementos que pertenecen al intervalo dado por
    // leftkey y rightkey.  Si el mismo es abierto/cerrado depende de las variables leftIncluded
    // y rightIncluded. True indica que es cerrado. Si no hay matching devuelve arreglo de length 0
    T[] range(T leftKey, T rightKey, boolean leftIncluded, boolean rightIncluded);

    // imprime el contenido del índice ordenado por su key.
    void sortedPrint();

    // devuelve el máximo elemento del índice. Lanza RuntimeException si no hay elementos
    T getMax();

    // devuelve el mínimo elemento del índice. Lanza RuntimeException si no hay elementos
    T getMin();
}
