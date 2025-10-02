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

class Solution {
    public static double sqrtWithPrecision(double n) {
        double low = 0;
        double high = n;
        double mid;

        while (high - low > 0.01) {
            mid = low + (high - low) / 2;
            double square = mid * mid;

            if (square < n) {
                low = mid;
            } else {
                high = mid;
            }
        }

        // Use high and floor to ensure rounding down to 2 decimal places
        return Math.floor(high * 100.0) / 100.0;
    }

    public static void main(String[] args) {
        double n = 10;
        System.out.println("Square root of " + n + " is: " + sqrtWithPrecision(n));
    }
}
