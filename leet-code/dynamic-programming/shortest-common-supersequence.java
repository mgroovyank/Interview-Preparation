// https://leetcode.com/problems/shortest-common-supersequence/

class Solution {
    public String shortestCommonSupersequence(String str1, String str2) {
        int l1 = str1.length();
        int l2 = str2.length();
        int[][] dp = new int[l1+1][l2+1];
        for(int i=0;i<=l1;i++){
            dp[i][0] = 0;
        }
        for(int j=0;j<=l2;j++){
            dp[0][j] = 0;
        }
        for(int i=1;i<=l1;i++){
            for(int j=1;j<=l2;j++){
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    dp[i][j] = 1 + dp[i-1][j-1];
                }else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        int lcs = dp[l1][l2];
        int scs = l1 + l2 - lcs;
        int i=l1;
        int j=l2;
        String scss = "";
        while(i>0 && j>0){
            if(str1.charAt(i-1) == str2.charAt(j-1)){
                scss = str1.charAt(i-1) + scss;
                i--;
                j--;
            }else if(dp[i-1][j] >= dp[i][j-1]){
                scss = str1.charAt(i-1) + scss;
                i--;
            }else{
                scss = str2.charAt(j-1) + scss;
                j--;
            }
        }
        while(i>0){
            scss = str1.charAt(i-1) + scss;
            i--;
        }
        while(j>0){
            scss = str2.charAt(j-1) + scss;
            j--;
        }
        return scss;
    }
}
