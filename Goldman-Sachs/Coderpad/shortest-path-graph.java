// https://www.geeksforgeeks.org/problems/implementing-dijkstra-set-1-adjacency-matrix/1
// Time Complexity: O(V) + O(E) + O(V*logV) + O(E*logV)
// Space Complexity: O(V) + O(E)
// so in case all nodes are connected to all other nodes, in that case priority queue can contain elements in order of V*V
// Works only when weights are POSITIVE
class Solution {
    public int[] dijkstra(int V, int[][] edges, int src) {
        // code here
        // Adjacency List Representation of graph - V nodes
        // using List vs Map(when nodes are not numbers or frequent add/del are required)
        List<List<List<Integer>>> graph = new ArrayList<>();
        for(int i=0;i<V;i++){
            graph.add(new ArrayList<>());
        }
        int numEdges = edges.length;
        for(int i=0;i<numEdges;i++){
            int u = edges[i][0];
            int v = edges[i][1];
            int w = edges[i][2];
            List<Integer> targetNodeAndWeight = new ArrayList<>();
            targetNodeAndWeight.add(v);
            targetNodeAndWeight.add(w);
            graph.get(u).add(targetNodeAndWeight);
        }
        int[] shortestDistFrmSrc = new int[V];
        for(int i=0;i<V;i++){
            shortestDistFrmSrc[i] = Integer.MAX_VALUE; 
        }
        PriorityQueue<List<Integer>> pq = new PriorityQueue<>((a, b) -> {
            if(a.get(0) == b.get(0)){
                return a.get(1) - a.get(1);
            }
            return a.get(0) - b.get(0);
        });
        shortestDistFrmSrc[src] = 0;
        List<Integer> temp = new ArrayList<>();
        temp.add(0);
        temp.add(src);
        pq.add(temp);
        while(!pq.isEmpty()){
            List<Integer> currNode = pq.poll();
            List<List<Integer>> neighbors = graph.get(currNode.get(1));
            int currDistance = currNode.get(0);
            for(int i=0;i<neighbors.size();i++){
                List<Integer> nextNode = neighbors.get(i);
                int nextNodeNum = nextNode.get(0);
                int nextWeight = nextNode.get(1);
                int nextDistance = currDistance + nextWeight;
                if(nextDistance < shortestDistFrmSrc[nextNodeNum]){
                    shortestDistFrmSrc[nextNodeNum] = nextDistance;
                    List<Integer> nextNodePq = new ArrayList<>();
                    nextNodePq.add(nextDistance);
                    nextNodePq.add(nextNodeNum);
                    pq.add(nextNodePq);
                }
            }
        }
        return shortestDistFrmSrc;
    }
    
}

// Less Verbose version
class Solution {
    public int[] dijkstra(int V, int[][] edges, int src) {
        // code here
        // Adjacency List Representation of graph - V nodes
        // using List vs Map(when nodes are not numbers or frequent add/del are required)
        List<List<Edge>> graph = new ArrayList<>();
        for(int i=0;i<V;i++){
            graph.add(new ArrayList<>());
        }
        int numEdges = edges.length;
        for(int i=0;i<numEdges;i++){
            graph.get(edges[i][0]).add(new Edge(edges[i][1], edges[i][2]));
        }
        int[] shortestDistFrmSrc = new int[V];
        for(int i=0;i<V;i++){
            shortestDistFrmSrc[i] = Integer.MAX_VALUE; 
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> a.weight - b.weight);
        shortestDistFrmSrc[src] = 0;
        pq.add(new Edge(src, 0));
        while(!pq.isEmpty()){
            Edge currNode = pq.poll();
            List<Edge> neighbors = graph.get(currNode.to);
            int currDistance = currNode.weight;
            for(int i=0;i<neighbors.size();i++){
                Edge nextNode = neighbors.get(i);
                int nextNodeNum = nextNode.to;
                int nextWeight = nextNode.weight;
                int nextDistance = currDistance + nextWeight;
                if(nextDistance < shortestDistFrmSrc[nextNodeNum]){
                    shortestDistFrmSrc[nextNodeNum] = nextDistance;
                    pq.add(new Edge(nextNodeNum, nextDistance));
                }
            }
        }
        return shortestDistFrmSrc;
    }
    
    private class Edge {
        private int to;
        private int weight;
        
        private Edge(int to, int weight){
            this.to = to;
            this.weight = weight;
        }
    }
}
