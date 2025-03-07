public class Proof {
    public static void main(String[] args) {
        long start = 10;
        MyJodaTimer t = new MyJodaTimer(start);
        t.stop(start + 1000000);
        System.out.println(t);
    }
}
