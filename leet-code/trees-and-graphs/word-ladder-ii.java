// https://www.geeksforgeeks.org/problems/word-ladder-ii/1
// this solution doesn't work on leet code
class Solution {
    public ArrayList<ArrayList<String>> findSequences(String startWord,
                                                      String targetWord,
                                                      String[] wordLists) {
        String beginWord = startWord;
        String endWord = targetWord;
        List<String> wordList = new ArrayList<>();
        for(String word: wordLists){
            wordList.add(word);
        }
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        int len = beginWord.length();
        Set<String> words = new HashSet<>(wordList);
        Deque<ArrayList<String>> q = new ArrayDeque<>();
        q.add(new ArrayList(List.of(beginWord)));
        words.remove(beginWord);
        while(!q.isEmpty()){
            int numInCurrLevel = q.size();
            List<String> wordsVisitedFromCurrentLevel = new ArrayList<>();
            while(numInCurrLevel != 0){
                ArrayList<String> currSeq = q.remove();
                numInCurrLevel--;
                String currWord = currSeq.getLast();
                if(currWord.equals(endWord)){
                    result.add(currSeq);
                    continue;
                }
                for(int i=0;i<len;i++){
                    StringBuilder temp = new StringBuilder(currWord);
                    for(int j=0;j<26;j++){
                        temp.setCharAt(i, (char)('a' + j));
                        String nextWord = temp.toString();
                        if(words.contains(nextWord)){
                            ArrayList<String> nextSeq = new ArrayList<>(currSeq);
                            nextSeq.add(nextWord);
                            q.add(nextSeq);
                            wordsVisitedFromCurrentLevel.add(nextWord);
                        }
                    }
                }
            }
            // next words visited from previous level will not be visited via a smaller path now
            // so should not be visited again as that will be a longer path
            for(String word: wordsVisitedFromCurrentLevel){
                words.remove(word);
            }
        }
        return result;
        
    }
}
