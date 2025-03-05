package U1_Algoritmos.Ej2_MyTimer;

public class MyTimer {
    private final long start;
    private long end;
    private boolean isStopped;

    public MyTimer(){this(System.currentTimeMillis());}
    public MyTimer(long start) {
        this.start = start;
        isStopped = false;
    }

    public void stop(){
        stop(System.currentTimeMillis());
    }
    public void stop(long stop) {
        if (isStopped) {
            throw new RuntimeException("El timer ya ha sido detenido.");
        }
        end = stop;
        if (end < start) {
            throw new RuntimeException("El valor de fin no puede ser menor que el de inicio.");
        }
        isStopped = true;
    }


    @Override
    public String toString() {
        if (!isStopped) {
            throw new RuntimeException("El timer no ha sido detenido.");
        }

        long durationMs = end - start;

        long days = durationMs / (1000 * 60 * 60 * 24);
        long hours = (durationMs / (1000 * 60 * 60)) % 24;
        long minutes = (durationMs / (1000 * 60)) % 60;
        long seconds = (durationMs / 1000) % 60;
        long milliseconds = durationMs % 1000;

        return String.format("(%d ms) %d día(s) %d hs %d min %.3f s", durationMs, days, hours, minutes,
                seconds + milliseconds / 1000.0);
    }

    private boolean isStopped() {
        return isStopped;
    }
}
