// https://www.geeksforgeeks.org/problems/shortest-path-in-undirected-graph-having-unit-distance/1

class Solution {
    /**
     * 1. Shortest path gives an idea of BFS
     * */
    public int[] shortestPath(int V, int[][] edges, int src) {
        // Adjacency List representation
        List<List<Integer>> graph = new ArrayList<>();
        
        for(int i=0;i<V;i++){
            graph.add(new ArrayList<>());
        }
        
        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            // undirected graph
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        
        // BFS
        int[] distance = new int[V];
        Arrays.fill(distance, -1);
        
        Deque<List<Integer>> q = new ArrayDeque<>();
        q.add(List.of(src, distance[src]=0));
        
        while(!q.isEmpty()){
            List<Integer> curr = q.remove();
            int currNode = curr.get(0);
            int currDistance = curr.get(1);
            List<Integer> neighbors = graph.get(currNode);
            for(int neighbor: neighbors){
                if(distance[neighbor] == -1){
                    q.add(List.of(neighbor, distance[neighbor]=currDistance+1));
                }
            }
            
        }
        
        return distance;
    }
}
