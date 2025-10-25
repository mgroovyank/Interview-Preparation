// https://leetcode.com/problems/target-sum/

class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        return findTargetSumWaysTillIthIdx(nums, n-1, target);
    }
    private int findTargetSumWaysTillIthIdx(int[] nums, int i, int target) {
        // base case
        if(i == 0){
            if(target == 0 && nums[0] == 0){
                return 2;
            }
            if(target-nums[0]==0 || target+nums[0]==0){
                return 1;
            }
            return 0;
        }
        return findTargetSumWaysTillIthIdx(nums, i-1, target-nums[i]) + 
                findTargetSumWaysTillIthIdx(nums, i-1, target+nums[i]);
    }
}
