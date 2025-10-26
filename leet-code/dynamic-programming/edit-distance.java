// https://leetcode.com/problems/edit-distance/

class Solution {
    public int minDistance(String word1, String word2) {
        // Recursion Top Down Approach
        // f(i,j) = min number of operations required to convert word1[0...i] to word2[0...j]
        // try all possibilites
        int i = word1.length();
        int j = word2.length();
        return minDistanceTillIj(word1, word2, i-1, j-1);
    }
    private int minDistanceTillIj(String word1, String word2, int i, int j){
        // base case
        if(i<0){
            return j+1;//inserts //if i==-1 => return 0
        }
        if(j<0){
            return i+1; //deletes
        }
        if(word1.charAt(i) == word2.charAt(j)){
            return minDistanceTillIj(word1, word2, i-1, j-1);
        }
        return 1 + Math.min(minDistanceTillIj(word1, word2, i-1, j), //delete
        Math.min(minDistanceTillIj(word1, word2, i-1, j-1), // replace
        minDistanceTillIj(word1, word2, i, j-1))); // insert
    }
}

// DP
class Solution {
    public int minDistance(String word1, String word2) {
        // Recursion Top Down Approach
        // f(i,j) = min number of operations required to convert word1[0...i] to word2[0...j]
        // try all possibilites
        int i = word1.length();
        int j = word2.length();
        int[][] dp = new int[i][j];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return minDistanceTillIj(word1, word2, i-1, j-1, dp);
    }
    private int minDistanceTillIj(String word1, String word2, int i, int j, int[][] dp){
        // base case
        if(i<0){
            return j+1;//inserts //if i==-1 => return 0
        }
        if(j<0){
            return i+1; //deletes
        }
        if(dp[i][j] != -1) {
            return dp[i][j];
        }
        if(word1.charAt(i) == word2.charAt(j)){
            return dp[i][j] = minDistanceTillIj(word1, word2, i-1, j-1, dp);
        }
        return dp[i][j] = 1 + Math.min(minDistanceTillIj(word1, word2, i-1, j, dp), //delete
        Math.min(minDistanceTillIj(word1, word2, i-1, j-1, dp), // replace
        minDistanceTillIj(word1, word2, i, j-1, dp))); // insert
    }
}

// Right Shift Indexing
class Solution {
    public int minDistance(String word1, String word2) {
        // Recursion Top Down Approach
        // f(i,j) = min number of operations required to convert word1[0...i] to word2[0...j]
        // try all possibilites
        int i = word1.length();
        int j = word2.length();
        int[][] dp = new int[i+1][j+1];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return minDistanceTillIj(word1, word2, i, j, dp);
    }
    private int minDistanceTillIj(String word1, String word2, int i, int j, int[][] dp){
        // base case
        if(i == 0){
            return j;//inserts //if i==-1 => return 0
        }
        if(j == 0){
            return i; //deletes
        }
        if(dp[i][j] != -1) {
            return dp[i][j];
        }
        if(word1.charAt(i-1) == word2.charAt(j-1)){
            return dp[i][j] = minDistanceTillIj(word1, word2, i-1, j-1, dp);
        }
        return dp[i][j] = 1 + Math.min(minDistanceTillIj(word1, word2, i-1, j, dp), //delete
        Math.min(minDistanceTillIj(word1, word2, i-1, j-1, dp), // replace
        minDistanceTillIj(word1, word2, i, j-1, dp))); // insert
    }
}
