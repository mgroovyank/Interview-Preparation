// Time Complexity: O(KN^2)
// Space Complexity: O(1)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        int n = lists.length;
        ListNode res = null;
        for(int i=0;i<n;i++){
            res = merge(res, lists[i]);
        }
        return res;
    }

    private ListNode merge(ListNode res, ListNode currList){
        ListNode resPtr =  null;
        ListNode leftPtr = res;
        ListNode rightPtr = currList;
        while(leftPtr!=null && rightPtr!=null){
            if(leftPtr.val < rightPtr.val){
                if(resPtr == null){
                    resPtr = leftPtr;
                    res = resPtr;
                    leftPtr = leftPtr.next;
                    continue;
                }
                resPtr.next = leftPtr;
                resPtr = resPtr.next;
                leftPtr = leftPtr.next;
            }else{
                if(resPtr == null){
                    resPtr = rightPtr;
                    res = resPtr;
                    rightPtr = rightPtr.next;
                    continue;
                }
                resPtr.next = rightPtr;
                resPtr = resPtr.next;
                rightPtr = rightPtr.next;
            }
        }
        while(leftPtr != null){
            if(resPtr == null){
                resPtr = leftPtr;
                res = resPtr;
                leftPtr = leftPtr.next;
                continue;
            }
            resPtr.next = leftPtr;
            resPtr = resPtr.next;
            leftPtr = leftPtr.next;
        }
        while(rightPtr != null){
            if(resPtr == null){
                resPtr = rightPtr;
                res = resPtr;
                rightPtr = rightPtr.next;
                continue;
            }
            resPtr.next = rightPtr;
            resPtr = resPtr.next;
            rightPtr = rightPtr.next;
        }
        return res;
    }
}


// Time Complexity: O(KN)
// Space Complexity: O(N)

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        int k = lists.length;
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val-b.val);
        for(int i=0;i<k;i++){
            if(lists[i] != null){
                pq.add(lists[i]);
            }
        }
        ListNode res = null;
        ListNode resItr = null;
        while(!pq.isEmpty()){
            ListNode temp = pq.poll();
            if(temp.next != null){
                pq.add(temp.next);
            }
            if(res == null){
                res = temp;
                resItr = res;
                continue;
            }
            resItr.next = temp;
            resItr = resItr.next;
        }
        return res;

    }

}

