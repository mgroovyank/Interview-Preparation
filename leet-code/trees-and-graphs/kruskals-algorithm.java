// https://www.geeksforgeeks.org/problems/minimum-spanning-tree-kruskals-algorithm/1
// DSU - Disjoint Set Union

// Time Complexity: O(ElogE) + O(E)     
// Space Complexity: O(V)

class Solution {
    static int kruskalsMST(int V, int[][] edges) {
        int result = 0;
        DisjointSet ds = new DisjointSet(V);
        Arrays.sort(edges, (e1, e2) -> Integer.compare(e1[2], e2[2])); // O(ElogE)
        for(int[] edge: edges){ // O(E)
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            int pu = ds.findParent(u); // O(1)
            int pv = ds.findParent(v); // O(1)
            if(pu == pv){
                // same parent => same component => already connected => edge not required for MST
                continue;
            }
            ds.union(pu, pv); // O(1)
            result += w;
        }
        return result;
    }
    
    static class DisjointSet {
        int V;
        int[] parent;
        int[] rank;
        
        public DisjointSet(int V){
            this.V = V;
            this.parent = new int[V];
            for(int i=0;i<V;i++){
                this.parent[i] = i; // parent is itself
            }
            this.rank = new int[V]; // rank = 0
        }
        
        public void union(int u, int v){
            int pu = findParent(u);
            int pv = findParent(v);
            int ranku = this.rank[pu];
            int rankv = this.rank[pv];
            if(ranku == rankv || ranku > rankv){
                // attach pv to pu
                this.parent[pv] = pu;
                this.rank[pu]++;
            }else {
                // attach pu to pv
                this.parent[pu] = pv;
            }
        }
        
        public int findParent(int u){
            // base case
            if(u == this.parent[u]){
                return u;
            }
            return this.parent[u] = findParent(this.parent[u]); // patch compression
        }
    }
    
}
