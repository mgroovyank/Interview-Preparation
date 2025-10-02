// Design a Data Structure to update, return and change all the values. All the method should be in O(1) time complexity.

class FastMap {
    private Map<Integer, Integer> map = new HashMap<>();
    private int globalOffset = 0;

    public void set(int key, int value) {
        map.put(key, value - globalOffset);
    }

    public int get(int key) {
        return map.getOrDefault(key, 0) + globalOffset;
    }

    public void updateAll(int delta) {
        globalOffset += delta;
    }
}
