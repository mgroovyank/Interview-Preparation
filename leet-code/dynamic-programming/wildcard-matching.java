// https://leetcode.com/problems/wildcard-matching/

class Solution {
    public boolean isMatch(String s, String p) {
        // Recursion - Top Down Approach
        // f(i,j) = if pattern p till index j can fully represent string s till index i
        // f(i,j) = f(i-1, j-1) | (f(i, j-1) || f(i-1, j))
        int sl = s.length();
        int pl = p.length();
        return isMatchTillIndexIj(s, p, sl-1, pl-1)==1 ? true : false;
    }

    private int isMatchTillIndexIj(String s, String p, int i, int j){
        // base case
        if(i<0){
            if(j<0){
                return 1;
            }
            for(int k=0;k<=j;k++){
                if(p.charAt(k) != '*'){ // even chars are a wildcard character
                    return 0;
                }
            }
            return 1;
        }
        if(j<0){
            return 0;
        }
        char sc = s.charAt(i);
        char pc = p.charAt(j);
        if(pc == '?' ||  sc==pc){
            return isMatchTillIndexIj(s, p, i-1, j-1);
        }
        if(pc == '*'){
            return (isMatchTillIndexIj(s, p, i, j-1) + isMatchTillIndexIj(s, p, i-1, j)) > 0 ? 1 : 0;
        }
        return 0;
    }
}

// DP
class Solution {
    public boolean isMatch(String s, String p) {
        // Recursion - Top Down Approach
        // f(i,j) = if pattern p till index j can fully represent string s till index i
        // f(i,j) = f(i-1, j-1) | (f(i, j-1) || f(i-1, j))
        int sl = s.length();
        int pl = p.length();
        int[][] dp = new int[sl][pl];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return isMatchTillIndexIj(s, p, sl-1, pl-1, dp)==1 ? true : false;
    }

    private int isMatchTillIndexIj(String s, String p, int i, int j, int[][] dp){
        // base case
        if(i<0){
            if(j<0){
                return 1;
            }
            for(int k=0;k<=j;k++){
                if(p.charAt(k) != '*'){ // even chars are a wildcard character
                    return 0;
                }
            }
            return 1;
        }
        if(j<0){
            return 0;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        char sc = s.charAt(i);
        char pc = p.charAt(j);
        if(pc == '?' ||  sc==pc){
            return dp[i][j] = isMatchTillIndexIj(s, p, i-1, j-1, dp);
        }
        if(pc == '*'){
            return dp[i][j] = (isMatchTillIndexIj(s, p, i, j-1, dp) + isMatchTillIndexIj(s, p, i-1, j, dp)) > 0 ? 1 : 0;
        }
        return dp[i][j] = 0;
    }
}

// Right Shift Indexing
class Solution {
    public boolean isMatch(String s, String p) {
        // Recursion - Top Down Approach
        // f(i,j) = if pattern p till index j can fully represent string s till index i
        // f(i,j) = f(i-1, j-1) | (f(i, j-1) || f(i-1, j))
        int sl = s.length();
        int pl = p.length();
        int[][] dp = new int[sl+1][pl+1];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return isMatchTillIndexIj(s, p, sl, pl, dp)==1 ? true : false;
    }

    private int isMatchTillIndexIj(String s, String p, int i, int j, int[][] dp){
        // base case
        if(i == 0){
            if(j == 0){
                return 1;
            }
            for(int k=1;k<=j;k++){
                if(p.charAt(k-1) != '*'){ // even chars are a wildcard character
                    return 0;
                }
            }
            return 1;
        }
        if(j == 0){
            return 0;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        char sc = s.charAt(i-1);
        char pc = p.charAt(j-1);
        if(pc == '?' ||  sc==pc){
            return dp[i][j] = isMatchTillIndexIj(s, p, i-1, j-1, dp);
        }
        if(pc == '*'){
            return dp[i][j] = (isMatchTillIndexIj(s, p, i, j-1, dp) + isMatchTillIndexIj(s, p, i-1, j, dp)) > 0 ? 1 : 0;
        }
        return dp[i][j] = 0;
    }
}
