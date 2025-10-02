// https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/

1. First of all I relate this with a real life scenario. If I can see all cards, I should not be a greedy person and just choose start/end card which is bigger. Since I can see all cards, I should look at inner cards as well and make a strategy and choose cards wisely.
2. So I see my final score would be sum of points from starting cards and points from ending cards. so basically sum up to a certain prefix of array and certain suffix of array.
3. So I can choose the length of prefix as K and suffix as 0 and then get the points.
4. In next I can reduce number of cards from prefix and increase from suffix.
5. Finally return  maximum points which I got for a combination of prefix cards and suffix cards

// Time Complexity: O(k)
// Space Complexity: O(1)
class Solution {
    public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int prefixSum = 0;
        int suffixSum = 0;
        int i = k-1;
        while(i>=0){
            prefixSum += cardPoints[i];
            i--;
        }
        i = k-1;
        int maxScore = prefixSum;
        while(i>-1){
            prefixSum -= cardPoints[i];
            suffixSum += cardPoints[(n-1)-(k-1-i)];
            maxScore = Math.max(maxScore, prefixSum + suffixSum);
            i--;
        }
        return maxScore;
    }
}

