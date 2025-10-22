// https://leetcode.com/problems/partition-equal-subset-sum/

class Solution {
    public boolean canPartition(int[] nums) {
        // x(int) + x(int) = sum(nums)
        //2x = sum(nums) => x = sum(nums)/2 => int - explore, float/double - false
        int n = nums.length;
        int sum = 0;
        for(int i=0;i<n;i++){
            sum += nums[i];
        }
        if(sum%2 != 0){
            return false;
        }
        int target = sum / 2;
        return isSubsetSum(nums, target);
    }

    private Boolean isSubsetSum(int arr[], int sum) {
        // Reduce recursion stack space - Bottom Up Approach
        // Tabulation
        int n = arr.length;
        int[] prev = new int[sum+1];
        // sum is zero, always true
        for(int i=0;i<n;i++){
            prev[0] = 1;
        }
        // i=0, then 1 if arr[0]==sum
        for(int s=1;s<=sum;s++){
            prev[s] = (s == arr[0]) ? 1 : 0;
        }
        for(int i=1;i<n;i++) {
            int[] curr = new int[sum+1];
            curr[0] = 1;
            for(int s=1;s<=sum;s++) {
                int noTake = prev[s];
                int take = 0;
                if(s >= arr[i]){
                    take = prev[s-arr[i]];
                }
                if(noTake==1 || take==1){
                    curr[s] = 1;
                }else{
                    curr[s] = 0;
                }   
            }
            prev = curr;
        }
        return prev[sum] == 1;
    }
}
