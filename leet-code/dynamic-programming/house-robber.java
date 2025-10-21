// https://leetcode.com/problems/house-robber/
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if(n == 1){
            return nums[0];
        }
        int first = nums[0];
        int second = Math.max(nums[0], nums[1]);
        for(int i=2;i<n;i++){
            int temp = Math.max(second, first + nums[i]);
            first = second;
            second = temp;
        }
        return second;
    }
}
