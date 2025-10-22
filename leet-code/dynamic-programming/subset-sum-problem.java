// https://www.geeksforgeeks.org/problems/subset-sum-problem-1611555638/1

// Time Complexity: O(2^n)
// Space Complexity: O(n)
class Solution {

    static Boolean isSubsetSum(int arr[], int sum) {
        // Recursion - Top Down Approach
        // f(i, target) = subsequence till ith index with sum as target
        // every leaf node will be f(n-1, target)
        int n = arr.length;
        return isSubsetSumTillIthIdx(arr, n-1, sum);
    }
    
    static Boolean isSubsetSumTillIthIdx(int[] arr, int i, int sum){
        // base case
        if(sum == 0){
            return true;
        }
        if(i == 0){
            return arr[0] == sum;
        }
        return isSubsetSumTillIthIdx(arr, i-1, sum) 
               || isSubsetSumTillIthIdx(arr, i-1, sum-arr[i]);
    }
}

// DP
// Time Complexity: O(n*sum)
// Space Complexity: O(n*sum) + O(n)
class Solution {

    static Boolean isSubsetSum(int arr[], int sum) {
        // Recursion - Top Down Approach
        // f(i, target) = subsequence till ith index with sum as target
        // every leaf node will be f(n-1, target)
        // overlapping subproblems
        int n = arr.length;
        int[][] dp = new int[n][sum+1];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return isSubsetSumTillIthIdx(arr, n-1, sum, dp) == 1;
    }
    
    static int isSubsetSumTillIthIdx(int[] arr, int i, int sum, int[][] dp){
        // base case
        if(i<0){
            return 0;
        }
        if(sum == 0){
            return 1;
        }
        if(i == 0){
            return arr[0] == sum ? 1 : 0;
        }
        if(dp[i][sum] != -1){
            return dp[i][sum];
        }
        int noTake = isSubsetSumTillIthIdx(arr, i-1, sum, dp);
        // if you take without this condition, then sum will become negative index
        // for dp
        int take = 0;
        if(sum >= arr[i]){
            take = isSubsetSumTillIthIdx(arr, i-1, sum-arr[i], dp);
        }
        if(noTake==1 || take==1){
            return dp[i][sum] = 1;
        }
        return dp[i][sum] = 0;
    }
}
