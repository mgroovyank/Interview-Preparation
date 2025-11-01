// https://leetcode.com/problems/number-of-longest-increasing-subsequence/

class Solution {
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n]; // lis ending at ith index
        int[] count = new int[n];
        Arrays.fill(dp, 1);
        Arrays.fill(count, 1);
        int result = 1;
        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(nums[i] > nums[j] && dp[i] < 1+dp[j]){
                    dp[i] = 1+dp[j];
                    count[i] = count[j];
                }else if(nums[i] > nums[j] && dp[i] == 1+dp[j]){
                    count[i] += count[j];
                }
            }
            result = Math.max(result, dp[i]);
        }
        int numResult = 0;
        for(int i=0;i<n;i++){
            if(dp[i] == result){
                numResult += count[i];
            }
        }
        return numResult;
    }
}
