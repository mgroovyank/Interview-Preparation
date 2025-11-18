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

// DP
// Time Complexity: O(n)
// Space Complexity: O(n) + O(n) - recursion stack + dp
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        // base case for recursion would be when I'm already at top = nth index
        // f(i) = min cost to reach nth index for ith index
        // f(i) = cost[i] + Math.min(f(i+1), f(i+2))
        // recursion tree shows lots of  overlapping subproblems
        // DP
        int n = cost.length;
        int[] dp = new int[n+1];
        Arrays.fill(dp, -1);
        dp[n] = 0;
        return Math.min(minCostClimbingStairsFromIthIdx(cost, 0, dp), minCostClimbingStairsFromIthIdx(cost, 1, dp));
    }

    private int minCostClimbingStairsFromIthIdx(int[] cost, int i, int[] dp){
        // base case
        if(i > cost.length){
            return Integer.MAX_VALUE;
        }
        if(dp[i] != -1){
            return dp[i];
        }
        // do everything on index
        return dp[i] = cost[i] + Math.min(
            minCostClimbingStairsFromIthIdx(cost, i+1, dp),
            minCostClimbingStairsFromIthIdx(cost, i+2, dp));
    }
}

// Tabulation - Bottom Up Approach
// Time Complexity: O(n)
// Space Complexity: O(n)
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        // base case for recursion would be when I'm already at top = nth index
        // f(i) = min cost to reach nth index for ith index
        // f(i) = cost[i] + Math.min(f(i+1), f(i+2))
        // recursion tree shows lots of  overlapping subproblems
        // DP
        // DP can be converted to Tabulation approach - Bottom Up Approach - start from base case
        int n = cost.length;
        int[] dp = new int[n+1];
        Arrays.fill(dp, -1);
        // base case
        dp[n] = 0;
        for(int i=n-1;i>=0;i--){
            // do everything on index
            int climb1 = Integer.MAX_VALUE;
            int climb2 = Integer.MAX_VALUE;
            if(i+1 <=n){
                climb1 = cost[i] + dp[i+1];
            }
            if(i+2 <= n){
                climb2 = cost[i] + dp[i+2];
            }
            dp[i] = Math.min(climb1, climb2);
        }
        return Math.min(dp[0], dp[1]);
    }
}

// Space Opitmized
// Time Complexity: O(n)
// Space Complexity: O(1)
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        // base case for recursion would be when I'm already at top = nth index
        // f(i) = min cost to reach nth index for ith index
        // f(i) = cost[i] + Math.min(f(i+1), f(i+2))
        // recursion tree shows lots of  overlapping subproblems
        // DP
        // DP can be converted to Tabulation approach - Bottom Up Approach - start from base case
        // I can further space optimize the solution as only dp[i+1] and dp[i+2] values are required at any point of time
        int n = cost.length;
        int[] dp = new int[n+1];
        Arrays.fill(dp, -1);
        // base case
        int iPlus1 = Integer.MAX_VALUE;
        int iPlus2 = Integer.MAX_VALUE;
        iPlus1 = 0;
        for(int i=n-1;i>=0;i--){
            // do everything on index
            int climb1 = Integer.MAX_VALUE;
            int climb2 = Integer.MAX_VALUE;
            if(i+1 <=n){
                climb1 = cost[i] + iPlus1;
            }
            if(i+2 <= n){
                climb2 = cost[i] + iPlus2;
            }
            iPlus2 = iPlus1;
            iPlus1 = Math.min(climb1, climb2);
        }
        return Math.min(iPlus1, iPlus2);
    }
}
