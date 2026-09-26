// https://leetcode.com/problems/number-of-operations-to-make-network-connected/
// DSU

// Time Complexity: O(C) + O(n) , C = number of connections
// Space Complexity: O(n)
class Solution {
    public int makeConnected(int n, int[][] connections) {
        DisjointSet ds = new DisjointSet(n);
        int redundantConnections = 0;
        for(int[] connection: connections){ // O(C)
            int a = connection[0];
            int b = connection[1];
            int aParent = ds.findParent(a);
            int bParent = ds.findParent(b);
            if(aParent == bParent){
                redundantConnections++;
                continue;
            }
            ds.union(aParent, bParent);
        }
        int numberOfComponents = 0;
        for(int i=0;i<n;i++){ //O(n)
            if(ds.parent[i] == i){
                numberOfComponents++;
            }
        }
        if(numberOfComponents - 1 <= redundantConnections){
            return numberOfComponents - 1;
        }
        return -1;
    }

    class DisjointSet {
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
