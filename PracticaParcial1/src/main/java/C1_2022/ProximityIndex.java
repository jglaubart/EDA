package C1_2022;

public class ProximityIndex {
    private String[] elements;
    private int size = 0;

    public void initialize(String[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("elements no puede ser null");
        }
        
        for(int rec= 0; rec < elements.length-1; rec++) {
        	if (elements[rec].compareTo(elements[rec+1]) >= 0)
                throw new IllegalArgumentException("hay repetidos o no est� ordenado");   // sin repetidos y ordenado
        }
        
        this.elements = elements;
        this.size = elements.length;

     }


    public String search(String element, int distance) {
        int pos = binarySearch(element);
        if (pos == -1) {
            return null;
        }
        if(distance==0) return element;

        int circularPos = (pos + distance) % size;
        if (circularPos < 0) {
            circularPos += size;
        }
        return elements[circularPos];
    }

    private int binarySearch(String element) {
        int l = 0;
        int r = size - 1;

        while(l<=r){
            int mid = l +(r-l)/2;
            if (elements[mid].equals(element)) {
                return mid;
            } else if (elements[mid].compareTo(element) < 0) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return -1;
    }

   
}
