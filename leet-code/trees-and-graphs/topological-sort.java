// https://www.geeksforgeeks.org/problems/topological-sort/1

class Solution {
    /**
     * 1. Topological sort is valid only for Directed Acyclic graph because, in c
     * case of a cycle in a directed graph, if you pick any node as start and then try to
     * do DFS, then the last node will eventually want to come before the start node,
     * but that is not possible.
     * 
     * 2. Why can't I just have the order as same as when I visit a node my result
     * as whenvever we are doing DFS, we are travelling from u to v?
     * -> In this case issue will come when you need to put the nodes which are
     * having outgoing edges to your start node, they need to be put before your start
     * node, but you already decided the order.
     * -> But that means you have good order for your start node being the u.
     * But now for other nodes where your start node becomes 'v'. you need to put the
     * other DFS results at the start of your result array. The same would happen
     * for each new start node that you pick.
     * -> A->B->D
     *     A->C->D
     *     result for this would be A B D C == INVALID for C and D
     *    
     * That's why pre order traversal is not safe to do.
     */
    ArrayList<Integer> result = new ArrayList<>();
    Deque<Integer> s = new ArrayDeque<>();
    public ArrayList<Integer> topoSort(int V, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<V;i++){
            graph.add(new ArrayList<>());
        }
        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
        }
        
        int[] visited = new int[V];
        for(int i=0;i<V;i++){
            if(visited[i] == 0){
                visited[i] = 1;
                dfs(graph, i, visited);
                // once you are done with dfs for a node, then it should be
                // put before all the children nodes in the topological order
                // So that's like a level by level travelling up in the recursion tree.
                s.push(i);
                
            }
        }
        
        while(!s.isEmpty()){
            result.add(s.pop());
        }
        return result;
    }
    
    private void dfs(List<List<Integer>> graph, int currNode, int[] visited){
        List<Integer> neighbors = graph.get(currNode);
        for(int neighbor: neighbors){
            if(visited[neighbor] == 0){
                visited[neighbor] = 1;
                dfs(graph, neighbor, visited);
                // once you are done with DFS you are sure that all other nodes for
                // this neighbor have already been put in stack
                s.push(neighbor);
            }
        }
    }
}


class Solution {
    /*
    // KAHN'S ALGORITHM - BFS
    1. Since the graph is acylic there is atleast one node with indegree=0.
    2. So BFS actually does make more sense than DFS here, but if you just
       pick the first unvisited node as your starting point and keep the sort
       order same as order of BFS traversal, there can be cases where
       this might go wrong.
    3. So the good idea here is to pick those nodes as your starting point 
       those don't have any incoming edges, that means, nothing comes before them.
    4. So everytime when an incoming edge to a node is processed, that means
       one of the parent of that node is getting processed.
    5. When all the parents get processed, the indegree would becoming zero,
       and now it is guaranteed that this node's parents will come before this node
       in my final answer.
    */
    
    ArrayList<Integer> result = new ArrayList<>();
    
    public ArrayList<Integer> topoSort(int V, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i< V;i++){
            graph.add(new ArrayList<>());
        }
        
        int[] indegree = new int[V];

        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            indegree[v]++;
        }
        
        Deque<Integer> q = new ArrayDeque<>();
        // add nodes with indegree zero into queue
        for(int i=0;i<indegree.length;i++){
            if(indegree[i] == 0){
                q.add(i);
            }
        }
        
        while(!q.isEmpty()){
            int currNode = q.remove();
            result.add(currNode);
            List<Integer> neighbors = graph.get(currNode);
            for(int neighbor: neighbors){
                if(indegree[neighbor] == 0){
                    continue;
                }
                if(--indegree[neighbor] == 0){
                    q.add(neighbor);
                }
            }
        }
        
        return result;

    }
    
}
