// https://leetcode.com/problems/partition-array-for-maximum-sum/

class Solution {
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int n = arr.length;
        return maxSumAfterPartitioningFromIthIdx(arr, 0, k);
    }

    private int maxSumAfterPartitioningFromIthIdx(int[] arr, int i, int maxLen){
        if(i == arr.length){
            return 0;
        }
        int maxSum = Integer.MIN_VALUE;
        int maxVal = Integer.MIN_VALUE;
        for(int k=i;k<Math.min(i+maxLen, arr.length);k++){
            maxVal = Math.max(maxVal, arr[k]);
            maxSum = Math.max(maxSum, (k-i+1)*maxVal + maxSumAfterPartitioningFromIthIdx(arr, k+1, maxLen));
        }
        return maxSum;
    }
}
