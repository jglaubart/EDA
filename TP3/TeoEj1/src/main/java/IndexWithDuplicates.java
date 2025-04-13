package eda;

import java.util.Arrays;

public class IndexWithDuplicates implements IndexService{
    private int[] indexData;
    private final int CHUNK = 20;
    private int size;
    private int cant;

    public IndexWithDuplicates() {
        indexData = new int[CHUNK];
        size = 0;
        cant = 0;
    }

    @Override
    public void initialize(int[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("Elements array cannot be null");
        }
        this.indexData = elements;
        this.size = elements.length;
        this.cant = elements.length;
        Arrays.sort(indexData);
    }

     @Override
    public boolean search(int key) {
        return indexData[getClosestPosition(key)] == key;
    }

    public int getClosestPosition(int key){
        int left = 0;
        int right = size - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (indexData[mid] == key) {
                return mid;
            }
            if (indexData[mid] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    @Override
    public void insert(int key) {
        int pos = QuickSort.partition(indexData, 0, size - 1, key);
        if(cant == size){
            indexData = Arrays.copyOf(indexData, indexData.length + CHUNK);
            size = indexData.length;
        }
        if (pos < cant) {
            System.arraycopy(indexData, pos, indexData, pos + 1, cant - pos);
        }
        indexData[pos] = key;
        cant++;
    }

    @Override
    public void delete(int key) {
        return;
    }

    @Override
    public int occurrences(int key) {
        return 0;
    }

    public int getMin(){
        if(cant == 0) throw new IllegalArgumentException("Index is empty");
        return indexData[0];
    }

    public int getMax(){
        if(cant == 0) throw new IllegalArgumentException("Index is empty");
        return indexData[size - 1];
    }

    public int[] range(int min, int max, boolean includeMin, boolean includeMax){
        if(cant == 0) throw new IllegalArgumentException("Index is empty");
        int minPos = getClosestPosition(min);
        int maxPos = getClosestPosition(max);
        if(includeMin) minPos = getClosestPosition(min) - 1;
        if(includeMax) maxPos = getClosestPosition(max) + 1;
        if(minPos == maxPos) return new int[0];
        return Arrays.copyOfRange(indexData, minPos, maxPos);
    }
}
