// https://leetcode.com/problems/fruit-into-baskets/
// Time Complexity: O(N)
// This solution doesn't generalize well to more than 2 baskets and is slightly more complex
class Solution {
    public int totalFruit(int[] fruits) {
        int numFruits = fruits.length;
        int right = 0;
        int basket1FruitType = -1;
        int basket2FruitType = -1;
        int currentTotal = 0;
        int ans = 0;
        while(right<numFruits){
            int currFruitType = fruits[right];
            if(basket1FruitType == -1 || basket1FruitType == currFruitType){
                // put in basket 1
                basket1FruitType = currFruitType;
                currentTotal++;
                right++;
            }else if(basket2FruitType == -1 || basket2FruitType == currFruitType){
                basket2FruitType = currFruitType;
                currentTotal++;
                right++;
            }else{
                // window ends
                ans = Math.max(ans, currentTotal);
                right = right - 1;
                currentTotal = 0;
                while(right>0 && fruits[right] == fruits[right-1]){
                    right--;
                }
                basket1FruitType = fruits[right];
                basket2FruitType = -1;
            }
        }
        return Math.max(ans, currentTotal);
    }
}

// Time Complexity: O(N)
// Space Complexity: O(1)
class Solution {
    public int totalFruit(int[] fruits) {
        int numFruits = fruits.length;
        Map<Integer, Integer> bucketToFruitCountMap = new HashMap<>();
        int left = 0;
        int right = 0;
        int ans = 0;
        while(right<numFruits){
            int currFruitType = fruits[right];
            bucketToFruitCountMap.put(currFruitType, bucketToFruitCountMap.getOrDefault(currFruitType, 0) + 1);
            while(bucketToFruitCountMap.size() >= 3){
                bucketToFruitCountMap.put(fruits[left], bucketToFruitCountMap.get(fruits[left]) - 1);
                if(bucketToFruitCountMap.get(fruits[left]) == 0){
                    bucketToFruitCountMap.remove(fruits[left]);
                }
                left++;
            }
            ans = Math.max(ans, right-left+1);
            right++;
        }
        return ans;
    }
}
