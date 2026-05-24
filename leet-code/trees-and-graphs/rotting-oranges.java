// https://leetcode.com/problems/rotting-oranges/

// Time Complexity: O(V + 2*E), V=m*n, E=m*n*4
// Space Complexity: O(V) + O(m*n)
// 1. If I take all rotten oranges and put them in queue
// 2. Now process all of them, and insert their neighboring fresh oranges into queue
// 3. at the end of this one second has passed
// 4. finally after queue is empty, still if grid has any fresh oranges left return -1 else return time
class Solution {
    public int orangesRotting(int[][] grid) {
        // BFS way to rot will give minimum time to rot all
        // but I need the time when last orange is rotten
        // so one way to solve this problem can be to store time at which an orange got 
        // rotten as well in the queue
        // and while processing its neighbors, we take maximum time that we have to update our answer
        int m = grid.length;
        int n = grid[0].length;
        Queue<List<Integer>> q = new ArrayDeque<>();
        boolean nothingFreshToRot = true;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j] == 2){
                    q.add(List.of(i, j));
                }else if(grid[i][j] == 1){
                    nothingFreshToRot = false;
                }
            }
        }
        if(nothingFreshToRot){
            return 0;
        }
        // if everything is already rotten, then time=0 is the answer.
        // so start with time = -1
        // because if all are rotten except 1, then when you process the next level, that time
        // extra increment in time happens
        int time = -1;
        while(!q.isEmpty()){
            int currOranges = q.size();
            while(currOranges!=0){
                List<Integer> curr = q.poll();
                int[][] delta = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
                for(int[] d: delta){
                    int nextI = curr.get(0) + d[0];
                    int nextJ = curr.get(1) + d[1];
                    if(nextI>=0 && nextI<m && nextJ>=0 && nextJ<n && grid[nextI][nextJ]==1){
                        grid[nextI][nextJ] = 2;
                        q.add(List.of(nextI, nextJ));
                    }
                }
                currOranges--;
            }
            time++;
        }
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j] == 1){
                    return -1; //when  fresh orange is left
                }
            }
        }
        return time;
    }
}


//WRONG APPROACH
// WON'T WORK as all rotten oranges at beginning should be considered at level 0
class Solution {
    /**
    1. Impossible scenario - when there are no rotten oranges in beginning, or they are not able to reach
       all fresh orannges to rot them.
    2. BFS
      -> Start from the first unvisited rotten orange
      -> do a BFS from there and return number of levels traversed.
      -> Number of levels = number of minutes
      -> Find the next unvisited rotten orange and perform the same BFS
      -> Once there is no unvisited rotten orange, that means whatever can be rotten has been rotten.
      -> even now if there is a fresh orange left, then return -1;
     */
    public int orangesRotting(int[][] grid) {
        int m = grid.length;
        int n= grid[0].length;
        int[][] visited = new int[m][n];
        int time = 0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j] == 2 && visited[i][j]==0){
                    int currTime = bfs(grid, i, j, visited);
                    time = Math.max(time, currTime);
                }
            }
        }
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if((visited[i][j] == 0) && (grid[i][j] == 1)){
                    return -1;
                }
            }
        }
        return time;
    }

    private int bfs(int[][] grid, int si, int sj, int[][] visited){
        int levels = 0;
        Queue<List<Integer>> q = new ArrayDeque<>();
        visited[si][sj] = 1;
        q.add(List.of(si, sj));
        while(!q.isEmpty()){
            List<Integer> l = q.remove();
            int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
            for(int i=0;i<4;i++){
                int nextI = l.get(0) + next[i][0];
                int nextJ = l.get(1) + next[i][1];
                if(nextI>=0 && nextI<grid.length && nextJ>=0 && nextJ<grid[0].length && (visited[nextI][nextJ]==0) && (grid[nextI][nextJ]==1)){
                    q.add(List.of(nextI, nextJ));
                    visited[nextI][nextJ] = 1;
                }
            }
            levels++;
        }
        return levels;
    }
}

// DFS Based Approach
// So basically if I'm using DFS, for each rotten orange, I can keep the minimum time it takes for it to rot.
// As it might rot via different DFS paths. And then finally choose the maximum time among all the rotten oranges time as my answer.
