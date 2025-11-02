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

// DP
class Solution {
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n];
        Arrays.fill(dp , -1);
        return maxSumAfterPartitioningFromIthIdx(arr, 0, k, dp);
    }

    private int maxSumAfterPartitioningFromIthIdx(int[] arr, int i, int maxLen, int[] dp){
        if(i == arr.length){
            return 0;
        }
        if(dp[i] != -1){
            return dp[i];
        }
        int maxSum = Integer.MIN_VALUE;
        int maxVal = Integer.MIN_VALUE;
        for(int k=i;k<Math.min(i+maxLen, arr.length);k++){
            maxVal = Math.max(maxVal, arr[k]);
            maxSum = Math.max(maxSum, (k-i+1)*maxVal + maxSumAfterPartitioningFromIthIdx(arr, k+1, maxLen, dp));
        }
        return dp[i] = maxSum;
    }
}

// Tabulation
class Solution {
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n+1];
        dp[n] = 0;
        for(int i=n-1;i>=0;i--){
            int maxSum = Integer.MIN_VALUE;
            int maxVal = Integer.MIN_VALUE;
            for(int j=i;j<Math.min(i+k, arr.length);j++){
                maxVal = Math.max(maxVal, arr[j]);
                maxSum = Math.max(maxSum, (j-i+1)*maxVal + dp[j+1]);
            }
            dp[i] = maxSum;
        }
        return dp[0];
    }
}
