// https://leetcode.com/problems/numbers-with-same-consecutive-differences/

// Time Complexity: O(9*2^n-1)
// Space Complexity: O(n)
class Solution {
    List<Integer> result = new ArrayList<>();
    int currNum = 0;
    public int[] numsSameConsecDiff(int n, int k) {
        for(int i=1;i<=9;i++){
            currNum = i;
            dfs(n, k);
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }
    void dfs(int n, int k){
        if(String.valueOf(currNum).length() == n){
            result.add(currNum);
            return;
        }
        int prevDig = currNum%10;
        // +k
        if(prevDig+k <=9){
            currNum = currNum * 10 + (prevDig + k);
            dfs(n, k);
            currNum = currNum / 10;
        }
        if(k == 0){
            return;
        }
        // -k
        if(prevDig-k >=0){
            currNum = currNum * 10 + (prevDig - k);
            dfs(n, k);
            currNum = currNum / 10;
        }
    }
}
