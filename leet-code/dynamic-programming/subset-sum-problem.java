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
