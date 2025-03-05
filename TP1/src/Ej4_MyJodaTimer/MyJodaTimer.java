package U1_Algoritmos.Ej4_MyJodaTimer;

import org.joda.time.Instant;
import org.joda.time.Period;

public class MyJodaTimer {
    private final Instant start;
    private Period period;

    public MyJodaTimer(){
        this.start = Instant.now();
    }

    public void stop(){
        this.period = new Period(start, Instant.now());
    }

    @Override
    public String toString(){
        return period.toString();
    }
}
