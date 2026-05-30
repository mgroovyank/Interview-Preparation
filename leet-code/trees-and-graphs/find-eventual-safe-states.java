// https://leetcode.com/problems/find-eventual-safe-states/


class Solution {
    /**
    1. All terminal nodes are safe nodes.
    2. If a node is connected to only safe nodes, then that node is also safe.
    3. If any of the connected nodes from a node is unsafe, that node is also unsafe.
    4. So when we do DFS, for each node, if none of the neighbors is unsafe, then mark that node as safe. 
    5. When you revisit a node, it might already marked as safe. And you mark a node as safe only when is is fully processed. A fully processed node means
       it is not in the current recursion stack(not on current path).
       -> if it is marked as unsafe, then that can be because it is not fully processed or it was fully processed and then marked unsafe.
       -> If it is not fully processed but visited, that means it is on current path. That means there is a cycle. So no termination. So current node is unsafe.
       -> If is fully processed and marked unsafe, then the current node is also unsafe.
       -> So if a node is revisited and not marked as safe, then surely the current node is unsafe.
     */
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int v = graph.length;
        int[] visited = new int[v];
        int[] safe = new int[v];
        for(int i=0;i<v;i++){
            if(visited[i] == 0){
                visited[i] = 1;
                dfs(graph, i, visited, safe);
            }
        }

        List<Integer> result = new ArrayList<>();
        for(int i=0;i<v;i++){
            if(safe[i] == 1){
                result.add(i);
            }
        }
        return result;
    }

    private boolean dfs(int[][] graph, int currNode, int[] visited, int[] safe){
        int[] neighbors = graph[currNode];
        for(int neighbor: neighbors){
            if(visited[neighbor] == 1){
                if(safe[neighbor] == 1){ // a node is marked as safe
                    continue;
                }else{
                    // nodes by default are unsafe, so no need to mark here
                    return false;
                }
            }else {
                visited[neighbor] = 1;
                boolean isSafe = dfs(graph, neighbor, visited, safe);
                if(!isSafe){
                    return false;
                }else{
                    continue;
                }
            }
        }
        safe[currNode] = 1;
        return true;
    }
}

class Solution {
    /**
    1. When do you reach end of path?
       -> When the node on the path is already visited, you can't visit again. So that's an end. Now
          check if it is a terminal node.
       -> but also that can be a cycle. If there is a cycle(revisit node on current path twice), 
          then definitely all nodes which are 
          part of the cycle will never have a terminal node and are never Safe.
       -> also all nodes which are leading up to that cycle will also never be safe nodes because at least
          one path will not have a terminal node. So that example can be a linear set of nodes,
          making entry into a cycle, so once they enter the cycle, they are just stuck within the cycle,
          the path never terminates.
       -> So this actually means that nodes from where if you perform DFS and detect a cycle
          can never be safe nodes.
       -> So you should just pick up first unvisited node, perform Dfs, and at any node where you detect
          cycle, just put it as unsafe.  
       -> Mark a node as safe only when you don't detect a cycle on any of the paths. 
       -> When you detect a cycle on a path, just simply return, don't remove that node from the 
          current path. As the other paths starting from that node, you haven't still visited. 
          Any unvisited node that you visit next, might fall on the remaining paths that
          start from that node on which you did DFS. So keep that node on the currentPath.
        -> If you visit the next unvisited node, that doesn't fall on any of the paths starting 
        from that node, it is still fine to have that node on the current path as it doesn't interfere.              
       -> When the node was not already visited, but doesn't have any outgoing edges to further 
          add to path. That is a terminal node.
     */

    List<Integer> safeNodes = new ArrayList<>();

    public List<Integer> eventualSafeNodes(int[][] graph) {
        int v = graph.length;
        int[] visited = new int[v];
        int[] currentPathFlag = new int[v];

        for(int i=0;i<v;i++){
            if(visited[i] == 0){
                visited[i] = 1;
                currentPathFlag[i] = 1;
                boolean isCycle = dfsAndDetectCycle(graph, i, visited, currentPathFlag);
                if(!isCycle){
                    safeNodes.add(i);
                    currentPathFlag[i] = 0;
                }
            }
        }

        Collections.sort(safeNodes);
        return safeNodes;
    }

    private boolean dfsAndDetectCycle(int[][] graph, int currNode, int[] visited, int[] currentPathFlag){
        int[] neighbors = graph[currNode];
        for(int neighbor: neighbors){
            // neighbor not already visited
            if(visited[neighbor] == 0){
                visited[neighbor] = 1;
                currentPathFlag[neighbor] = 1;
                boolean isCycle = dfsAndDetectCycle(graph, neighbor, visited, currentPathFlag);
                if(!isCycle){
                    currentPathFlag[neighbor] = 0;
                    safeNodes.add(neighbor);
                }else {
                    return true;
                }         
            }else{ // neighbor already visited
                if(currentPathFlag[neighbor] == 1){ // visited on current path - cycle detected
                    return true;
                }else{ // cycle not detected
                    // check next neighbor
                }
            }
        } 
        return false;
    }
}

// TOPOLOGICAL SORT
class Solution {
    /**
    1. A node is unsafe it is part of a cycle or connected to a cycle
    2. Topological Sort
    3. For topological sort to apply, we need to start with nodes whose indegree=0. Also these nodes must be safe nodes.
    4. So we need terminal nodes to become our initial nodes with indegree=0;
    5. but terminal nodes have outdegree=0, so if we flip all the edges direction in graph, then
       terminal nodes will have indegree=0 and they can become input for topological sort.
    6. Now when we process only safe nodes, then we reduce indegree of nodes having incoming edges from safe nodes. If
       a node's total indegree gets reduced to zero, that means that node is only having outgoing edges(in original graph)
       to safe nodes. That means that node is a safe node.
    7. This way topological sort can be used to find safe nodes.      
     */
    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<List<Integer>> newGraph = new ArrayList<>();
        int V = graph.length;
        for(int i=0;i<V;i++){
            newGraph.add(new ArrayList<>());
        }

        int[] indegree = new int[V];

        // build reverse graph
        for(int u=0;u<V;u++){
            for(int v: graph[u]){
                newGraph.get(v).add(u);
                indegree[u]++;
            }
        }

        List<Integer> safeNodes = new ArrayList<>();

        Deque<Integer> q = new ArrayDeque<>();
        for(int i=0;i<V;i++){
            if(indegree[i] == 0){
                q.add(i);
            }
        }

        while(!q.isEmpty()){
            int currNode = q.remove();
            safeNodes.add(currNode);
            List<Integer> neighbors = newGraph.get(currNode);
            for(int neighbor: neighbors){
                indegree[neighbor]--;
                if(indegree[neighbor] == 0){
                    q.add(neighbor);
                }
            }
        }

        Collections.sort(safeNodes);
        return safeNodes;
        
    }
}
