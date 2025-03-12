public class Performance {
    public static final int N = 20000000;


    public static void main(String[] args) {
        MyJodaTimer t2;
        try {
            t2= new MyJodaTimer();
            t2.stop();
        }
        catch(Exception e) {
        }

        int[] myArray = new int[N];
        int rta;

        // generate array
        for (int rec = N; rec > 0; rec--)
            myArray[N - rec] = rec;


        t2= new MyJodaTimer();
        rta = AlgoA.max(myArray);
        t2.stop();

        System.out.println(String.format("max Algo A %d. Delay %d (ms)", rta, t2.getTotalMillis()));

        // generate array
        for (int rec = N; rec > 0; rec--)
            myArray[N - rec] = rec;

        t2= new MyJodaTimer();
        rta = AlgoB.max(myArray);
        t2.stop();
        System.out.println(String.format("max Algo B %d. Delay %d (ms)", rta, t2.getTotalMillis()));
    }
}
