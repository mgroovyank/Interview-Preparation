// https://leetcode.com/problems/word-ladder/
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordListSet = new HashSet<>(wordList);
        Queue<String> q = new ArrayDeque<>();
        q.offer(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        int res = 1;
        while(!q.isEmpty()){
            int n = q.size();
            for(int i=0;i<n;i++){
                String word = q.poll();
                if(word.equals(endWord)){
                    return res;
                }
                for(int j=0;j<word.length();j++){
                    for(int k=0;k<26;k++){
                        StringBuilder nextWord = new StringBuilder(word);
                        nextWord.setCharAt(j, (char)('a' + k));
                        if(wordListSet.contains(nextWord.toString()) && visited.add(nextWord.toString())){
                            q.offer(nextWord.toString());
                        }
                    }
                }
            }
            res++;
        }
        return 0;
    }

// Time Complexity: O(N*M*26) N=wordList size, M=sizeOfWord
// Space Complexity: O(N)
}
