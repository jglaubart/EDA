package ClosedHashing;

import java.util.function.Function;

public class ClosedHashingSinColisiones<K, V> implements IndexParametricService<K, V> {
    final private int initialLookupSize= 10;
    final private double threshold = 0.75;
    private int size = 0;
    private int colisiones = 0;

    // estática. No crece. Espacio suficiente...
    @SuppressWarnings({"unchecked"})
    private Slot<K,V>[] lookup= (Slot<K,V>[]) new Slot[initialLookupSize];

    private Function<? super K, Integer> prehash;

    public ClosedHashingSinColisiones(Function<? super K, Integer> mappingFn) {
        if (mappingFn == null)
            throw new RuntimeException("fn not provided");

        prehash= mappingFn;
    }

    // ajuste al tamaño de la tabla
    private int hash(K key) {
        if (key == null)
            throw new IllegalArgumentException("key cannot be null");

        return prehash.apply(key) % lookup.length;
    }

    private void rehash() {
        Slot<K,V>[] aux = lookup;
        lookup = (Slot<K,V>[]) new Slot[lookup.length*2];
        size = 0;
        for (Slot<K, V> kvSlot : aux) {
            if (kvSlot != null)
                insertOrUpdate(kvSlot.key, kvSlot.value);
        }
    }

    public boolean containsKey(K key) {
        if (key == null)
            throw new IllegalArgumentException();
        Slot<K,V> entry = lookup[hash(key)];

        return entry != null && entry.key.equals(key);
    }


    public void insertOrUpdate(K key, V data) {
        if (key == null || data == null) {
            String msg= String.format("inserting or updating (%s,%s). ", key, data);
            if (key==null)
                msg+= "Key cannot be null. ";

            if (data==null)
                msg+= "Data cannot be null.";

            throw new IllegalArgumentException(msg);
        }

        if(lookup[hash(key)]==null || containsKey(key)){
            lookup[hash(key)] = new Slot<K, V>(key, data);
            if(lookup[hash(key)] == null)
                size++;
            if((double)size/lookup.length >= threshold)
                rehash();
        }
        else{    //  No tira error por colisiones
            //throw new IllegalArgumentException();
            colisiones++;
        }
    }



    // find or get
    public V find(K key) {
        if (key == null)
            return null;

        Slot<K, V> entry = lookup[hash(key)];
        if (entry == null)
            return null;

        return entry.value;
    }

    public boolean remove(K key) {
        if (key == null)
            return false;

        // lo encontre?
        if (lookup[ hash( key) ] == null)
            return false;

        lookup[ hash( key) ] = null;
        return true;
    }


    public void dump()  {
        for(int rec= 0; rec < lookup.length; rec++) {
            if (lookup[rec] == null)
                System.out.println(String.format("slot %d is empty", rec));
            else
                System.out.println(String.format("slot %d contains %s",rec, lookup[rec]));
        }
    }


    public int getColisiones(){
        return colisiones;
    }
    public int size() {
        return size;
    }

    static private final class Slot<K, V>	{
        private final K key;
        private V value;

        private Slot(K theKey, V theValue){
            key= theKey;
            value= theValue;
        }
        public String toString() {
            return String.format("(key=%s, value=%s)", key, value );
        }
    }


    public static void main(String[] args) {
        ClosedHashingSinColisiones<Integer, String> myHash= new ClosedHashingSinColisiones<>(f->f);
        myHash.insertOrUpdate(55, "Ana");
        myHash.insertOrUpdate(44, "Juan");
        myHash.insertOrUpdate(18, "Paula");
        myHash.insertOrUpdate(19, "Lucas");
        myHash.insertOrUpdate(21, "Sol");
        System.out.println("Colisiones: " + myHash.getColisiones());
        System.out.println("Size: " + myHash.size());
        myHash.dump();

    }
}
