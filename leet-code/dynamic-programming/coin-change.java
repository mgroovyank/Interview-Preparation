// https://leetcode.com/problems/coin-change/

// Recursion time complexity: O(2^n) - even more than that

// DP
// Time Complexity: O(n*amount)
// Space Complexity: O(n*amount) + O(amount)
class Solution {
    public int coinChange(int[] coins, int amount) {
        // Infinite supplies
        // Recursion - Top Down Approach
        int n = coins.length;
        int[][] dp = new int[n][amount+1];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        int ans = fewestCoinsTillIthIndex(coins, n-1, amount, dp);
        if(ans == Integer.MAX_VALUE){
            return -1;
        }
        return ans;
    }
    private int fewestCoinsTillIthIndex(int[] coins, int i, int amount, int[][] dp){
        // base case
        if(i == 0){
            if(amount == 0){
                return 0;
            }
            if(amount % coins[0] == 0){
                return amount / coins[0];
            }
            return Integer.MAX_VALUE; // Impossible to achieve amount, don't return -1/0
        }
        if(dp[i][amount] != -1){
            return dp[i][amount];
        }
        int notTake = fewestCoinsTillIthIndex(coins, i-1, amount, dp);
        int take = Integer.MAX_VALUE;
        if(amount >= coins[i]){
            int temp = fewestCoinsTillIthIndex(coins, i, amount - coins[i], dp);
            if(temp != Integer.MAX_VALUE){
                take = 1 + temp;
            }
        }
        return dp[i][amount] = Math.min(take, notTake);
    }
}

// Tabulation
class Solution {
    public int coinChange(int[] coins, int amount) {
        // Infinite supplies
        // Tabulation - Bottom Up Approach
        int n = coins.length;
        int[][] dp = new int[n][amount+1];
        // base case
        for(int a=0;a<=amount;a++){
            if(a % coins[0] == 0){
                dp[0][a] = a / coins[0];
            }else{
                dp[0][a] = Integer.MAX_VALUE;
            }
        }
        // do stuff
        for(int i=1;i<n;i++){
            for(int a=0;a<=amount;a++){
                int notTake = dp[i-1][a];
                int take = Integer.MAX_VALUE;
                if(a >= coins[i]){
                    int temp = dp[i][a - coins[i]];
                    if(temp != Integer.MAX_VALUE){
                        take = 1 + temp;
                    }
                }
                dp[i][a] = Math.min(take, notTake);
            }
        }
        int ans = dp[n-1][amount];
        if(ans == Integer.MAX_VALUE){
            return -1;
        }
        return ans;
    }
}
