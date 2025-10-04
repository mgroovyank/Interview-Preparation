// https://leetcode.com/problems/longest-increasing-subsequence/

// Using Basic Recursion Approach
// Time Complexity: O(2^N) - exponential
public class IncreasingSubsequences {
    public static void generateSubsequences(int[] arr, int index, List<Integer> current, List<List<Integer>> result) {
        if (index == arr.length) {
            if (!current.isEmpty()) {
                result.add(new ArrayList<>(current));
            }
            return;
        }

        // Include the current element if it's greater than the last one in current
        if (current.isEmpty() || arr[index] > current.get(current.size() - 1)) {
            current.add(arr[index]);
            generateSubsequences(arr, index + 1, current, result);
            current.remove(current.size() - 1); // backtrack
        }

        // Exclude the current element
        generateSubsequences(arr, index + 1, current, result);
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 2};
        List<List<Integer>> result = new ArrayList<>();
        generateSubsequences(arr, 0, new ArrayList<>(), result);

        for (List<Integer> subseq : result) {
            System.out.println(subseq);
        }
    }
}


// Using Memoization
// Time Complexity: O(N^2)
// Space Complexity: O(N^2) + O(N) - recursion stack
class Solution {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        // currIdx, prevIdx
        int[][] dp = new int[n][n+1];
        for(int[] currIdxs: dp){
            Arrays.fill(currIdxs, -1);
        }
        return lis(nums, 0, -1, dp);
    }

    private int lis(int[] nums, int currIdx, int prevIdx, int[][] dp){
        if(currIdx == nums.length){
            return 0;
        }
        if(dp[currIdx][prevIdx+1] != -1){
            return dp[currIdx][prevIdx+1];
        }
        if(prevIdx==-1 || nums[currIdx] > nums[prevIdx]){
            return dp[currIdx][prevIdx+1] = Math.max(1 + lis(nums, currIdx+1, currIdx, dp), lis(nums, currIdx+1, prevIdx, dp));
        }
        return dp[currIdx][prevIdx+1] = lis(nums, currIdx+1, prevIdx, dp);
    }
}

