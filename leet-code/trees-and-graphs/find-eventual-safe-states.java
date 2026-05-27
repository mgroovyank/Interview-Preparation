// https://leetcode.com/problems/find-eventual-safe-states/

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
