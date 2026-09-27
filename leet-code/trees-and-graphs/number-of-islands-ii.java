// https://www.geeksforgeeks.org/problems/number-of-islands/1

// Time Complexity: O(K*N*M) K queries
// Space Complexity: O(N*M)
class Solution {
    public ArrayList<Integer> numOfIslands(int n, int m, int[][] operators) {
        int totalNodes = n*m; // total number of nodes
        DisjointSet ds = new DisjointSet(totalNodes);
        int[] isIsland = new int[totalNodes];
        ArrayList<Integer> result = new ArrayList<>();
        for(int[] operator: operators){
            int i = operator[0];
            int j = operator[1];
            int currNode = i * m + j;
            isIsland[currNode] = 1;
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
                        ds.union(currNode, nextNode);
                    }
                }
            }
            
            int numIslands = 0;
            for(int k=0;k<totalNodes;k++){
                if(isIsland[k] == 1 && k == ds.findParent(k)){
                    numIslands++;
                }
            }
            result.add(numIslands);
        }
        
        return result;
        
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


// Time Complexity: O(K)
// Space Complexity: O(N*M)
class Solution {
    public ArrayList<Integer> numOfIslands(int n, int m, int[][] operators) {
        int totalNodes = n*m; // total number of nodes
        DisjointSet ds = new DisjointSet(totalNodes);
        int[] isIsland = new int[totalNodes];
        ArrayList<Integer> result = new ArrayList<>();
        int count = 0;
        for(int[] operator: operators){
            int i = operator[0];
            int j = operator[1];
            int currNode = i * m + j;
            if(isIsland[currNode] == 1){
                result.add(count);
                continue;
            }
            isIsland[currNode] = 1;
            count++;
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
                        // if currNode and nextNode are not already connected
                        // then do union
                        int parentCurrNode = ds.findParent(currNode);
                        int parentNextNode = ds.findParent(nextNode);
                        if(parentCurrNode != parentNextNode){
                            ds.union(currNode, nextNode);
                            count--;
                        }
                    }
                }
            }
            result.add(count);
        }
        
        return result;
        
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
