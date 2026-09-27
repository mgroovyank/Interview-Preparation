// https://www.geeksforgeeks.org/problems/maximum-connected-group/1

// Time Complexity: O(n*m)
// Space Complexity: O(n*m)
class Solution {
    public int maxConnection(int grid[][]) {
        int n = grid.length;
        int m = grid[0].length;
        int totalNodes = n*m;
        DisjointSet ds = new DisjointSet(totalNodes);
        int[] isIsland = new int[totalNodes];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                int currNode = i*m+j;
                isIsland[currNode] = grid[i][j];
                if(isIsland[currNode] == 0){
                    continue;
                }
                int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                for(int[] direction: directions){
                    int delI = direction[0];
                    int delJ = direction[1];
                    int nextI = i + delI;
                    int nextJ = j + delJ;
                    if(nextI>=0 && nextI<n && nextJ>=0 && nextJ<m){
                        int nextNode = nextI * m + nextJ;
                        if(isIsland[nextNode] == 1){
                            // found edge
                            ds.unionBySize(currNode, nextNode);
                        }
                    }
                }
            }
        }
        
        int ans = Integer.MIN_VALUE;
        for(int i=0;i<totalNodes;i++){
            int parent = ds.findParent(i);
            if(isIsland[i]==1 && i == parent){
                ans = Math.max(ans, ds.size[i]);
            }
        }
        
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j] == 0){
                    // convert to 1;
                    int currNode = i*m + j;
                    int currSize = 1;
                    int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                    Set<Integer> parents = new HashSet<>();
                    for(int[] direction: directions){
                        int delI = direction[0];
                        int delJ = direction[1];
                        int nextI = i + delI;
                        int nextJ = j + delJ;
                        if(nextI>=0 && nextI<n && nextJ>=0 && nextJ<m){
                            int nextNode = nextI * m + nextJ;
                            if(isIsland[nextNode] == 1){
                                // found edge
                                int parentNextNode = ds.findParent(nextNode);
                                parents.add(parentNextNode);
                            }
                        }
                    }
                    for(int parent: parents){
                        int s = ds.size[parent];
                        currSize = currSize + s;
                    }
                    ans = Math.max(ans, currSize);
                }
            }
        }
        return ans;
        
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
}
