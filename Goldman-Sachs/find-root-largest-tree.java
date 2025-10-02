/**
You are given a forest represented as a map of child → parent relationships. Each key-value pair indicates that the key is a child of the value. The forest is guaranteed to be well-formed: each tree has exactly one root, and no child has more than one parent.
Your task is to find the root of the largest tree (i.e., the tree with the most nodes). If multiple trees have the same largest size, return the smallest root ID among them.

**/
// N = number of nodes, M = number of edges
// Time Complexity: O(N+M)
// Space Complexiy: O(N)
// Disjoint set union rank algorithm pattern
class Solution {
    private int[] parent;
    private int[] rank;
    private int[] count;

    public int findRootOfLargestTree(int n, int[][] immediateParent) {
        parent = new int[n + 1]; // 1-based indexing
        rank = new int[n + 1];
        count = new int[n + 1];

        // Initialize parent and rank
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        // Union each child with its parent
        for (int[] edge : immediateParent) {
            union(edge[0], edge[1]);
        }

        // Count size of each tree root
        for (int i = 1; i <= n; i++) {
            int root = find(i);
            count[root]++;
        }

        // Find root with max size (first one encountered in ascending order)
        int maxSize = 0;
        int resultRoot = -1;
        for (int i = 1; i <= n; i++) {
            if (count[i] > maxSize) {
                maxSize = count[i];
                resultRoot = i;
            }
        }

        return resultRoot;
    }

    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }

    private void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA == rootB) return;

        if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
        } else if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }
    }
}

