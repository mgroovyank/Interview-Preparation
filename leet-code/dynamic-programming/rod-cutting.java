// https://www.geeksforgeeks.org/problems/rod-cutting0840/1

// Time Complexity: Exponential
// Space Complexity: O(N)
class Solution {
    public int cutRod(int[] price) {
        // similar to coin denominations problem
        int n = price.length;
        return maxValueTillIthIdx(n-1, n, price);
    }
    
    private int maxValueTillIthIdx(int i, int remainingLen, int[] price){
        // base case
        if(i == 0){
            return (remainingLen/1) * price[i];
        }
        int notTake = maxValueTillIthIdx(i-1, remainingLen, price);
        int take = 0;
        if(remainingLen>=i+1){
            take = price[i]*1 + maxValueTillIthIdx(i, remainingLen-i-1, price);
        }
        return Math.max(notTake, take);
    }
}

class Solution {
    public int cutRod(int[] price) {
        // similar to coin denominations problem
        int n = price.length;
        int[][] dp = new int[n][n+1];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return maxValueTillIthIdx(n-1, n, price, dp);
    }
    
    private int maxValueTillIthIdx(int i, int remainingLen, int[] price, int[][] dp){
        // base case
        if(i == 0){
            return (remainingLen/1) * price[i];
        }
        if(dp[i][remainingLen] != -1){
            return dp[i][remainingLen];
        }
        int notTake = maxValueTillIthIdx(i-1, remainingLen, price, dp);
        int take = 0;
        if(remainingLen>=i+1){
            take = price[i]*1 + maxValueTillIthIdx(i, remainingLen-i-1, price, dp);
        }
        return dp[i][remainingLen] = Math.max(notTake, take);
    }
}
