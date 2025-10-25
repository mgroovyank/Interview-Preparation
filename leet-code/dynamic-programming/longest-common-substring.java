// https://www.geeksforgeeks.org/problems/longest-common-substring1452/1

// Time Complexity: O(M*N)
// Space Complexity: O(M*N)
class Solution {
    public int longestCommonSubstr(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m+1][n+1];
        for(int i=0;i<=m;i++){
            dp[i][0] = 0;
        }
        for(int j=0;j<=n;j++){
            dp[0][j] = 0;
        }
        int lcss = 0;
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    dp[i][j] = 1 + dp[i-1][j-1];
                    lcss = Math.max(lcss, dp[i][j]);
                }else{
                    dp[i][j] = 0;
                }
            }
        }
        return lcss;
    }
}
