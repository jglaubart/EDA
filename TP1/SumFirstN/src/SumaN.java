public class SumaN {
    public int sumaIter(int n){
        int ans = 0;
        for(int i=0; i<=n; i++){
            ans += i;
        }
        return ans;
    }

    public int sumaRec(int n){
        if(n == 0) return 0;
        return n + sumaRec(n-1);
    }
}
