// https://www.geeksforgeeks.org/problems/depth-first-traversal-for-a-graph/1

// Time Complexity: O(V) + O(2*E)
// Space Complexity: O(V) + O(V) recursion + visited
class Solution {
    ArrayList<Integer> result = new ArrayList<>();
    public ArrayList<Integer> dfs(ArrayList<ArrayList<Integer>> adj) {
        int n = adj.size();
        int[] visited = new int[n];
        Arrays.fill(visited, 0);
        // code here
        dfsHelper(adj, 0, visited);
        return result;
    }
    
    private void dfsHelper(ArrayList<ArrayList<Integer>> adj, int curr, int[] visited){
        result.add(curr);
        visited[curr] = 1;
        ArrayList<Integer> neighbors = adj.get(curr);
        for(int next: neighbors){
            if(visited[next] == 0){
                dfsHelper(adj, next, visited);
            }
        }
    }
}
