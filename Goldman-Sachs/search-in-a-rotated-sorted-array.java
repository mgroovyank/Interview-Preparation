/**
1- I keep left as 0 and right as length-1
2. I find mid
3. mid can fall in left sorted part or mid can fall in right sorted part.
4. if mid falls in left sorted part, then nums[left]<=nums[mid]
5. and also nums[right]<nums[left] due to rotation
6. if mid falls in right part, then nums[right] >= nums[mid]
7. at one time mid can fall only in either left part or right part.
8. if the condition in point 4 is correct, then nums[right] can not be greater than nums[mid] as seen in point 6. so only once condition can be true at a time.
9. When I run binary search on left part, I updated my end=mid-1
10. when I run binary search on right part, I updated my start=mid+1
11. so after first mid, binary search will always be as normal binary search. I don't have to worry about rotation after that.
12. I actually have to worry about rotation afterwards as well. read next point.
13. in my algorithm, I didn't say the important point that whenever mid falls in left sorted or right sorted, after that I've to see if target is less than or greater than mid. In case mid falls in left part: if target is less than mid, then I go to left part, but target should also be greater than left element. otherwise I choose right part. And when I choose right part, I'm again dealing with same array which is composed of left sorted and right sorted parts. So the problem is again breaking down into subproblem of same type.
**/
// Time Complexity: O(logN)
// Space Complexity: O(1) and O(log N) for recursion stack
class Solution {
    public int search(int[] nums, int target) {
        return binarySearch(nums, 0, nums.length - 1, target);
    }
    private int binarySearch(int[] nums, int left, int right, int target){
        if(right < left){
            return -1;
        }
        int mid = left + (right-left)/2;
        if(nums[mid] == target){
            return mid;
        }
        // mid falling in left sorted part
        if(nums[left] <= nums[mid]){
            // if target is lower than nums[mid], go on left, else on right
            if(target < nums[mid] && target>= nums[left]){
                return binarySearch(nums, left, mid-1, target);
            }
            return binarySearch(nums, mid+1, right, target);
        }else{
            if(target > nums[mid] && target<=nums[right]){
                return binarySearch(nums, mid+1, right, target);
            }
            return binarySearch(nums, left, mid-1, target);
        }
    }
}


// Iterative Method - reduced space complexity to O(1)
class Solution {
    public int search(int[] nums, int target) {
        return binarySearch(nums, 0, nums.length - 1, target);
    }
    private int binarySearch(int[] nums, int left, int right, int target){
        while(left<=right){
            int mid = left + (right-left)/2;
            if(nums[mid] == target){
                return mid;
            }
            // mid falling in left sorted part
            if(nums[left] <= nums[mid]){
                // if target is lower than nums[mid], go on left, else on right
                if(target < nums[mid] && target>= nums[left]){
                    right = mid - 1;
                }else {
                    left = mid + 1;
                }
            }else{
                if(target > nums[mid] && target<=nums[right]){
                    left = mid + 1;
                }else{
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}
