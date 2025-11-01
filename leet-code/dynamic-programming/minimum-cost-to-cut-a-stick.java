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
