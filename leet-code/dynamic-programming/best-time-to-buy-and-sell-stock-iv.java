// https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/

class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        return maxProfitFromIthDay(prices, 2*k, 0, 0);
    }
    private int maxProfitFromIthDay(int[] prices, int totalTxns, int i, int txnNumber){
        // base case
        if(i==prices.length || txnNumber == totalTxns){
            return 0;
        }
        // buy
        if(txnNumber %2 == 0){
            int buy = -prices[i] + maxProfitFromIthDay(prices, totalTxns, i+1, txnNumber+1);
            int notBuy = maxProfitFromIthDay(prices, totalTxns, i+1, txnNumber);
            return Math.max(buy, notBuy);
        }
        int sell = prices[i] + maxProfitFromIthDay(prices, totalTxns, i+1, txnNumber+1);
        int notSell = maxProfitFromIthDay(prices, totalTxns, i+1, txnNumber);
        return Math.max(sell, notSell);
    }

}

// DP
class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+1][2*k];
        for(int[] d: dp){
            Arrays.fill(d, Integer.MIN_VALUE);
        }
        return maxProfitFromIthDay(prices, 2*k, 0, 0, dp);
    }
    private int maxProfitFromIthDay(int[] prices, int totalTxns, int i, int txnNumber, int[][] dp){
        // base case
        if(i==prices.length || txnNumber == totalTxns){
            return 0;
        }
        if(dp[i][txnNumber] != Integer.MIN_VALUE){
            return dp[i][txnNumber];
        }
        // buy
        if(txnNumber %2 == 0){
            int buy = -prices[i] + maxProfitFromIthDay(prices, totalTxns, i+1, txnNumber+1, dp);
            int notBuy = maxProfitFromIthDay(prices, totalTxns, i+1, txnNumber, dp);
            return  dp[i][txnNumber] = Math.max(buy, notBuy);
        }
        int sell = prices[i] + maxProfitFromIthDay(prices, totalTxns, i+1, txnNumber+1, dp);
        int notSell = maxProfitFromIthDay(prices, totalTxns, i+1, txnNumber, dp);
        return dp[i][txnNumber] = Math.max(sell, notSell);
    }

}
