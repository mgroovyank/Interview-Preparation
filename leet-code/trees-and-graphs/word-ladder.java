// https://leetcode.com/problems/word-ladder/

// Time complexity: O(N * word length * 26 * O(1)/O(logN))
class Solution {
    /**
    1. At any point, i can try changing each character in the word to any of the a-z chars
       only if after change the word exists in wordList
    2. If after change, word is not word list, stop there
    3. If after change, word exists in word list, move ahead and repeat
    4. at some point if you find your endWord, that's a possible answer
    5. but maybe there are other better ways as well to reach that word, so need to explore
       all cases
    6. also you have to do BFS in this case - as shortest path is needed
    7. So I think we have a Map<String, Set<String>> where key in (beginWord, endWord, ...wordList)
    8. and then just do a BFS ans store distance of each word when it is visited
    9. at the end of BFS, if the endWord is not visited, return 0. otherwise return distance[endWord]
    10. Number of vertices = number of words, number of edges=   
    11. The below implemented approach is little simpler and efficient than the described approach.
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> words = new HashSet<>(wordList);
        Deque<Pair> q = new ArrayDeque<>();
        q.add(new Pair(beginWord, 1));
        while(!q.isEmpty()){
            Pair curr = q.remove();
            if(curr.s.equals(endWord)){
                return curr.dist;
            }
            // N * word length * 26 * O(1)/O(logN)
            for(int i=0;i<curr.s.length();i++){
                StringBuilder temp = new StringBuilder(curr.s);
                for(int j=0;j<26;j++){
                    temp.setCharAt(i, (char)('a'+ j));
                    String nextWord = temp.toString();
                    if(words.contains(nextWord)){
                        q.add(new Pair(nextWord, curr.dist + 1));
                        words.remove(nextWord);
                    }
                }
            }
        }
        return 0;
    }

    class Pair{
        String s;
        int dist;
        Pair(String s, int dist){
            this.s = s;
            this.dist = dist;
        }
    }
}

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
