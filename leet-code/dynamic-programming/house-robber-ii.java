// https://leetcode.com/problems/house-robber-ii/

class Solution {
    public int rob(int[] nums) {
        // if you pick last num - then you can't pick first one also
        // if you don't pick last num - then it is same problem as house robber
        int n = nums.length;
        if(n == 1){
            return nums[0];
        }
        if(n == 2){
            return Math.max(nums[0], nums[1]);
        }
        int withoutLastNum = maxSumTillIndex(nums, n-2);
        int[] temp = new int[n];
        for(int i=0;i<n;i++){
            temp[n-1-i] = nums[i];
        }
        int withLastNum = maxSumTillIndex(temp, n-2);
        return Math.max(withoutLastNum, withLastNum);
    }
    private int maxSumTillIndex(int nums[], int i){
        // base case
        if(i == 0){
            return nums[0];
        }
        if(i<0){
            return 0;
        }
        return Math.max(maxSumTillIndex(nums, i-1), maxSumTillIndex(nums, i-2) + nums[i]);
    }
}
