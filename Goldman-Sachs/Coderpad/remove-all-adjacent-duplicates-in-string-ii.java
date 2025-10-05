// https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/
// Time Complexity: O(N)
class Solution {
    public String removeDuplicates(String s, int k) {
        // stack operations - push, pop, peek
        Deque<CharAndCount> stack = new ArrayDeque();
        int n = s.length();
        int i = 0;
        while(i<n){
            char currentChar = s.charAt(i);
            CharAndCount prevCharAndCount = null; 
            if(!stack.isEmpty()){
                prevCharAndCount = stack.peek();
            }
            if(prevCharAndCount!=null && currentChar == prevCharAndCount.getChar()){
                int nowCount = prevCharAndCount.incrementCount();
                if(nowCount >= k){
                    stack.pop();
                }
            }else{
                CharAndCount newCharAndCount = new CharAndCount(currentChar);
                stack.push(newCharAndCount);
            }
            i++;
        }
        StringBuilder rs = new StringBuilder();
        while(!stack.isEmpty()){
            CharAndCount charAndCount = stack.pop();
            for(int j=0;j<charAndCount.getCount();j++){
                rs.append(charAndCount.getChar());
            }
        }
        return rs.reverse().toString();

    }

    class CharAndCount{
        char c;
        int count;

        public CharAndCount(char c){
            this.c = c;
            this. count = 1;
        }

        public int incrementCount(){
            this.count++;
            return this.count;
        }

        public char getChar(){
            return this.c;
        }

        public int getCount(){
            return this.count;
        }
    }
}
