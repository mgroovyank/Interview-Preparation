// https://leetcode.com/problems/triangle/description/

// Time Complexity: O(2^(1+2+3+...rows)) = O(2^(N^2))
// Space Complexity: O(N) where N=number of rows
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        // Recursion - Top Down Approach
        // f(i,j)(minPathSum from i,j to last row) = triangle[i][j] + Min(f(i+1, j), f(i+1,j+1))
        int rows = triangle.size();
        return minimumTotalFrom(triangle, rows, 0, 0);
    }
    private int minimumTotalFrom(List<List<Integer>> triangle, int rows, int i, int j){
        // base case
        if(j >= rows){
            return Integer.MAX_VALUE;
        }
        if(i == rows-1){
            return triangle.get(i).get(j);
        }
        return triangle.get(i).get(j) + Math.min(
            minimumTotalFrom(triangle, rows, i+1, j), 
            minimumTotalFrom(triangle, rows, i+1, j+1)
            );
    }
}

// Time Complexity: O(N*N)
// Space Complexity: O(N) + O(N*N)
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        // Recursion - Top Down Approach
        // f(i,j)(minPathSum from i,j to last row) = triangle[i][j] + Min(f(i+1, j), f(i+1,j+1))
        // overlapping subproblems - memoization
        int rows = triangle.size();
        int[][] dp = new int[rows][rows];
        for(int i=0;i<rows;i++){
            Arrays.fill(dp[i], -1);
        }
        return minimumTotalFrom(triangle, rows, 0, 0, dp);
    }
    private int minimumTotalFrom(List<List<Integer>> triangle, int rows, int i, int j, int[][] dp){
        // base case
        if(j >= rows){
            return Integer.MAX_VALUE;
        }
        if(i == rows-1){
            return triangle.get(i).get(j);
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        return dp[i][j] = triangle.get(i).get(j) + Math.min(
            minimumTotalFrom(triangle, rows, i+1, j, dp), 
            minimumTotalFrom(triangle, rows, i+1, j+1, dp)
            );
    }
}

// Tabulation - Bottom Up
// Time Complexity: O(N^2)
// Space Complexity: O(N^2)
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        // Tabulation - Bottom Up
        int rows = triangle.size();
        int[][] dp = new int[rows][rows];

        for(int j=0;j<rows;j++){
            dp[rows-1][j] = triangle.get(rows-1).get(j);
        }
        for(int i=rows-2;i>=0;i--){
            for(int j=i;j>=0;j--){
                int dg = Integer.MAX_VALUE;
                if(j+1<=i+1){
                    dg = dp[i+1][j+1];
                }
                int d = dp[i+1][j];
                dp[i][j] = triangle.get(i).get(j) + Math.min(d, dg);
            }
        }
        return dp[0][0];
    }
}

