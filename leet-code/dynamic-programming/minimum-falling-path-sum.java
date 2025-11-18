// https://leetcode.com/problems/minimum-falling-path-sum

// Time Complexity: O(3^(n*n))
// Space Complexity: O(n)
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int ans = Integer.MAX_VALUE;
        for(int j=0;j<n;j++){
            ans = Math.min(ans, minFallingPathSumFromIj(matrix, 0, j));
        }
        return ans;
    }

    private int minFallingPathSumFromIj(int[][] matrix, int i, int j){
        // base case
        if(j>=matrix.length || j<0){
            return Integer.MAX_VALUE;
        }
        if(i == matrix.length-1){
            return matrix[matrix.length-1][j];
        }
        return matrix[i][j] + 
            Math.min(minFallingPathSumFromIj(matrix, i+1, j-1), 
                Math.min(
                    minFallingPathSumFromIj(matrix, i+1, j), 
                    minFallingPathSumFromIj(matrix, i+1, j+1)
                )
            );
    }
}

// DP

class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int ans = Integer.MAX_VALUE;
        int[][] dp = new int[n][n];
        for(int[] d: dp){
            Arrays.fill(d, Integer.MAX_VALUE);
        }
        for(int j=0;j<n;j++){
            ans = Math.min(ans, minFallingPathSumFromIj(matrix, 0, j, dp));
        }
        return ans;
    }

    private int minFallingPathSumFromIj(int[][] matrix, int i, int j, int[][] dp){
        // base case
        if(j>=matrix.length || j<0){
            return Integer.MAX_VALUE;
        }
        if(i == matrix.length-1){
            return matrix[matrix.length-1][j];
        }
        if(dp[i][j] != Integer.MAX_VALUE){
            return dp[i][j];
        }
        return dp[i][j] = matrix[i][j] + 
            Math.min(minFallingPathSumFromIj(matrix, i+1, j-1, dp), 
                Math.min(
                    minFallingPathSumFromIj(matrix, i+1, j, dp), 
                    minFallingPathSumFromIj(matrix, i+1, j+1, dp)
                )
            );
    }
}
