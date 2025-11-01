// https://www.geeksforgeeks.org/problems/longest-bitonic-subsequence0824/1

class Solution {
    public static int LongestBitonicSequence(int n, int[] nums) {
        // code here
        int[] frontLis = new int[n];
        int[] backLis = new int[n];
        Arrays.fill(frontLis, 1);
        Arrays.fill(backLis, 1);
        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(nums[j] < nums[i]){
                    frontLis[i] = Math.max(frontLis[i], 1 + frontLis[j]);
                }
            }
        }
        
        int result = 0;
        
        for(int i=n-2;i>0;i--){
            for(int j=n-1;j>i;j--){
                if(nums[j] < nums[i]){
                    backLis[i] = Math.max(backLis[i], 1 + backLis[j]);
                }
            }
            if((frontLis[i] != 1) && (backLis[i] != 1)){
                result = Math.max(result, frontLis[i] + backLis[i] - 1);
            }
        }
        
        return result;
        
    }
}
