// https://leetcode.com/problems/longest-string-chain/

// Time Complexity: O(nlogn) + O(n^2)
// Space Complexity: O(n)
class Solution {
    public int longestStrChain(String[] words) {
        // since word size is max just 16 - so that is constant time complexity
        // if i sort words on basis of length of words
        // if I have a LSC, then I can add another word to that LSC just by comparing 
        // with last word in previous LSC
        int n = words.length;
        Arrays.sort(words, new LengthComparator());
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int res = 1;
        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(checkPredecessor(words[j],words[i])){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    // check a is predecessor of b
    private boolean checkPredecessor(String a, String b){
        if(a.length()+1 != b.length()){
            return false;
        }
        int i = 0;
        int j = 0;
        while(i<a.length() && j<b.length()){
            if(a.charAt(i) == b.charAt(j)){
                i++;
                j++;
                continue;
            }
            j++;
        }
        // only 1 char difference is there
        // also i moves ahead only when matches
        // if both conditions are applicable, then below condition works
        if(i==a.length() && (j==b.length() || j==b.length()-1)){
            return true;
        }
        return false;
    }

    class LengthComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b){
            return a.length() - b.length();
        }
    }
}
