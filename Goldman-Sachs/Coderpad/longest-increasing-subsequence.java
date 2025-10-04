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

// Getting LIS from above solution

class Solution {
    public List<Integer> getLIS(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        // Fill dp table
        lis(nums, 0, -1, dp);

        // Reconstruct LIS
        List<Integer> result = new ArrayList<>();
        int currIdx = 0, prevIdx = -1;

        while (currIdx < n) {
            int include = 0;
            if (prevIdx == -1 || nums[currIdx] > nums[prevIdx]) {
                include = 1 + dp[currIdx + 1][currIdx + 1];
            }
            int exclude = dp[currIdx + 1][prevIdx + 1];

            if (include >= exclude) {
                result.add(nums[currIdx]);
                prevIdx = currIdx;
            }
            currIdx++;
        }

        return result;
    }

    private int lis(int[] nums, int currIdx, int prevIdx, int[][] dp) {
        if (currIdx == nums.length) {
            return 0;
        }

        if (dp[currIdx][prevIdx + 1] != -1) {
            return dp[currIdx][prevIdx + 1];
        }

        int include = 0;
        if (prevIdx == -1 || nums[currIdx] > nums[prevIdx]) {
            include = 1 + lis(nums, currIdx + 1, currIdx, dp);
        }

        int exclude = lis(nums, currIdx + 1, prevIdx, dp);

        dp[currIdx][prevIdx + 1] = Math.max(include, exclude);
        return dp[currIdx][prevIdx + 1];
    }
}


// Reduced space complexity to O(N)
class Solution {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n]; // hold LIS which ends by including element at index i
        Arrays.fill(dp, 1);
        int lis = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<i;j++){
                if(nums[j] < nums[i]){
                    dp[i] = Math.max(1 + dp[j], dp[i]);
                }
            }
            lis = Math.max(lis, dp[i]);
        }
        return lis;
    }
}

// Print LIS
// Reduced space complexity to O(N)
class Solution {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n]; // hold LIS which ends by including element at index i
        int[] prevIdx = new int[n];
        Arrays.fill(dp, 1);
        int lis = 0;
        int lisIdx = -1;
        for(int i=0;i<n;i++){
            prevIdx[i] = i;
            for(int j=0;j<i;j++){
                int temp = dp[i];
                if(nums[j] < nums[i]){
                    dp[i] = Math.max(1 + dp[j], dp[i]);
                }
                if(dp[i] > temp){
                    prevIdx[i] = j;
                }
            }
            if(dp[i] > lis){
                lisIdx = i;
            }
            lis = Math.max(lis, dp[i]);
        }
        // Print LIS
        int[] lisNums = new int[lis];
        int i = lis - 1;
        
        while(lisIdx != prevIdx[lisIdx]){
            lisNums[i] = nums[lisIdx];
            lisIdx = prevIdx[lisIdx];
            i--;
        }
        lisNums[i] = nums[lisIdx];
        System.out.println(Arrays.toString(lisNums));
        return lis;
    }
}
