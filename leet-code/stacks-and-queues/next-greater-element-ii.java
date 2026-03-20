class Solution {
    /**
    example 1:
    1 2 1               1 2 1
        2               2 1 
      -1 2              2
    2 -1 2              1 2

    example 2:
    1 2 3 4 3           1 2 3 4 3
            4           3 4 3
          -1 4          4
        4 -1 4          3 4
      3 4 -1 4          2 3 4
    2 3 4 -1 4          1 2 3 4 

    in the stack only those elements are there which are a candidate to become answer for the current 
    element or elements to left of current element
     */
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for(int i=n-1;i>=0;i--){
            stack.push(nums[i]);
        }
        for(int i=n-1;i>=0;i--){
            int currNum = nums[i];
            if(stack.isEmpty()){
                ans[i] = -1;
                stack.push(currNum);
                continue;
            }
            while(!stack.isEmpty() && stack.peek() <= currNum){
                stack.pop();
            }
            if(stack.isEmpty()){
                ans[i] = -1;
                stack.push(currNum);
            }else{
                ans[i] = stack.peek();
                stack.push(currNum);
            }
        }
        return ans;
    }
}
