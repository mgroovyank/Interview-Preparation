// https://leetcode.com/problems/palindrome-partitioning-ii/

class Solution {
    public int minCut(String s) {
        // Recursion - Top Down Approach
        int n = s.length();
        return minCutPalindromePartition(s, 0) - 1; // remove extra cut from end
    }

    private int minCutPalindromePartition(String s, int i){
        // base case
        if(i == s.length()){
            return 0;
        }
        // explore all possibilities
        int minCuts = Integer.MAX_VALUE;
        for(int k=i;k<s.length();k++){
            // make a cut at k is i-<k is palindrome
            if(isPalindrome(s, i, k)){
                minCuts = Math.min(minCuts, 1+minCutPalindromePartition(s, k+1));
            }
        }
        return minCuts;
    }

    private boolean isPalindrome(String s, int i, int j){
        while(i<=j){
            if(s.charAt(i) != s.charAt(j)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}

// Time Complexity: O(n*n^2)
// Space Complexity: O(n) + O(n)
class Solution {
    public int minCut(String s) {
        // Recursion - Top Down Approach
        int n = s.length();
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return minCutPalindromePartition(s, 0, dp) - 1; // remove extra cut from end
    }

    private int minCutPalindromePartition(String s, int i,  int[] dp){
        // base case
        if(i == s.length()){
            return 0;
        }
        if(dp[i] != -1){
            return dp[i];
        }
        // explore all possibilities
        int minCuts = Integer.MAX_VALUE;
        for(int k=i;k<s.length();k++){
            // make a cut at k is i-<k is palindrome
            if(isPalindrome(s, i, k)){
                minCuts = Math.min(minCuts, 1+minCutPalindromePartition(s, k+1, dp));
            }
        }
        return dp[i] = minCuts;
    }

    private boolean isPalindrome(String s, int i, int j){
        while(i<=j){
            if(s.charAt(i) != s.charAt(j)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}

// Tabulation
class Solution {
    public int minCut(String s) {
        // tabulation
        int n = s.length();
        int[] dp = new int[n+1];
        dp[n] = 0;
        for(int i=n-1;i>=0;i--){
            int minCuts = Integer.MAX_VALUE;
            for(int k=i;k<s.length();k++){
                // make a cut at k is i-<k is palindrome
                if(isPalindrome(s, i, k)){
                    minCuts = Math.min(minCuts, 1+dp[k+1]);
                }
            }
            dp[i] = minCuts;
        }
        return dp[0] - 1; // remove extra cut from end
    }

    private boolean isPalindrome(String s, int i, int j){
        while(i<=j){
            if(s.charAt(i) != s.charAt(j)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
