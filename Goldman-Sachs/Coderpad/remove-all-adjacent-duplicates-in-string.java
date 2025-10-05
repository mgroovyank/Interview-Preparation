// https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/
class Solution {
    public String removeDuplicates(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        int n = s.length();
        int i = 0;
        while(i<n){
            char c = s.charAt(i);
            if(stack.isEmpty()){
                stack.push(c);
            }else if(stack.peek() == c){
                // duplicate found
                stack.pop();
            }else{
                // no duplicate
                stack.push(c);
            }
            i++;
        }
        StringBuilder rs = new StringBuilder();
        while(!stack.isEmpty()){
            rs.append(stack.pop());
        }
        return rs.reverse().toString();
    }
}
