// Time Complexity: O(nlogn)
// Space Complexity: O(n)
class Solution {
    public int minSetSize(int[] arr) {
        Map<Integer, Integer> numToCount = new HashMap<>();
        for(int a: arr){ //O(n)
            numToCount.put(a, numToCount.getOrDefault(a, 0) + 1);
        }
        Map<Integer, Integer> sortedMap = numToCount.entrySet().stream().sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
        .collect(Collectors.toMap(
            (e) -> e.getKey(),
            (e) -> e.getValue(),
            (k1, k2) -> k1, // key collision
            () -> new LinkedHashMap<>()
        )); //O(nlogn)
        int resultSize = 0;
        int numElements = 0;
        for(int v: sortedMap.values()){ //O(n)
            if(numElements >= Math.ceil(arr.length/2)) {
                break;
            }
            resultSize += 1;
            numElements += v;
        }
        return resultSize;
    }
}
