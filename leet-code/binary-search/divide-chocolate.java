// https://leetcode.com/problems/divide-chocolate
// https://www.geeksforgeeks.org/dsa/divide-chocolates/
// https://www.youtube.com/watch?v=Ppy9lvyMnuc

class Solution {
  public int maximizeSweetness(int[] sweetness, int K){
    int n = sweetness.length;
    int low = 1;
    int high = Arrays.stream(sweetness).sum()/(K+1); // k - friends + 1 me
    // the minimum chunk sweetness can be 1 to all chunks being of equal sweetness
    int ans = high;
    while(low <= high){
      int mid = low + (high - low)/2;
      // calculate number of chunks with 'mid' being piece with minimum sweetness
      int chunks = calculateChunks(sweetness, mid);
      if(chunks >= K+1){
        ans = mid;
        low = mid + 1;
      }else{
        high = mid - 1;
      }
    }
    return ans;
  }

  private int calculateChunks(int[] sweetness, int limit){
    int chunks = 0;
    int sum = 0;
    for(int i=0;i<sweetness.length;i++){
      sum += sweetness[i];
      if(sum >= limit){
        chunks++;
        sum = 0;
      }
    } 
    return chunks;
  }
}
