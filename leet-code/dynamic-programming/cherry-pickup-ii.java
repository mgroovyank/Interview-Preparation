// https://leetcode.com/problems/cherry-pickup-ii/

// Time Complexity: 9^(n*m*m)
// number of states = n*m*m => for each state you call recursion for 9 deltas
// Space Complexity: O(n) - recursion stack

class Solution {
    public int cherryPickup(int[][] grid) {
        // Top Down Approach - Fixed Starting Point, Variable Ending Point
        // Way 1 - Move R1 first across grid -> get max Path -> mark zero on that path -> move R2 similarly
        // Way 1 - little more complex due to tracking of path
        // Way 2 - move both together
        int n = grid.length;
        int m = grid[0].length;
        return maxCherryPickupFrom(grid, 0, 0, m-1);
    }

    private int maxCherryPickupFrom(int[][] grid, int i12, int j1, int j2){
        // base case
        if(j1<0 || j2<0 || j1>=grid[0].length || j2>=grid[0].length){
            return Integer.MIN_VALUE;
        }
        if(i12 == grid.length-1){
            if(j1 == j2){
                return grid[i12][j1];
            }
            return grid[i12][j1] + grid[i12][j2];
        }
        int[] delta = new int[]{-1, 0, 1};
        int maxi = Integer.MIN_VALUE;
        for(int d1:delta){
            for(int d2:delta){
                int temp = grid[i12][j1] + grid[i12][j2];
                if(j1 == j2){
                    temp = grid[i12][j1];
                }
                maxi = Math.max(maxi, temp + maxCherryPickupFrom(grid, i12+1, j1+d1, j2+d2));
            }
        }
        return maxi;
    }
}

// Time Complexity: O(n*m*m)
// Space Complexity: O(n*m*m)
class Solution {
    public int cherryPickup(int[][] grid) {
        // Top Down Approach - Fixed Starting Point, Variable Ending Point
        // Way 1 - Move R1 first across grid -> get max Path -> mark zero on that path -> move R2 similarly
        // Way 1 - little more complex due to tracking of path
        // Way 2 - move both together
        int n = grid.length;
        int m = grid[0].length;
        int[][][] dp = new int[n][m][m];
        for(int[][] dp1: dp){
            for(int[] dp2: dp1){
                Arrays.fill(dp2, -1);
            }
        }
        return maxCherryPickupFrom(grid, 0, 0, m-1, dp);
    }

    private int maxCherryPickupFrom(int[][] grid, int i12, int j1, int j2, int[][][] dp){
        // base case
        if(j1<0 || j2<0 || j1>=grid[0].length || j2>=grid[0].length){
            return Integer.MIN_VALUE;
        }
        if(i12 == grid.length-1){
            if(j1 == j2){
                return grid[i12][j1];
            }
            return grid[i12][j1] + grid[i12][j2];
        }
        if(dp[i12][j1][j2] != -1){
            return dp[i12][j1][j2];
        }
        int[] delta = new int[]{-1, 0, 1};
        int maxi = Integer.MIN_VALUE;
        for(int d1:delta){
            for(int d2:delta){
                int temp = grid[i12][j1] + grid[i12][j2];
                if(j1 == j2){
                    temp = grid[i12][j1];
                }
                maxi = Math.max(maxi, temp + maxCherryPickupFrom(grid, i12+1, j1+d1, j2+d2, dp));
            }
        }
        return dp[i12][j1][j2] = maxi;
    }
}

// Tabulation -Bottom Up Approach
// Time Complexity: O(n*m*m*9)
// Space Complexity: O(n*m*m)
class Solution {
    public int cherryPickup(int[][] grid) {
        // Top Down Approach - Fixed Starting Point, Variable Ending Point
        // Way 1 - Move R1 first across grid -> get max Path -> mark zero on that path -> move R2 similarly
        // Way 1 - little more complex due to tracking of path
        // Way 2 - move both together
        int n = grid.length;
        int m = grid[0].length;
        int[][][] dp = new int[n][m][m];
        for(int i12=n-1;i12>=0;i12--){
            for(int j1=0;j1<m;j1++){
                for(int j2=0;j2<m;j2++){
                    if(i12 == grid.length-1){
                        if(j1 == j2){
                            dp[i12][j1][j2] = grid[i12][j1];
                        }else {
                            dp[i12][j1][j2] =  grid[i12][j1] + grid[i12][j2];
                        }
                        continue;
                    }
                    int[] delta = new int[]{-1, 0, 1};
                    int maxi = Integer.MIN_VALUE;
                    for(int d1:delta){
                        for(int d2:delta){
                            if(j1+d1<0 || j2+d2<0 || j1+d1>=m || j2+d2>=m){
                                continue;
                            }
                            int temp = grid[i12][j1] + grid[i12][j2];
                            if(j1 == j2){
                                temp = grid[i12][j1];
                            }
                            maxi = Math.max(maxi, temp + dp[i12+1][j1+d1][j2+d2]);
                        }
                    }
                    dp[i12][j1][j2] = maxi;
                }
            }
        }
        return dp[0][0][m-1];
    }
}
