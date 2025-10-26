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
