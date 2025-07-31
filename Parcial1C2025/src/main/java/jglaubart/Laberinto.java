package jglaubart;

public class Laberinto {

    public static boolean existeCamino(int[][] laberinto, int filaInicio, int columnaInicio, int filaFin, int columnaFin) {
        if (filaInicio < 0 || filaInicio >= laberinto.length ||
                columnaInicio < 0 || columnaInicio >= laberinto[0].length ||
                filaFin < 0 || filaFin >= laberinto.length ||
                columnaFin < 0 || columnaFin >= laberinto[0].length ||
                laberinto[filaInicio][columnaInicio] == 1 || laberinto[filaFin][columnaFin] == 1) {
            return false;
        }

        return buscarCamino(laberinto, filaInicio, columnaInicio, filaFin, columnaFin);
    }

    private static boolean buscarCamino(int[][] laberinto, int fila, int columna, int filaFin, int columnaFin) {
        if (fila == filaFin && columna == columnaFin) {
            return true;
        }

        if (fila < 0 || fila >= laberinto.length || columna < 0 || columna >= laberinto[0].length ||
                laberinto[fila][columna] != 0) {
            return false;
        }

        laberinto[fila][columna] = -1;


        boolean caminoEncontrado = buscarCamino(laberinto, fila - 1, columna, filaFin, columnaFin) || // Arriba
                buscarCamino(laberinto, fila + 1, columna, filaFin, columnaFin) || // Abajo
                buscarCamino(laberinto, fila, columna - 1, filaFin, columnaFin) || // Izquierda
                buscarCamino(laberinto, fila, columna + 1, filaFin, columnaFin);   // Derecha


        //laberinto[fila][columna] = 0;

        return caminoEncontrado;
    }

    public static void main(String[] args) {
        int[][] laberinto = {
                {0, 0, 1, 0},
                {1, 0, 1, 0},
                {0, 0, 0, 0},
                {0, 1, 1, 1}
        };

        boolean existe = existeCamino(laberinto, 0, 0, 3, 0);
        if (existe) {
            System.out.println("Existe un camino en el laberinto.");
        } else {
            System.out.println("No existe un camino en el laberinto.");
        }
        System.out.println("Caminos recorridos:");
        imprimirLaberinto(laberinto);

        int[][] laberintoSinSalida = {
                {0, 0, 1, 0},
                {1, 0, 1, 1},
                {0, 0, 0, 0},
                {0, 1, 1, 1}
        };
        boolean existeSinSalida = existeCamino(laberintoSinSalida, 0, 0, 0, 3);
        if (existeSinSalida) {
            System.out.println("Existe un camino en el laberinto sin salida (¡error!).");
        } else {
            System.out.println("No existe un camino en el laberinto sin salida.");
        }
        System.out.println("Caminos recorridos:");
        imprimirLaberinto(laberintoSinSalida);
    }

    public static void imprimirLaberinto(int[][] laberinto) {
        for (int[] fila : laberinto) {
            for (int celda : fila) {
                System.out.print(celda + " ");
            }
            System.out.println();
        }
    }
}
