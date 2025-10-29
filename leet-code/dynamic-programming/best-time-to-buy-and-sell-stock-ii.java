class Solution {
    public int maxProfit(int[] prices) {
        // 1. you cannot have two consecutive buys
        // 2. => you can't have two consecutive sells, as you can hold only one share and 
        // hence if you sell once, you need to first buy again
        // 3. any day you enter either with - 0 share or 1 share(prev S or prev B)
        // 4. at end of day - 0 share or 1 share(last sell or last buy)
        // 5. so at any day what was the last txn(B/S) - only that matters
        // if you look at one more day to your cycle - you can sell on that day only
        // if you BUY AFTER THE LAST SELL
        int maxProfit = 0;
        int n = prices.length;
        int minPrice = prices[0];
        for(int day=1;day<n;day++) {
            int profit = prices[day] - minPrice; // sellPrice - buyPrice
            if(profit > 0){
                maxProfit += profit;
                minPrice = prices[day];
            }
            minPrice = Math.min(minPrice, prices[day]);
        }
        return maxProfit;
    }
}

// Recursion
class Solution {
    public int maxProfit(int[] prices) {
        // 1. on any day you can either buy/sell or do nothing - 2^n combinations for n days
        // 2. even if you do multiple buys and sells on same day - only the last odd buy/sell
        // on that day matters
        // f(i, buy) = max profit from ith day to n-th day, given buy condition on ith day
        // Recursion - Top Down Approach
        int days = prices.length;
        return maxProfitFromIthDay(prices, 0, true);
    }

    private int maxProfitFromIthDay(int[] prices, int i, boolean buyFlag){
        // base case
        if(i == prices.length){
            return 0;
        }
        if(buyFlag){
            //buy
            int buy = -prices[i] + maxProfitFromIthDay(prices, i+1, false);
            int notBuy = maxProfitFromIthDay(prices, i+1, true);
            return Math.max(buy, notBuy);
        }
        int sell = prices[i] + maxProfitFromIthDay(prices, i+1, true);
        int notSell = maxProfitFromIthDay(prices, i+1, false);
        return Math.max(sell, notSell);
    }
}

// DP
// Time Complexity: O(n*2)
// Space Complexity: O(n*2) + O(n)
class Solution {
    public int maxProfit(int[] prices) {
        // 1. on any day you can either buy/sell or do nothing - 2^n combinations for n days
        // 2. even if you do multiple buys and sells on same day - only the last odd buy/sell
        // on that day matters
        // f(i, buy) = max profit from ith day to n-th day, given buy condition on ith day
        // Recursion - Top Down Approach
        // Overlapping subproblems - DP
        int days = prices.length;
        int[][] dp = new int[days+1][2];
        for(int[] d: dp){
            Arrays.fill(d, Integer.MIN_VALUE);
        }
        return maxProfitFromIthDay(prices, 0, 1, dp);
    }

    private int maxProfitFromIthDay(int[] prices, int i, int buyFlag, int[][] dp){
        // base case
        if(i == prices.length){
            return dp[prices.length][buyFlag] = 0;
        }
        if(dp[i][buyFlag] != Integer.MIN_VALUE){
            return dp[i][buyFlag];
        }
        if(buyFlag == 1){
            //buy
            int buy = -prices[i] + maxProfitFromIthDay(prices, i+1, 0, dp);
            int notBuy = maxProfitFromIthDay(prices, i+1, 1, dp);
            return dp[i][buyFlag] = Math.max(buy, notBuy);
        }
        int sell = prices[i] + maxProfitFromIthDay(prices, i+1, 1, dp);
        int notSell = maxProfitFromIthDay(prices, i+1, 0, dp);
        return dp[i][buyFlag] = Math.max(sell, notSell);
    }
}

// Tabulation - Bottom Up Approach
// Time Complexity: O(n*2)
// Space Complexity: O(n*2)
class Solution {
    public int maxProfit(int[] prices) {
        // 1. on any day you can either buy/sell or do nothing - 2^n combinations for n days
        // 2. even if you do multiple buys and sells on same day - only the last odd buy/sell
        // on that day matters
        // f(i, buy) = max profit from ith day to n-th day, given buy condition on ith day
        // Tabulation - Bottom Up Approach
        int days = prices.length;
        int[][] dp = new int[days+1][2];
        dp[days-1][0] = 0;
        dp[days-1][1] = 0;
        for(int day=days-1;day>=0;day--) {
            int buy = -prices[day] + dp[day+1][0];
            int notBuy = dp[day+1][1];
            dp[day][1] = Math.max(buy, notBuy);
            int sell = prices[day] + dp[day+1][1];
            int notSell = dp[day+1][0];
            dp[day][0] = Math.max(sell, notSell);
        }
        return dp[0][1];
    }
}

// Space Optimization
class Solution {
    public int maxProfit(int[] prices) {
        // 1. on any day you can either buy/sell or do nothing - 2^n combinations for n days
        // 2. even if you do multiple buys and sells on same day - only the last odd buy/sell
        // on that day matters
        // f(i, buy) = max profit from ith day to n-th day, given buy condition on ith day
        // Tabulation - Bottom Up Approach
        // Space Optimization
        int days = prices.length;
        int[] dp = new int[2];
        dp[0] = 0;
        dp[1] = 0;
        for(int day=days-1;day>=0;day--) {
            int[] curr = new int[2];
            int buy = -prices[day] + dp[0];
            int notBuy = dp[1];
            curr[1] = Math.max(buy, notBuy);
            int sell = prices[day] + dp[1];
            int notSell = dp[0];
            curr[0] = Math.max(sell, notSell);
            dp = curr;
        }
        return dp[1];
    }
}
