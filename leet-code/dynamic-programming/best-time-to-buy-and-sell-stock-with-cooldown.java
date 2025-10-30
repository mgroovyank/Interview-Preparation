// https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/description/

// DP
class Solution {
    public int maxProfit(int[] prices) {
        // after sell on ith day - i don't hold any stock on i+1th day, so I can't sell
        // also i can't buy because of cooldown, so I just skip i+1th day after sell on ith day
        int n = prices.length;
        int[][] dp = new int[n+2][2];
        for(int[] d: dp){
            Arrays.fill(d, Integer.MIN_VALUE);
        }
        return maxProfitAfterIthDay(prices, 0, 1, dp);
    }

    private int maxProfitAfterIthDay(int[] prices, int i, int buyFlag, int[][] dp){
        // base case
        if(i == prices.length || i == prices.length+1){
            return 0;
        }
        if(dp[i][buyFlag] != Integer.MIN_VALUE){
            return dp[i][buyFlag];
        }
        if(buyFlag == 1){
            int buy = -prices[i] + maxProfitAfterIthDay(prices, i+1, 0, dp);
            int notBuy = maxProfitAfterIthDay(prices, i+1, 1, dp);
            return dp[i][buyFlag] = Math.max(buy, notBuy);
        }
        int sell = prices[i] + maxProfitAfterIthDay(prices, i+2, 1, dp);
        int notSell = maxProfitAfterIthDay(prices, i+1, 0, dp);
        return dp[i][buyFlag] = Math.max(sell, notSell);
    }
}

// Tabulation - Bottom Up Approach

class Solution {
    public int maxProfit(int[] prices) {
        // after sell on ith day - i don't hold any stock on i+1th day, so I can't sell
        // also i can't buy because of cooldown, so I just skip i+1th day after sell on ith day
        // Tabulation
        int n = prices.length;
        int[][] dp = new int[n+2][2];
        dp[n][0] = 0;
        dp[n][1] = 0;
        dp[n+1][0] = 0;
        dp[n+1][1] = 0;
        for(int i=n-1;i>=0;i--){
            int buy = -prices[i] + dp[i+1][0];
            int notBuy = dp[i+1][1];
            dp[i][1] = Math.max(buy, notBuy);
            int sell = prices[i] + dp[i+2][1];
            int notSell = dp[i+1][0];
            dp[i][0] = Math.max(sell, notSell);
        }
        return dp[0][1];
    }
}
