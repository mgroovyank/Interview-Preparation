// https://leetcode.com/problems/course-schedule/

class Solution {
    /**
    1. If there is a cyclic prerequisite dependency, then you can't finish all the courses.
    2. DFS - Directed Graph to detect cycle
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<numCourses;i++){
            graph.add(new ArrayList<>());
        }
        for(int[] edge: prerequisites){
            int u = edge[1];
            int v = edge[0];
            graph.get(u).add(v);
        }

        int[] visited = new int[numCourses];
        int[] currentPathFlag = new int[numCourses];

        for(int i=0;i<numCourses;i++){
            if(visited[i] == 0){
                visited[i] = 1;
                currentPathFlag[i] = 1;
                if(dfsAndCheckCycle(graph, i, visited, currentPathFlag)){
                    return false;
                }
                currentPathFlag[i] = 0;
            }
        }
        return true;
    }

    private boolean dfsAndCheckCycle(List<List<Integer>> graph, int currNode, int[] visited, int[] currentPathFlag){
        List<Integer> neighbors = graph.get(currNode);
        for(int neighbor: neighbors){
            if(visited[neighbor] == 1 && currentPathFlag[neighbor]==1){
                return true; // cycle
            }
            if(visited[neighbor] == 0){
                visited[neighbor] = 1;
                currentPathFlag[neighbor] = 1;
                if(dfsAndCheckCycle(graph, neighbor, visited, currentPathFlag)){
                    return true;
                }
                currentPathFlag[neighbor] = 0;
            }
        }
        return false;
    }
}
