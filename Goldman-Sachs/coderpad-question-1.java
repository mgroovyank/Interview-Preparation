// https://leetcode.com/problems/open-the-lock/

// Truck - 4tyres
// Each tyre has 10 spikes
// start position of tyre - [0 , 1, 2, 2] - present at spike
// end positon of tyre - [4, 5, 6, 7] - reach this spike
// each tyre can either move forward/backward
// one second you can move only one tyre
// Blocked states - [[1,2,3,4], [5,6,7,8], [2,4,5,6]]
// Min time for tyres to reach end position??

// int wheelValue = (int) current.charAt(i); - '5' - this gets converted to ASCII value = 53
// int wheelValue = (int) current.charAt(i) - '0'; now that character becomes int
// '5' - '0' = 53 - 48 = 5
// int wheelValue = Integer.valueOf(current.charAt(i)); - this also gives ASCII value = 53

// N - no of wheels
// D - number of digits
// T - number of deadlocks - to create hashset
// Time Complexity: O(D^N * N^2 + T)
// Space Complexity: O(D^N)

class Solution {
    public int openLock(String[] deadends, String target) {
        // 10_000 - total number of possible lock combinations
        // each lock combination is a node in a graph
        // initially you are at one node, from there you can go 
        // to 4(pick a wheel) * 2(forward/backward) = 8 other nodes 
        // out of these 8 options, just don't go to deadends or mark them as viisited
        // since we need min number of steps, use BFS
        // to maintain visited - use a Set of string
        String start = "0000";
        Queue<String> q = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        for(int i=0;i<deadends.length;i++){
            visited.add(deadends[i]);
        }
        if(visited.contains(start)){
            return -1;
        }
        visited.add(start);
        int steps = -1;
        q.add(start);
        while(!q.isEmpty()){
            int n = q.size();
            steps++;
            for(int x=0;x<n;x++){
                String current = q.poll();
                if(current.equals(target)){
                    // reached target;
                    return steps;
                }
                for(int i=0;i<4;i++){
                    // int wheelValue = (int) current.charAt(i);
                    // int wheelValue = Integer.valueOf(current.charAt(i));
                    int wheelValue = current.charAt(i) -'0';
                    StringBuilder sb1 = new StringBuilder(current);
                    sb1.setCharAt(i, (char)('0' + (wheelValue + 1) % 10));
                    String option1 = sb1.toString();

                    StringBuilder sb2 = new StringBuilder(current);
                    sb2.setCharAt(i, (char)('0' + (10 + (wheelValue - 1) % 10) % 10));
                    String option2 = sb2.toString();

                    if(!visited.contains(option1)){
                        q.add(option1);
                        visited.add(option1);
                    }
                    if(!visited.contains(option2)){
                        q.add(option2);
                        visited.add(option2);
                    }
                }
            }
        }
        return -1;
    }
}

