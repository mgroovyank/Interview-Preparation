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
