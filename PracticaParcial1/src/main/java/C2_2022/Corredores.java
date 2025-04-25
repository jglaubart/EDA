package C2_2022;

public class Corredores {

    public int[] tiemposEntre(int[] tiempos, Pedido[] p) {
        int[] ans = new int[p.length];

        for(int i=0; i< p.length; i++) {
            int desde = p[i].desde;
            int hasta = p[i].hasta;

            int start = binarySearch(tiempos, desde, true);
            int end = binarySearch(tiempos, hasta, false);

            if(start == -1 || end == -1 || start > end) {
                ans[i] = 0;
            } else {
                ans[i] = end - start + 1;
            }
        }

        return ans;
    }

    private int binarySearch(int[] tiempos, int desde, boolean searchFirst){
        int left = 0;
        int right = tiempos.length - 1;
        int ans = -1;
        
        while(left <= right) {
            int mid = left + (right - left)/2;

            if(tiempos[mid] == desde) {
                ans = mid;
                if(searchFirst) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else if(tiempos[mid] < desde) {
                left = mid + 1;
                if(!searchFirst) ans = mid;
            } else {
                right = mid - 1;
                if(searchFirst) ans = mid;
            }
        }

        return ans;
    }
    
    public static void main(String[] args) {
        Corredores c = new Corredores();

        Pedido[] pedidos = new Pedido[] {
                new Pedido(200, 240),
                new Pedido(180, 210),
                new Pedido(220, 280),
                new Pedido(0, 200),
                new Pedido(290, 10000)
        };

        int[] tiempos = new int[] {
                192,
                200,
                210,
                221,
                229,
                232,
                240,
                240,
                243,
                247,
                280,
                285
        };

        int [] respuestas = c.tiemposEntre(tiempos, pedidos);
        for(int i=0; i< respuestas.length; i++) {
            System.out.println(respuestas[i]);
        }

    }
}

class Pedido {
    public int desde;
    public int hasta;
    public Pedido(int desde, int hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }
}
