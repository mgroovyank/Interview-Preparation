// https://leetcode.com/problems/first-unique-character-in-a-string/
class Solution {
    public int firstUniqChar(String s) {
        Map<Character, Integer> charFrequency = new HashMap<>(); 
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
        }
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(charFrequency.get(c) == 1){
                return i;
            }
        }
        return -1;
    }
}
