// https://leetcode.com/problems/coin-change-ii/

// Time Complexity: Exponential
// Space Complexity: O(n)
class Solution {
    public int change(int amount, int[] coins) {
        int n = coins.length;
        return waysToAmountTillIthIdx(coins, n-1, amount);
    }

    private int waysToAmountTillIthIdx(int[] coins, int i, int amount){
        // base case
        if(i==0){
            if(amount % coins[0] == 0){
                return 1;
            }
            return 0;
        }
        int notTake = waysToAmountTillIthIdx(coins, i-1, amount);
        int take = 0;
        if(amount >= coins[i]){
            take = waysToAmountTillIthIdx(coins, i, amount-coins[i]);
        }
        return notTake + take;
    }
}

// Time Complexity: O(n*amount)
// Space Complexity: O(n*amount) + O(n)
class Solution {
    public int change(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n][amount+1];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return waysToAmountTillIthIdx(coins, n-1, amount, dp);
    }

    private int waysToAmountTillIthIdx(int[] coins, int i, int amount, int[][] dp){
        // base case
        if(i==0){
            if(amount % coins[0] == 0){
                return 1;
            }
            return 0;
        }
        if(dp[i][amount] != -1){
            return dp[i][amount];
        }
        int notTake = waysToAmountTillIthIdx(coins, i-1, amount, dp);
        int take = 0;
        if(amount >= coins[i]){
            take = waysToAmountTillIthIdx(coins, i, amount-coins[i], dp);
        }
        return dp[i][amount] = notTake + take;
    }
}
