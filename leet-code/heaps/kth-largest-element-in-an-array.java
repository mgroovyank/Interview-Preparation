// Time Limit Exceeded but correct
class Solution {
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        int start = 0;
        int end = n-1;
        return quickselect(nums, start, end, k);
    }

    int quickselect(int[] nums, int start, int end, int k){
        int pivot = partition(nums, start, end);
        if(pivot == k-1){
            return nums[pivot];
        }else if(k-1 < pivot){
            return quickselect(nums, start, pivot-1, k);
        }else{
            return quickselect(nums, pivot+1, end, k);
        }
    }

    int partition(int[] nums, int start, int end){
        int pivot = end;
        int lastSmallerElementIdx = start - 1;
        for(int j=start;j<end;j++){
            if(nums[j] <= nums[pivot]){
                continue;
            }
            lastSmallerElementIdx++;
            swap(nums, lastSmallerElementIdx, j);
        }
        lastSmallerElementIdx++;
        swap(nums, lastSmallerElementIdx, pivot);
        return lastSmallerElementIdx;
    }

    void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}


// Using mid as pivot, ACCEPTED
class Solution {
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        int start = 0;
        int end = n-1;
        return quickselect(nums, start, end, k);
    }

    int quickselect(int[] nums, int start, int end, int k){
        int pivot = partition(nums, start, end);
        if(pivot == k-1){
            return nums[pivot];
        }else if(k-1 < pivot){
            return quickselect(nums, start, pivot-1, k);
        }else{
            return quickselect(nums, pivot+1, end, k);
        }
    }

    int partition(int[] nums, int start, int end){
        int mid = start + (end-start)/2;
        int pivotValue = nums[mid];
        swap(nums, mid, end);
        int lastSmallerElementIdx = start - 1;
        for(int j=start;j<end;j++){
            if(nums[j] <= pivotValue){
                continue;
            }
            lastSmallerElementIdx++;
            swap(nums, lastSmallerElementIdx, j);
        }
        lastSmallerElementIdx++;
        swap(nums, lastSmallerElementIdx, end);
        return lastSmallerElementIdx;
    }

    void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
