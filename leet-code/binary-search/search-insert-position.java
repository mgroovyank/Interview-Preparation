// https://leetcode.com/problems/search-insert-position/

// Time Complexity: O(logn)
// Space Complexity: O(1)

class Solution {
    public int searchInsert(int[] nums, int target) {
        // upper bound = first number greater than target
        // lower bound = first number greater than or equal to target
        // since distinct integers, both upper bound and lower bound would be same
        // let's use lower bound - since I need equal element as well
        int n = nums.length;
        int low = 0;
        int high = n-1;
        int ans = n;
        while(low<=high){
            int mid = low + (high-low)/2;
            if(nums[mid] >= target) {
                ans = mid;
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return ans;
    }
}
