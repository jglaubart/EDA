package MyJodaTimer;

public class MyJodaTimerTester {
    public static void main(String[] args) {
        MyJodaTimer t = new MyJodaTimer(2);
        t.stop(93623042);
        System.out.println(t);
    }
}
