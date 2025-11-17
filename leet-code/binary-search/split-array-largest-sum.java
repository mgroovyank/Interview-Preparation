// https://leetcode.com/problems/split-array-largest-sum/description/

// Time Complexity: O(nlogn)
class Solution {
    public int splitArray(int[] nums, int k) {
        // minimize the maximum of subarrays sums across different ways of splitting
        // if I can set a limit of sum on every subarray that I create
        // then if I'm successful to create k subarrays = I can try to reduce limit and see if its still possible: high = mid - 1
        // if I create more than k subarrays - then I've to increase limit so that I get required number of subarrays: low = mid + 1
        // if I create less than k subarrays - then my limit is too high, I'll reduce limit: high = mid - 1
        // range of limit: low=max element in nums as my limit should be high enough to 
        // store have at least 1 element, high=sum of all elements in nums
        int n = nums.length;
        int low = Arrays.stream(nums).max().getAsInt();
        int high = Arrays.stream(nums).sum();
        int ans = high;
        while(low <= high){
            int mid = low + (high-low)/2;
            int count = countSubarrays(nums, mid);
            if(count <= k){
                ans = mid;
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return ans;
    }

    private int countSubarrays(int[] nums, int limit){
        int currSum = 0;
        int count = 0;
        for(int i=0;i<nums.length;i++){
            if(currSum + nums[i] > limit){ // adding number breaches limit
                // then don't add split
                count++;
                currSum = nums[i];
            }else{
                currSum += nums[i];
            }
        }
        return count+1;
    }
}
