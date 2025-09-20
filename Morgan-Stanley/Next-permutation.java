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
        // Quick Sort(Primitive data types) or Tim Sort(Objects)
        Arrays.sort(nums, startIdx, numsLen);
    }
}

// Time Complexity: O(N) + O(NlogN)
// Space Complexity: O(1) + O(logN) - recursion stack


// Time Complexity: O(N)
// Space Complexity: O(1)

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
            // instead of sorting you can just reverse the array
            reverse(nums, 0, numsLen-1);
            return;
        }
        int nextGreaterElementIdx = idx+1;
        for(int i=numsLen-1;i>=idx+1;i--){
            if(nums[i] > nums[idx]){
                nextGreaterElementIdx = i;
                break;
            }
        }
        swap(nums, idx, nextGreaterElementIdx);
        int startIdx = idx + 1;
        int endIdx = numsLen-1;
        reverse(nums, startIdx, endIdx);
    }

    private void swap(int[] nums, int firstIdx, int secondIdx){
        int temp = nums[firstIdx];
        nums[firstIdx] = nums[secondIdx];
        nums[secondIdx] = temp;
    }

    private void reverse(int[] nums, int startIdx, int endIdx){
        while(startIdx <= endIdx){
            swap(nums, startIdx++, endIdx--);
        }
    }
}
