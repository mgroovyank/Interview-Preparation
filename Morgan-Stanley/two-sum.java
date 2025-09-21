// https://leetcode.com/problems/two-sum/ 
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numToIdxs = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            int curr = nums[i];
            int toFind = target-nums[i];
            if(numToIdxs.containsKey(toFind)){
                return new int[]{i, numToIdxs.get(toFind)};
            }
            numToIdxs.put(curr, i);
        }
        return new int[]{};
    }
}
