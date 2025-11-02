// https://leetcode.com/problems/maximal-rectangle/

// Time Complexity: O(m*n) + O(m*n)
class Solution {
    public int maximalRectangle(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] intMatrix = new int[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                intMatrix[i][j] = matrix[i][j] == '1' ? 1 : 0;
                if(i>0){
                    if(intMatrix[i][j] != 0){
                        intMatrix[i][j] = intMatrix[i-1][j] + 1;
                    }
                }
            }
        }
        int ans = 0;
        for(int i=0;i<rows;i++){
            ans = Math.max(ans, largestRectangleArea(intMatrix, i));
        }
        return ans;
    }

    private int largestRectangleArea(int[][] intMatrix, int row) {
        int[] heights = intMatrix[row];
        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int ans = 0;
        for(int i=0;i<=n;i++){
            while(!stack.isEmpty() && (i==n || heights[stack.peek()] > heights[i])){
                // found right smaller element for stack peek
                int height = heights[stack.peek()];
                int rs = i;
                stack.pop();
                int ls = -1;
                if(!stack.isEmpty()){
                    ls = stack.peek();
                }
                ans = Math.max(ans, (rs - ls - 1) * height);
            }
            stack.push(i);
        }
        return ans;
    }
}
