// https://leetcode.com/problems/kth-largest-element-in-a-stream/
class KthLargest {

    int k;
    int[] nums;
    PriorityQueue<Integer> pq;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.nums = nums;
        this.pq = new PriorityQueue<>();
        for(int i=0;i<nums.length;i++){
            if(pq.size()==k){
                if(pq.peek() < nums[i]){
                    pq.poll();
                    pq.add(nums[i]);
                }
            }else{
                pq.add(nums[i]);
            }
        }
    }
    
    public int add(int val) {
        if(pq.size() == k){
            if(pq.peek() < val){
                pq.poll();
                pq.add(val);
            }
        }else{
            pq.add(val);
        }
        return pq.peek();
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */
