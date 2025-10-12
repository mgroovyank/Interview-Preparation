// https://leetcode.com/problems/shortest-path-in-binary-matrix/
// Djikstra's Algorithm
// Priority Queue is not required here distance remains same for all neighbor nodes. we can save on time complexity
// Time Complexity: O(N^2)

class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int numNodes = rows * cols;
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<numNodes;i++){
            graph.add(new ArrayList<>());
        }
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                int currNode = i*cols + j; // numberOfRowsTravelled*ColsInEachRow + Column in current Row
                if(grid[i][j] !=0 ){
                    continue;
                }
                int[][] nextCells = new int[][]{{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
                for(int k=0;k<8;k++){
                    int nextRow = i+nextCells[k][0];
                    int nextCol = j+nextCells[k][1];
                    if(nextRow <rows && nextRow>=0 && nextCol<cols && nextCol>=0 && (grid[nextRow][nextCol] == 0)){
                        // edge exists b/w currentCell and nextCell
                        int nextNode = nextRow*cols + nextCol;
                        graph.get(currNode).add(nextNode);
                        graph.get(nextNode).add(currNode);
                    }
                }
            }
        }

        // first in list is node and next is distance
        Deque<List<Integer>> pq = new ArrayDeque<>();
        int[] dist = new int[numNodes];
        Arrays.fill(dist, Integer.MAX_VALUE);
        if(grid[0][0] == 0){
            dist[0] = 0;
            pq.add(List.of(0, 0));
        }
        while(!pq.isEmpty()){
            List<Integer> currNode = pq.poll();
            for(int nextNode: graph.get(currNode.get(0))){
                int nextDistance = currNode.get(1) + 1;
                if(nextDistance < dist[nextNode]){
                    dist[nextNode] = nextDistance;
                    pq.add(List.of(nextNode, nextDistance));
                }
            }
        }
        if(dist[numNodes - 1] == Integer.MAX_VALUE){
            return -1;
        }
        return dist[numNodes - 1] + 1; // +1 for number of visited cells
    }
}
