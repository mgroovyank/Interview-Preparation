// https://leetcode.com/problems/minimum-cost-to-cut-a-stick/

class Solution {
    public int minCost(int n, int[] cuts) {
        Arrays.sort(cuts);
        int numCuts = cuts.length;
        int[] newCuts = new int[numCuts + 2];
        for(int i=1;i<numCuts + 2-1;i++){
            newCuts[i] = cuts[i-1];
        }
        newCuts[0] = 0;
        newCuts[numCuts + 1] = n;
        return minCostForPartition(newCuts, 1, numCuts);
    }

    private int minCostForPartition(int[] newCuts, int i, int j){
        // base case
        if(i>j){
            return 0;
        }
        int cost = Integer.MAX_VALUE;
        for(int k=i;k<=j;k++){
            cost = Math.min(cost, newCuts[j+1] - newCuts[i-1] + minCostForPartition(newCuts, i, k-1) +
                minCostForPartition(newCuts, k+1, j));
        }
        return cost;
    }
}

// DP
// Time Complexity: O(n*n*n)
// Space Complexity: O(n*n) + O(n)
class Solution {
    public int minCost(int n, int[] cuts) {
        Arrays.sort(cuts);
        int numCuts = cuts.length + 2;
        int[] newCuts = new int[numCuts];
        for(int i=1;i<numCuts-1;i++){
            newCuts[i] = cuts[i-1];
        }
        newCuts[0] = 0;
        newCuts[numCuts-1] = n;
        int[][] dp = new int[numCuts][numCuts];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return minCostForPartition(newCuts, 1, numCuts-2, dp);
    }

    private int minCostForPartition(int[] newCuts, int i, int j, int[][] dp){
        // base case
        if(i>j){
            return 0;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        int cost = Integer.MAX_VALUE;
        for(int k=i;k<=j;k++){
            cost = Math.min(cost, newCuts[j+1] - newCuts[i-1] + minCostForPartition(newCuts, i, k-1, dp) +
                minCostForPartition(newCuts, k+1, j, dp));
        }
        return dp[i][j] = cost;
    }
}
