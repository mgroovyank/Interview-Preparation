// https://leetcode.com/problems/remove-stones-to-minimize-the-total/

class Solution {
    public int minStoneSum(int[] piles, int k) {
        // so when compare(a, b) returns > 0 , then a should come after b
        PriorityQueue<Integer> pq =  new PriorityQueue<>((a, b) -> b-a);
        for(int i=0;i<piles.length;i++){
            pq.add(piles[i]);
        }
        while(k != 0){
            int maxPile = pq.poll();
            int remove = (int)Math.floor(maxPile / 2.0);
            pq.add(maxPile - remove);
            k--;
        }
        int minStones = 0;
        while(!pq.isEmpty()){
            minStones += pq.poll();
        }
        return minStones;
    }
}
