/*
You’re given a 2D array of [name, marks]. You need to:
Group marks by name.
Compute average per name.
Return the maximum average among all names.
*/

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GoldmanSachs {
    static void main() {
        String[][] data = new String[][]{{"Alice","80"},{"Bob","10"},{"Alice","100"}};
        // groupingBy(Function classifer, Collector downstream)
        // classifier - a function mapping input elements to keys
        // downstream - a collector implementing downstream reduction, by default it just puts elements of a group into a list
        Map<String, List<Integer>> groupByName = Arrays.stream(data)
                .collect(Collectors.groupingBy(
                        d -> d[0],
                        Collectors.mapping(d -> Integer.parseInt(d[1]), Collectors.toList())
                ));
        System.out.println(groupByName);

        // averaging double -> applies a function to input elements and does average of output
        // since the marks are whole numbers, Integer.parseInt is slightly more efficient.
        // averagingDouble will automatically convert the int to double internally
        Map<String, Double> averageMarks = Arrays.stream(data).collect(
                Collectors.groupingBy(d -> d[0],
                        Collectors.averagingDouble(
                                d -> Integer.parseInt(d[1])
                        )
                ));
        System.out.println(averageMarks);

        Double maxAverage = Arrays.stream(data).collect(
                Collectors.groupingBy(d -> d[0],
                        Collectors.averagingDouble(
                                d -> Integer.parseInt(d[1])
                        )
                )).values().stream().max(Comparator.naturalOrder()).orElse(0.0);
        System.out.println(maxAverage);
    }
}
