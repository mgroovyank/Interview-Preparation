// https://leetcode.com/problems/number-of-islands/

// Time Complexity: Outer loop O(m*n) and for each m*n I call inner loop 4 times, but still each node
// is visited only once, so overall O(m*n)
// Space Complexity: O(m*n) - for grid/visited
class Solution {
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int result = 0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j] == '1'){
                    result++;
                    dfs(grid, i, j);
                }
            }
        }
        return result;
    }

    private void dfs(char[][] grid, int i, int j){
        grid[i][j] = '0'; // mark as visited
        int[][] dij = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for(int[] nij: dij){
            int nextI = i + nij[0];
            int nextJ = j + nij[1];
            if(nextI>=0 && nextI<grid.length && nextJ>=0 && nextJ<grid[0].length && grid[nextI][nextJ] == '1'){
                dfs(grid, nextI, nextJ);
            }
        }
    }
}
