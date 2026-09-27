// https://www.geeksforgeeks.org/problems/strongly-connected-components-kosarajus-algo/1

// Kosaraju Algorithm
// Time Complexity: O(V+E)
// Space Complexity: O(V)
class Solution {
    public int countSCC(int V, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        List<List<Integer>> reverseGraph = new ArrayList<>();
        for(int i=0;i<V;i++){
            graph.add(new ArrayList<>());
            reverseGraph.add(new ArrayList<>());
        }
        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            reverseGraph.get(v).add(u);
        }
        
        // Sort the nodes by finishing time
        Deque<Integer> st = new ArrayDeque<>();
        int[] visited = new int[V];
        for(int i=0;i<V;i++){
            if(visited[i] == 0){
                dfs(i, visited, graph, st);
            }
        }
        
        // use reverse graph
        
        // dfs in the order of nodes present in stack
        int result = 0;
        Arrays.fill(visited, 0);
        while(!st.isEmpty()){
            int currNode = st.pop();
            if(visited[currNode] == 1){
                continue;
            }
            // node not visited
            dfs(currNode, visited, reverseGraph);
            result++;
        }
        
        return result;
        
    }
    
    void dfs(int currNode, int[] visited, List<List<Integer>> graph, Deque<Integer> st){
        visited[currNode] = 1;
        List<Integer> neighbors = graph.get(currNode);
        for(int neighbor: neighbors){
            if(visited[neighbor] == 0){
                dfs(neighbor, visited, graph, st);
            }
        }
        st.push(currNode);
    }
    
    void dfs(int currNode, int[] visited, List<List<Integer>> graph){
        visited[currNode] = 1;
        List<Integer> neighbors = graph.get(currNode);
        for(int neighbor: neighbors){
            if(visited[neighbor] == 0){
                dfs(neighbor, visited, graph);
            }
        }
    }
}
