// https://leetcode.com/problems/unique-paths/

// Time Complexity: O(2^MN)
// Space Complexity: O(M+N) - length of longest Path
class Solution {
    public int uniquePaths(int m, int n) {
        // Recursion - Top Down Approach
        // f(i,j) = f(i-1, j) * 1 + f(i, j-1) * 1
        return uniquePathsTo(m-1, n-1);
    }

    private int uniquePathsTo(int i, int j){
        if(i==0 && j==0){
            return 1;
        }
        if(i<0 || j<0){
            return 0;
        }
        return uniquePathsTo(i-1, j) + uniquePathsTo(i, j-1);
    }
}


// Time Complexity: O(M*N*1)
// Space Complexity: O(M*N) + (M+N)
class Solution {
    public int uniquePaths(int m, int n) {
        // Recursion - Top Down Approach
        // f(i,j) = f(i-1, j) * 1 + f(i, j-1) * 1
        // dp on i,j - changing parameters(dynamic) - memoization
        int[][] dp = new int[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                dp[i][j] = -1;
            }
        }
        uniquePathsTo(m-1, n-1, dp);
        return dp[m-1][n-1];
    }

    private int uniquePathsTo(int i, int j, int[][] dp){
        if(i==0 && j==0){
            return dp[i][j]=1;
        }
        if(i<0 || j<0){
            return 0;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        dp[i][j] = (uniquePathsTo(i-1, j, dp) + uniquePathsTo(i, j-1, dp));
        return dp[i][j];
    }
}

// Time Complexity: O(M*N*1)
// Space Complexity: O(M*N)
class Solution {
    public int uniquePaths(int m, int n) {
        // Tabulation, Bottom Up Approach
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        // iteration on all states reached via recursion
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                // "do stuff" on indexes code
                if(i==0 && j==0){
                    dp[i][j] = 1;
                    continue;
                }
                int up = 0;
                int left = 0;
                if(i-1 >= 0){
                    up = dp[i-1][j];
                }
                if(j-1>=0){
                    left = dp[i][j-1];
                }
                dp[i][j] = up + left;
            }
        }
        return dp[m-1][n-1];
    }
}

// Time Complexity: O(M*N*1)
// Space Complexity: O(N*2)
class Solution {
    public int uniquePaths(int m, int n) {
        // Tabulation, Bottom Up Approach
        // Space Optimization
        int[] prev = new int[n];
        Arrays.fill(prev, 0);
        // iteration on all states reached via recursion
        for(int i=0;i<m;i++){
            int[] temp = new int[n];
            Arrays.fill(temp, -1);
            for(int j=0;j<n;j++){
                // "do stuff" on indexes code
                if(i==0 && j==0){
                    temp[j] = 1;
                    continue;
                }
                int up = 0;
                int left = 0;
                if(i-1 >= 0){
                    up = prev[j];
                }
                if(j-1>=0){
                    left = temp[j-1];
                }
                temp[j] = up + left;
            }
            prev = temp;
        }
        return prev[n-1];
    }
}

// Time Complexity: O(Min(M,N))
class Solution {
    public int uniquePaths(int m, int n) {
        // To reach end, I have to move m-1 rows downward and n-1 columns righwards
        // mandatorily
        // So Total Number of Moves = m-1 + n-1 = m + n - 2
        // ans = choose m-1 downward moves out of total moves
        // return combinations(m+n-2, Math.min(m-1, n-1)); // 58,8
        return combinations(m+n-2, m-1); // 58,8
    }
    
    // n choose k
    // (n*(n-1)*(n-2) .... (n-k+1)) / (k*(k-1)*(k-2) ..... 1)
    private int combinations(int n, int k){
        if(k<0 || k>n){
            return 0;
        }
        if(k == 0 || k==n){
            return 1;
        }
        // not required if I'm using min
        if(k>n/2){
            k = n-k;
        }
        long res = 1;
        for(int i=1;i<=k;i++){
            res = res * (n-i+1)/i; // n/1 , n-1/2, n-2/3 ....... n-k/k-1,n-k+1/k
        }
        return (int)res;
    }
}
