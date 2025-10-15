// https://leetcode.com/problems/apply-operations-to-maximize-frequency-score/
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

/*
1. Let's say I sort the original array. Now I can start from first element as target. I'll need 0 operations to get target from left side. But from right side, 
I need to see how many elements I can convert to target. So I start checking elements on right. So I'm increasing my window size on right till cost is less than/equal to k.

2. My final window size will be a possible answer.
3. I need to repeat the same process for every index in nums. Time complexity would be O(N^2). - worst case when k is very high, I can convert all elements to any
other element.
4. But here I'm moving towards left first and right after, which is not correct. I should should choose on basis of cost.


1. When I'm at an index i, I choose to expand left/right depending upon which is closer to nums[i] . I expand till totalCost<=k.
2. After that I move my index, now I see the difference b/w currentIdx value and previous index value. The currIdx value is greater. So, now to elements on 
left of it I need to add that difference to each element in the window on it's left. Also elements on right will need lesser operations than the previous target. So I require lesser operations on right. Now I'll be starting with an intial cost. and again expand left/right depending upon cost and calculate window size.

3. This does reduce some computation, but we might miss

**When I move my right pointer, then I need more k to make same window size equal to my new target(new target greater than previous target)**. Those more k's can only come by reducing window size i.e removing element farthest from target. So I can only move left++;

so finally what I understand is for a right index, I have to increase certain elements to its right by different values to reach target. Now when my right moves ahead, all these have to be increased again by the difference between the previous right
and the current right. This will require a lot of k. So the window size will definitely not be better by taking right elements.
*/


// The above solution will work only when only increments are allowed.

// The below solution is the best solution.
// Time Complexity: O(nlogn)
class Solution {
    public int maxFrequencyScore(int[] nums, long k) {
        int n = nums.length;
        Arrays.sort(nums);
        long[] prefixSum = new long[n+1];
        for(int i=0;i<n;i++){
            prefixSum[i+1] = nums[i] + prefixSum[i];
        }
        int left = 0;
        int ans = 0;
        long leftToRightSum = 0;
        for(int right=0;right<n;right++){
            while(left<=right){
                int median = left + (right-left)/2;
                int target = nums[median];
                long leftCost = (median-left)*target - (prefixSum[median] - prefixSum[left]);
                long rightCost = (prefixSum[right+1] - prefixSum[median+1]) - (right-median)*target;
                long totalCost = leftCost + rightCost;
                if(totalCost > k){
                    left++;
                    continue;
                }
                break; 
            }
            ans = Math.max(ans, right-left+1);
        }
        return ans;
    }
}
