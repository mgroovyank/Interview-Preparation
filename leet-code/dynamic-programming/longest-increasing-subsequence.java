//https://leetcode.com/problems/longest-increasing-subsequence/

// Time Complexity: O(n*n)
// Space Complexity: O(n)
class Solution {
    public int lengthOfLIS(int[] nums) {
        // Recursion - Top Down Approach
        // f(i,prevIdx) = LIS from ith index to n-1th index, given prevIdx
        // right shifted prev Idx
        int n = nums.length;
        int[][] dp = new int[n][n+1];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return lengthOfLISFromIthIdx(nums, 0, 0, dp);
    }

    private int lengthOfLISFromIthIdx(int[] nums, int i, int prevIdx, int[][] dp){
        // base case
        if(i == nums.length){
            return 0;
        }
        if(dp[i][prevIdx] != -1){
            return dp[i][prevIdx];
        }
        if(prevIdx == 0 || nums[i] > nums[prevIdx-1]){
            return dp[i][prevIdx] = Math.max(1 + lengthOfLISFromIthIdx(nums, i+1, i+1, dp), 
                lengthOfLISFromIthIdx(nums, i+1, prevIdx, dp));
        } 
        return dp[i][prevIdx] = lengthOfLISFromIthIdx(nums, i+1, prevIdx, dp);
    }
}

// DP
class Solution {
    public int lengthOfLIS(int[] nums) {
        // Recursion - Top Down Approach
        // f(i,prevIdx) = LIS from ith index to n-1th index, given prevIdx
        // right shifted prev Idx
        int n = nums.length;
        int[][] dp = new int[n][n+1];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return lengthOfLISFromIthIdx(nums, 0, 0, dp);
    }

    private int lengthOfLISFromIthIdx(int[] nums, int i, int prevIdx, int[][] dp){
        // base case
        if(i == nums.length){
            return 0;
        }
        if(dp[i][prevIdx] != -1){
            return dp[i][prevIdx];
        }
        if(prevIdx == 0 || nums[i] > nums[prevIdx-1]){
            return dp[i][prevIdx] = Math.max(1 + lengthOfLISFromIthIdx(nums, i+1, i+1, dp), 
                lengthOfLISFromIthIdx(nums, i+1, prevIdx, dp));
        } 
        return dp[i][prevIdx] = lengthOfLISFromIthIdx(nums, i+1, prevIdx, dp);
    }
}

// Tabulation
class Solution {
    public int lengthOfLIS(int[] nums) {
        // Tabulation - Bottom Up Approach
        int n = nums.length;
        int[][] dp = new int[n+1][n+1];
        // base case
        for(int j=0;j<=n;j++){
            dp[n][j] = 0;
        }
        for(int i=n-1;i>=0;i--){
            for(int j=i;j>=0;j--){
                if(j == 0 || nums[i] > nums[j-1]){
                    dp[i][j] = Math.max(1 + dp[i+1][i+1], dp[i+1][j]);
                    continue;
                }
                dp[i][j] = dp[i+1][j];
            }
        }
        return dp[0][0];
    }
}
