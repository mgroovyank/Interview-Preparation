// https://www.geeksforgeeks.org/problems/articulation-point2616/1

// Time Complexity: O(V+2E)
// Space Complexity: O(V)
class Solution {
    static int timer = 1;
    static ArrayList<Integer> articulationPoints(int V, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<V;i++){
            graph.add(new ArrayList<>());
        }
        for(int[] edge: edges){
            int a = edge[0];
            int b = edge[1];
            graph.get(a).add(b);
            graph.get(b).add(a);
        }
        
        int[] visited = new int[V];
        int[] iTime = new int[V];
        int[] minTime = new int[V];
        
        int[] articulationPoint = new int[V];
        
        for(int i=0;i<V;i++){
            if(visited[i] == 0){
                dfs(i, -1, graph, visited, iTime, minTime, articulationPoint);
            }
        }
        
        ArrayList<Integer> result = new ArrayList<>();
        
        for(int i=0;i<V;i++){
            if(articulationPoint[i] == 1){
                result.add(i);
            }
        }
        
        if(result.size() == 0){
            result.add(-1);
            return result;
        }
        
        return result;
    }
    
    static void dfs(int curr, int parent, List<List<Integer>> graph, int[] visited, int[] iTime, int[] minTime, int[] articulationPoint){
        visited[curr] = 1;
        iTime[curr] = minTime[curr] = timer;
        timer++;
        int child = 0;
        List<Integer> neighbors = graph.get(curr);
        for(int neighbor: neighbors){
            if(neighbor == parent){
                // we don't want to update the minTime of the curr wrt to the parent
                // hence don't go in else block
                continue;
            }
            if(visited[neighbor] == 0){
                dfs(neighbor, curr, graph, visited, iTime, minTime, articulationPoint);
                minTime[curr] = Math.min(minTime[curr], minTime[neighbor]);
                // can curr->parent be a bridge
                int currItime = iTime[curr];
                int neighborMinTime = minTime[neighbor];
                if(neighborMinTime >= currItime && parent!=-1){
                    //a articulation point
                    articulationPoint[curr] = 1;
                }
                child++;
            }else{
                // we don't take minTime for visited nodes as that point might be an articulation point
                minTime[curr] = Math.min(minTime[curr], iTime[neighbor]);
            }
        }
        
        if(parent == -1 && child>1){
            articulationPoint[curr] = 1;
        }
    }
    
}
