// https://www.geeksforgeeks.org/problems/matrix-chain-multiplication0303/1

// Time Complexity: Recursion = Exponential
// Time Complexity: O(n*n*n)
// Space Complexity: O(n*n) + O(n)
class Solution { 
    static int matrixMultiplication(int arr[]) {
        // number of multiplication operations for matrices of size a x b and b x c
        // = a x b x c
        //ABCD = (A)(BCD), (AB)(CD), (ABC)(D)
        //f(arr, i, j) = least number of multiplications to multiply matrices
        // from ith index to jith index
        //f(arr, i, j) = f(arr, i, k) + f(arr, k+1, j) + arr[i-1] * arr[k] * arr[j]
        int n = arr.length;
        int[][] dp = new int[n][n];
        for(int[] d: dp){
            Arrays.fill(d, -1);
        }
        return leastMatrixMultiplication(arr, 1, n-1, dp);
    }
    
    static int leastMatrixMultiplication(int[] arr, int i, int j, int[][] dp){
        // base case
        if(i == j){
            return 0;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        int res = Integer.MAX_VALUE;
        for(int k=i;k<j;k++){
            int steps = arr[i-1] * arr[k] * arr[j];
            steps += leastMatrixMultiplication(arr, i, k, dp) + leastMatrixMultiplication(arr, k+1, j, dp);
            res = Math.min(res, steps);
        }
        return dp[i][j] = res;
    }
}
