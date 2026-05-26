// https://www.geeksforgeeks.org/problems/detect-cycle-in-a-directed-graph/1

// DFS Based approach
class Solution {
    /**
     * 1.  The same algorithm that we use for undirected graphs would not work here as,
     * when you encounter an already visited node, it can be the case that from that visited node,
     * you might not be able to go backwards as "the edge is directed", so the cycle is not completed.
     * 2. So, for cycle is really exist, the visited node should be on the same path.
     * 3. So we need a way to keep track if the already visited is on the same path as current path.
     * 4. So during DFS, each node that you visit, you assign it to current path(PATH ~ RECURSION PATH is also a way to look at it, also DIRECTION - current edge direction vs previous edge direction).
     * 5. When does the path change?
     *    -> Cycle is formed when you encounter a node twice on the same path.
     *    -> when you visit a node, you try to process its neighbors.
     *    -> when you are recursively travelling across nodes, when do you stop? 
     *     -> you stop 
     *        -> when the node either doesn't have any edges to travel. 
     *           -> then you just return back.
     *           -> when you return back from that, now you might again visit that node, but it will
     *              not be on the current path. => SO THIS IS WHERE THE PATH CHANGES ON A WHICH A NODE IS VISITED
     *        -> Or the neighbor nodes are already visited.
     *           -> for each node you need to check if it is visited as part of the current path
     *           -> if yes, then cycle exists.
     *           -> if no, then cycle doesn't exist. but still check all the neighbors.
     *           -> After processing all neighbors =>  SO THIS IS WHERE THE PATH CHANGES ON A WHICH A NODE IS VISITED
     * 6. At each already visited node, we just need to check if is visited on the current path or not.
     * 7. So we just need a flag for each visited node, if it is on the current path or not.
     * 8. So now apart from visited array, you need another array to keep track of this flag for each visited node.
     * 9. One more thing is that edges are directed, even if the graph is a single component, it might happen
     * from any starting node, you might not be able to travel all the nodes. 
     * So you need to try traversal from each unvisited node.
    */
    public boolean isCyclic(int V, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<V;i++){
            graph.add(new ArrayList<>());
        }
        
        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v); // directed edges
        }
        
        int[] visited = new int[V];
        int[] currentPathFlag = new int[V]; // 0 means not current path, 1 means on current path
        
        for(int i=0;i<V;i++){
            if(visited[i] == 0){
                visited[i] = 1;
                currentPathFlag[i] = 1;
                if(dfsAndCheckCycle(graph, i, visited, currentPathFlag)){
                    return true; // cycle found
                }
                // if no cycle, explore other path, current node will not be on current path now
                currentPathFlag[i] = 0;
            }
        }
        return false;
    }
    
    private boolean dfsAndCheckCycle(List<List<Integer>> graph, int currNode, int[] visited, int[] currentPathFlag){
        List<Integer> neighbors = graph.get(currNode);
        for(int neighbor: neighbors){
            // not already visited
            if(visited[neighbor] == 0){
                visited[neighbor] = 1;
                currentPathFlag[neighbor] = 1;
                if(dfsAndCheckCycle(graph, neighbor, visited, currentPathFlag)){ // cycle found
                    return true; 
                }
                // cycle not found - PASS
                currentPathFlag[neighbor] = 0;
            }else { // already visited
                // cycle found
                if(currentPathFlag[neighbor] == 1){
                    return true;
                }
                // cycle not found - not visited on current path
                // just ignore it. - PASS
            }
            
        }
        return false;
    }
}


// BFS BASED APPROACH - DOESN'T WORK
    class Solution {
    /**
     * BFS Based Approach
     * 1. When we try to do BFS, we visit nodes level by level.
     * 2. If we revisit an already visited node, in case of undirected graph, that's a cycle.
     * 3. But in case of a directed graph, it is not necessarily a cycle as edges are
     *    directed.
     * 4. So if we are doing BFS, in case we revisit a node at same level as it was previously
     *    visited, then that is not a cycle.
     * 5. This is because at same  L, if you think from coming from level L-1, then first
     *    time when we visited that node, the edge was from L-1 to L.
     * 6. Now next time, when we revisit it, the edge again is from L-1 to L. So
     *    both the edges are pointing towards each other. So there is no path for a cycle.
     * 7. But when we revisit a node at a level different from previously visited level,
     *    then that previously visited level is different smaller than current visit level,
     *    so from lower level we always have path to higher level nodes. That means there
     *    is a cycle.
     * 8 . THIS LOGIC DOESN'T WORK. Let's say you visit a node at level 2, then you are
     *     at level 4, from level 4 you are again able to revisit that node. But it can also happen
     *     that the node at level 2, might not even have an outgoing edge to level 3. So it
     *     can't again come to level 4 to form a cycle.
     * 
     * */
    public boolean isCyclic(int V, int[][] edges) {
        // code here
        
    }
}
