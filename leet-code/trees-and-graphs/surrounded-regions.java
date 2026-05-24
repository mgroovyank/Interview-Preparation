//https://leetcode.com/problems/surrounded-regions/

// Time Complexity: O(m) + O(n) + O(m*n + 2*m*n*4)
// Space Complexity: O(m*n) - worst case recursion stack
class Solution {
    private final char o = 'O';
    private final char x = 'X';
    /**
    Any O from which if you do a DFS/BFS can you can reach a O on the boundary that means, that region will never be
    sorrounded.
    But instead of looking at all Os for this check, just do the check from Os on the boundary.
    Any Os on the board which are not connected with any of the Os on the boundary can be captured.
    1. Start from Os present on the boundary
    2. Try to do a DFS/BFS from each of those Os, and since we visit each O only once, we need to keep track of visited
    3. For that use a visited array
    4. At the end whatever Os are unvisited, in the original board mark them as X
    5. BFS/DFS doesn't matter here, but I'll do with DFS, seems more simpler here.
    6. Don't think to use board itself instead of visited in this case.
     */
    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        int[][] visited = new int[m][n];
        // boundary Os: first row, first column, last row, last column
        for(int j=0;j<n;j++){
            // first row
            if(board[0][j] == o && visited[0][j]==0){
                visited[0][j] = 1;
                dfs(board, 0, j, visited);
            }
            // last row
            if(board[m-1][j] == o && visited[m-1][j]==0){
                visited[m-1][j] = 1;
                dfs(board, m-1, j, visited);
            }
        }

        for(int i=0;i<m;i++){
            // first column
            if(board[i][0] == o && visited[i][0]==0){
                visited[i][0] = 1;
                dfs(board, i, 0, visited);
            }
            //last column
            if(board[i][n-1] == o && visited[i][n-1]==0){
                visited[i][n-1] = 1;
                dfs(board, i, n-1, visited);
            }
        }

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(board[i][j]==o && visited[i][j] == 0){
                    board[i][j] = x;
                }
            }
        }
    }

    private void dfs(char[][] board, int i, int j, int[][] visited){
        int m = board.length;
        int n = board[0].length;
        int[][] nextIjs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        for(int[] nextIj : nextIjs){
            int nextI = i + nextIj[0];
            int nextJ = j + nextIj[1];
            if(nextI>=0 && nextI<m && nextJ>=0 && nextJ<n && board[nextI][nextJ]==o && visited[nextI][nextJ]==0){
                visited[nextI][nextJ] = 1;
                dfs(board, nextI, nextJ, visited);
            }
        }
    }
}
