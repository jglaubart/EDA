public class Soundex {
    private static final char[] MAP = {'0','1','2','3','0','1','2','0','0','2','2','4','5','5','0','1','2','6','2','3','0','1','0','2','0','2'};

    public String encode(String s){
        char[] x = s.toUpperCase().toCharArray();
        char first = x[0];

        for(int i=0; i<x.length; i++){
            x[i] = MAP[x[i] - 'A'];
        }

        StringBuilder ans = new StringBuilder("" + first);
        for(int i=1; i<x.length; i++){
            if(x[i] != x[i-1] && x[i]!= '0'){
                ans.append(x[i]);
            }
        }

        ans.append("0000");
        return ans.substring(0, 4);
    }

    public double calculateSimilarity(String s1, String s2){
        String c1 = encode(s1);
        String c2 = encode(s2);

        double count = 0;
        int length = Math.min(s1.length(), s2.length());
        for(int i=0; i<length; i++){
            if (c1.charAt(i) == c2.charAt(i)) count++;
        }

        return count / length;
    }
}
