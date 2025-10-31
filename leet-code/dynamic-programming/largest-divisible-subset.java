// https://leetcode.com/problems/largest-divisible-subset

// Time Complexity: O(n^2)
// Space Complexity: O(n) * 2
class Solution {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        // if you sort the array, then if i>j then only one condition will be
        // applicable in your result set i.e. answer[j] % answer[i] == 0
        // after that you just have to print Longest Divisible Subsequence similar to LIS
        // if last element in LDS divides nums[i], then all elements divides nums[i]
        int n = nums.length;
        Arrays.sort(nums);
        int[] dp = new int[n]; // length of LDS
        int[] prev = new int[n];
        Arrays.fill(dp, 1); // LDS ending at i index
        Arrays.fill(prev, -1); // prev element of LDS ending at i index
        for(int i=1;i<n;i++){
            for(int j=0;j<=i-1;j++){
                if(nums[i] % nums[j] == 0){
                    if(1+dp[j] > dp[i]){
                        dp[i] = 1 + dp[j];
                        prev[i] = j;
                    }
                }
            }
        }
        int maxIndex = 0;
        int maxValue = dp[0];
        for(int i=1;i<n;i++){
            if(dp[i] > maxValue){
                maxIndex = i;
                maxValue = dp[i];
            }
        }
        int i = maxIndex;
        List<Integer> result = new ArrayList<>();
        while(i!=-1){
            result.add(nums[i]);
            i = prev[i];
        }
        return result;
    }
}
