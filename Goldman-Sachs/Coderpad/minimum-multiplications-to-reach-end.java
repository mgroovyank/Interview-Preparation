// https://www.geeksforgeeks.org/problems/minimum-multiplications-to-reach-end/1
// Time Complexity: O(N*100_000)

// User function Template for Java

class Solution {
    int minimumMultiplications(int[] arr, int start, int end) {

        // Your code here
        // no DFS
        // BFS - good here
        int MOD = 100000;
        Deque<Integer> q = new ArrayDeque<>();
        q.add(start);
        int[] visited = new int[MOD];
        Arrays.fill(visited, 0);
        visited[start] = 1;
        int steps = 0;
        while(!q.isEmpty()){
            int n = q.size();
            while(n != 0){
                int curr = q.poll();
                if(curr == end){
                    return steps;
                }
                for(int num: arr){
                    int nextStart = (curr%MOD * num%MOD)%MOD;
                    if(visited[nextStart] == 0){
                        q.add(nextStart);
                        visited[nextStart] = 1;
                    }
                }
                n--;
            }
            steps++;
        }
        return -1;
    }
}
