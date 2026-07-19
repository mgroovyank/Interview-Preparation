// https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/
class Solution {
    /**
    1. From each city, find the distance of all other cities. Some cities might not
       be visitable from a particular city.
    2. We should try to use shortest distance between any two cities
       so that we don't miss due to distance being greater than distanceThreshold   
    3. we can use floyd warshall algorithm, or use djikstra's algorithm
       to find shortest distance as there are no negative distances.   
     */
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] distance = new int[n][n];
        for(int[] d: distance){
            Arrays.fill(d, Integer.MAX_VALUE);
        }
        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            distance[u][v] = w;
            distance[v][u] = w;
        }

        for(int k=0; k<n; k++){
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(i == j){
                        distance[i][j] = 0;
                        continue;
                    }
                    if(distance[i][k] != Integer.MAX_VALUE && distance[k][j] != Integer.MAX_VALUE){
                        distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                    }
                }
            }
        }

        int resultCity = -1;
        int resultCount = Integer.MAX_VALUE;
        
        for(int i=0;i<n;i++){
            int currCount = 0;
            for(int j=0;j<n;j++){
                if(distance[i][j] <= distanceThreshold){
                    currCount++;
                }
            }
            if(currCount <= resultCount){
                resultCount = currCount;
                resultCity = i;
            }
        }
        return resultCity;
    }
}
