// https://www.geeksforgeeks.org/problems/geek-jump/1

class Solution {
    int minCost(int[] height) {
        // Top Down Approach - recursion
        int n = height.length;
        return minCostTo(height, n-1);
    }
    
    private int minCostTo(int[] height, int i){
        // base case
        if(i == 0){
            return 0;
        }
        int mini = Integer.MAX_VALUE;
        if(i-1>=0){
            mini = Math.min(mini, Math.abs(height[i]-height[i-1]) + minCostTo(height, i-1));
        }
        if(i-2>=0){
            mini = Math.min(mini, Math.abs(height[i]-height[i-2]) + minCostTo(height, i-2));
        }
        return mini;
    }
}
