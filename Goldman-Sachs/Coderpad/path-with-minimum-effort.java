// https://leetcode.com/problems/path-with-minimum-effort/

class Solution {
    public int minimumEffortPath(int[][] heights) {
        int rows = heights.length;
        int cols = heights[0].length;
        PriorityQueue<List<Integer>> pq = new PriorityQueue<>((a, b) -> a.get(2) - b.get(2));
        int[][] minEfforts = new int[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                minEfforts[i][j] = Integer.MAX_VALUE;
            }
        }
        minEfforts[0][0] = 0;
        pq.add(List.of(0, 0, 0));
        while(!pq.isEmpty()){
            List<Integer> currNode = pq.poll();
            int currRow = currNode.get(0);
            int currCol = currNode.get(1);
            int currEffort = currNode.get(2);
            int[][] nextCells = new int[][]{{-1,0},{0,1},{1,0},{0,-1}};
            for(int[] nextCell: nextCells){
                int nextRow = currRow + nextCell[0];
                int nextCol = currCol + nextCell[1];
                if(nextRow < rows && nextRow >=0 && nextCol<cols && nextCol>=0){
                    int nextEffort = Math.max(currEffort, Math.abs(heights[nextRow][nextCol] - heights[currRow][currCol]));
                    if(nextEffort < minEfforts[nextRow][nextCol]){
                        minEfforts[nextRow][nextCol] = nextEffort;
                        pq.add(List.of(nextRow, nextCol, nextEffort));
                    }
                }
            }
        }
        return minEfforts[rows-1][cols-1];
    }
}
