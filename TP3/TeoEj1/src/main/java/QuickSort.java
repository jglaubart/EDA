package eda;

public class QuickSort {
    public static void quickSort(int[] unsorted){
        quickSort(unsorted, unsorted.length-1);
    }

    public static void quickSort(int[] unsorted, int cant) {
        quickSortHelper(unsorted, 0, cant);
    }

    private static void swap(int[] unsorted, int left, int right) {
        int aux = unsorted[left];
        unsorted[left] = unsorted[right];
        unsorted[right] = aux;
    }

    public static int partition(int[] unsorted, int left, int right, int pivot) {
        while (left <= right) {
            while (unsorted[left] < pivot) {
                left++;
            }
            while (unsorted[right] > pivot) {
                right--;
            }
            if (left <= right) {
                swap(unsorted, left, right);
                left++;
                right--;
            }
        }
        return left;
    }
    private static void quickSortHelper(int[] unsorted, int left, int right) {
        if (right <= left) return;

        int pivot = unsorted[left];

        swap(unsorted, left, right);

        int pivotPosCalculated = partition(unsorted, left, right - 1, pivot);

        swap(unsorted, pivotPosCalculated, right);

        quickSortHelper(unsorted, left, pivotPosCalculated - 1);
        quickSortHelper(unsorted, pivotPosCalculated + 1, right);
    }
}
