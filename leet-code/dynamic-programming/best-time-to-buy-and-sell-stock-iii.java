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

// DP
// Time Complexity: O(n*2*3)
// Space Complexity: O(n*2*3) + O(n)
class Solution {
    public int maxProfit(int[] prices) {
        // Recursion - Top Down Approach
        // f(i, buyFlag, remainingTxns) = max Profit from ith day to n-1th day, given buyFlag
        // for ith day and number of remaining txns you can perform
        int days = prices.length;
        int[][][] dp = new int[days+1][2][3];
        for(int[][] d1: dp){
            for(int[] d2: d1){
                Arrays.fill(d2, Integer.MIN_VALUE);
            }
        }
        return maxProfitFromIthDay(prices, 0, 1, 2, dp);
    }

    private int maxProfitFromIthDay(int[] prices, int i, int buyFlag, int remainingTxns, int[][][] dp){
        // base case
        if(i == prices.length){
            return 0;
        }
        if(remainingTxns == 0){
            return 0;
        }

        if(dp[i][buyFlag][remainingTxns] != Integer.MIN_VALUE){
            return dp[i][buyFlag][remainingTxns];
        }

        if(buyFlag == 1){
            int buy = -prices[i] + maxProfitFromIthDay(prices, i+1, 0, remainingTxns, dp);
            int notBuy = maxProfitFromIthDay(prices, i+1, 1, remainingTxns, dp);
            return dp[i][buyFlag][remainingTxns] = Math.max(buy, notBuy);
        }
        int sell = prices[i] + maxProfitFromIthDay(prices, i+1, 1, remainingTxns-1, dp);
        int notSell = maxProfitFromIthDay(prices, i+1, 0, remainingTxns, dp);
        return dp[i][buyFlag][remainingTxns] = Math.max(sell, notSell);
    }
}

// Tabulation - Bottom Up Approach
class Solution {
    public int maxProfit(int[] prices) {
        // tabulation - Bottom Up Approach
        int days = prices.length;
        int[][][] dp = new int[days+1][2][3];
        // base case
        for(int buyFlag=0;buyFlag<2;buyFlag++){
            for(int remainingTxns=0;remainingTxns<3;remainingTxns++){
                dp[days][buyFlag][remainingTxns] = 0;
            }
        }
        for(int day=0;day<=days;day++){
            for(int buyFlag=0;buyFlag<2;buyFlag++){
                dp[day][buyFlag][0] = 0;
            }
        }
        // access states bottom up - reverse of recursion order
        for(int day=days-1;day>=0;day--){
            for(int buyFlag=0;buyFlag<2;buyFlag++){
                for(int remainingTxns=1;remainingTxns<3;remainingTxns++){
                    if(buyFlag == 1){
                        int buy = -prices[day] + dp[day+1][0][remainingTxns];
                        int notBuy = dp[day+1][1][remainingTxns];
                        dp[day][buyFlag][remainingTxns] = Math.max(buy, notBuy);
                        continue;
                    }
                    int sell = prices[day] + dp[day+1][1][remainingTxns-1];
                    int notSell = dp[day+1][0][remainingTxns];
                    dp[day][buyFlag][remainingTxns] = Math.max(sell, notSell);
                }
            }
        }
        return dp[0][1][2];
    }
}

// Space Optimization
class Solution {
    public int maxProfit(int[] prices) {
        // tabulation - Bottom Up Approach
        int days = prices.length;
        int[][] after = new int[2][3];
        // base case
        for(int buyFlag=0;buyFlag<2;buyFlag++){
            for(int remainingTxns=0;remainingTxns<3;remainingTxns++){
                after[buyFlag][remainingTxns] = 0;
            }
        }
        // access states bottom up - reverse of recursion order
        for(int day=days-1;day>=0;day--){
            int[][] curr = new int[2][3];
            curr[0][0] = 0;
            curr[1][0] = 0;
            for(int buyFlag=0;buyFlag<2;buyFlag++){
                for(int remainingTxns=1;remainingTxns<3;remainingTxns++){
                    if(buyFlag == 1){
                        int buy = -prices[day] + after[0][remainingTxns];
                        int notBuy = after[1][remainingTxns];
                        curr[buyFlag][remainingTxns] = Math.max(buy, notBuy);
                        continue;
                    }
                    int sell = prices[day] + after[1][remainingTxns-1];
                    int notSell = after[0][remainingTxns];
                    curr[buyFlag][remainingTxns] = Math.max(sell, notSell);
                }
            }
            after = curr;
        }
        return after[1][2];
    }
}


// N*4 Solutions
class Solution {
    public int maxProfit(int[] prices) {
        // recursion - N*4
        int days = prices.length;
        return maxProfitFromIthDay(prices, 0, 0);
    }

    private int maxProfitFromIthDay(int[] prices, int i, int txnNumber){
        // base case
        if(i == prices.length || txnNumber == 4){
            return 0;
        }
        // even transaction - has to be a buy
        if(txnNumber%2 == 0){
            int buy = -prices[i] + maxProfitFromIthDay(prices, i+1, txnNumber+1);
            int notBuy = maxProfitFromIthDay(prices, i+1, txnNumber);
            return Math.max(buy, notBuy);
        }
        int sell = prices[i] + maxProfitFromIthDay(prices, i+1, txnNumber+1);
        int notSell = maxProfitFromIthDay(prices, i+1, txnNumber);
        return Math.max(sell, notSell);
    }

}

// DP N*4
class Solution {
    public int maxProfit(int[] prices) {
        // recursion - N*4
        int days = prices.length;
        int[][] dp = new int[days+1][4];
        for(int[] d: dp){
            Arrays.fill(d, Integer.MIN_VALUE);
        }
        return maxProfitFromIthDay(prices, 0, 0, dp);
    }

    private int maxProfitFromIthDay(int[] prices, int i, int txnNumber, int[][] dp){
        // base case
        if(i == prices.length || txnNumber == 4){
            return 0;
        }
        if(dp[i][txnNumber] != Integer.MIN_VALUE){
            return dp[i][txnNumber];
        }
        // even transaction - has to be a buy
        if(txnNumber%2 == 0){
            int buy = -prices[i] + maxProfitFromIthDay(prices, i+1, txnNumber+1, dp);
            int notBuy = maxProfitFromIthDay(prices, i+1, txnNumber, dp);
            return dp[i][txnNumber] = Math.max(buy, notBuy);
        }
        int sell = prices[i] + maxProfitFromIthDay(prices, i+1, txnNumber+1, dp);
        int notSell = maxProfitFromIthDay(prices, i+1, txnNumber, dp);
        return dp[i][txnNumber] = Math.max(sell, notSell);
    }

}
