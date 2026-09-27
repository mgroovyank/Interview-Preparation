// https://www.geeksforgeeks.org/problems/maximum-stone-removal-1662179442/1

// Time Complexity: O(S)
// Space Complexity: O(N + M)
class Solution {
    // O(N*N*N) Approach - Brute Force Approach
    // Disjoint Set Union Approach
    public int maxRemove(int[][] stones) {
        int totalStones = stones.length;
        int maxRow = 0;
        int maxCol = 0;
        for(int[] stone: stones){
            maxRow = Math.max(maxRow, stone[0]);
            maxCol = Math.max(maxCol, stone[1]);
        }
        DisjointSet ds = new DisjointSet(maxRow + maxCol + 2); // +1+1 due to zero indexing
        for(int[] stone: stones){
            int stoneRow = stone[0];
            int stoneCol = stone[1] + maxRow + 1;
            ds.unionBySize(stoneRow, stoneCol);
        }
        int connectedComponents = 0;
        int[] parent = ds.parent;
        int[] size = ds.size;
        for(int i=0;i<maxRow+maxCol+1;i++){
            if(i == parent[i] && size[i]>1){
                connectedComponents++;
            }
        }
        return totalStones-connectedComponents;
    }
    
    class DisjointSet {
        int V;
        int[] parent;
        int[] rank;
        int[] size;

        public DisjointSet(int V){
            this.V = V;
            this.parent = new int[V];
            for(int i=0;i<V;i++){
                this.parent[i] = i; // parent is itself
            }
            this.rank = new int[V]; // rank = 0
            this.size = new int[V];
            Arrays.fill(this.size, 1);
        }

        public void unionByRank(int u, int v){
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

        public void unionBySize(int u, int v){
            int pu = findParent(u);
            int pv = findParent(v);
            if(pu == pv){
                return;
            }
            int sizeu = this.size[pu];
            int sizev = this.size[pv];
            if(sizeu == sizev || sizeu > sizev){
                // attach pv to pu
                this.parent[pv] = pu;
                this.size[pu] += sizev;
            }else {
                // attach pu to pv
                this.parent[pu] = pv;
                this.size[pv] += sizeu;
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

};
