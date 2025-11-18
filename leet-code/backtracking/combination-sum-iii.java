// https://leetcode.com/problems/combination-sum-iii/

// Time Complexity: O(9^k)
// Space Complexity: O(k)
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> currList = new ArrayList<>();
    int currSum = 0;
    int[] visited = new int[10];

    public List<List<Integer>> combinationSum3(int k, int n) {
        Arrays.fill(visited, 0);
        visited[0] = 1;
        dfs(k, n);
        return result;
    }

    void dfs(int digits, int sum){
        if(currList.size() == digits) {
            if(currSum == sum){
                result.add(new ArrayList<>(currList));
            }
            return;
        }
        for(int i=1;i<10;i++) {
            // since we don't want same combination to be repeated
            if(!currList.isEmpty() && i < currList.get(currList.size()-1)) {
                continue;
            }
            if(visited[i] == 0 && currSum + i <= sum) {
                currList.add(i);
                currSum = currSum + i;
                visited[i] = 1;
                dfs(digits, sum);
                currList.remove(currList.size()-1);
                currSum = currSum - i;
                visited[i] = 0;
            }
        }
    }
}
