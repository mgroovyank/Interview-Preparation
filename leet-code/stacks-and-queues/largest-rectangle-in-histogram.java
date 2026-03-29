// https://leetcode.com/problems/largest-rectangle-in-histogram/
class Solution {
    /**
    Naive Approach:
    1. consider all possible (i,j) pairs where 0<=i,j<heights.length
    2. Calculate valid area using (j-i+1) * Min(heights i...j)
    3. Pick max area among all such (i,j) pairs as answer
    4. O(n^3)
    5. Min height caluculation can be optimized by updating it for every j as we do j++ and reset it to
    heights[i] for each next i
    6. => O(n^2)

    o(2^n) approach
    1. Let's start with biggest possible width 
    2. Calculate area for this width
    3. answer  = Max(currentArea, max(largestRectangleArea(heights, i+1, j), largestRectangleArea(heights, i, j-1)))

    O(nlogN) approach
    1. where you split the two subproblems by k index - the min for the current window
    2. I know in any window where that kth index is present, the height is fixed to that, and the
       width will always be lesser than current window width, so next possible answer will have to be 
       without that kth index being in the width
    3. O(n) = O(K) + (N-K) + O(N) = 2*O(N/2) + O(N)  = O(N*LOGN)
    4. but even in this case if k is towards either end of array, that becomes bad complexity

    O(N) approach
    1. Bar height is the limiting factor for an area i.e. if we fix a bar height, the valid area
    that we can have can include only equal or bigger heights.
    2. So each bar can be tried as an option for the height of largestRectangle, for that bar
    we need to find firstLeftSmallerHeight(exclude equal height) and nextSmallerHeight(exclude equal height)
    3. So if we can get these values for each bar in O(1) time, the time complexity can be O(N)
    4. Also for calculating these values for each bar, we can take O(N) complexity

    #calculating leftSmallerHeight for each index
    1. First Bar - we can just put it as 0
    2. Then we move on to next Bar
        -> If the currentHeight is smaller or equal than previous height(i-1), we increase left width
            -> now we should  compare the currentHeight with i-2
        -> if currentHeight is bigger, then we got the leftSmallerHeight

    3. [2 1 5 6 2 3]
       [-1] - leftSmallerThan 2 is -1
       [-1 -1] - leftSmallerThan1 - I check 2, is 2 < 1 => no, => what is smallerThan2 = -1 => -1 is end
       so stop, that is the answer for 1
       [-1 -1 1] - 5 smaller than 1 => answer=1
       [-1 -1 1 2] - 5 smaller than 6 => answer= 5
       [-1 -1 1 2 1] - 6>2 => what is smaller than 6 => 2 index i.e.5 => 5>2 => what is smaller than 5
       => 1st index - 1<2 => answer=1 =
       [-1 -1 1 2 1 4] - 2<3
       +1 index = [0 0 2 3 2 5]
       here we see we are making hops in the leftSmallerArray that we have, that is O(N) time complexity
       and we need to do that hop for each ith index => O(N^2)
       so if somehow we can make avoid these hops, we can reduce time complexity: O(N)*O(1)
       - if we see anytime we need to move beyond previous element we just need to have elements which
       are in linerly increasing order(from left to right) or in other words linearly decreasing order
       (from right to left) for comparison
       so this same hops where we skip indices is acheived via stack as it is more cleaner approach
       and more easy to maintain.

     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] leftSmallerIdx = new int[n];
        int[] nextSmallerIdx = new int[n];

        // calculate leftSmallerIdx
        Deque<Integer> stack = new ArrayDeque<>();
        for(int i=0;i<n;){
            if(stack.isEmpty()){
                stack.push(i);
                leftSmallerIdx[i++] = -1;
                continue;
            }
            int top = stack.peek();
            if(heights[top] < heights[i]){
                stack.push(i);
                leftSmallerIdx[i++] = top;
            }else{
                stack.pop();
            }
        }

        // calculate nextSmallerIdx
        stack = new ArrayDeque<>();
        for(int i=n-1;i>=0;){
            if(stack.isEmpty()){
                stack.push(i);
                nextSmallerIdx[i--] = n;
                continue;
            }
            int top = stack.peek();
            if(heights[top] < heights[i]){
                stack.push(i);
                nextSmallerIdx[i--] = top;
            }else{
                stack.pop();
            }
        }

        int largestRectangleArea = Integer.MIN_VALUE;
        for(int i=0;i<n;i++){
            int area = heights[i] * (nextSmallerIdx[i] - leftSmallerIdx[i] - 1);
            largestRectangleArea = Math.max(largestRectangleArea, area);
        }
        return largestRectangleArea;
    }
}
