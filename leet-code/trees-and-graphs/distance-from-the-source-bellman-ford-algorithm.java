// https://www.geeksforgeeks.org/problems/distance-from-the-source-bellman-ford-algorithm/1

// Time Complexity: O(V*E)
// Space Complexity: O(1)
class Solution {
    /**
     * Djikstra's Algorithm: to find shortest path from a source to target node.
     * -> Works only when edge weights are non-negative
     * -> Works for both directed and undirected graphs
     * 
     * Bellman-Ford Algorithm
     * -> to find shortest path from a source to target node
     * -> Works for both positive and negative edge weights
     * -> Works for directed graphs
     * -> Works for un-directed graphs but we need to replace the undirected edges with
     * corresponding directed edges.
     * 
     * -> Relax all edges sequentially N-1 times.
     * -> N-1 is applicable for worst case scenario when the edges are ordered
     *    for nodes from target to src fashion, such that edges from src node 
     *    are last in the sequence.
     * -> It is possible that edges are ordered in a better sequence and
     *    we find the shortest distance even before N-1 iterations.
     * -> And it is possible that if we stuck in a negative cycle, the distance found
     *    is not correct.
     * -> So in case of negative cycles, we can't tell the shortest path.
     * -> Also, to detect negative cycles we need to see if even after N-1 iterations
     *    the shortest path is decreasing for any node, that means we are stuck in 
     *    a negative cycle.
     * 
     */
    public int[] bellmanFord(int V, int[][] edges, int src) {
        int[] distance = new int[V];
        Arrays.fill(distance, (int)1e8);
        
        distance[src] = 0;
        for(int i=0; i<V-1; i++){
            for(int[] edge:edges){
                int u = edge[0];
                int v = edge[1];
                int w = edge[2];
                
                if(distance[u]!=1e8 && distance[u] + w < distance[v]){
                    distance[v] = distance[u] + w;
                }
            }
        }
        
        boolean isNegativeCycle = false;
        
        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            if(distance[u]!=1e8 && distance[u] + w < distance[v]){
                isNegativeCycle = true;
                return new int[]{-1};
            }
        }
        
        return distance;
    }
}
