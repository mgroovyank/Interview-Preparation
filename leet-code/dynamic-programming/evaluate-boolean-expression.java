// https://www.geeksforgeeks.org/problems/boolean-parenthesization5610/1

// User function Template for Java
class Solution {
    static int countWays(String s) {
        // xor = same as OR, just that if both are T, then xor returns false
        int n = s.length();
        return countWaysPartition(s, 0, n-1, 1);
    }
    
    static int countWaysPartition(String s, int i, int j, int isTrue){
        // base case
        if(i>j){
            return 0;
        }
        if(i == j){
            if(s.charAt(i) == 'T'){
                if(isTrue == 1){
                    return 1;
                }else{
                    return 0;
                }
            }else{
                if(isTrue == 1){
                    return 0;
                }
                return 1;
            }
        }
        int result = 0;
        // use kth index for partition
        for(int k=i+1;k<j;k+=2){
            char operator = s.charAt(k);
            int lT = countWaysPartition(s, i, k-1, 1);
            int lF = countWaysPartition(s, i, k-1, 0);
            int rT = countWaysPartition(s, k+1, j, 1);
            int rF = countWaysPartition(s, k+1, j, 0);
            if(operator == '&'){
                if(isTrue == 1){
                    result += (lT * rT);
                }else{
                    result += (lF * rF + lF*rT + lT*rF);
                }
            }else if(operator == '|'){
                if(isTrue == 1){
                    result += (lT*rT + lT*rF + lF*rT);
                }else{
                    result += lF*rF;
                }
            }else {
                if(isTrue == 1){
                    result += (lT*rF + lF*rT);
                }else{
                    result += (lT*rT + lF*rF);
                }
            }
        }
        return result;
    }
}

// DP
class Solution {
    static int countWays(String s) {
        // xor = same as OR, just that if both are T, then xor returns false
        int n = s.length();
        int[][][] dp = new int[n][n][2];
        for(int[][] d1: dp){
            for(int[] d2: d1){
                Arrays.fill(d2, -1);
            }
        }
        return countWaysPartition(s, 0, n-1, 1, dp);
    }
    
    static int countWaysPartition(String s, int i, int j, int isTrue, int[][][] dp){
        // base case
        if(i>j){
            return 0;
        }
        if(i == j){
            if(s.charAt(i) == 'T'){
                if(isTrue == 1){
                    return 1;
                }else{
                    return 0;
                }
            }else{
                if(isTrue == 1){
                    return 0;
                }
                return 1;
            }
        }
        if(dp[i][j][isTrue] != -1){
            return dp[i][j][isTrue];
        }
        int result = 0;
        // use kth index for partition
        for(int k=i+1;k<j;k+=2){
            char operator = s.charAt(k);
            int lT = countWaysPartition(s, i, k-1, 1, dp);
            int lF = countWaysPartition(s, i, k-1, 0, dp);
            int rT = countWaysPartition(s, k+1, j, 1, dp);
            int rF = countWaysPartition(s, k+1, j, 0, dp);
            if(operator == '&'){
                if(isTrue == 1){
                    result += (lT * rT);
                }else{
                    result += (lF * rF + lF*rT + lT*rF);
                }
            }else if(operator == '|'){
                if(isTrue == 1){
                    result += (lT*rT + lT*rF + lF*rT);
                }else{
                    result += lF*rF;
                }
            }else {
                if(isTrue == 1){
                    result += (lT*rF + lF*rT);
                }else{
                    result += (lT*rT + lF*rF);
                }
            }
        }
        return dp[i][j][isTrue]=result;
    }
}
