// https://leetcode.com/problems/maximum-units-on-a-truck/

// Time Complexity: O(n) + O(nlogn)
class Solution {
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        
        // this doesn't work
        // Arrays.sort(boxTypes, new Comparator<Integer[]>(){
        //     public int compare(Integer[] a, Integer[] b){
        //         return Integer.compare(b[1], a[1]);
        //     }
        // });

        //but lambda works
        Arrays.sort(boxTypes, (a, b) -> Integer.compare(b[1], a[1]));

        int n = boxTypes.length;
        int ans = 0;
        for(int i=0;i<n;i++){
            int numberOfBoxes = boxTypes[i][0];
            if(numberOfBoxes > truckSize){
                numberOfBoxes = truckSize;
            }
            ans += numberOfBoxes * boxTypes[i][1];
            truckSize -= numberOfBoxes;
        }
        return ans;
    }
}
