// https://www.geeksforgeeks.org/problems/perfect-sum-problem5633/1
class Solution {
    // Function to calculate the number of subsets with a given sum
    public int perfectSum(int[] nums, int target) {
        // Recursion - Top Down Approach
        // f(i, target) = f(i-1, target-arr[i]) + f(i-1, target);
        int n = nums.length;
        int[][] dp = new int[n][target+1];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return countOfSubsetsTillIthIndex(nums, n-1, target, dp);
    }
    
    private int countOfSubsetsTillIthIndex(int[] nums, int i, int target, int[][] dp){
        //  base case
        // go till leaf nodes, returning earlier doesn't take all ways
        // to achieve target ex: taking 0s if target is achieved still is another way
        if(i == -1){
            if(target==0){
                return 1;
            }
            return 0;
        }
        if(dp[i][target] != -1){
            return dp[i][target];
        }
        int take = target >= nums[i] ? countOfSubsetsTillIthIndex(nums, i-1, target-nums[i], dp) : 0;
        int notTake = countOfSubsetsTillIthIndex(nums, i-1, target, dp);
        return  dp[i][target] = take + notTake;
    }
}
