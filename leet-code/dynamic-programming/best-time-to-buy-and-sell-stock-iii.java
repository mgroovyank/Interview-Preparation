// https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/

class Solution {
    public int maxProfit(int[] prices) {
        // Recursion - Top Down Approach
        // f(i, buyFlag, remainingTxns) = max Profit from ith day to n-1th day, given buyFlag
        // for ith day and number of remaining txns you can perform
        int days = prices.length;
        return maxProfitFromIthDay(prices, 0, 1, 2);
    }

    private int maxProfitFromIthDay(int[] prices, int i, int buyFlag, int remainingTxns){
        // base case
        if(i == prices.length){
            return 0;
        }
        if(remainingTxns == 0){
            return 0;
        }
        if(buyFlag == 1){
            int buy = -prices[i] + maxProfitFromIthDay(prices, i+1, 0, remainingTxns);
            int notBuy = maxProfitFromIthDay(prices, i+1, 1, remainingTxns);
            return Math.max(buy, notBuy);
        }
        int sell = prices[i] + maxProfitFromIthDay(prices, i+1, 1, remainingTxns-1);
        int notSell = maxProfitFromIthDay(prices, i+1, 0, remainingTxns);
        return Math.max(sell, notSell);
    }
}


