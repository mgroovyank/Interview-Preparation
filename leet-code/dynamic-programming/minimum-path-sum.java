// Time Complexity: O(M*N*log(M*N))
// Space Complexity: O(M*N)
// Djikstra's Algorithm - Overkill for just right and down movements
class Solution {
    public int minPathSum(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] minSums = new int[rows][cols];
        Edge[][] prev = new Edge[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                minSums[i][j] = Integer.MAX_VALUE;
                prev[i][j] = new Edge(i, j, -1);
            }
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> a.minSum - b.minSum);
        minSums[0][0] = grid[0][0];
        pq.add(new Edge(0, 0, minSums[0][0]));
        while(!pq.isEmpty()){
            Edge currentNode = pq.poll();
            int currI = currentNode.i;
            int currJ = currentNode.j;
            int currSum = currentNode.minSum;
            int[][] neighbors = new int[][]{{0,1}, {1,0}};
            for(int i=0;i<2;i++){
                int nextI = currI + neighbors[i][0];
                int nextJ = currJ + neighbors[i][1];
                if(nextI<rows && nextJ<cols){
                    int nextSum = currSum + grid[nextI][nextJ];
                    if(nextSum < minSums[nextI][nextJ]){
                        minSums[nextI][nextJ] = nextSum;
                        prev[nextI][nextJ] = new Edge(currI, currJ, 0);
                        pq.add(new Edge(nextI, nextJ, nextSum));
                    }
                }
            } 
        }

        return minSums[rows-1][cols-1];


    }

    private class Edge {
        private int i;
        private int j;
        private int minSum;

        private Edge(int i, int j, int minSum){
            this.i = i;
            this.j = j;
            this.minSum = minSum;
        }
    }
}

// Recursion - Top Down + Memoization
class Solution {
    public int minPathSum(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] dp = new int[rows][cols];
        for(int i=0;i<rows;i++){
            Arrays.fill(dp[i], -1);
        }
        return minPathSumTo(grid, rows-1, cols-1, dp);
    }

    private int minPathSumTo(int[][] grid, int i, int j, int[][] dp){
        // base case
        if(i==0 && j==0){
            return grid[0][0];
        }
        if(i<0 || j<0){
            return Integer.MAX_VALUE;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        return dp[i][j] = grid[i][j] + Math.min(minPathSumTo(grid, i-1, j, dp), minPathSumTo(grid, i, j-1, dp));
    }
}

// Tabulation - Bottom Up
class Solution {
    public int minPathSum(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] dp = new int[rows][cols];
        dp[0][0] = grid[0][0];
        // go over all recursion states
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(i==0 && j==0){
                    dp[0][0] = grid[0][0];
                    continue;
                }
                int upCost = Integer.MAX_VALUE;
                int leftCost = Integer.MAX_VALUE;
                if(i-1>=0){
                    upCost = grid[i][j] + dp[i-1][j];
                }
                if(j-1>=0){
                    leftCost = grid[i][j] + dp[i][j-1];
                }
                dp[i][j] = Math.min(upCost, leftCost);
            }
        }
        return dp[rows-1][cols-1];
    }
}
