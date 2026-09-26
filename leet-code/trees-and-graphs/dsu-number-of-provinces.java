// https://leetcode.com/problems/number-of-provinces

// Time Complexity: O(n*n)
// Space Complexity: O(n)
class Solution {
    // Disjoint Set Union Solution
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        DisjointSet ds = new DisjointSet(n);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(isConnected[i][j] == 1){
                    ds.union(i, j);
                }
            }
        }

        int numberOfProvinces = 0;

        for(int i=0;i<n;i++){
            if(i == ds.parent[i]){
                numberOfProvinces++;
            }
        }
        return numberOfProvinces;
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
