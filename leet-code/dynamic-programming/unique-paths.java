// https://leetcode.com/problems/unique-paths/

// Time Complexity: O(2^MN)
// Space Complexity: O(M+N) - length of longest Path
class Solution {
    public int uniquePaths(int m, int n) {
        // Recursion - Top Down Approach
        // f(i,j) = f(i-1, j) * 1 + f(i, j-1) * 1
        return uniquePathsTo(m-1, n-1);
    }

    private int uniquePathsTo(int i, int j){
        if(i==0 && j==0){
            return 1;
        }
        if(i<0 || j<0){
            return 0;
        }
        return uniquePathsTo(i-1, j) + uniquePathsTo(i, j-1);
    }
}

