// https://leetcode.com/problems/min-cost-climbing-stairs/

// Time Complexity: O(2^n)
// Space Complexity: O(n)
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        // Top Down Approach
        // base case for recursion would be when I'm already at top = nth index
        // f(i) = min cost to reach nth index for ith index
        // f(i) = cost[i] + Math.min(f(i+1), f(i+2))
        return Math.min(minCostClimbingStairsFromIthIdx(cost, 0), minCostClimbingStairsFromIthIdx(cost, 1));
    }

    private int minCostClimbingStairsFromIthIdx(int[] cost, int i){
        // base case
        if(i == cost.length){
            return 0;
        }
        if(i > cost.length){
            return Integer.MAX_VALUE;
        }
        // do everything on index
        return cost[i] + Math.min(
            minCostClimbingStairsFromIthIdx(cost, i+1),
            minCostClimbingStairsFromIthIdx(cost, i+2));
    }
}
