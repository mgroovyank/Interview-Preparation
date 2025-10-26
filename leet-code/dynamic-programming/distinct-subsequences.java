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
