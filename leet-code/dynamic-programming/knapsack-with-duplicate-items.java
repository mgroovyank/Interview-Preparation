// https://www.geeksforgeeks.org/problems/knapsack-with-duplicate-items4201/1

// Time Complexity: Exponential
// Space Complexity: O(N)
class Solution {
    static int knapSack(int val[], int wt[], int capacity) {
        int n = val.length;
        return maxProfitTillIthIdx(n-1, capacity, wt, val);
    }
    
    static int maxProfitTillIthIdx(int i, int capacity, int[] wt, int[] val){
        // base case
        if(i == 0){
            int num = capacity / wt[0];
            return num * val[0];
        }
        int notTake = maxProfitTillIthIdx(i-1, capacity, wt, val);
        int take = 0;
        if(capacity >= wt[i]){
            take = val[i] + maxProfitTillIthIdx(i, capacity-wt[i], wt, val);
        }
        return Math.max(notTake,  take);
    }
}

// User function Template for Java

// Time Complexity: O(C*N)
// Space Complexity: O(C*N) + O(N)
class Solution {
    static int knapSack(int val[], int wt[], int capacity) {
        int n = val.length;
        int[][] dp = new int[n][capacity+1];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return maxProfitTillIthIdx(n-1, capacity, wt, val, dp);
    }
    
    static int maxProfitTillIthIdx(int i, int capacity, int[] wt, int[] val, int[][] dp){
        // base case
        if(i == 0){
            int num = capacity / wt[0];
            return num * val[0];
        }
        if(dp[i][capacity] != -1){
            return dp[i][capacity];
        }
        int notTake = maxProfitTillIthIdx(i-1, capacity, wt, val, dp);
        int take = 0;
        if(capacity >= wt[i]){
            take = val[i] + maxProfitTillIthIdx(i, capacity-wt[i], wt, val, dp);
        }
        return dp[i][capacity] = Math.max(notTake,  take);
    }
}
