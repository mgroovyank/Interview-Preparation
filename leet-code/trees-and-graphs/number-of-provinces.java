// https://leetcode.com/problems/number-of-provinces/

// Time Complexity: due to adjacency matrix: O(N*N) - outer loop in findCircleNum runs once for each node and as part of dfs we check all n nodes
// if we have adjacency list representation, then O(V+2*E)
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


// BFS Solution
// Time Complexity:
// every node goes into the queue once, 
// for each node, we loop over isConnected - n => n + n + n + .... ntimes = n*n = O(N*N) + O(N) <- queue operations on each node
// so total time complexity of entire bfs is O(N*N)
// now for outer loop, the calls will be something like this: bfs1 + O(1) + bfs3 + bfs4 + O(1) + ... n nodes => totalbfs + remaining
// => O(N*N) + O(N)
class Solution {
    /**
    1. Start from an univisited node. Use visited[] to maintain that.
    2. From that node, try doing bfs
    3. After doing bfs, since you must have visited all the direct/indirect connected nodes, you have visited
       all nodes part of same province, so increment province count.
    4. Continue from 2 with next unvisited node.
    5. In BFS, I would need to know the negihbors for each node, so I can use adjacencyMatrix representation
     */
    public int findCircleNum(int[][] isConnected) {
        int numberOfProvinces = 0;
        int n = isConnected.length;
        int[] visited = new int[n];
        for(int i=0;i<n;i++){
            if(visited[i] == 0){
                bfs(isConnected, i, visited);
                numberOfProvinces++;
            }
        }
        return numberOfProvinces;
    }

    private void bfs(int[][] isConnected, int i, int[] visited){
        Queue<Integer> q = new ArrayDeque<>();
        visited[i] = 1;
        q.add(i);
        while(!q.isEmpty()){
            int curr = q.remove();
            int[] neighbors = isConnected[curr];
            for(int j=0;j<neighbors.length;j++){
                if(neighbors[j] == 1 && visited[j]==0){
                    visited[j] = 1;
                    q.add(j);
                }
            }
        }
    } 
}
