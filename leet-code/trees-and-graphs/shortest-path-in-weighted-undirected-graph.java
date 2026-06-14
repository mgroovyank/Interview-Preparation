// https://www.geeksforgeeks.org/problems/shortest-path-in-weighted-undirected-graph/1

// Time Complexity: O(ElogV)
// Space Complexity: 2*O(n) + O(n(n-1)) - heap
class Solution {
    public List<Integer> shortestPath(int n, int m, int edges[][]) {
        List<List<NodeWeight>> graph = new ArrayList<>();
        
        for(int i=0;i<n+1;i++){
            graph.add(new ArrayList<>());
        }
        
        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            graph.get(u).add(new NodeWeight(v, w));
            graph.get(v).add(new NodeWeight(u, w));
        }
        
        PriorityQueue<NodeWeight> pq = new PriorityQueue<>();
        int[] shortestDist = new int[n+1];
        int[] parent = new int[n+1];
        Arrays.fill(shortestDist, Integer.MAX_VALUE);
        for(int i=0;i<n+1;i++){
            parent[i] = i;
        }
        
        pq.add(new NodeWeight(1, shortestDist[1]=0));
        
        while(!pq.isEmpty()){
            NodeWeight curr = pq.poll();
            int currNode = curr.node;
            int currW = curr.weight;
            List<NodeWeight> neighbors = graph.get(currNode);
            for(NodeWeight neighbor: neighbors){
                int nextNode = neighbor.node;
                int nextW = currW + neighbor.weight;
                if(nextW < shortestDist[nextNode]){
                    // found a shorter distance to nextNode
                    shortestDist[nextNode] = nextW;
                    parent[nextNode] = currNode;
                    pq.add(new NodeWeight(nextNode, nextW));
                }
            }
        }
        
        if(shortestDist[n] == Integer.MAX_VALUE){
            return List.of(-1);
        }
        
        List<Integer> path = new ArrayList<>();
        int currNode = n;
        while(currNode != 1){
            path.add(currNode);
            currNode = parent[currNode];
        }
        path.add(1);
        List<Integer> result = new ArrayList<>();
        result.add(shortestDist[n]);
        for(int i=path.size()-1;i>=0;i--){
            result.add(path.get(i));
        }
        return result;
        
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
