// https://leetcode.com/problems/lru-cache/

class LRUCache {
    // 1. I need a map to store key->Node Mapping
    // 2. Node - int key,val,next,prev
    // 3. Nodes will be part of doubly linked list

    private int capacity;
    private HashMap<Integer, Node> keyNodeMap; // concurrent hash map
    Node head;
    Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.keyNodeMap = new HashMap<>();
        this.head = new Node(-1, 0);
        this.tail = new Node(-1, 0);
        this.head.prev = this.tail;
        this.tail.next = this.head;
    }
    
    public int get(int key) {
        if(keyNodeMap.get(key) == null){
            return -1;
        }
        Node currNode = keyNodeMap.get(key);
        // curr Node should move after tail
        Node prevNode = currNode.prev;
        Node nextNode = currNode.next;
        currNode.prev = null;
        currNode.next = null;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        Node temp = this.tail.next;
        this.tail.next = currNode;
        currNode.next = temp;
        temp.prev = currNode;
        currNode.prev = this.tail;
        return currNode.value;
    }
    
    public void put(int key, int value) {
        // check if key already exists
        // if yes, then find the node of that key and move that node to tail
        // if no, then create a node and move that to tail
        // also update the keyNodeMap with new key and node mapping
        if(keyNodeMap.get(key) == null){
            Node newNode = new Node(key, value);
            insert(newNode);
            keyNodeMap.put(key, newNode);
            if(keyNodeMap.size() > this.capacity){
                keyNodeMap.remove(this.head.prev.key);
                Node headPrev = this.head.prev;
                Node headPrevPrev = headPrev.prev;
                headPrev.next = null;
                headPrev.prev = null;
                this.head.prev = headPrevPrev;
                headPrevPrev.next = this.head;
            }
        }else{
            Node existingNode = keyNodeMap.get(key);
            Node existingNodePrev = existingNode.prev;
            Node existingNodeNext = existingNode.next;
            existingNode.next = null;
            existingNode.prev = null;
            existingNodeNext.prev = existingNodePrev;
            existingNodePrev.next = existingNodeNext;
            insert(existingNode);
            existingNode.value = value;
        }

    }

    void insert(Node n){
        Node tailNext = this.tail.next;
        this.tail.next = n;
        n.next = tailNext;
        tailNext.prev = n;
        n.prev = this.tail;
    }
}

class Node{
    int key;
    int value;
    Node prev;
    Node next;

    public Node(int key, int value){
        super();
        this.key = key;
        this.value = value;
        this.prev = null;
        this.next = null;
    }

}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
