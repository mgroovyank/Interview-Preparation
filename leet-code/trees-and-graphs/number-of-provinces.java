// https://leetcode.com/problems/number-of-provinces/

// Time Complexity: O(V+2*E) // doing dfs on entire graph
// Space Complexity: O(V) + O(V)
class Solution {
    public int findCircleNum(int[][] isConnected) {
        // adjacency matrix representation
        int n = isConnected.length;
        int[] visited = new int[n];
        Arrays.fill(visited, 0);
        int result = 0;
        for(int i=0;i<n;i++){
            if(visited[i] == 0){
                result++;
                dfs(isConnected, i, visited);
            }
        }
        return result;
    }

    private void dfs(int[][] isConnected, int i, int[] visited){
        visited[i] = 1;
        int[] neighbors = isConnected[i];
        for(int j=0;j<neighbors.length;j++){
            if(neighbors[j] == 1 && visited[j]==0){
                dfs(isConnected, j, visited);
            }
        }
    }
}
