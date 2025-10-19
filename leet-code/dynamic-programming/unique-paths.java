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

// Time Complexity: O(M*N*1)
// Space Complexity: O(M*N)
class Solution {
    public int uniquePaths(int m, int n) {
        // Tabulation, Bottom Up Approach
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        // iteration on all states reached via recursion
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                // "do stuff" on indexes code
                if(i==0 && j==0){
                    dp[i][j] = 1;
                    continue;
                }
                int up = 0;
                int left = 0;
                if(i-1 >= 0){
                    up = dp[i-1][j];
                }
                if(j-1>=0){
                    left = dp[i][j-1];
                }
                dp[i][j] = up + left;
            }
        }
        return dp[m-1][n-1];
    }
}

// Time Complexity: O(M*N*1)
// Space Complexity: O(N*2)
class Solution {
    public int uniquePaths(int m, int n) {
        // Tabulation, Bottom Up Approach
        // Space Optimization
        int[] prev = new int[n];
        Arrays.fill(prev, 0);
        // iteration on all states reached via recursion
        for(int i=0;i<m;i++){
            int[] temp = new int[n];
            Arrays.fill(temp, -1);
            for(int j=0;j<n;j++){
                // "do stuff" on indexes code
                if(i==0 && j==0){
                    temp[j] = 1;
                    continue;
                }
                int up = 0;
                int left = 0;
                if(i-1 >= 0){
                    up = prev[j];
                }
                if(j-1>=0){
                    left = temp[j-1];
                }
                temp[j] = up + left;
            }
            prev = temp;
        }
        return prev[n-1];
    }
}
