class Solution {
    public int maxProfit(int[] prices) {
        // sell must be after buy
        // choose sell day and then choose buy on left side
        // choose buy day on left side with minimum price
        int n = prices.length;
        int minPrice = prices[0];
        int maxProfit = 0;
        for(int day=1;day<n;day++){
            int profit = prices[day] - minPrice;
            maxProfit = Math.max(maxProfit, profit);
            minPrice = Math.min(minPrice, prices[day]);
        }
        return maxProfit;
    }
}
