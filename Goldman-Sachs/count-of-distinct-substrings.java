// https://www.geeksforgeeks.org/dsa/count-number-of-distinct-substring-in-a-string/

// Hash set time complexity and balanced tree

/*You are required to complete this method */
class GfG {
    public static int countDistinctSubstring(String st) {
        int n = st.length();
        Set<String> distinctSubstrings = new HashSet<>();
        for(int i=0;i<n;i++){
            StringBuilder sb = new StringBuilder("");
            for(int j=i;j<n;j++){
                sb.append(st.charAt(j));
                distinctSubstrings.add(sb.toString());
            }
        }
        return distinctSubstrings.size();
    }
}

// using string Builder .toString() increases time complexity by length of substring



// even this is O(N^2 * k) as new string is created
/*You are required to complete this method */
class GfG {
    public static int countDistinctSubstring(String st) {
        int n = st.length();
        Set<String> distinctSubstrings = new HashSet<>();
        for(int i=0;i<n;i++){
            String s = "";
            for(int j=i;j<n;j++){
                s = s + st.charAt(j);
                distinctSubstrings.add(s);
            }
        }
        return distinctSubstrings.size() + 1;
    }
}

Space Complexity: O(N^2(number of substrings) * N/2(avg length of substring))



// Get substrings that start with character at 0 index
// Add those substrings to the trie. So for character at index 0, all it's children till end nodes are its possible combinations.
// Move to next index - i.e. 1-index
// Add substrings to trie that start with character at 1-index


//in my approach, I'm not first doing search and then getting a Boolean. if Boolean is false, then I add the substring. If I would've done that //way, would that have been better
//so single pass is made possible by maintain the itr, after checking every character?
//but that is possible because, I know that my next string to insert/find just has next character change
// so that means I know the prefix already

/*
* Time Complexity: O(N^2)
* Space Complexity: O(N^2)
*/
class GfG {
    public static int countDistinctSubstring(String st) {
        Trie t = new Trie();
        TrieNode root = t.getRoot();
        int n = st.length();
        int count = 1;
        for(int firstCharIdx=0;firstCharIdx<n;firstCharIdx++){
            TrieNode itr = root;
            for(int lastCharIdx=firstCharIdx;lastCharIdx<n;lastCharIdx++){
                int charIdx = st.charAt(lastCharIdx) - 'a';
                TrieNode[] children = itr.getChildren();
                if(children[charIdx] == null){
                    // character doesn't exist at node => unique substring
                    count++;
                    children[charIdx] = new TrieNode(); //indicates char now exists
                }
                itr = children[charIdx];
            }
        }
        return count;
    }
}

class TrieNode {
    TrieNode[] children;
    public TrieNode(){
        this.children = new TrieNode[26];
    }
    
    public TrieNode[] getChildren(){
        return this.children;
    }
}

class Trie {
    private TrieNode root;
    public Trie(){
        this.root = new TrieNode();
    }
    
    public TrieNode getRoot(){
        return this.root;
    }
    
}
