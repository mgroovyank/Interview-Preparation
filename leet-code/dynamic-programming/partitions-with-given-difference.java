// https://www.geeksforgeeks.org/problems/partitions-with-given-difference/1


class Solution {
    int countPartitions(int[] arr, int d) {
        // Tabulation - Bottom Up Approach
        // sum1 + sum2 = total
        // sum1 - sum2 = d
        // sum1 = (d+total)/2
        // if %2 !=0 => return 0;
        int n = arr.length;
        int total = 0;
        for(int i=0;i<n;i++){
            total += arr[i];
        }
        int target = (d+total)/2;
        if((d+total)%2 != 0){
            return 0;
        }
        int[] prev = new int[target+1];
        for(int sum=0;sum<=target;sum++){
            prev[sum] = 0;
            prev[sum] = sum == arr[0] ? prev[sum] + 1 : prev[sum];
        }
        prev[0] += 1;
        for(int i=1;i<n;i++){
            int[] curr = new int[target+1];
            for(int sum=0;sum<=target;sum++){
                int take = sum >= arr[i] ? prev[sum-arr[i]] : 0;
                int notTake = prev[sum];
                curr[sum] = take + notTake;
            }
            prev = curr;
        }
        return prev[target];
    
    }
}
