/* 
1. if I choose a number which is already present in nums for the becoming the most frequent, I save on k. I can use those  savings to make other numbers equal to k.
2. Let' say I choose a number greater than maxValue as K, i.e. maxValue+1, then I have to make even the max value equal to k. And not only that, I would need k to make all elements which could become equal to maxValue, also to add 1 to make them as maxValue+1.
3. So always choosing a number exisitig already is a better choice.
*/

// Brute Force
// Time Complexity: O(N^2log(N))
class Solution {
    public int maxFrequencyScore(int[] nums, long k) {
        int n = nums.length;
        int maxFrequencyScore = 0;
        for(int i=0;i<n;i++){
            int target = nums[i];
            // each number in nums, has a cost for becoming equal to target
            // But I'll only choose numbers which have lowest cost, i.e. nearest to target
            int[] cost = new int[n]; // cost of converting number at jth Index to target
            for(int j=0;j<n;j++){
                cost[j] = Math.abs(target - nums[j]);
            }
            Arrays.sort(cost);
            // Maintaining this cost array and sorting it helps me to resolve the problem I was facing before
            // when I was thinking how to choose how many elements smaller than need to be converted to
            // nums[i] and how many greater. Now just choose those which have lower cost.
            int currentFrequency = 0;
            int currentCost = 0;
            for(int c: cost){
                if(currentCost + c <= k){
                    currentFrequency++; // one number that became/or either is already target(when c=0)
                    currentCost += c;
                    continue;
                }
                break;
            }
            maxFrequencyScore = Math.max(maxFrequencyScore, currentFrequency);

        }
        return maxFrequencyScore;
    }
}
