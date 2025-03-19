import java.util.HashMap;
import java.util.Map;

public class QGram {
    private final int n;
    public QGram(int n) {
        this.n = n;
    }

    private HashMap<String, Integer> getTokens(String string){
        HashMap<String, Integer> tokens = new HashMap<>();
        StringBuilder complete = new StringBuilder();
        complete.append("#".repeat(Math.max(0, n - 1)));

        StringBuilder s = new StringBuilder();
        s.append(complete).append(string).append(complete);

        for(int i = 0; i < s.length() - n + 1; i++) {
            String token = s.substring(i, i+n);
            tokens.put(token, tokens.getOrDefault(token, 0) + 1);
        }

        return tokens;
    }

    public  void printTokens(String string) {
        HashMap<String, Integer> tokens = getTokens(string);
        for(Map.Entry<String, Integer> token : tokens.entrySet()) {
            System.out.println(token.getKey() + " " + token.getValue());
        }
    }

    public double similarity(String s1, String s2) {
        HashMap<String, Integer> tokens1 = getTokens(s1);
        HashMap<String, Integer> tokens2 = getTokens(s2);
        double intersection = 0;
        for( Map.Entry<String, Integer> entry : tokens1.entrySet()) {
            if(tokens2.containsKey(entry.getKey())) {
                intersection += Math.min(entry.getValue(), tokens2.get(entry.getKey()));
            }
        }

        int total = 0;
        for(int value : tokens1.values()) {
            total += value;
        }
        for(int value : tokens2.values()) {
            total += value;
        }

        return 2*intersection / total;

    }

}
