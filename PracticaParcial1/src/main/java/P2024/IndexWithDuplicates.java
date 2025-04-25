package P2024;

import java.util.Arrays;

/**
 * @author dpenaloza
 *
 */
public class IndexWithDuplicates  {

	final static private int chunksize= 5;

	private int[] indexedData;
	private int cantElems;
	
	
	public IndexWithDuplicates() {
		indexedData= new int[chunksize];
		cantElems= 0;
	}

	public void initialize(int[] unsortedElements) {

		if (unsortedElements == null)
			throw new RuntimeException("Problem: null data collection");

		indexedData = unsortedElements;
		Arrays.sort(indexedData);
		cantElems = indexedData.length;
	}


	public int[] getIndexedData() {
		return indexedData;
	}

	public void print() {
		System.out.print("[");
		for (int i : indexedData)
			System.out.print(i + " ") ;
		System.out.println("]");
		
	}

	public void merge(IndexWithDuplicates other) {
		if(other.cantElems == 0) return;
		if(cantElems == 0) {
			indexedData = Arrays.copyOf(other.indexedData, other.cantElems);
			cantElems = other.cantElems;
			return;
		}

		int[] aux = new int[cantElems + other.cantElems];
		int idxThis = 0;
		int idxOther = 0;
		int idxAux = 0;

		// Merge mientras tengamos elementos en ambos arrays
		while(idxThis < cantElems && idxOther < other.cantElems) {
			if(indexedData[idxThis] <= other.indexedData[idxOther]) {
				aux[idxAux++] = indexedData[idxThis++];
			} else {
				aux[idxAux++] = other.indexedData[idxOther++];
			}
		}

		// Copiar elementos restantes de this (si hay)
		while(idxThis < cantElems) {
			aux[idxAux++] = indexedData[idxThis++];
		}

		// Copiar elementos restantes de other (si hay)
		while(idxOther < other.cantElems) {
			aux[idxAux++] = other.indexedData[idxOther++];
		}

		indexedData = aux;
		cantElems = idxAux;
	}


}
