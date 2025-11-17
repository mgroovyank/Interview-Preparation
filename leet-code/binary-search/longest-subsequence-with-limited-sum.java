// https://leetcode.com/problems/longest-subsequence-with-limited-sum/

// Time Complexity: O(mlogn)
// Space Complexity: O(m)
class Solution {
    public int[] answerQueries(int[] nums, int[] queries) {
        Arrays.sort(nums);
        for(int i=1;i<nums.length;i++){
            nums[i] += nums[i-1];
        }
        // target = queries[i]
        // first prefixSum entry >= target - if equal, then return as it is, but if greater then return ans-1
        // use upper bound as if equal, I still want to have higher length, if more equals are present
        int n = nums.length;
        int m = queries.length;
        int[] answer = new int[m];
        for(int i=0;i<m;i++){
            int low = 0;
            int high = n-1;
            int ans = n;
            while(low<=high){
                int mid = low + (high-low)/2;
                if(nums[mid] > queries[i]){
                    ans = mid;
                    high = mid - 1;
                }else{
                    low = mid + 1;
                }
            }
            answer[i] = ans;
        }
        return answer;
    }
}
