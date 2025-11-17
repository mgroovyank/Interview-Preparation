// https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/

// Time Complexity: O(nlogn)
class Solution {
    public int smallestDivisor(int[] nums, int threshold) {
        // less than or equal to - lower/upper bound - binary search
        // take values of  divisor on basis on binary search
        // if at mid value of divisor, sum is < threshold, then I can try reducing divisor
        // else sum > threshold, I will have to increase divisor
        // you have to find first index where sum <= threshold - lower bound
        int low = 0;
        int high = 1000000 - 1;
        int ans = 1000000;
        int divisionResult = 0;
        while(low <= high){
            int mid = low + (high-low)/2;
            divisionResult = 0;
            int divisor = mid + 1;
            for(int a: nums){
                divisionResult += (a/divisor) + (a%divisor == 0 ? 0 : 1); 
            }
            if(divisionResult > threshold){
                low = mid + 1;
            }else{
                ans = mid;
                high = mid - 1;
            }
        }
        return ans+1;
    }
}
