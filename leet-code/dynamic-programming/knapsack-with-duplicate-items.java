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

// Time Complexity: O(C*N)
// Space Complexity: O(C*N)
class Solution {
    static int knapSack(int val[], int wt[], int capacity) {
        // Bottom Up Approach - Tabulation
        int n = val.length;
        int[][] dp = new int[n][capacity+1];
        for(int c=0;c<=capacity;c++){
            dp[0][c] = (c / wt[0]) * val[0];
        }
        for(int i=1;i<n;i++){
            for(int c=0;c<=capacity;c++){
                int notTake = dp[i-1][c];
                int take = 0;
                if(c >= wt[i]){
                    take = val[i] + dp[i][c-wt[i]];
                }
                dp[i][c] = Math.max(notTake,  take);
            }
        }
        return dp[n-1][capacity];
    }
}

// Time Complexity: O(C*N)
// Space Complexity: O(C*2)
class Solution {
    static int knapSack(int val[], int wt[], int capacity) {
        // Bottom Up Approach - Tabulation
        // Sapace Optimization
        int n = val.length;
        int[] dp = new int[capacity+1];
        for(int c=0;c<=capacity;c++){
            dp[c] = (c / wt[0]) * val[0];
        }
        for(int i=1;i<n;i++){
            int[] curr = new int[capacity+1];
            for(int c=0;c<=capacity;c++){
                int notTake = dp[c];
                int take = 0;
                if(c >= wt[i]){
                    take = val[i] + curr[c-wt[i]];
                }
                curr[c] = Math.max(notTake,  take);
            }
            dp = curr;
        }
        return dp[capacity];
    }
}

// Time Complexity: O(C*N)
// Space Complexity: O(C)
class Solution {
    static int knapSack(int val[], int wt[], int capacity) {
        // Bottom Up Approach - Tabulation
        // Space Optimization
        int n = val.length;
        int[] curr = new int[capacity+1];
        for(int c=0;c<=capacity;c++){
            curr[c] = (c / wt[0]) * val[0];
        }
        for(int i=1;i<n;i++){
            for(int c=0;c<=capacity;c++){
                // from prev I just need value at c
                int notTake = curr[c];
                int take = 0;
                if(c >= wt[i]){
                    take = val[i] + curr[c-wt[i]];
                }
                curr[c] = Math.max(notTake,  take);
            }
        }
        return curr[capacity];
    }
}
