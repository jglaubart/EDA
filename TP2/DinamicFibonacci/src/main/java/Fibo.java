import java.util.HashMap;

public class Fibo {
    private static final HashMap<Integer, Long> cache = new HashMap<>();

    public static long fibo(int n){
        if (cache.containsKey(n)) return cache.get(n);

        long ans;
        if(n <= 1) ans = 1;
        else ans = fibo(n-1) + fibo(n-2);

        cache.put(n, ans);
        return ans;
    }
}
