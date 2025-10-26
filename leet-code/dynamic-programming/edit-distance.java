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


// With Tabulation, runtime can increase as now we populate all states, instead of just normal DP
class Solution {
    public int minDistance(String word1, String word2) {
        // Tabulation
        int w1 = word1.length();
        int w2 = word2.length();
        int[][] dp = new int[w1+1][w2+1];
        // base case
        for(int j=0;j<=w2;j++){
            dp[0][j] = j;
        }
        for(int i=1;i<=w1;i++){
            dp[i][0] = i;
        }
        for(int i=1;i<=w1;i++){
            for(int j=1;j<=w2;j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                }else {
                    dp[i][j] = 1 + Math.min(dp[i-1][j], //delete
                        Math.min(
                            dp[i-1][j-1], // replace
                            dp[i][j-1])); // insert
                }
            }
        }
        return dp[w1][w2];
    }
}

// Space Optimized
class Solution {
    public int minDistance(String word1, String word2) {
        // Tabulation
        int w1 = word1.length();
        int w2 = word2.length();
        int[] prev = new int[w2+1];
        // base case
        for(int j=0;j<=w2;j++){
            prev[j] = j;
        }
        for(int i=1;i<=w1;i++){
            int[] curr = new int[w2+1];
            curr[0] = i;
            for(int j=1;j<=w2;j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    curr[j] = prev[j-1];
                }else {
                    curr[j] = 1 + Math.min(prev[j], //delete
                        Math.min(
                            prev[j-1], // replace
                            curr[j-1])); // insert
                }
            }
            prev = curr;
        }
        return prev[w2];
    }
}
