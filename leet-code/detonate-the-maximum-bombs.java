// https://leetcode.com/problems/detonate-the-maximum-bombs/
class Solution {
    public int maximumDetonation(int[][] bombs) {
        Map<Integer, List<Integer>> bombNeighbors = new HashMap<>();
        int numberOfBombs = bombs.length;
        for(int currBombIdx=0;currBombIdx<numberOfBombs;currBombIdx++){
            List<Integer> nextBombs = new ArrayList<>();
            for(int otherBombIdx=0;otherBombIdx<numberOfBombs;otherBombIdx++){
                if(otherBombIdx == currBombIdx){
                    continue;
                }
                double distanceBetweenBombs = 
                Math.sqrt(
                    Math.pow(bombs[currBombIdx][0] - bombs[otherBombIdx][0], 2) + 
                    Math.pow(bombs[currBombIdx][1] - bombs[otherBombIdx][1], 2)
                );
                if(distanceBetweenBombs <= bombs[currBombIdx][2]){
                    nextBombs.add(otherBombIdx);
                }
            }
            bombNeighbors.put(currBombIdx, nextBombs);
        }
        int res = 0;
        for(int currBombIdx=0;currBombIdx<numberOfBombs;currBombIdx++){
            Queue<Integer> q = new ArrayDeque<>();
            q.offer(currBombIdx);
            Set<Integer> detonated = new HashSet<>();
            detonated.add(currBombIdx);
            while(!q.isEmpty()){
                int n = q.size();
                for(int i=0;i<n;i++){
                    int b = q.poll();
                    for(int k: bombNeighbors.get(b)){
                        if(detonated.add(k)){
                            q.offer(k);
                        }
                    }
                }
            }
            res = Math.max(res, detonated.size());
        }
        return res;
    }
}
