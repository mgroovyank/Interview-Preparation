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

// Using Priority Queue
class Solution {
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>(k);
        for(int i=0;i<n;i++){
            if(pq.size() == k){
                if(pq.peek() <= nums[i]){
                    pq.poll();
                    pq.add(nums[i]);
                }
            }else{
                pq.add(nums[i]);
            }   
        }
        return pq.peek();
    }
}
