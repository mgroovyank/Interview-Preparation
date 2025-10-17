// https://www.geeksforgeeks.org/problems/geeks-training/0
// Time Complexity: O(N*4) * 3
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

// Bottom Up Approach - Tabulation
// Same time and space complexity, but looks cleaner and reduced stack memory
class Solution {
    public int maximumPoints(int arr[][]) {
        // recursion - bottom up approach
        // bottom up approach is iterative in nature
        int numDays = arr.length;
        int[][] dp = new int[numDays][3];
        dp[0][0] = arr[0][0];
        dp[0][1] = arr[0][1];
        dp[0][2] = arr[0][2];
        for(int day=1;day<numDays;day++){
            for(int task=0;task<3;task++){
                // do task on day
                // calculate maximumPoints if I do task on day
                if(task == 0){
                    dp[day][task] = arr[day][task] + Math.max(dp[day-1][1], dp[day-1][2]);
                }else if(task == 1){
                    dp[day][task] = arr[day][task] + Math.max(dp[day-1][0], dp[day-1][2]);
                }else {
                    dp[day][task] = arr[day][task] + Math.max(dp[day-1][0], dp[day-1][1]);
                }
            }
        }
        return Math.max(dp[numDays-1][0], Math.max(dp[numDays-1][1], dp[numDays-1][2]));
    }
}
