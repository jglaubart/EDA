package ar.edu.itba.EDA;
public class ExactSearch {
    public int indexOf(char[] query, char[] target) {
        int ans = -1;
        if(query.length > target.length) return ans;
        int j = 0;
        for(int i = 0; i < target.length && j < query.length; i++) {
            if(target[i] == query[j]){
                j++;
                ans = ans != -1 ? ans : i;
            } else{
                ans = -1;
                j = 0;
                if(target[i] == query[j]){
                    j++;
                    ans = i;
                };
            }
        }
        return j == query.length ? ans : -1;

    }
}
