// https://leetcode.com/problems/path-with-minimum-effort/

// Time Complexity: O(ElogV)
// Space Complexity: O(n*m)
class Solution {
    // Recursion/DFS can definitely be used, but BFS will be more optimized with Djikstra's algorithm 
    // Djikstra's Algorithm is simply BFS optimized using a priority queue, given it handles edge weights
    public int minimumEffortPath(int[][] heights) {
        int n = heights.length;
        int m = heights[0].length;
        int[][] efforts = new int[n][m];
        for(int[] effort: efforts){
            Arrays.fill(effort, Integer.MAX_VALUE);
        }
        PriorityQueue<List<Integer>> pq = new PriorityQueue<>((l1, l2)-> Integer.compare(l1.get(2), l2.get(2)));
        pq.add(List.of(0, 0, 0));
        efforts[0][0] = 0;
        while(!pq.isEmpty()){
            List<Integer> curr = pq.poll();
            int currI = curr.get(0);
            int currJ = curr.get(1);
            int currMax = curr.get(2);
            // if I reach my destination cell, then on that path the effort = currMax.
            // on other paths from where I can reach the destination cell are in priority queue
            // they all have effort>=currMax. => this path itself is the most optimal path
            // to reach our destination cell. No need to explore other paths. 
            if(currI == n-1 && currJ==m-1){
                return currMax;
            }
            int[][] delta = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
            for(int[] del : delta){
                int nextI = currI + del[0];
                int nextJ = currJ + del[1];
                if(nextI>=0 && nextI<n && nextJ>=0 && nextJ<m){
                    int nextMax = Math.max(currMax, Math.abs(heights[nextI][nextJ] - heights[currI][currJ]));
                    if(nextMax<efforts[nextI][nextJ]){
                        efforts[nextI][nextJ] = nextMax;
                        pq.add(List.of(nextI, nextJ, nextMax));
                    }

                }    
            }
        }
        return 0;
    }
}
