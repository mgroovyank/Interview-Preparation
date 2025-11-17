// https://leetcode.com/problems/generate-parentheses/

// Time Complexity: O(2^n)
// Space Complexity: O(2*n)
class Solution {

    List<String> result = new ArrayList<>();
    StringBuilder currStr = new StringBuilder("");

    public List<String> generateParenthesis(int n) {
        // 1. you can pick open anytime you want if open are available
        // 2. you can pick close only if there is a corresponding previous open available
        // 3. how to keep track of that? - use co = count of open
        int open = n;
        int close = n;
        open--;
        int co = 1;
        currStr.append('(');
        dfs(open, close, co);
        return result;
    }

    void dfs(int open, int close, int co){
        if(open == 0 && close == 0){
            result.add(currStr.toString());
            return;
        }
        // try to add open first
        if(open > 0){
            currStr.append('(');
            dfs(open - 1, close, co + 1);
            currStr.deleteCharAt(currStr.length() - 1);
        }
        if(close > 0 && co>0){
            currStr.append(')');
            dfs(open, close - 1, co - 1);
            currStr.deleteCharAt(currStr.length() - 1);
        }
    }
}
