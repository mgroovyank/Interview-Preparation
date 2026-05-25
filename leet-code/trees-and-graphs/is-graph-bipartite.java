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


// DFS BASED SOLUTION
class Solution {
    /**
    1. Just do a DFS traversal and you visit each node only once.
    2. Make sure that when you visit a node you assign it a set. Assign it a set which is inverse of set
       you assigned to previous node you visited. For this you can just assign all the neighbors
       of a node the inverse color of current node.
    3. When you reach a node which is already colored, but the color that you are trying to assign
    is a conflicting color. That means, the graph is not bipartite.   

     */
    public boolean isBipartite(int[][] graph) {
        int v = graph.length;
        int[] assignedSet = new int[v]; // 0 or 1
        Arrays.fill(assignedSet, -1);

        // loop over nodes which are not assigned a set.
        for(int i=0;i<v;i++){
            if(assignedSet[i] == -1){
                assignedSet[i] = 0;
                if(!dfsAndCheckBipartite(graph, i, assignedSet)){ // traverse a component
                    return false;
                } 
            }
        }
        return true;
    }

    private boolean dfsAndCheckBipartite(int[][] graph, int currNode, int[] assignedSet){
        int[] neighbors = graph[currNode];
        int currSet = assignedSet[currNode];
        for(int neighbor: neighbors){
            // if not assigned set
            if(assignedSet[neighbor] == -1){
              assignedSet[neighbor] = 1 - currSet;
              if(!dfsAndCheckBipartite(graph, neighbor, assignedSet)){
                return false;
              }  
            }
            // if already assigned set and set is conflicting
            if(assignedSet[neighbor] !=-1 && currSet==assignedSet[neighbor]){
                return false;
            }
            // if already assigned set and not conflicting
            // pass
        }
        return true;
    }
}
