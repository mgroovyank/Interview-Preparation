// https://leetcode.com/problems/maximum-69-number/

class Solution {
    public int maximum69Number (int num) {
        // 1. convert number to string
        // 2. start from first character - highest place value
        // 3. at each index, change character from 6 to 9
        // 4. never change 9 to 6
        // 5. first time you change 6 to 9 is the answer
        String numStr = String.valueOf(num);
        for(int i=0;i<numStr.length();i++){
            char c = numStr.charAt(i);
            if(c == '6'){
                return Integer.parseInt(numStr.substring(0, i) +  "9" +  numStr.substring(i+1, numStr.length()));
            }
        }
        return num;
    }
}
