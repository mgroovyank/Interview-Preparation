// https://www.geeksforgeeks.org/problems/distance-of-nearest-cell-having-1-1587115620/1

class Solution {
    public ArrayList<ArrayList<Integer>> nearest(int[][] grid) {
        // 1. Never move diagonally from a node as the distance will be 2 and not 1
        // so diagonal nodes are not at level 1 but at level 2, so you can only move
        // in 4 directions
        // 2. all the intial zero cells are at 0 level of BFS
        // 3. I process all of them and determine nearest 1 distance for 
        // each of their neighbors
        // 4. if I'm using BFS, it will never happen that an already visited node,
        // will get a lower distance once it has been assigned a distance once
        int m = grid.length;
        int n = grid[0].length;
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for(int i=0;i<m;i++){
            result.add(new ArrayList<>());
        }
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                result.get(i).add(-1);
            }
        }
        Queue<List<Integer>> q = new ArrayDeque<>();
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j] == 1){
                    result.get(i).set(j, 0);
                    q.add(List.of(i, j, 0));
                }
            }
        }
        while(!q.isEmpty()){
            List<Integer> curr = q.poll();
            int i = curr.get(0);
            int j = curr.get(1);
            int currDistance = curr.get(2);
            int[][] ijs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for(int[] ij : ijs){
                int nextI = i + ij[0];
                int nextJ = j + ij[1];
                if(nextI>=0 && nextI<m && nextJ>=0 && nextJ<n && result.get(nextI).get(nextJ) == -1){
                    result.get(nextI).set(nextJ, currDistance + 1);
                    q.add(List.of(nextI, nextJ, currDistance + 1));
                }
            }
        }
        return result;
    }
}
