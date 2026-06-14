// https://www.geeksforgeeks.org/problems/minimum-multiplications-to-reach-end/1

// Question doesn't need djikstra algorithm, simple BFS is good enough
class Solution {
    public int minSteps(int[] arr, int start, int end) {
        int[] visited = new int[1000];
        Deque<Integer> q = new ArrayDeque<>();
        q.add(start);
        int level = 0;
        
        while(!q.isEmpty()){
            int n = q.size();
            while(n!=0){
                int curr = q.remove();
                n--;
                if(curr == end){
                    return level;
                }
                for(int num: arr){
                    int nextNum = (curr * num) % 1000;
                    if(visited[nextNum] == 0){
                        visited[nextNum] = 1;
                        q.add(nextNum);
                    }
                }
            }
            level++;
        }
        
        return -1;
    }
}
