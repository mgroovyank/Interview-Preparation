// https://www.geeksforgeeks.org/problems/implementing-floyd-warshall2042/1

// Time Complexity: O(V * V * V)
class Solution {
    /**
     * Floyd Warshall Algorithm
     * -> Works for negative edge weights
     * -> Works for both directed and undirected graph.
     * -> If the costing of a node to node (src=target) is negative, that means a negative cycle exists.
     * 
     * 
     * -> We use adjacency matrix representation of the graph.
     * -> Try to visit every source and target node combination
     *    via every other node.
     * -> At the end, you'll have shortest distance between each source and target node
     *    combination.
     * -> d[src][target] via node 'k' = d[src][k] + dist[k][target]
     **/
    public void floydWarshall(int[][] dist) {
        int n = dist.length;
        for(int intermediateNode=0;intermediateNode<n; intermediateNode++){
            for(int src=0; src<n; src++){
                for(int target=0; target<n; target++){
                    if(dist[src][intermediateNode] != (int)1e8 && 
                            dist[intermediateNode][target] != (int)1e8){
                                dist[src][target] = Math.min(dist[src][target], 
                        dist[src][intermediateNode] + dist[intermediateNode][target]);
                    }
                }
            }
        }
        
    }
}
