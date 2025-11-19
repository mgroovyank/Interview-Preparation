// https://www.geeksforgeeks.org/problems/bfs-traversal-of-graph/1

// Time Complexity: O(V*E)
// Space Complexity: O(V) + O(V) queue + visited array
class Solution {
    public ArrayList<Integer> bfs(ArrayList<ArrayList<Integer>> adj) {
        // think of BFS as traversing nodes level by level
        // and level depends on starting node
        int n= adj.size();
        Queue<Integer> q = new ArrayDeque<>();
        int[] visited = new int[n];
        Arrays.fill(visited, 0);
        q.add(0);
        visited[0] = 1;
        ArrayList<Integer> result = new ArrayList<>();
        while(!q.isEmpty()){
            int curr = q.poll();
            result.add(curr);
            ArrayList<Integer> neighbors = adj.get(curr);
            for(int next: neighbors){
                if(visited[next] == 0){
                    q.add(next);
                    visited[next] = 1; 
                }
            }
        }
        return result;
    }
}
