// https://www.geeksforgeeks.org/problems/alien-dictionary/1

class Solution {
    /**
     * 1. Alphabets can be considered as vertices in a graph from 0 to 25.(a to b)
     * 2. I should not consider vertices from a to b, but only those which appear in 
     * the words.
     * 3. For simplicity, I'll still use 0 to 26 vertices, but I'll also mark
     * the alphabets as visited only when they actually occur in any of the words.
     * 4. While starting my topological sort, I'll start with only these
     *    visited nodes who have indegree=0.
     * 5. This way I'll only consider alphabets that actually occur in the words
     *    to be part of my sort.
     * 6. Also this way, it'll handle the scenarios where the alphabets occuring in words
     *    are not strictly in +1 increasing order. ex: a to d and then z is there.
    */
    public String findOrder(String[] words) {
        int numWords = words.length;
        int[] alphabetFlag = new int[26];
        int alphabetsCount = 0;
        
        // mark alphabets which actually occur in words
        for(String word: words){
            for(int j=0;j<word.length();j++){
                int alphabet = word.charAt(j) - 'a';
                if(alphabetFlag[alphabet] == 0){
                    alphabetsCount++;
                    alphabetFlag[alphabet] = 1;
                }
            }
        }
        
        List<Set<Integer>> graph = new ArrayList<>();
        for(int i=0;i<26;i++){
            graph.add(new HashSet<>());
        }
        
        int[] indegree = new int[26];
        
        // process edges
        for(int i=1;i<numWords;i++){
            String prevWord = words[i-1];
            String currWord = words[i];
            int minLength = Math.min(prevWord.length(), currWord.length());
            boolean currWordIsPrefix = true;
            for(int j=0;j<minLength;j++){
                int u = prevWord.charAt(j) - 'a';
                int v = currWord.charAt(j) - 'a';
                if(u != v){
                    if(!graph.get(u).contains(v)){
                        graph.get(u).add(v);
                        indegree[v]++;
                    }
                    currWordIsPrefix = false;
                    break;
                }
            }
            if(currWordIsPrefix && currWord.length()<prevWord.length()){
                return "";
            }
        }
        
        // Topological Sort
        Deque<Integer> q = new ArrayDeque<>();
        for(int i=0;i<26;i++){
            if((alphabetFlag[i] == 1) && (indegree[i] == 0)){
                q.add(i);
            }
        }
        
        StringBuilder result = new StringBuilder();
        while(!q.isEmpty()){
            int currNode = q.remove();
            result.append((char)('a' + currNode));
            Set<Integer> neighbors = graph.get(currNode);
            for(int neighbor: neighbors){
                indegree[neighbor]--;
                if(indegree[neighbor] == 0){
                    q.add(neighbor);
                }
            }
            
        }
        
        return result.length() == alphabetsCount ? result.toString() : "";
        
        
        
    }
}
