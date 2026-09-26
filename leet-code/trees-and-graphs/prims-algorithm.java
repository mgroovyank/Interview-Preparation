// https://leetcode.com/problems/min-cost-to-connect-all-points/description/

// Time Complexity: O(N)
// Space Complexity: O(N)
class Solution {
    public int minCostConnectPoints(int[][] points) {
        int numberOfPoints = points.length;
        int[] visited = new int[numberOfPoints];
        PriorityQueue<PqEdge> pq = new PriorityQueue<>();
        pq.add(new PqEdge(0, 0, -1));
        int minCost = 0;
        while(!pq.isEmpty()){
            PqEdge p = pq.poll();
            int currPoint = p.currPoint;
            if(visited[currPoint] == 1){
                continue;
            }
            visited[currPoint] = 1;
            minCost += p.distance;
            int currX = points[currPoint][0];
            int currY = points[currPoint][1];
            for(int i=0;i<numberOfPoints;i++){
                if(visited[i] == 0){ // not visited point
                    int nextX = points[i][0];
                    int nextY = points[i][1];
                    int manDistance = Math.abs(nextX - currX) + Math.abs(nextY - currY);
                    pq.add(new PqEdge(manDistance, i, currPoint));
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
