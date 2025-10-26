// https://www.geeksforgeeks.org/problems/minimum-number-of-deletions-and-insertions0209/1

class Solution {
    public int minOperations(String s1, String s2) {
        // 1. Find LCS
        // 2. Deletions = if(length(s1) > length(s2)) => length(s1)-length(s2)
        // 3. Insertions = (length(s2) - LCS )*2
        // 4. total = s1 - s2 + 2s2 - 2*LCS = s1 + s2 - 2*LCS
        // 5. if(length(s2) > length(s2)) => deletions = (length(s1)-LCS)*2
        // 6. insertions = length(s2)-length(s1)
        // 7. total = 2*s1 - 2*LCS + s2 - s1 = s1+s2-2*LCS
        // both cases total = s1 + s2 - 2*LCS
        
        int l1 = s1.length();
        int l2 = s2.length();
        int[][] dp = new int[l1+1][l2+1];
        for(int i=0;i<=l1;i++){
            dp[i][0] = 0;
        }
        for(int j=0;j<=l2;j++){
            dp[0][j] = 0;
        }
        for(int i=1;i<=l1;i++){
            for(int j=1;j<=l2;j++){
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    dp[i][j] = 1 + dp[i-1][j-1];
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return l1 + l2 - 2*(dp[l1][l2]);
    }
}
