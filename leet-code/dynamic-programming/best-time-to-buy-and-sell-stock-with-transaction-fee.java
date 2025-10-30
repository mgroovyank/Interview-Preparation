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
