// https://www.geeksforgeeks.org/problems/implement-lower-bound/1
class Solution {
    int lowerBound(int[] arr, int target) {
        // lowerBound - smalles number greater than or equal to target
        int arrSize = arr.length;
        int ans = arrSize;
        int low = 0;
        int high = arrSize - 1;
        while(low<=high){
            int mid = low + (high - low)/2;
            if(arr[mid] >= target){
                ans = mid;
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return ans;
    }
}



// https://www.geeksforgeeks.org/problems/implement-upper-bound/1

class Solution {
    int upperBound(int[] arr, int target) {
        // upperBound - first/smallest element greater than target
        int arrSize = arr.length;
        int ans = arrSize;
        int low = 0;
        int high = arrSize-1;
        while(low<=high){
            int mid = low + (high-low)/2;
            if(arr[mid] > target){
                ans = mid;
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return ans;
    }
}
