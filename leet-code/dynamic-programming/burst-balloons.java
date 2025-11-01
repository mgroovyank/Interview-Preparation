// https://leetcode.com/problems/burst-balloons/description/

// Recursion
class Solution {
    public int maxCoins(int[] nums) {
        // f(i,j) = max coins from ith index to jth index ballons
        // if the ballon is bursted, you can't get coins from it
        // so choose last in a range(i to j) to be bursted
        // if I decide to burst kth in b/w ith and jth, that means all in i to j
        // except k are bursted so before k=i-1 and after k= j+1
        // this would always work, last ->second last -> third last -> first bursted
        // coins = nums[i-1] * nums[k] * nums[j+1]
        int n = nums.length + 2;
        int[] newNums = new int[n];
        for(int i=1;i<n-1;i++){
            newNums[i] = nums[i-1];
        }
        newNums[0] = 1;
        newNums[n-1] = 1;
        return maxCoinsPartition(newNums, 1, n-2);
    }

    private int maxCoinsPartition(int[] newNums, int i, int j){
        // base case
        if(i>j){
            return 0;
        }
        int maxCoins = Integer.MIN_VALUE;
        // choose kth indexed ballon to be bursted last in the range
        for(int k=i;k<=j;k++){
            maxCoins = Math.max(maxCoins, newNums[i-1]*newNums[k]*newNums[j+1] + maxCoinsPartition(newNums, i, k-1)
                + maxCoinsPartition(newNums, k+1, j));
        }
        return maxCoins;
    }
}
