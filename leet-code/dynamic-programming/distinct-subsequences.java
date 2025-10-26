// https://leetcode.com/problems/distinct-subsequences/

// Time Complexity: Exponential(2^m, 2^n)
// Space Complexity: O(m+n)
class Solution {
    public int numDistinct(String s, String t) {
        // Recursion Top Down Approach
        // f(i,j) = (f(i-1,j-1) + f(i-1, j)) or (f(i-1,j)) depending upon if s[i]==t[j] 
        int sl = s.length();
        int tl = t.length();
        return numDistinctTillIj(s, t, sl-1, tl-1);
    }

    private int numDistinctTillIj(String s, String t, int i, int j){
        // base case
        if(j<0){
            return 1;
        }
        if(i<0){
            return 0;
        }
        if(s.charAt(i) == t.charAt(j)){
            return numDistinctTillIj(s, t, i-1, j-1) + numDistinctTillIj(s, t, i-1, j);
        }
        return numDistinctTillIj(s, t, i-1, j);
    }
}

// DP
class Solution {
    public int numDistinct(String s, String t) {
        // Recursion Top Down Approach
        // f(i,j) = (f(i-1,j-1) + f(i-1, j)) or (f(i-1,j)) depending upon if s[i]==t[j] 
        int sl = s.length();
        int tl = t.length();
        int[][] dp = new int[sl][tl];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return numDistinctTillIj(s, t, sl-1, tl-1, dp);
    }

    private int numDistinctTillIj(String s, String t, int i, int j, int[][] dp){
        // base case
        if(j<0){
            return 1;
        }
        if(i<0){
            return 0;
        }
        if(dp[i][j] != -1) {
            return dp[i][j];
        }
        if(s.charAt(i) == t.charAt(j)){
            return dp[i][j] = numDistinctTillIj(s, t, i-1, j-1, dp) + numDistinctTillIj(s, t, i-1, j, dp);
        }
        return dp[i][j] = numDistinctTillIj(s, t, i-1, j, dp);
    }
}

// Right Shift DP
class Solution {
    public int numDistinct(String s, String t) {
        // Recursion Top Down Approach
        // f(i,j) = (f(i-1,j-1) + f(i-1, j)) or (f(i-1,j)) depending upon if s[i]==t[j] 
        int sl = s.length();
        int tl = t.length();
        int[][] dp = new int[sl+1][tl+1];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return numDistinctTillIj(s, t, sl, tl, dp);
    }

    private int numDistinctTillIj(String s, String t, int i, int j, int[][] dp){
        // base case
        if(j == 0){
            return 1;
        }
        if(i == 0){
            return 0;
        }
        if(dp[i][j] != -1) {
            return dp[i][j];
        }
        if(s.charAt(i-1) == t.charAt(j-1)){
            return dp[i][j] = numDistinctTillIj(s, t, i-1, j-1, dp) + numDistinctTillIj(s, t, i-1, j, dp);
        }
        return dp[i][j] = numDistinctTillIj(s, t, i-1, j, dp);
    }
}

// Tabulation
class Solution {
    public int numDistinct(String s, String t) {
        // Tabulation - Bottom Up Approach
        // f(i,j) = (f(i-1,j-1) + f(i-1, j)) or (f(i-1,j)) depending upon if s[i]==t[j] 
        int sl = s.length();
        int tl = t.length();
        int[][] dp = new int[sl+1][tl+1];
        // base case
        for(int i=0;i<=sl;i++){
            dp[i][0] = 1;
        }
        for(int j=1;j<=tl;j++){
            dp[0][j] = 0;
        }
        // do stuff
        for(int i=1;i<=sl;i++){
            for(int j=1;j<=tl;j++){
                if(s.charAt(i-1) == t.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + dp[i-1][j];
                }else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[sl][tl];
    }
}

// Space Optimized
class Solution {
    public int numDistinct(String s, String t) {
        // Tabulation - Bottom Up Approach
        // f(i,j) = (f(i-1,j-1) + f(i-1, j)) or (f(i-1,j)) depending upon if s[i]==t[j] 
        int sl = s.length();
        int tl = t.length();
        int[] prev = new int[tl+1];
        // base case
        for(int i=0;i<=sl;i++){
            prev[0] = 1;
        }
        for(int j=1;j<=tl;j++){
            prev[j] = 0;
        }
        // do stuff
        for(int i=1;i<=sl;i++){
            int[] curr = new int[tl+1];
            curr[0] = 1;
            for(int j=1;j<=tl;j++){
                if(s.charAt(i-1) == t.charAt(j-1)){
                    curr[j] = prev[j-1] +prev[j];
                }else {
                    curr[j] = prev[j];
                }
            }
            prev = curr;
        }
        return prev[tl];
    }
}

// 1D Array Optimized
class Solution {
    public int numDistinct(String s, String t) {
        // Tabulation - Bottom Up Approach
        // f(i,j) = (f(i-1,j-1) + f(i-1, j)) or (f(i-1,j)) depending upon if s[i]==t[j] 
        int sl = s.length();
        int tl = t.length();
        int[] prev = new int[tl+1];
        // base case
        for(int i=0;i<=sl;i++){
            prev[0] = 1;
        }
        for(int j=1;j<=tl;j++){
            prev[j] = 0;
        }
        // do stuff
        for(int i=1;i<=sl;i++){
            for(int j=tl;j>=1;j--){
                if(s.charAt(i-1) == t.charAt(j-1)){
                    prev[j] = prev[j-1] +prev[j];
                }else {
                    prev[j] = prev[j];
                }
            }
        }
        return prev[tl];
    }
}
