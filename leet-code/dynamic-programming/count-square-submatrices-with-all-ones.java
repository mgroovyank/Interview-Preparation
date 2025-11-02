// https://leetcode.com/problems/count-square-submatrices-with-all-ones/
class Solution {
    public int countSquares(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int ans = -matrix[0][0];
        for(int i=0;i<m;i++){
            ans += matrix[i][0];
        }
        for(int j=0;j<n;j++){
            ans += matrix[0][j];
        }

        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                if(matrix[i][j] != 0){
                    matrix[i][j] += Math.min(matrix[i-1][j-1], Math.min(matrix[i-1][j], matrix[i][j-1]));
                    ans += matrix[i][j];
                }
            }
        }
        return ans;
    }
}
