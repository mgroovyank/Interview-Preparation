// https://leetcode.com/problems/trapping-rain-water/

class Solution {
    public int trap(int[] height) {
        // 1. To store water at any position it needs support from left and right
        // 2. for every height we need max height on left and right
        // 3. use two pointers to maintain leftMax and RightMax
        int n = height.length;
        int leftMax = 0;
        int rightMax = 0;
        int left = 0;
        int right = n-1;
        int ans = 0;
        while(left<right){
            if(height[left] <= height[right]){
                // support on right will definitely be there and left is smaller so that will be deciding factor
                if(leftMax > height[left]){ // on left also support is there
                    // How to find which is smaller leftMax or rightSupport??
                    // I've processed leftMax and reached the current left only when I would've found
                    // something bigger that leftMax on right. so leftMax is smaller than that right element or
                    // if that right element became rightMax, then also leftMax is smaller than even rightMax
                    // => leftMax is smaller than height[right]
                    ans += leftMax - height[left];
                }else{
                    leftMax = height[left];
                }
                left++; // traversing smaller one first
            }else{
                // height on right is smaller than height on left.
                // since I've processed rightMax, that means, I moved right inward because I found something
                // on left bigger than rightMax
                // so that means if there is some rightMax which can support water on right side, then
                // right can hold water
                if(rightMax > height[right]){
                    ans += rightMax - height[right];
                }else{
                    rightMax = height[right];
                }
                right--;
            }
        }
        return ans;
    }
}

class Solution {
    public int trap(int[] height) {
        // 1. To store water at any position it needs support from left and right
        // 2. for every height we need max height on left and right
        int n = height.length;
        int[] leftMax = new int[n];
        leftMax[0] = height[0];
        for(int i=1;i<n;i++){
            leftMax[i] = Math.max(leftMax[i-1], height[i]);
        }
        int[] rightMax = new int[n];
        rightMax[n-1] = height[n-1];
        for(int i=n-2;i>=0;i--){
            rightMax[i] = Math.max(rightMax[i+1], height[i]);
        }
        int ans = 0;
        for(int i=0;i<n;i++){
            ans += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return ans;
    }
}
