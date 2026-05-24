// https://www.geeksforgeeks.org/problems/number-of-distinct-islands/1

// Time Complexity: O(m*n)[Outer loop] + O(m*n + 2*m*n*4/2)
// Space Complexity: O(m*n)(DFS recursion stack) + O(m*n)(Set)
class Solution {
    /**
     * 1. Two islands are equal when they are of same shape, can be superimposed on one another
     * without any rotation/reflection.
     * 2. Once you find an island, that would be made of certain cells.
     * 3. Another island which you find will also contain certain cells.
     * 4. If you sort the cells. then the first cell after sorting would be the 
     * top left cell of the island.
     * 5. You can normalize that top left cell w.r.t to (0,0) and then normalize other cells
     * which are part of the island by the same factor.
     * 6. To compare two islands you can normalize both of them in same manner and then make sure
     * number of cells is same and cell indexes at corresponding positions are also equal.
     * 7. You can also avoid sorting of the cells in an island, by ensuring that your traversal
     * is consistent in terms of direction you take to pick next cells. Consistently follow
     * a single approach BFS/DFS and consistent directions to pick next cells.
    */
    
    Set<List<List<Integer>>> distinctIslands = new HashSet<>();
    
    int countDistinctIslands(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j] == 1){
                    List<List<Integer>> islandCells = new ArrayList<>();
                    grid[i][j] = 0; // mark visited
                    islandCells.add(List.of(i-i, j-j)); // store normalized cell indexes
                    dfs(grid, i, j, islandCells, i, j);
                    distinctIslands.add(islandCells);
                }
            }
        }
        return distinctIslands.size();
        
    }
    
    private void dfs(int[][] grid, int i, int j, List<List<Integer>> islandCells, 
    int row0, int col0){
        
        int n = grid.length;
        int m = grid[0].length;
        
        int[][] nextIjs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        for(int[] nextIj: nextIjs){
            int nextI = i + nextIj[0];
            int nextJ = j + nextIj[1];
            if(nextI>=0 && nextI<n && nextJ>=0 && nextJ<m && grid[nextI][nextJ]==1){
                grid[nextI][nextJ] = 0;
                islandCells.add(List.of(nextI - row0, nextJ-col0));
                dfs(grid, nextI, nextJ, islandCells, row0, col0);// row0/col0 fixed for an island
            }
        }
    }
}
