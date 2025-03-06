package MyJodaTimer;

import org.joda.time.Instant;
import org.joda.time.Period;

public class MyJodaTimer {
    private final Instant start;
    private Instant end;
    private boolean isStopped;

    public MyJodaTimer(){this(Instant.now().getMillis());}
    public MyJodaTimer(long start) {
        this.start = new Instant(start);
        isStopped = false;
    }

    public void stop(){
        stop(Instant.now().getMillis());
    }
    public void stop(long stop) {
        if (isStopped) {
            throw new RuntimeException("El timer ya ha sido detenido.");
        }
        end = new Instant(stop);
        if (end.isBefore(start)) {
            throw new RuntimeException("El valor de fin no puede ser menor que el de inicio.");
        }
        isStopped = true;
    }

    @Override
    public String toString() {
        if (!isStopped) {
            throw new RuntimeException("El timer no ha sido detenido.");
        }
        long durationMs = end.getMillis() - start.getMillis();
        Period period = new Period(start, end);
        return String.format("(%d ms) %d dias, %d hs, %d min, %d s, %d ms", durationMs, period.getDays(), period.getHours(), period.getMinutes(), period.getSeconds(), period.getMillis());
    }

    private boolean isStopped() {
        return isStopped;
    }
}
