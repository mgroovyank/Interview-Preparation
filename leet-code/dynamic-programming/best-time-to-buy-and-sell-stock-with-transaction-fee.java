// https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/

class Solution {
    public int maxProfit(int[] prices, int fee) {
        int n= prices.length;
        int[][] dp = new int[n+1][2];
        for(int[] d: dp){
            Arrays.fill(d, Integer.MIN_VALUE);
        }
        return maxProfitFromIthDay(prices, fee, 0, 1, dp);
    }
    private int maxProfitFromIthDay(int[] prices, int fee, int i, int buyFlag, int[][] dp){
        // base case
        if(i == prices.length){
            return 0;
        }

        if(dp[i][buyFlag] != Integer.MIN_VALUE){
            return dp[i][buyFlag];
        }

        if(buyFlag == 1){
            int buy = -prices[i] + maxProfitFromIthDay(prices, fee, i+1, 0, dp);
            int notBuy = maxProfitFromIthDay(prices, fee, i+1, 1, dp);
            return dp[i][buyFlag] = Math.max(buy, notBuy);
        }
        int sell = prices[i]-fee + maxProfitFromIthDay(prices, fee, i+1, 1, dp);
        int notSell = maxProfitFromIthDay(prices, fee, i+1, 0, dp);
        return dp[i][buyFlag] = Math.max(sell, notSell);
    }
}

// Recursion
class Solution {
    public int maxProfit(int[] prices, int fee) {
        // Recursion - Top Down Approach
        // Since I know my intial buyFlag=1, I start processing from index=0
        // f(i, buyFlag) = maxProfitFromIthDay with given buyFlag for ith day
        // my first activity has to be a Buy
        return maxProfitFromIthDay(prices, fee, 0, 1);
    }
    private int maxProfitFromIthDay(int[] prices, int fee, int i, int buyFlag){
        // base case
        if(i == prices.length){
            return 0;
        }
        if(buyFlag == 1){
            int buy = -prices[i] + maxProfitFromIthDay(prices, fee, i+1, 0);
            int notBuy = maxProfitFromIthDay(prices, fee, i+1, buyFlag);
            // if buy is allowed that means, I don't have any stock held, that means I can't sell.
            return Math.max(buy, notBuy);
        }
        int sell = prices[i] - fee + maxProfitFromIthDay(prices, fee, i+1, 1);
        int notSell = maxProfitFromIthDay(prices, fee, i+1, buyFlag);
        return Math.max(sell, notSell);
    }
    
}

// DP
class Solution {
    public int maxProfit(int[] prices, int fee) {
        // Recursion - Top Down Approach
        // Since I know my intial buyFlag=1, I start processing from index=0
        // f(i, buyFlag) = maxProfitFromIthDay with given buyFlag for ith day
        // overlapping subproblems - DP
        int n = prices.length;
        int[][] dp = new int[n+1][2];
        for(int[] d: dp){
            Arrays.fill(d, Integer.MIN_VALUE);
        }
        return maxProfitFromIthDay(prices, fee, 0, 1, dp);
    }
    private int maxProfitFromIthDay(int[] prices, int fee, int i, int buyFlag, int[][] dp){
        // base case
        if(i == prices.length){
            return 0;
        }
        if(dp[i][buyFlag] != Integer.MIN_VALUE){
            return dp[i][buyFlag];
        }
        if(buyFlag == 1){
            int buy = -prices[i] + maxProfitFromIthDay(prices, fee, i+1, 0, dp);
            int notBuy = maxProfitFromIthDay(prices, fee, i+1, buyFlag, dp);
            // if buy is allowed that means, I don't have any stock held, that means I can't sell.
            return dp[i][buyFlag] = Math.max(buy, notBuy);
        }
        int sell = prices[i] - fee + maxProfitFromIthDay(prices, fee, i+1, 1, dp);
        int notSell = maxProfitFromIthDay(prices, fee, i+1, buyFlag, dp);
        return dp[i][buyFlag] = Math.max(sell, notSell);
    }
}

// Tabulation
// Time Complexity: O(n*2)
// Space Complexity: O(n*2)
class Solution {
    public int maxProfit(int[] prices, int fee) {
        // Recursion - Top Down Approach
        // Since I know my intial buyFlag=1, I start processing from index=0
        // f(i, buyFlag) = maxProfitFromIthDay with given buyFlag for ith day
        // overlapping subproblems - DP
        // Bottom Up Approach - Tabulation - to remove recursion stack memory
        int n = prices.length;
        int[][] dp = new int[n+1][2];
        // base case
        dp[n][0] = 0;
        dp[n][1] = 0;
        for(int i=n-1;i>=0;i--){
            int buy = -prices[i] + dp[i+1][0];
            int notBuy = dp[i+1][1];
            // if buy is allowed that means, I don't have any stock held, that means I can't sell.
            dp[i][1] = Math.max(buy, notBuy);

            int sell = prices[i] - fee + dp[i+1][1];
            int notSell = dp[i+1][0];
            dp[i][0] = Math.max(sell, notSell);
        }
        return dp[0][1];
    }
}

// Space Optimization
// Time Complexity: O(n*2)
// Space Complexity: O(n*2)
class Solution {
    public int maxProfit(int[] prices, int fee) {
        // Recursion - Top Down Approach
        // Since I know my intial buyFlag=1, I start processing from index=0
        // f(i, buyFlag) = maxProfitFromIthDay with given buyFlag for ith day
        // overlapping subproblems - DP
        // Bottom Up Approach - Tabulation - to remove recursion stack memory
        // I only dp[i+1] values to calculate dp[i] - so I can do space optimization
        int n = prices.length;
        int[] dp = new int[2];
        // base case
        dp[0] = 0;
        dp[1] = 0;
        for(int i=n-1;i>=0;i--){
            int[] curr = new int[2];
            int buy = -prices[i] + dp[0];
            int notBuy = dp[1];
            // if buy is allowed that means, I don't have any stock held, that means I can't sell.
            curr[1] = Math.max(buy, notBuy);

            int sell = prices[i] - fee + dp[1];
            int notSell = dp[0];
            curr[0] = Math.max(sell, notSell);
            dp = curr;
        }
        return dp[1];
    }
}
