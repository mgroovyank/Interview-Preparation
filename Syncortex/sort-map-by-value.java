import java.util.*;

public class Main {
    public static void main(String[] args) {
      Map<String, Integer> scores = new HashMap<>();
      scores.put("David", 95);
      scores.put("Jane", 80);
      scores.put("Mary", 97);
      scores.put("Lisa", 78);
      scores.put("Dino", 65);
      scores = sortByValue(scores);
      System.out.println(scores);
  }
  
  static Map<String, Integer> sortByValue(Map<String, Integer> scores){
    // 1. get the keys in a list
    // 2. sort the keys using a custom comparator base on value from the scores
    // 3. list of keys sorted by value
    // 4. insert values for each key in a LinkedHashMap
    
    Set<String> temp = scores.keySet();
    List<String> keys = new ArrayList<>();
    for(String t: temp){
      keys.add(t);
    }
    Collections.sort(keys, (a, b) -> scores.get(a) - scores.get(b));
    // linked hash map maintains insertion order
    LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
    for(String key: keys){
      sortedMap.put(key, scores.get(key));
    }
    return sortedMap;
  }
  
}
