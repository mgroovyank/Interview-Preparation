// https://leetcode.com/problems/valid-parentheses/
class Solution {
    /**
    1. Open bracket - store it in order and move ahead
    2. Close bracket
        - needs to have an opening bracket among the open brackets that I've stored till now
        - the last opening bracket that I got - if that matches the closing bracket type, then I'm good
           and I can remove the opening bracket from my store
        - can it happen that the closing bracket that I have is being paired with an opening bracket
          before the last opening bracket that I see
          ex: ([{}
              ([{] - whever a closing bracket is matched with an opening bracket, the string between
                     those two also has to be valid, so => an closing bracket has to be paired with
                     the last opening bracket that I have.
    3. One we match a closing bracket with an opening bracket, we pop that opening bracket from the stack.
    4. next we move ahead in the string
    5. If we traverse entire string and still stack is non empty => Invalid string. Because stack will
       always contain only opening brackets and if no closing bracket opportunities are there, => invalid
    6. Also at any point of time, if closing bracket doesn't match with an opening bracket => invalid

     */
    public boolean isValid(String s) {
        int n = s.length();
        Deque<Character> openBrackets = new ArrayDeque<>();
        for(int i=0;i<n;i++){
            Character c = s.charAt(i);
            if(c == '(' || c == '{' || c == '['){
                openBrackets.push(c);
                continue;
            }
            if(openBrackets.isEmpty()){
                return false;
            }
            Character topOpenBracket = openBrackets.pop();
            boolean isValid = switch(c){
                case ')' -> topOpenBracket == '(';
                case '}' -> topOpenBracket == '{';
                case ']' -> topOpenBracket == '[';
                default -> true;
            };
            if(!isValid){
                return false;
            }
        }
        if(!openBrackets.isEmpty()){
            return false;
        }
        return true;
    }
}
