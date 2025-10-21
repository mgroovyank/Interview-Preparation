// https://www.geeksforgeeks.org/problems/path-in-matrix3805/1
// Time Complexity: O(3^n*m)
// Space Complexity: O(n) - recursion stack
class Solution {
    public int maximumPath(int[][] mat) {
        // Top Down Approach - Recursion
        int n = mat.length;
        int m = mat[0].length;
        int maxi = Integer.MIN_VALUE;
        for(int j=0;j<m;j++){
            maxi = Math.max(maxi, maximumPathFrom(mat, 0, j));
        }
        return maxi;
    }
    
    private int maximumPathFrom(int[][] mat, int i, int j){
        if(j >= mat[0].length || j < 0){
            return Integer.MIN_VALUE;
        }
        if(i == mat.length - 1){
            return mat[i][j];
        }
        return mat[i][j] + Math.max(maximumPathFrom(mat, i+1, j-1),
            Math.max(maximumPathFrom(mat, i+1, j),
            maximumPathFrom(mat, i+1, j+1)));
    }
}

// Time Complexity: O(n*m)
// Space Complexity: O(n*m) + O(n)
class Solution {
    public int maximumPath(int[][] mat) {
        // Top Down Approach - Recursion
        // lot of overlapping subproblems - each in one starting cell
        int n = mat.length;
        int m = mat[0].length;
        int maxi = Integer.MIN_VALUE;
        int[][] dp = new int[n][m];
        for(int[] c: dp){
            Arrays.fill(c, -1);
        }
        for(int j=0;j<m;j++){
            maxi = Math.max(maxi, maximumPathFrom(mat, 0, j, dp));
        }
        return maxi;
    }
    
    private int maximumPathFrom(int[][] mat, int i, int j, int[][] dp){
        if(j >= mat[0].length || j < 0){
            return Integer.MIN_VALUE;
        }
        if(i == mat.length - 1){
            return mat[i][j];
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        return dp[i][j] = mat[i][j] + Math.max(maximumPathFrom(mat, i+1, j-1, dp),
            Math.max(maximumPathFrom(mat, i+1, j, dp),
            maximumPathFrom(mat, i+1, j+1, dp)));
    }
}
