package ClosedHashing;

import java.util.function.Function;

public class ClosedHashingConColisiones<K, V> implements IndexParametricService<K, V> {
    final private int initialLookupSize= 10;
    final private double threshold = 0.75;
    private int size = 0;

    // estatica. No crece. Espacio suficiente...
    @SuppressWarnings({"unchecked"})
    private Slot<K,V>[] lookup= (Slot<K,V>[]) new Slot[initialLookupSize];
    private Function<? super K, Integer> prehash;

    public ClosedHashingConColisiones( Function<? super K, Integer> mappingFn) {
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
    public void insertOrUpdate(K key, V data) {
        if (key == null || data == null) {
            String msg= String.format("inserting or updating (%s,%s). ", key, data);
            if (key==null)
                msg+= "Key cannot be null. ";

            if (data==null)
                msg+= "Data cannot be null.";

            throw new IllegalArgumentException(msg);
        }
        if((double)(size+1)/lookup.length >= threshold)
            rehash();
        size++;

        int index = hash(key);
        int indexLogico = -1;
        int indexFinal = (index + lookup.length - 1) % lookup.length;

        while(lookup[index]!=null && !lookup[index].key.equals(key) && index!=indexFinal) {
            if(indexLogico==-1 && lookup[index].bajaLogica)
                indexLogico = index;
            index++;
            index = index % lookup.length;
        }

        if(indexLogico!=-1 && (lookup[index]==null || !lookup[index].key.equals(key)))
            lookup[indexLogico] = new Slot<>(key,data);
        else{
            if(lookup[index]!=null)
                size--;
            lookup[index] = new Slot<>(key, data);
        }
    }

    @SuppressWarnings({"unchecked"})
    private void rehash() {
        Slot<K,V>[] aux = lookup;
        lookup = (Slot<K,V>[]) new Slot[lookup.length*2];
        size = 0;
        for (Slot<K, V> kvSlot : aux) {
            if (kvSlot != null && !kvSlot.bajaLogica) //solo rehasheamos los que estan ocupados y sin baja logica
                insertOrUpdate(kvSlot.key, kvSlot.value);
        }
    }

    // find or get
    public V find(K key) {
        if (key == null)
            return null;

        int index = hash(key);
        int indexFinal = (index + lookup.length - 1) % lookup.length;
        while(index != indexFinal) {
            Slot<K,V> entry = lookup[index];
            if(entry == null)
                return null;
            if(entry.key.equals(key) && !entry.bajaLogica)
                return entry.value;
            index++;
            index = index % lookup.length;
        }

        return null;
    }

    public boolean remove(K key) {
        if (key == null)
            return false;

        int index = hash(key);
        int indexFinal = (index + lookup.length - 1) % lookup.length;
        while(index != indexFinal) {
            Slot<K,V> entry = lookup[index];
            if(entry != null && entry.key.equals(key)) {
                size--;
                if(lookup[(index+1) % lookup.length] == null)
                    lookup[index] = null;
                else
                    lookup[index].bajaLogica = true;
                return true;
            }
            index++;
            index = index % lookup.length;
        }
        return false;
    }

    public void dump()  {
        for(int rec= 0; rec < lookup.length; rec++) {
            if (lookup[rec] == null)
                System.out.println(String.format("slot %d is empty", rec));
            else
                System.out.println(String.format("slot %d contains %s bajaLogica= %s",rec, lookup[rec], lookup[rec].bajaLogica));
        }
    }

    public int size() {
        return size;
    }

    static private final class Slot<K, V>	{
        private final K key;
        private V value;
        private boolean bajaLogica = false;

        private Slot(K theKey, V theValue){
            key= theKey;
            value= theValue;
        }

        public String toString() {
            return String.format("(key=%s, value=%s)", key, value );
        }
    }
}
