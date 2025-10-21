// https://leetcode.com/problems/climbing-stairs/

class Solution {
    public int climbStairs(int n) {
        // n steps = n+1 stairs
        int[] dp = new int[n+1];
        Arrays.fill(dp, -1);
        return climbStairsFrom(n, dp);
    }
    private int climbStairsFrom(int i, int[] dp){
        // base case
        if(i == 0){
            return 1;
        }
        if(i<0){
            return 0;
        }
        if(dp[i] != -1) {
            return dp[i];
        }
        return dp[i] = climbStairsFrom(i-1, dp) + climbStairsFrom(i-2, dp);
    }
}

// Tabulation
class Solution {
    public int climbStairs(int n) {
        // n steps = n+1 stairs
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for(int i=1;i<n+1;i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }
}

// Space Optimized
class Solution {
    public int climbStairs(int n) {
        int first = 1; // 0th index
        int second = 1; // 1th index
        for(int i=2;i<n+1;i++) {
            int temp = first + second;
            first = second;
            second = temp;
        }
        return second;
    }
}
