// https://leetcode.com/problems/find-the-winner-of-the-circular-game/
// Bottom Up DP

class Solution {
    public int findTheWinner(int n, int k) {
        int winner = 0;
        for(int numOfPlayers=2;numOfPlayers<=n;numOfPlayers++){
            winner = (winner + k)%(numOfPlayers);
        }
        return winner+1;
    }
}

class Solution {
    public int findTheWinner(int n, int k) {
        // returns index of winner in original array
        return recursion(n, k) + 1;
    }

    private int recursion(int n, int k){
        if(n == 1){
            // winner index in last remanining array of size n=1
            return 0;
        }
        return (recursion(n-1, k) +k)%n;
    }
}
