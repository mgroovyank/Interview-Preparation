// https://leetcode.com/problems/all-paths-from-source-to-target/

// Time Complexity: worst case, each node as edge to other next nodes => O(2^n * n)
// Space Complexity: O(n) n - number of nodes

class Solution {
    List<List<Integer>> paths = new ArrayList<>();
    List<Integer> currPath = new ArrayList<>();

    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        // 1. since graph is acyclic, that means from any node, if I travel along the edges
        // I can't come back to same node, so no need of a visited array
        // 2. Also, I should not use a visited array as it might happen that same node is part of multiple paths, especially
        // if I'm using DFS for graph traversal
        
        currPath.add(0);
        dfs(graph, 0);
        return paths;
    }

    private void dfs(int[][] graph, int currNode){
        if(currNode == graph.length - 1){
            paths.add(new ArrayList<>(currPath));
            return;
        }
        for(int nextNode: graph[currNode]){
            currPath.add(nextNode);
            dfs(graph, nextNode);
            currPath.remove(currPath.size() - 1);
        }
    }
}
