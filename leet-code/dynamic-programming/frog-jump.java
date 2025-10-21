// https://www.geeksforgeeks.org/problems/geek-jump/1

class Solution {
    int minCost(int[] height) {
        // Top Down Approach - recursion
        int n = height.length;
        return minCostTo(height, n-1);
    }
    
    private int minCostTo(int[] height, int i){
        // base case
        if(i == 0){
            return 0;
        }
        int mini = Integer.MAX_VALUE;
        if(i-1>=0){
            mini = Math.min(mini, Math.abs(height[i]-height[i-1]) + minCostTo(height, i-1));
        }
        if(i-2>=0){
            mini = Math.min(mini, Math.abs(height[i]-height[i-2]) + minCostTo(height, i-2));
        }
        return mini;
    }
}

// DP
class Solution {
    int minCost(int[] height) {
        // Top Down Approach - recursion
        int n = height.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return minCostTo(height, n-1, dp);
    }
    
    private int minCostTo(int[] height, int i, int[] dp){
        // base case
        if(i == 0){
            return 0;
        }
        if(dp[i] != -1){
            return dp[i];
        }
        int mini = Integer.MAX_VALUE;
        if(i-1>=0){
            mini = Math.min(mini, Math.abs(height[i]-height[i-1]) + minCostTo(height, i-1, dp));
        }
        if(i-2>=0){
            mini = Math.min(mini, Math.abs(height[i]-height[i-2]) + minCostTo(height, i-2, dp));
        }
        return dp[i] = mini;
    }
}

// Tabulation
class Solution {
    int minCost(int[] height) {
        // Top Down Approach - recursion
        int n = height.length;
        if(n == 1){
            return 0;
        }
        int[] dp = new int[n];
        dp[0] = 0;
        dp[1] = Math.abs(height[1] - height[0]);
        for(int i=2;i<n;i++){
            dp[i] = Math.min(Math.abs(height[i]-height[i-1]) + dp[i-1], 
                    Math.abs(height[i]-height[i-2]) + dp[i-2]);
        }
        return dp[n-1];
    }
}

// Space Optimized
class Solution {
    int minCost(int[] height) {
        // Space Optimization
        int n = height.length;
        if(n == 1){
            return 0;
        }
        int first = 0;
        int second = Math.abs(height[1] - height[0]);
        for(int i=2;i<n;i++){
            int temp = Math.min(Math.abs(height[i]-height[i-1]) + second, 
                    Math.abs(height[i]-height[i-2]) + first);
            first = second;
            second = temp;
        }
        return second;
    }
}
