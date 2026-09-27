// https://leetcode.com/problems/critical-connections-in-a-network/

// Time Complexity: O(V+E)
// Space Complexity: O(V)
class Solution {
    // Tarjan's Algorithm
    int timer = 1;
    List<List<Integer>> bridges = new ArrayList<>();
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<n;i++){
            graph.add(new ArrayList<>());
        }
        for(List<Integer> connection: connections){
            int a = connection.get(0);
            int b = connection.get(1);
            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        int[] visited = new int[n];
        int[] iTime = new int[n];
        int[] minTime = new int[n];

        
 
        for(int i=0;i<n;i++){
            if(visited[i] == 0){
                dfs(i, -1, graph, visited, iTime, minTime);
            }
        }

        return bridges;
    }

    void dfs(int curr, int parent, List<List<Integer>> graph, int[] visited, int[] iTime, int[] minTime){
        visited[curr] = 1;
        iTime[curr] = minTime[curr] = timer;
        timer++;
        List<Integer> neighbors = graph.get(curr);
        for(int neighbor: neighbors){
            if(neighbor == parent){
                // we don't want to update the minTime of the curr wrt to the parent
                // hence don't go in else block
                continue;
            }
            if(visited[neighbor] == 0){
                dfs(neighbor, curr, graph, visited, iTime, minTime);
                minTime[curr] = Math.min(minTime[curr], minTime[neighbor]);
                // can curr->parent be a bridge
                int currItime = iTime[curr];
                int neighborMinTime = minTime[neighbor];
                if(neighborMinTime > currItime){
                    // a bridge
                    bridges.add(List.of(curr, neighbor));
                }
            }else{
                minTime[curr] = Math.min(minTime[curr], minTime[neighbor]);
            }

        }
    }
}
