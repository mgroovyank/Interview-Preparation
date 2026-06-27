// https://leetcode.com/problems/number-of-ways-to-arrive-at-destination/
class Solution {
    /**
    1. You should have a good understanding of working of DJIKSTRA'S Algorithm.
    2. In this algorithm, we don't travel across all the paths, we only try to travel across paths, which give
       minimum weight.
    3. For example, there are two paths to reach node A from src node. One path has weight Wa1 = 2
       , another has Wa2 = 3.
    4. Now from Node A there are two paths to reach destination node, one path is Wd1=3,
       other is Wd2=2
    5. Now via Djikstra's algorithm, I would take path1 to node A and then path2 to node dest, TW=5
    6. When I try, path 2 to node A, I would not even take it because Wa2=3 is costlier than
       the previous path, so I'll never reach destination node via this path.
    7. Although if I wouldve visited this path also, I would have got the same total weight as 5.
    8. So the other path is never even taking me to the destination node, so if I use
       the metric how many times I reach node n-1 as the answer, that is WRONG.         
     */
    public int countPaths(int n, int[][] roads) {
        List<List<NodeAndWeight>> graph = new ArrayList<>();
        for(int i=0;i<n;i++){
            graph.add(new ArrayList<>());
        }

        for(int[] road: roads){
            int u = road[0];
            int v = road[1];
            int t = road[2];
            graph.get(u).add(new NodeAndWeight(v, t));
            graph.get(v).add(new NodeAndWeight(u, t));
        }

        // DJIKSTRA'S ALGORITHM TRAVERSAL
        long[] ways = new long[n];
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        PriorityQueue<NodeAndWeight> pq = new PriorityQueue<>();
        ways[0] = 1;
        dist[0] = 0;
        pq.add(new NodeAndWeight(0, 0));
        final int MOD = 1_000_000_007;
        while(!pq.isEmpty()){
            NodeAndWeight curr = pq.poll();
            int currNode = curr.node;
            long currDist = curr.weight;

            List<NodeAndWeight> neighbors = graph.get(currNode);
            for(NodeAndWeight neighbor: neighbors){
                int nextNode = neighbor.node;
                long nextDist = currDist + neighbor.weight;
                if(nextDist < dist[nextNode]){
                    dist[nextNode] = nextDist;
                    ways[nextNode] = ways[currNode] * 1;
                    pq.add(new NodeAndWeight(nextNode, nextDist));
                }else if(nextDist == dist[nextNode]){
                    ways[nextNode] = (ways[nextNode]%MOD + (ways[currNode] * 1) % MOD) % MOD;
                    // nextNode already reached by same distance, so need to process again
                }
            }
        }

        return (int)(ways[n-1] % MOD);
    }

    class NodeAndWeight implements Comparable<NodeAndWeight> {
        int node;
        long weight;

        NodeAndWeight(int node, long weight){
            this.node = node;
            this.weight = weight;
        }

        public int compareTo(NodeAndWeight nw){
            return Long.compare(this.weight, nw.weight);
        }
    }
}
