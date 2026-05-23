class Solution {

    /**
    0
    01
    0110
    01101001
    0110100110010110
    1. kth index in nth row = 
        -> if k is even
           -> then value at k must be derived from digit at k/2 index in (n-1)th row
           -> if value in (n-1)th rowis 0, then value at kth index in nth row would be 1
           -> if value in (n-1)th row is 1, then value at kth index in nth row would be 0
        -> if k is odd
           -> then value at k must be derived from digit at (k-1/2)+1 index in (n-1)th row
           -> value in (n-1)th row = value at kth index in nth row

    2. base cases:
       -> kthGrammar(1, 1) = 1
       -> kthGrammar(2, 1) = 0 and kthGrammar(2, 2) = 0
     */

    3. Recursion
       -> Time Complexity: O(N)
          -> not exponential because each call to (n,k) makes exactly one recursive call to (n-1, prevK)
       -> Space Complexity: O(N)     
    public int kthGrammar(int n, int k) {
        if(n == 1 && k==1){
            return 0;
        }
        if(n == 2){
            if(k == 1){
                return 0;
            }
            return 1;
        }
        int digit = -1;
        if(k%2 == 0){
            digit = kthGrammar(n-1, (k/2));
            if(digit == 0){
                return 1;
            }
            return 0;
        }else {
            digit = kthGrammar(n-1, (k-1)/2 + 1);
            return digit;
        }

    }

}
