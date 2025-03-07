import org.joda.time.Instant;
import org.joda.time.Period;

public class MyJodaTimer {
    private final Instant start;
    private Instant end;
    private Period period;
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
        this.period = new Period(start, end);
        isStopped = true;
    }

    @Override
    public String toString() {
        if (!isStopped) {
            throw new RuntimeException("El timer no ha sido detenido.");
        }
        return String.format("(%d ms) %d dias, %d hs, %d min, %d s, %d ms", getTotalMillis(), getDays(), period.getHours(), period.getMinutes(), period.getSeconds(), period.getMillis());
    }

    private boolean isStopped() {
        return isStopped;
    }

    public long getTotalMillis(){
        return end.getMillis() - start.getMillis();
    }
    public long getDays(){
        return this.period.getDays();
    }
    public long getHours(){
        return this.period.getHours();
    }
    public long getMinutes(){
        return this.period.getMinutes();
    }
    public long getSeconds(){
        return this.period.getSeconds();
    }
    public long getMillis(){
        return this.period.getMillis();
    }

}
