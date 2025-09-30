// https://leetcode.com/problems/k-closest-points-to-origin/
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b)->{
            return distanceFromOrigin(points[b]) - distanceFromOrigin(points[a]);
        });
        int n = points.length;
        for(int i=0;i<n;i++){
            pq.add(i);
            if(pq.size() > k){
                pq.poll();
            }
        }
        int[][] closestPoints = new int[k][2];
        int i = 0;
        while(!pq.isEmpty()){
            closestPoints[i] = points[pq.poll()];
            i++;
        }
        return closestPoints;
    }

    private int distanceFromOrigin(int[] point){
        int x = point[0];
        int y = point[1];
        return x*x + y*y;
    }
}
