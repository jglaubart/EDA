public class Levenshtein {
    public static int distance(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int[][] dp = new int[m + 1][n + 1];

        // Relleno la primera columna y fila
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for(int i = 1; i <= m; i++) {
            for(int j = 1; j <= n; j++) {
               dp[i][j] = Math.min(
                       dp[i-1][j] + 1,
                       Math.min( dp[i][j-1] + 1, dp[i-1][j-1] + (s1[i-1] == s2[j-1] ? 0 : 1)
               ));
            }
        }

        return dp[m][n];
    }


    public static double normalizedSimilarity(String str1, String str2) {
        return 1 - (double) distance(str1, str2) / Math.max(str1.length(), str2.length());
    }
}
