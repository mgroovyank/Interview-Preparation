/**
Given an array heights[], for each index i, find the difference in indices to the next taller height (i.e., the first height greater than heights[i] to its right). 
If no such height exists, return -1 for that index.
**/

/**

Iterate from back and then jump to element bigger than previous element, when previous element is smaller than current element.
5 3 8 2 6
0 1 2 3 4

2 2 -1 4 -1
2 1 -1 1 -1

To solve this problem efficiently, we can use a **monotonic stack** — a classic approach for "next greater element" type problems.
**/

public class Solution {
    public int[] nextTallerDiff(int[] heights) {
        int n = heights.length;
        int[] result = new int[n];
        Arrays.fill(result, -1); // default to -1
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] <= heights[i]) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                result[i] = stack.peek() - i;
            }
            stack.push(i);
        }

        return result;
    }
}
// Time Complexity: O(N)
// Space Complexity: O(N)
