import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.QGram;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.tuple.Pair;

import java.text.Normalizer;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ECommerce {
    private final HashMap<String, MyPair<Double, String>> productos = new HashMap<>();
    private final String productsPath = "src/main/resources/product.txt";
    private final Soundex soundex = new Soundex();
    private final Levenshtein levenshtein = new Levenshtein();
    private final QGram qGram = new QGram(3);
    private final Metaphone metaphone = new Metaphone();

    public ECommerce() {
        cargarProductos();
    }

    private void cargarProductos() {
        try (BufferedReader br = new BufferedReader(new FileReader(productsPath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                productos.put(linea.trim(), new MyPair<Double, String>(0.0, "")); // <producto, distancia>()
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private boolean existeExacto(String producto) {
        return productos.containsKey(producto);
    }

    public String normalizarProducto(String input) {
        // Convertir a minúsculas
        input = input.toLowerCase();

        // Reemplazar múltiples espacios por uno solo
        input = input.trim().replaceAll("\\s+", " ");

        // Normalizar caracteres (eliminar acentos)
        input = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "") // Quita los caracteres diacríticos (acentos)
                .replace("ñ", "n"); // Reemplaza 'ñ' por 'n'

        return input;
    }

    private MyPair<Double, String> maxWithAlgorithm(double d1, String name1,
                                                    double d2, String name2,
                                                    double d3, String name3,
                                                    double d4, String name4) {
        if (d1 >= d2 && d1 >= d3 && d1 >= d4) return new MyPair<>(d1, name1);
        if (d2 >= d1 && d2 >= d3 && d2 >= d4) return new MyPair<>(d2, name2);
        if (d3 >= d1 && d3 >= d2 && d3 >= d4) return new MyPair<>(d3, name3);
        return new MyPair<>(d4, name4);
    }

    public void findProduct(String input) throws EncoderException {
        if (existeExacto(input)) {
            System.out.println("El producto '" + input + "' existe en el inventario.");
            return;
        } else {
            System.out.println("El producto '" + input + "' no existe en el inventario. Se muestran las 5 opciones mas parecidas:");
        }

        String producto = normalizarProducto(input);
        Map<String, MyPair<Double, String>> productosOrdenados = new HashMap<>();

        for(String p : productos.keySet()){
            double soundexDistance = soundex.difference(producto, p);
            double levenshteinDistance = levenshtein.distance(producto, p);
            double QGramDistance = qGram.distance(producto, p);
            double metaphoneDistance = levenshtein.distance(metaphone.encode(producto), metaphone.encode(p));

            // Encuentra la distancia máxima y el nombre del algoritmo ganador
            MyPair<Double, String> bestMatch = maxWithAlgorithm(
                    soundexDistance, "Soundex",
                    levenshteinDistance, "Levenshtein",
                    QGramDistance, "QGram",
                    metaphoneDistance, "Metaphone"
            );

            productosOrdenados.put(p, bestMatch);
        }

        ArrayList<Map.Entry<String, MyPair<Double, String>>> listaOrdenada = new ArrayList<>(productosOrdenados.entrySet());
        listaOrdenada.sort(Map.Entry.comparingByValue());

        for (int i = 0; i < 5; i++) {
            System.out.println("Producto: " + listaOrdenada.get(i).getKey() +  ", Similaridad: " + listaOrdenada.get(i).getValue().getKey() + ", Algoritmo: " + listaOrdenada.get(i).getValue().getValue());
        }
    }



}
