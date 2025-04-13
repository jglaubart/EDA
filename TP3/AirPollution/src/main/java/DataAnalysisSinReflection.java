import java.io.*;
import java.util.HashMap;

import eda.IndexParametricService;
import eda.IndexWithDuplicates;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public class DataAnalysisSinReflection {
	private final String fileName= "co_1980_alabama.csv";
	private Iterable<CSVRecord> records;

	// Coleccion de valores
	HashMap<Long, CSVRecord> datos= new HashMap<>();

	// Indice sobre polucion
	IndexParametricService<IdxRecord<Double, Long>> indexPolucion = new IndexWithDuplicates<>();

	// Indice sobre polucion con reflection
//	    IndexParametricService<IdxRecord<Double, Long>> indexPolucion = new IndexWithDuplicates<>(IdxRecord.class);




    public DataAnalysisSinReflection() throws IOException {

	    // leemos el archivo

    	/*
       	// opcion 1:  probar con  / o sin barra
	    URL resource = DataAnalysis.class.getClassLoader().getResource("co_1980_alabama.csv");
	    Reader in = new FileReader(resource.getFile());
    	*/


    	/*
    	// opcion 2:  probar con  / o sin barra
        URL resource= DataAnalysis.class.getResource("/co_1980_alabama.csv");
   	    Reader in = new FileReader(resource.getFile());
    	*/

    	/*
    	// opcion 3: probar con / o sin barra
    	String fileName= "/co_1980_alabama.csv";
    	InputStream is = DataAnalysis.class.getClass().getResourceAsStream(fileName);
    	Reader in = new InputStreamReader(is);
    	*/

    	/*
  		// opcion 4
 		String fileName= "/co_1980_alabama.csv";
 		InputStream is = DataAnalysis.class.getResourceAsStream(fileName );
 		Reader in = new InputStreamReader(is);
    	 */


    	// opcion 5
   		InputStream is = DataAnalysisSinReflection.class.getClassLoader().getResourceAsStream(fileName);
   		Reader in = new InputStreamReader(is);

 		CSVFormat theCSV = CSVFormat.DEFAULT.builder()
   				.setHeader()
   				.setSkipHeaderRecord(true)
   			    .get();

		 this.records = theCSV.parse(in);

		 completarColecciones(in);

    }

	private void completarColecciones(Reader in) throws IOException {
		for (CSVRecord record : records) {
			// insertamos en la coleccionIdxRecord<Double, Long>IdxRecord<Double, Long>
			datos.put(record.getRecordNumber(), record);

			// insertamos lo minimo en el indice
			String value = record.get("daily_max_8_hour_co_concentration");   //Tomo lo que dice en esa columna
			long id = record.getRecordNumber();
			indexPolucion.insert(new IdxRecord<>( Double.valueOf(value), id ));
		}
		in.close();
	}

	private IdxRecord<Double, Long>[] getAll(){
		IdxRecord<Double, Long> min = new IdxRecord<>(indexPolucion.getMin().getKey());
		IdxRecord<Double, Long> max = new IdxRecord<>(indexPolucion.getMax().getKey());
		return indexPolucion.range(min, max, true, true);
	}

	private IdxRecord<Double, Long>[] getAll(double min, double max, boolean includeMin, boolean includeMax){
		IdxRecord<Double, Long> minRec = new IdxRecord<>(min);
		IdxRecord<Double, Long> maxRec = new IdxRecord<>(max);
		return indexPolucion.range(minRec, maxRec, includeMin, includeMax);
	}

	public double getPolutionAverage(){
		IdxRecord<Double, Long>[] all  = getAll();

		double sum = 0;
		for(IdxRecord<Double, Long> rec: all){
			sum += rec.getKey();
		}

		return sum / all.length;
	}

	public void printAscInfo(){
		indexPolucion.sortedPrint();
	}

	public boolean existedPol(double pol){
		return indexPolucion.search(new IdxRecord<>(pol));
	}

	public double getMinPol(){
		return indexPolucion.getMin().getKey();
	}

	public double getMaxPol(){
		return indexPolucion.getMax().getKey();
	}

	public void printMinPolInfo(){
		IdxRecord<Double, Long> min = new IdxRecord<>(indexPolucion.getMin().getKey());
		IdxRecord<Double, Long>[] all = indexPolucion.range(min, min, true, true);

		for(IdxRecord<Double, Long> rec: all){
			System.out.println(datos.get(rec.getRowID()));
		}
	}

	public void printMaxPolInfo(){
		IdxRecord<Double, Long> max = new IdxRecord<>(indexPolucion.getMax().getKey());
		IdxRecord<Double, Long>[] all = indexPolucion.range(max, max, true, true);

		for(IdxRecord<Double, Long> rec: all){
			System.out.println(datos.get(rec.getRowID()));
		}
	}

	public void getRangePol(double min, double max, boolean includeMin, boolean includeMax){
		IdxRecord<Double, Long>[] all = getAll(min, max, includeMin, includeMax);
		for(IdxRecord<Double, Long> rec: all){
			System.out.println(datos.get(rec.getRowID()));
		}

	}


	public static void main(String[] args) throws IOException {
		DataAnalysisSinReflection da = new DataAnalysisSinReflection();
		da.printMaxPolInfo();
	}
}
