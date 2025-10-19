// https://leetcode.com/problems/unique-paths/

// Time Complexity: O(2^MN)
// Space Complexity: O(M+N) - length of longest Path
class Solution {
    public int uniquePaths(int m, int n) {
        // Recursion - Top Down Approach
        // f(i,j) = f(i-1, j) * 1 + f(i, j-1) * 1
        return uniquePathsTo(m-1, n-1);
    }

    private int uniquePathsTo(int i, int j){
        if(i==0 && j==0){
            return 1;
        }
        if(i<0 || j<0){
            return 0;
        }
        return uniquePathsTo(i-1, j) + uniquePathsTo(i, j-1);
    }
}


// Time Complexity: O(M*N*1)
// Space Complexity: O(M*N) + (M+N)
class Solution {
    public int uniquePaths(int m, int n) {
        // Recursion - Top Down Approach
        // f(i,j) = f(i-1, j) * 1 + f(i, j-1) * 1
        // dp on i,j - changing parameters(dynamic) - memoization
        int[][] dp = new int[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                dp[i][j] = -1;
            }
        }
        uniquePathsTo(m-1, n-1, dp);
        return dp[m-1][n-1];
    }

    private int uniquePathsTo(int i, int j, int[][] dp){
        if(i==0 && j==0){
            return dp[i][j]=1;
        }
        if(i<0 || j<0){
            return 0;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        dp[i][j] = (uniquePathsTo(i-1, j, dp) + uniquePathsTo(i, j-1, dp));
        return dp[i][j];
    }
}
