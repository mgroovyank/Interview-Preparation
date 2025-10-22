// https://www.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1

// Time Complexity: O(2^n)
// Space Complexity: O(n)
class Solution {
    static int knapsack(int W, int val[], int wt[]) {
        // Recursion - Top Down Approach
        // f(i,w) = Math.max(val[i] + f(i-1, w-wt[i]), f(i-1, w))
        int n = val.length;
        return maxValTillIthIdx(n-1, W, val, wt);
    }
    
    static int maxValTillIthIdx(int i, int capacity, int[] val, int[] wt){
        // base case
        if(i == 0){
            if(capacity >= wt[0]){
                return val[0];
            }
            return 0;
        }
        int notTake = maxValTillIthIdx(i-1, capacity, val, wt);
        int take = Integer.MIN_VALUE;
        if(capacity >= wt[i]){
            take = val[i] + maxValTillIthIdx(i-1, capacity - wt[i], val, wt);
        }
        return Math.max(take, notTake);
    }
}

// DP
// Time Complexity: O(n*W)
// Space Complexity: O(n*W) + O(n)
class Solution {
    static int knapsack(int W, int val[], int wt[]) {
        // Recursion - Top Down Approach
        // f(i,w) = Math.max(val[i] + f(i-1, w-wt[i]), f(i-1, w))
        int n = val.length;
        int[][] dp = new int[n][W+1];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return maxValTillIthIdx(n-1, W, val, wt, dp);
    }
    
    static int maxValTillIthIdx(int i, int capacity, int[] val, int[] wt ,int[][] dp){
        // base case
        if(i == 0){
            if(capacity >= wt[0]){
                return val[0];
            }
            return 0;
        }
        
        if(dp[i][capacity] != -1) {
            return dp[i][capacity];
        }
        
        int notTake = maxValTillIthIdx(i-1, capacity, val, wt, dp);
        int take = Integer.MIN_VALUE;
        if(capacity >= wt[i]){
            take = val[i] + maxValTillIthIdx(i-1, capacity - wt[i], val, wt, dp);
        }
        return dp[i][capacity] = Math.max(take, notTake);
    }
}

// Tabulation
class Solution {
    static int knapsack(int W, int val[], int wt[]) {
        // Recursion - Top Down Approach
        // f(i,w) = Math.max(val[i] + f(i-1, w-wt[i]), f(i-1, w))
        int n = val.length;
        int[][] dp = new int[n][W+1];
        for(int w=0;w<=W;w++){
            if(w < wt[0]){
                dp[0][w] = 0;
            }else{
                dp[0][w] = val[0];
            }
        }
        for(int i=1;i<n;i++){
            for(int w=0;w<=W;w++){
                int notTake = dp[i-1][w];
                int take = Integer.MIN_VALUE;
                if(w >= wt[i]){
                    take = val[i] + dp[i-1][w-wt[i]];
                }
                dp[i][w] = Math.max(take, notTake);
            }
        }
        return dp[n-1][W];
    }

}

// Space Optimized
class Solution {
    static int knapsack(int W, int val[], int wt[]) {
        // Recursion - Top Down Approach
        // f(i,w) = Math.max(val[i] + f(i-1, w-wt[i]), f(i-1, w))
        int n = val.length;
        int[] prev = new int[W+1];
        for(int w=0;w<=W;w++){
            if(w < wt[0]){
                prev[w] = 0;
            }else{
                prev[w] = val[0];
            }
        }
        for(int i=1;i<n;i++){
            int[] curr = new int[W+1];
            for(int w=0;w<=W;w++){
                int notTake = prev[w];
                int take = Integer.MIN_VALUE;
                if(w >= wt[i]){
                    take = val[i] + prev[w-wt[i]];
                }
                curr[w] = Math.max(take, notTake);
            }
            prev = curr;
        }
        return prev[W];
    }

}
