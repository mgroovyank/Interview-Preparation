// https://leetcode.com/problems/flood-fill/

// Time complexity: O(V + 2*E), V=m*n , E=m*n*4 / each node has 4 edges
// Space Complexity: O(V)
class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        // I can perform BFS
        // I don't need visited as well, once I change the visited cell's color to new color
        // I'll anyways not consider it as a neighbor
        int origColor = image[sr][sc];
        if(color == origColor){
            return image;
        }
        image[sr][sc] = color;
        Queue<List<Integer>> q = new ArrayDeque<>();
        q.add(List.of(sr, sc));
        while(!q.isEmpty()){
            List<Integer> curr = q.poll();
            // loop over neighbors
            int[][] nijs= {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for(int[] nij: nijs){
                int ni = curr.get(0) + nij[0];
                int nj = curr.get(1) + nij[1];
                if(ni>=0 && ni<image.length && nj>=0 && nj<image[0].length && image[ni][nj] == origColor){
                    q.add(List.of(ni, nj));
                    image[ni][nj] = color;
                }
            }
        }
        return image;
    }
}
