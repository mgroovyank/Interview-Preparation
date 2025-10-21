// https://www.geeksforgeeks.org/problems/max-sum-without-adjacents2430/1

class Solution {
    int findMaxSum(int arr[]) {
        // Top Down Approach - Recursion
        int n = arr.length;
        return maxSumTillIndex(arr, n-1);
    }
    private int maxSumTillIndex(int arr[], int i){
        // base case
        if(i == 0){
            return arr[0];
        }
        if(i<0){
            return 0;
        }
        return Math.max(maxSumTillIndex(arr, i-1), maxSumTillIndex(arr, i-2) + arr[i]);
    }
}

// DP
class Solution {
    int findMaxSum(int arr[]) {
        // Top Down Approach - Recursion
        int n = arr.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return maxSumTillIndex(arr, n-1, dp);
    }
    private int maxSumTillIndex(int arr[], int i, int[] dp){
        // base case
        if(i == 0){
            return arr[0];
        }
        if(i<0){
            return 0;
        }
        if(dp[i] != -1){
            return dp[i];
        }
        return dp[i] = Math.max(maxSumTillIndex(arr, i-1, dp), maxSumTillIndex(arr, i-2, dp) + arr[i]);
    }
}

// Tabulation
class Solution {
    int findMaxSum(int arr[]) {
        // Top Down Approach - Recursion
        int n = arr.length;
        if(n == 1){
            return arr[0];
        }
        int[] dp = new int[n];
        dp[0] = arr[0];
        dp[1] = Math.max(arr[0], arr[1]);
        for(int i=2;i<n;i++){
            dp[i] = Math.max(dp[i-1], dp[i-2] + arr[i]);
        }
        return dp[n-1];
    }
}

// Space Optimized
// User function Template for Java

class Solution {
    int findMaxSum(int arr[]) {
        // Top Down Approach - Recursion
        int n = arr.length;
        if(n == 1){
            return arr[0];
        }
        int[] dp = new int[n];
        int first = arr[0];
        int second = Math.max(arr[0], arr[1]);
        for(int i=2;i<n;i++){
            int temp = Math.max(second, first + arr[i]);
            first = second;
            second = temp;
        }
        return second;
    }

}
