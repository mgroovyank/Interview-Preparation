// https://leetcode.com/problems/min-cost-to-connect-all-points/description/

// How Prim's algorithm ensures N-1 edges?
// We visit each node only once, when we visit a node, we visit from a parent - that node edge, that means we process a single
// incoming edge to each visited node. the first starting we arrive at directly without any parent-that node edge => total
// N-1 Edges

// Worst case, each node connection to other nodes = N * (N-1) edges
// Time Complexity: O(E*logE) + O(E*logE)
// Space Complexity: O(E)
class Solution {
    public int minCostConnectPoints(int[][] points) {
        int numberOfPoints = points.length;
        int[] visited = new int[numberOfPoints];
        PriorityQueue<PqEdge> pq = new PriorityQueue<>();
        pq.add(new PqEdge(0, 0, -1));
        int minCost = 0;
        while(!pq.isEmpty()){ //O(N*N) = O(E)
            PqEdge p = pq.poll(); // O(logE) - each edge removed once
            int currPoint = p.currPoint;
            if(visited[currPoint] == 1){
                continue;
            }
            visited[currPoint] = 1;
            minCost += p.distance;
            int currX = points[currPoint][0];
            int currY = points[currPoint][1];
            for(int i=0;i<numberOfPoints;i++){ // Each node marked visited exactly once => this executes N times for each node when visited = N*N times for
                // entire lifespan of the algorithm - O(N*N) = O(E)
                if(visited[i] == 0){ // not visited point
                    int nextX = points[i][0];
                    int nextY = points[i][1];
                    int manDistance = Math.abs(nextX - currX) + Math.abs(nextY - currY);
                    pq.add(new PqEdge(manDistance, i, currPoint)); // O(log(E))
                }
            }
        }     
        return minCost;
    }

    public class PqEdge implements Comparable<PqEdge>{
        int distance;
        int currPoint;
        int prevPoint;

        public PqEdge(int distance, int currPoint, int prevPoint){
            this.distance = distance;
            this.currPoint = currPoint;
            this.prevPoint = prevPoint;
        }

        public int compareTo(PqEdge a){
            return Integer.compare(this.distance, a.distance);
        }
    }
}
