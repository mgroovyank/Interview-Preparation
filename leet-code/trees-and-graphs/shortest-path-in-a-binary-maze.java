// https://www.geeksforgeeks.org/problems/shortest-path-in-a-binary-maze-1655453161/1

// Time Complexity: O(ElogV) where V=n*m(whenever all cells==1), E=V*4=n*m*4(every node has 4 edges to other nodes)
// Space Complexity: O(n*m)

// without priority queue, time complecity  = O(V+E) = O(n*m)

// Priority Queue is not required as such because when you move from one level to another, all the nodes at the next level are at same distance from current level
class Solution {
    public int shortestPath(int[][] mat, int[] src, int[] dest) {
        if(mat[src[0]][src[1]] == 0){
            return -1;
        }
        
        int n = mat.length;
        int m = mat[0].length;
        int[][] dist = new int[n][m];
        for(int[] temp: dist){
            Arrays.fill(temp, Integer.MAX_VALUE);
        }
        
        
        PriorityQueue<List<Integer>> pq = new PriorityQueue<>((l1, l2) -> Integer.compare(l1.get(2), l2.get(2)));
        pq.add(List.of(src[0], src[1], 0));
        dist[src[0]][src[1]] = 0;
        
        while(!pq.isEmpty()){
            List<Integer> curr = pq.poll();
            int currI = curr.get(0);
            int currJ = curr.get(1);
            int currDist = curr.get(2);
            
            int[][] delta = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
            for(int[] del: delta){
                int nextI = currI + del[0];
                int nextJ = currJ + del[1];
                if(nextI>=0 && nextI<mat.length && nextJ>=0 && nextJ<mat[0].length && mat[nextI][nextJ]==1){
                    int nextDist = currDist + 1;
                    if(nextDist < dist[nextI][nextJ]){
                        dist[nextI][nextJ] = nextDist;
                        pq.add(List.of(nextI, nextJ, nextDist));
                    }
                }
            }
        }
        
        return dist[dest[0]][dest[1]] == Integer.MAX_VALUE ? -1 : dist[dest[0]][dest[1]];
        
    }
    
}
