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
