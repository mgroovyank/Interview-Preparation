// https://www.geeksforgeeks.org/problems/geeks-training/0
// Time Complexity: O(N*4)
// Space Complexity: O(N*4)

// Top Down Approach
class Solution {
    public int maximumPoints(int arr[][]) {
        // recursion - Top Down Approach - start with nth day
        // base case - 0th day
        // I have 3 options for each day.
        // maxPoints on ith day, given task done on (i+1th) day 
        // = Max(task1 score + f(i-1, task1), task2 score + f(i-1, task2))
        // f(i,last) = Max(task1 + f(i-1, task1), task2 + f(i-1, task2))
        // where task1 and task2 are not equalto last
        // overlapping subproblems - memoization = DP
        int numDays = arr.length;
        int[][] dp = new int[numDays][4];
        //Arrays.fill(dp, new int[]{-1, -1, -1, -1});
        for(int i=0;i<numDays;i++){
            for(int j=0;j<4;j++){
                dp[i][j] = -1;
            }
        }
        return maximumPointsAfterIthDay(arr, numDays - 1, 3, dp);
    }
    
    private int maximumPointsAfterIthDay(int[][] arr, int i, int last, int[][] dp){
        // base case
        if(i < 0){
            return 0;
        }
        if(dp[i][last] != -1){
            return dp[i][last];
        }
        // if(i == 0){
        //     int maxi = Integer.MIN_VALUE;
        //     for(int task=0;task<3;task++){
        //         if(task == last){
        //             continue;
        //         }
        //         maxi = Math.max(maxi, arr[i][task]);
        //     }
        //     return maxi;
        // }
        int maxi = Integer.MIN_VALUE;
        for(int task=0;task<3;task++){
            if(task == last){
                continue;
            }
            maxi = Math.max(maxi, arr[i][task] + maximumPointsAfterIthDay(arr, i-1, task, dp));
        }
        return dp[i][last] = maxi;
    }
}
