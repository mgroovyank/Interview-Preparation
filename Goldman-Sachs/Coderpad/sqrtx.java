// https://leetcode.com/problems/sqrtx/

class Solution {
    public int mySqrt(int x) {
        int low = 0;
        int high = x;
        while(high-low>1){
            int mid = low + (high-low)/2;
            long l = ((long)mid)*mid;
            if(l == x){
                return mid;
            }
            if(l < x){
                low = mid;
            }else{
                high = mid;
            }
        }
        return x==1 ? 1 : low; 
    }
}
