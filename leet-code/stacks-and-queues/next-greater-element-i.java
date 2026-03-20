// https://leetcode.com/problems/next-greater-element-i/

class Solution {
    /**
    1. Start from end of nums2. For last element, nothing is greater, so ans=-1
    2. Put the current element in a stack and move left in nums2
    3. If current element<top element in stack => ans for currentElement is topElement
    4. If current element>=top element in stack => top element is not the answer
        - the top element in the stack could've have been an answer for elements to left of current element
          but now since we have seen the current element, the current element will become
          the answer for them. => there is no use of top element in stack now, remove that.
        - after removing top element, again check the new top element similarly
        - if new top element is bigger => we got answer
          if not, then again remove top element, so keep doing this till either stack is empty
          or you find a greater top element
        - if stack becomes empty => answer=-1 for current element
    5. so this way this find next greater element for each element in nums2
    6. maintain this info in a map   
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int n2 = nums2.length;
        Deque<Integer> stack = new ArrayDeque<>();
        Map<Integer, Integer> nextGreater = new HashMap<>();
        for(int i=n2-1;i>=0;i--){
            int currNum = nums2[i];
            if(stack.isEmpty()){
                nextGreater.put(currNum, -1);
                stack.push(currNum);
                continue;
            }
            while(!stack.isEmpty() && stack.peek() <= currNum){
                stack.pop();
            }
            if(stack.isEmpty()){
                nextGreater.put(currNum, -1);
                stack.push(currNum);
            }else{
                nextGreater.put(currNum, stack.peek());
                stack.push(currNum);
            }
        }
        int n1 = nums1.length;
        for(int i=0;i<n1;i++){
            nums1[i] = nextGreater.get(nums1[i]);
        }
        return nums1;
    }
}
