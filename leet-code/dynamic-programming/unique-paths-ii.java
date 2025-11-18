// https://leetcode.com/problems/unique-paths-ii/
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // Recursion - Top Down Approach
        // f(i,j) = unique paths from (i,j) to reach m-1,n-1
        // f(i,j) = f(i-1, j) * 1 + f(i, j-1) * 1
        // overlapping subproblems - memoize recursion function over dynamic parametes
        if(obstacleGrid[0][0] == 1){
            return 0;
        }
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                dp[i][j] = -1;
            }
        }

        return uniquePathsTo(obstacleGrid, m-1, n-1, dp);
    }

    private int uniquePathsTo(int[][] obstacleGrid, int i, int j, int[][] dp){
        if(i==0 && j==0){
            return 1;
        }
        if(i<0 || j<0 || (obstacleGrid[i][j]==1)){
            return 0;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        return dp[i][j] = uniquePathsTo(obstacleGrid, i-1, j, dp) + uniquePathsTo(obstacleGrid, i, j-1, dp);
    }
}
