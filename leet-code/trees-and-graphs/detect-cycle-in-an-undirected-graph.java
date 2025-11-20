// https://www.geeksforgeeks.org/problems/detect-cycle-in-an-undirected-graph/1

// Time Complexity: O(V + 2*E)
// Space Complexity: O(V) + O(V)
class Solution {
    List<List<Integer>> graph = new ArrayList<>();
    public boolean isCycle(int V, int[][] edges) {
        // BFS - once you fix a starting node, all the other nodes are assigned levels
        // multiple nodes belong to same level
        // this means nodes at same level will have same previous Node
        // if from my current node, I can vist the neighboring nodes,
        // but the neighboring node is already visited and since from current node,
        // I'm trying to visit it for the first time(as I process every node only once)
        // that means the neighboring node is visited by some other node already
        // that means from src node, I have two paths to the neighboring node
        // that means there is a cycle

        // adjacency list representation
        
        for(int i=0;i<V;i++){
            graph.add(new ArrayList<>());
        }

        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        
        int[] visited = new int[V];
        Arrays.fill(visited, 0);
        for(int v=0;v<V;v++){
            if(visited[v] == 0){
                if(bfsAndCheckCycle(v, visited)){
                    return true;
                }
            }
        }
        return false;
        
    }
    
    private boolean bfsAndCheckCycle(int src, int[] visited){
        Queue<List<Integer>> q = new ArrayDeque<>();
        visited[src] = 1;
        q.add(List.of(src, -1));
        while(!q.isEmpty()){
            List<Integer> curr = q.poll();
            int currNode = curr.get(0);
            int prevNode = curr.get(1);
            List<Integer> neighbors = graph.get(currNode);
            for(int next: neighbors){
                if(visited[next] == 0){
                    visited[next] = 1;
                    q.add(List.of(next, currNode));
                }else{
                    if(next == prevNode){
                        // can't consider this as a cycle as that is because
                        // I just came from the prevNode to currNode
                        // that's why it is visited already
                        continue;
                    }
                    // someone else has already visited the neighboring next node
                    // that means there is a cycle
                    return true;
                }
            }
        }
        return false;
    }
}


// BFS Approach
class Solution {
    List<List<Integer>> graph = new ArrayList<>();
    public boolean isCycle(int V, int[][] edges) {
        // DFS - cycle means if you start from a node
        // and then if you visit any already visited node again,
        // then that means there is a cycle

        // adjacency list representation
        
        for(int i=0;i<V;i++){
            graph.add(new ArrayList<>());
        }

        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        
        int[] visited = new int[V];
        Arrays.fill(visited, 0);
        for(int v=0;v<V;v++){
            if(visited[v] == 0){
                visited[v] = 1;
                if(dfsAndCheckCycle(v, visited, -1)){
                    return true;
                }
            }
        }
        return false;
        
    }
    
    private boolean dfsAndCheckCycle(int src, int[] visited, int prev){
        List<Integer> neighbors = graph.get(src);
        for(int next: neighbors){
            if(visited[next] == 0){
                visited[next] = 1;
                if(dfsAndCheckCycle(next, visited, src)){
                    return true;
                }
            }else{
                // if the visited node is prev Node, then it is fine, not a cycle
                if(next == prev){
                    continue;
                }
                return true;
            }
        }
        return false;
    }

}
