// https://leetcode.com/problems/next-permutation/

class Solution {
    public void nextPermutation(int[] nums) {
        int numsLen = nums.length;
        int idx = numsLen-2;
        for(;idx>=0;idx--){
            if(nums[idx+1] > nums[idx]){
                break;
            }
        }
        if(idx == -1){
            // just simply sort the array
            Arrays.sort(nums, 0, numsLen);
            return;
        }
        int nextGreaterElementIdx = idx+1;
        for(int i=numsLen-1;i>=idx+1;i--){
            if(nums[i] > nums[idx]){
                nextGreaterElementIdx = i;
                break;
            }
        }
        int temp = nums[idx];
        nums[idx] = nums[nextGreaterElementIdx];
        nums[nextGreaterElementIdx] = temp;
        int startIdx = idx + 1;
        int endIndx = numsLen-1;
        Arrays.sort(nums, startIdx, numsLen);
    }
}
