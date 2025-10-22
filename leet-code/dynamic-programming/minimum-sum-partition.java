// https://www.geeksforgeeks.org/problems/minimum-sum-partition3317/1
class Solution {

    public int minDifference(int arr[]) {
        // In the DP table, the last row will contain all possible 
        // targets that can be achieved from subsequences
        int n = arr.length;
        int sum = 0;
        for(int i=0;i<n;i++){
            sum += arr[i];
        }
        int target = sum/2;
        // populate dp for n-1th index and all possible sums<=target
        int[] dp = subsetSumTillIthIdx(arr, target);
        int minDiff = Integer.MAX_VALUE;
        for(int s=0;s<=target;s++){
            if(dp[s] == 1){
                minDiff = Math.min(minDiff, Math.abs(s - (sum-s)));
            }
        }
        return minDiff;
    }
    
    private int[] subsetSumTillIthIdx(int arr[], int sum){
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
        return prev;
    }
    
}
