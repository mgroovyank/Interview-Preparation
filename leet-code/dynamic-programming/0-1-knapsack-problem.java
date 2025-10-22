// https://www.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1

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
