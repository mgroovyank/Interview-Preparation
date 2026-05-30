// https://leetcode.com/problems/course-schedule-ii/

class Solution {
    /**
    1. If there is a cycle, then you can't do complete courses. In case graph is acyclic, then courses can be completed.
    2. So for each prerequisite, if we can ensure there is an order of doing courses where each condition is satisifed
       we have an answer.
    3. => Topological Sort - DAG - BFS - Kahn's Algorithm   
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<numCourses;i++){
            graph.add(new ArrayList<>());
        }

        // To maintain all the courses before which no other course needs to be done
        int[] indegree = new int[numCourses];

        for(int[] edge: prerequisites){
            int u = edge[1];
            int v = edge[0];
            graph.get(u).add(v);
            indegree[v]++;
        }

        int[] result = new int[numCourses];
        int counter = 0;

        // BFS
        Deque<Integer> q = new ArrayDeque<>();
        for(int i=0;i<numCourses;i++){
            if(indegree[i] == 0){
                q.add(i);
            }
        }

        while(!q.isEmpty()){
            int currNode = q.remove();
            result[counter++] = currNode; // course done
            List<Integer> neighbors = graph.get(currNode);
            for(int neighbor: neighbors){
                indegree[neighbor]--;
                if(indegree[neighbor] == 0){
                    q.add(neighbor);
                }
            }
        }


        return counter == numCourses ? result: new int[0];
    }
}
