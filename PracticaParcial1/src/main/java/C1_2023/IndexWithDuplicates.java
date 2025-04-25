package C1_2023;

import java.util.Arrays;

public class IndexWithDuplicates<E extends Comparable<E> > {

    private final int chunk_size = 5;
    private E [] m_idx;
    private int m_size;

    @SuppressWarnings("unchecked")
    public IndexWithDuplicates(){
        m_idx = (E[]) new Comparable [chunk_size];
    }

    public void initialize(E[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("elements cannot be null");
        }
        for ( E e : elements )
            insert(e);
    }

    private void grow(){
        if (m_size < m_idx.length)
            return;
        m_idx = Arrays.copyOf(m_idx, m_idx.length + chunk_size );
    }

    public void insert(E key) {  //esta ordenado
        grow();

        int position = 0;
        for (position = 0; position < m_size && m_idx[position].compareTo( key ) < 0; ++position); // avanza hasta encontrar el lugar

        for (int i = m_size; i > position; --i)
            m_idx[i] = m_idx[i - 1];   // pasa todos los que quedan delante, un lugar hacie adelante

        m_idx[position] = key;
        ++m_size;
    }

    void repeatedValues( E[] values, SimpleLinkedList<E> repeatedLst, SimpleLinkedList<E> singleLst, SimpleLinkedList<E> notIndexedLst ) {
        checkNulls(values, repeatedLst, singleLst, notIndexedLst);
        for(E e : values){
            int pos = binarySearch(e);
            if(pos == -1){
                notIndexedLst.add(e);
            } else if((pos != 0 && m_idx[pos-1].equals(e)) || (pos != m_size-1 && m_idx[pos+1].equals(e))){
                repeatedLst.add(e);
            } else {
                singleLst.add(e);
            }
        }

    }

    void checkNulls( E[] values, SimpleLinkedList<E> repeatedLst, SimpleLinkedList<E> singleLst, SimpleLinkedList<E> notIndexedLst ){
        if(values == null)
            throw new IllegalArgumentException("values cannot be null");
        if(repeatedLst == null || singleLst == null || notIndexedLst == null)
            throw new IllegalArgumentException("lists cannot be null");
    }

    private int binarySearch(E elem){
        int l = 0;
        int r = m_size - 1;
        int ans = -1;

        while(l <= r){
            int mid = l + (r-l)/2;
            if(m_idx[mid].equals(elem)){
                ans = mid;
                l = mid + 1;
            }
            else if(m_idx[mid].compareTo(elem) < 0){
                l = mid + 1;
            } else { r = mid - 1;}
        }

        return ans;
    }


    public static void main(String[] args) {
        IndexWithDuplicates<Integer> idx = new IndexWithDuplicates<>();
        idx.initialize(  new Integer[] {100, 50, 30, 50, 80, 10, 100, 30, 20, 138} );   // ---> {10, 20, 30, 30, 50, 50, 80, 100, 100, 138}

        SimpleLinkedList<Integer> repeatedLst = new SimpleLinkedList();
        SimpleLinkedList<Integer> singleLst  = new SimpleLinkedList();
        SimpleLinkedList<Integer> notIndexedLst  = new SimpleLinkedList();
        idx.repeatedValues( new Integer[] { 100, 70, 40, 120, 33, 80, 10, 50 }, repeatedLst, singleLst, notIndexedLst );

        System.out.println("Repeated Values");
        repeatedLst.dump();

        System.out.println("Single Values");
        singleLst.dump();

        System.out.println("Non Indexed Values");
        notIndexedLst.dump();
    }


}
