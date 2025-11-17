// https://leetcode.com/problems/letter-combinations-of-a-phone-number/

// Time Complexity: O(4^n)
// Space Complexity: O(n)
class Solution {

    List<String> result = new ArrayList<>();
    StringBuilder currStr = new StringBuilder("");
    Map<Integer, List<Character>> digitToCharsMap = new HashMap<>();

    public List<String> letterCombinations(String digits) {
        digitToCharsMap.put(2, List.of('a', 'b', 'c'));
        digitToCharsMap.put(3, List.of('d', 'e', 'f'));
        digitToCharsMap.put(4, List.of('g', 'h', 'i'));
        digitToCharsMap.put(5, List.of('j', 'k', 'l'));
        digitToCharsMap.put(6, List.of('m', 'n', 'o'));
        digitToCharsMap.put(7, List.of('p', 'q', 'r', 's'));
        digitToCharsMap.put(8, List.of('t', 'u', 'v'));
        digitToCharsMap.put(9, List.of('w', 'x', 'y', 'z'));

        dfs(digits, 0);
        return result;
    }

    void dfs(String digits, int currIdx){
        if(currIdx == digits.length()){
            result.add(currStr.toString());
            return;
        }
        List<Character> possibleChars = digitToCharsMap.get(digits.charAt(currIdx) - '0');
        for(char c: possibleChars){
            currStr.append(c);
            dfs(digits, currIdx+1);
            currStr.deleteCharAt(currStr.length() - 1);
        }
    }
}
