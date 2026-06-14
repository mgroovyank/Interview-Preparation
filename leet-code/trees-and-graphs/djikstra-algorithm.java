// https://www.geeksforgeeks.org/problems/implementing-dijkstra-set-1-adjacency-matrix/1

// DOES NOT WORK WITH NEGATIVE WEIGHTS

// Time Complexity:
// Space Complexity: O(V*Number of edges)
class Solution {
    public int[] dijkstra(int V, int[][] edges, int src) {
        List<List<NodeWeight>> graph = new ArrayList<>();
        for(int i=0;i<V;i++){
            graph.add(new ArrayList<>());
        }
        
        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            graph.get(u).add(new NodeWeight(v, w));
            graph.get(v).add(new NodeWeight(u, w));
        }
        
        int[] shortestDist = new int[V];
        Arrays.fill(shortestDist, Integer.MAX_VALUE);
        
        PriorityQueue<NodeWeight> pq = new PriorityQueue<>();
        pq.add(new NodeWeight(src, shortestDist[src] = 0));
        
        
        while(!pq.isEmpty()){
            NodeWeight nw = pq.poll();
            int currNode = nw.node;
            int currWeight = nw.weight;
            
            List<NodeWeight> neighbors = graph.get(currNode);
            for(NodeWeight neighbor: neighbors){
                int nextNode = neighbor.node;
                int nextWeight = currWeight + neighbor.weight;
                if(nextWeight < shortestDist[nextNode]){
                    shortestDist[nextNode] = nextWeight;
                    pq.add(new NodeWeight(nextNode, nextWeight));
                }
            }
        }
        return shortestDist;
    }
    
    class NodeWeight implements Comparable<NodeWeight>{
        int node;
        int weight;
        
        NodeWeight(int node, int weight){
            this.node = node;
            this.weight = weight;
        }
        
        public int compareTo(NodeWeight nw){
            return Integer.compare(this.weight, nw.weight);
        }
    }
}

// Using Tree Set - Slight Optimization
class Solution {
    public int[] dijkstra(int V, int[][] edges, int src) {
        List<List<NodeWeight>> graph = new ArrayList<>();
        for(int i=0;i<V;i++){
            graph.add(new ArrayList<>());
        }
        
        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            graph.get(u).add(new NodeWeight(v, w));
            graph.get(v).add(new NodeWeight(u, w));
        }
        
        int[] shortestDist = new int[V];
        Arrays.fill(shortestDist, Integer.MAX_VALUE);
        
        TreeSet<NodeWeight> pq = new TreeSet<>();
        pq.add(new NodeWeight(src, shortestDist[src] = 0));
        
        
        while(!pq.isEmpty()){
            NodeWeight nw = pq.pollFirst();
            int currNode = nw.node;
            int currWeight = nw.weight;
            
            List<NodeWeight> neighbors = graph.get(currNode);
            for(NodeWeight neighbor: neighbors){
                int nextNode = neighbor.node;
                int nextWeight = currWeight + neighbor.weight;
                if(nextWeight < shortestDist[nextNode]){
                    pq.remove(new NodeWeight(nextNode, shortestDist[nextNode]));
                    shortestDist[nextNode] = nextWeight;
                    pq.add(new NodeWeight(nextNode, nextWeight));
                }
            }
        }
        return shortestDist;
    }
    
    class NodeWeight implements Comparable<NodeWeight>{
        int node;
        int weight;
        
        NodeWeight(int node, int weight){
            this.node = node;
            this.weight = weight;
        }
        
        public int compareTo(NodeWeight nw){
            int cmp = Integer.compare(this.weight, nw.weight);
            if (cmp != 0) return cmp;
            return Integer.compare(this.node, nw.node);
        }
        
        public boolean equals(Object o){
            if(o instanceof NodeWeight nw){
                return this.node== nw.node && this.weight==nw.weight;
            }
            return false;
        }
    }
}
