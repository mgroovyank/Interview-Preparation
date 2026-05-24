// https://leetcode.com/problems/is-graph-bipartite/

class Solution {
    /**
    1. When trying to find if a graph is biparitite, I do understands any linear graph with no cycles is always bipartite.
    2. Any non-linear graph which has a cycle with odd length is never bipartite.
    3. Any graph with even cycle lengths only will be partite. But let's say, the two even cycles are 
       connected by a linear nodes, then since cycles are good. And also linear graph is also always 
       bipartite, the entire graph will be bipartite

    Algorithm:
    1. Detect cycle in the graph
    2. Once you detect cycle, you need length of cycle.
    3. For length of cycle, for each node on the traversal path, you need to keep track of 
       each node's distance/number of nodes before that node.
    4. When you detect a cycle, the difference of this distance between the currentNode and the nextNode
       would be the length of cycle.
    5. THIS APPROACH IS CORRECT IN THEORY BUT COMPLEX AND THERE IS A MUCH SIMPLER APPROACH
       WHICH is implemented below using COLORING strategy.   


     */
    public boolean isBipartite(int[][] graph) {
        int v = graph.length;
        int[] color = new int[v];
        Arrays.fill(color, -1);
        for(int i=0;i<v;i++){
            if(color[i] == -1){
                if(!bfsAndCheckBipartitie(graph, i, color)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean bfsAndCheckBipartitie(int[][] graph, int node, int[] color){
        Queue<Integer> q = new ArrayDeque<>();
        color[node] = 0;
        q.add(node);
        while(!q.isEmpty()){
            int currNode = q.remove();
            int[] neighbors = graph[currNode];
            for(int neighbor: neighbors){
                // not already visited
                if(color[neighbor] == -1){
                    color[neighbor] = 1 - color[currNode];
                    q.add(neighbor);
                }else{
                    // already visited
                    if(color[neighbor] == color[currNode]){ // conflciting colors
                        return false;
                    }
                }
                
            }
        }
        return true;
    }
}
