// https://leetcode.com/problems/number-of-enclaves/

// Time Complexity: O(m*n)
// Space Complexity: O(m*n)

class Solution {
    /**
    1. For any land cell on the boundary, if you can perform a DFS/BFS and mark each visited land cell 
    as sea cell.
    2. After doing this for all the boundary land cells, whatever land cells left, the count of them
    is your answer.
     */
    public int numEnclaves(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // boundary Os: first row, first column, last row, last column
        for(int j=0;j<n;j++){
            // first row
            if(grid[0][j] == 1){
                grid[0][j] = 0;
                dfs(grid, 0, j);
            }
            // last row
            if(grid[m-1][j] == 1){
                grid[m-1][j] = 0;
                dfs(grid, m-1, j);
            }
        }

        for(int i=0;i<m;i++){
            // first column
            if(grid[i][0] == 1){
                grid[i][0] = 0;
                dfs(grid, i, 0);
            }
            //last column
            if(grid[i][n-1] == 1){
                grid[i][n-1] = 0;
                dfs(grid, i, n-1);
            }
        }

        int count = 0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]==1){
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(int[][] grid, int i, int j){
        int m = grid.length;
        int n = grid[0].length;
        int[][] nextIjs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        for(int[] nextIj : nextIjs){
            int nextI = i + nextIj[0];
            int nextJ = j + nextIj[1];
            if(nextI>=0 && nextI<m && nextJ>=0 && nextJ<n && grid[nextI][nextJ]==1){
                grid[nextI][nextJ] = 0;
                dfs(grid, nextI, nextJ);
            }
        }
    }
}
