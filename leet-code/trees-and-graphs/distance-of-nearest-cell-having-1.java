// https://www.geeksforgeeks.org/problems/distance-of-nearest-cell-having-1-1587115620/1

class Solution {
    public ArrayList<ArrayList<Integer>> nearest(int[][] grid) {
        // 1. Never move diagonally from a node as the distance will be 2 and not 1
        // so diagonal nodes are not at level 1 but at level 2, so you can only move
        // in 4 directions
        // 2. all the intial zero cells are at 0 level of BFS
        // 3. I process all of them and determine nearest 1 distance for 
        // each of their neighbors
        // 4. if I'm using BFS, it will never happen that an already visited node,
        // will get a lower distance once it has been assigned a distance once
        int m = grid.length;
        int n = grid[0].length;
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for(int i=0;i<m;i++){
            result.add(new ArrayList<>());
        }
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                result.get(i).add(-1);
            }
        }
        Queue<List<Integer>> q = new ArrayDeque<>();
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j] == 1){
                    result.get(i).set(j, 0);
                    q.add(List.of(i, j, 0));
                }
            }
        }
        while(!q.isEmpty()){
            List<Integer> curr = q.poll();
            int i = curr.get(0);
            int j = curr.get(1);
            int currDistance = curr.get(2);
            int[][] ijs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for(int[] ij : ijs){
                int nextI = i + ij[0];
                int nextJ = j + ij[1];
                if(nextI>=0 && nextI<m && nextJ>=0 && nextJ<n && result.get(nextI).get(nextJ) == -1){
                    result.get(nextI).set(nextJ, currDistance + 1);
                    q.add(List.of(nextI, nextJ, currDistance + 1));
                }
            }
        }
        return result;
    }
}


// DFS BASED APPROACH - WRONG WAY
// YOU RECURSE BACK INTO  CELLS ALREADY VISITED, NO STOPPING CONDITION
/**
Why even maintaing a prevNode paramter won't help?
Suppose you’re at (i, j) and you don’t recurse back into the cell you came from.
That prevents the trivial “ping-pong” recursion between two neighbors.
But DFS can still reach (i, j) again through a different path (e.g., (i, j) → (i+1, j) → (i+1, j+1) → (i, j+1) → (i, j)), creating a cycle.
Without a proper visited set or memoization of minimum distance, DFS will keep recursing indefinitely.
*/
class Solution {
    /**
     * 1. If grid[i][j] == 1, => ans=0
     * 2. If grid[i][j] == 0
     *   -> I've look at the 8 adjacent cells to (i,j) where value is 1
     *   -> out of those options, choose the one with minimum distance
     *   -> it might happen that there is no 1 among those 8 cells
     *   -> then I've look at the next nearest cells for potential 1s
     *   -> actually I should not look at diagonal option as they are at distance 2
     *   -> ans(i,j) = 1 + Math.min(ans(i-1, j), ans(i, j-1), ans(i, j+1), ans(i+1, j))
     *   -> so I see a recursion pattern
    */
    public ArrayList<ArrayList<Integer>> nearest(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] result = new int[m][n]; // similar to visited
        for(int[] res: result){
            Arrays.fill(res, -1);
        }
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j] == 1){
                    result[i][j] = 0;
                    continue;
                }
                if(result[i][j] == -1){
                    result[i][j] = dfs(grid, i, j, result);
                }
            }
        }
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for(int i=0;i<m;i++){
            ArrayList<Integer> temp = new ArrayList<>();
            for(int j=0;j<n;j++){
                temp.add(result[i][j]);
            }
            res.add(temp);
        }
        return res;
        
    }
    
    private int dfs(int[][] grid, int i, int j, int[][] result){
        if(grid[i][j] == 1){
            return result[i][j]=0;
        }
        int m = grid.length;
        int n = grid[0].length;
        int ans = Integer.MAX_VALUE;
        int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        for(int k=0;k<4;k++){
            int nextI = i + next[k][0];
            int nextJ = j + next[k][1];
            if(nextI>=0 && nextI<m && nextJ>=0 && nextJ<n){
                if(result[nextI][nextJ] != -1){
                    ans = Math.min(ans, result[nextI][nextJ]);
                }else{
                    ans = Math.min(ans, dfs(grid, nextI, nextJ, result));
                }
            }
        }
        return result[i][j] = ans;
    }
}
