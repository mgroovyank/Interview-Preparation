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

// Space Optimized
Space Complexity : O(N) * 2

class Solution {
    public int lengthOfLIS(int[] nums) {
        // Tabulation - Bottom Up Approach
        int n = nums.length;
        int[] dp = new int[n+1];
        // base case
        for(int j=0;j<=n;j++){
            dp[j] = 0;
        }
        for(int i=n-1;i>=0;i--){
            int[] curr = new int[n+1];
            for(int j=i;j>=0;j--){
                if(j == 0 || nums[i] > nums[j-1]){
                    curr[j] = Math.max(1 + dp[i+1], dp[j]);
                    continue;
                }
                curr[j] = dp[j];
            }
            dp = curr;
        }
        return dp[0];
    }
}

// PRINTING LIS
class Solution {
    public int lengthOfLIS(int[] nums) {
        // Single Array Approach
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int ans = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<i;j++){
                if(nums[j] < nums[i]){
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}

// Time Complexity: O(n*logn) - binary search
class Solution {
    public int lengthOfLIS(int[] nums) {
        // Single Array Approach
        int n = nums.length;
        List<Integer> lis = new ArrayList<>();
        for(int i=0;i<n;i++){
            int lb = lowerBound(lis, nums[i]);
            if(lb == lis.size()){
                lis.add(nums[i]);
            }else{
                lis.set(lb, nums[i]);
            }
        }
        return lis.size();
    }

    private int lowerBound(List<Integer> lis, int target){
        int left = 0;
        int right = lis.size()-1;
        while(left<=right){
            int mid = left + (right-left)/2;
            if(lis.get(mid) >= target){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return left;
    }
}
