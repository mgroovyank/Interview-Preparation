// https://leetcode.com/problems/longest-common-subsequence/

// Time Complexity: O(2^(m+n))
// Space Complexity: O(Min(m,n))
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        // compare subsequences on the go
        int l1 = text1.length();
        int l2 = text2.length();
        return longestCommonSubsequenceLenTillIthIdx(text1, text2, l1-1, l2-1);
    }
    private int longestCommonSubsequenceLenTillIthIdx(String text1, String text2, int i1, int i2){
        // base case
        if(i1<0 || i2<0){
            return 0;
        }
        if(text1.charAt(i1) == text2.charAt(i2)){
            return 1 + longestCommonSubsequenceLenTillIthIdx(text1, text2, i1-1, i2-1);
        }
        return Math.max(longestCommonSubsequenceLenTillIthIdx(text1, text2, i1-1, i2),
            longestCommonSubsequenceLenTillIthIdx(text1, text2, i1, i2-1));
    }
}

// Time Complexity: O(M * N)
// Space Complexity: O(M*N) + O(Min(M,N))
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        // compare subsequences on the go
        int l1 = text1.length();
        int l2 = text2.length();
        int[][] dp = new int[l1][l2];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return longestCommonSubsequenceLenTillIthIdx(text1, text2, l1-1, l2-1, dp);
    }
    private int longestCommonSubsequenceLenTillIthIdx(String text1, String text2, int i1, int i2, int[][] dp){
        // base case
        if(i1<0 || i2<0){
            return 0;
        }
        if(dp[i1][i2] != -1){
            return dp[i1][i2];
        }
        if(text1.charAt(i1) == text2.charAt(i2)){
            return 1 + longestCommonSubsequenceLenTillIthIdx(text1, text2, i1-1, i2-1, dp);
        }
        return dp[i1][i2] = Math.max(longestCommonSubsequenceLenTillIthIdx(text1, text2, i1-1, i2, dp),
            longestCommonSubsequenceLenTillIthIdx(text1, text2, i1, i2-1, dp));
    }
}
