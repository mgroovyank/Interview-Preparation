// https://leetcode.com/problems/largest-rectangle-in-histogram/

// Time Complexity: O(n)
// Space Complexity: O(n)
class Solution {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] leftSmall = new int[n];
        int[] rightSmall = new int[n];

        Deque<Integer> s = new ArrayDeque<>();

        for(int i=0;i<n;i++){
            while(!s.isEmpty() && heights[s.peek()] >= heights[i]){
                s.pop();
            }
            if(s.isEmpty()){
                leftSmall[i] = 0;
            }else{
                leftSmall[i] = s.peek() + 1;
            }
            s.push(i);
        }

        s = new ArrayDeque<>();

        for(int i=n-1;i>=0;i--){
            while(!s.isEmpty() && heights[s.peek()] >= heights[i]){
                s.pop();
            }
            if(s.isEmpty()){
                rightSmall[i] = n-1;
            }else{
                rightSmall[i] = s.peek() - 1;
            }
            s.push(i);
        }

        int largestRectangle = 0;
        for(int i=0;i<n;i++){
            // (i - leftSmall[i]+1) + (rightSmall[i] - i+1) - 1 = totalWidth = rightSmall[i] - leftSmall[i] + 1
            largestRectangle = Math.max(largestRectangle, (rightSmall[i] - leftSmall[i] + 1)*heights[i]);
        }
        return largestRectangle;
    }
}

// Different indexing strategy
class Solution {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] leftSmall = new int[n];
        int[] rightSmall = new int[n];

        Deque<Integer> s = new ArrayDeque<>();

        for(int i=0;i<n;i++){
            while(!s.isEmpty() && heights[s.peek()] >= heights[i]){
                s.pop();
            }
            if(s.isEmpty()){
                leftSmall[i] = -1;
            }else{
                leftSmall[i] = s.peek();
            }
            s.push(i);
        }

        s = new ArrayDeque<>();

        for(int i=n-1;i>=0;i--){
            while(!s.isEmpty() && heights[s.peek()] >= heights[i]){
                s.pop();
            }
            if(s.isEmpty()){
                rightSmall[i] = n;
            }else{
                rightSmall[i] = s.peek();
            }
            s.push(i);
        }

        int largestRectangle = 0;
        for(int i=0;i<n;i++){
            // (i - leftSmall[i]) + (rightSmall[i] - i) - 1 = totalWidth
            largestRectangle = Math.max(largestRectangle, (rightSmall[i] - leftSmall[i]-1)*heights[i]);
        }
        return largestRectangle;
    }
}
