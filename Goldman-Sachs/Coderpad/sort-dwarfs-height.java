/**
Given height of dwarfs, we have to arrange dwarfs in strictly increasing order. Dwarf can be only choosen from either the front or the back. 
Return the length as well as the array. example: arr : {1,3,4,5,6,3,2} , output : {1,2,3,4,5,6}
**/
public class DwarfSequence {
    public static List<Integer> maxIncreasingSequence(int[] arr) {
        int left = 0, right = arr.length - 1;
        List<Integer> result = new ArrayList<>();
        int last = Integer.MIN_VALUE;

        while (left <= right) {
            boolean canTakeLeft = arr[left] > last;
            boolean canTakeRight = arr[right] > last;

            if (canTakeLeft && canTakeRight) {
                // Choose the smaller one to keep future options open
                if (arr[left] < arr[right]) {
                    result.add(arr[left]);
                    last = arr[left++];
                } else {
                    result.add(arr[right]);
                    last = arr[right--];
                }
            } else if (canTakeLeft) {
                result.add(arr[left]);
                last = arr[left++];
            } else if (canTakeRight) {
                result.add(arr[right]);
                last = arr[right--];
            } else {
                break; // No valid moves left
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 4, 5, 6, 3, 2};
        List<Integer> sequence = maxIncreasingSequence(arr);
        System.out.println("Length: " + sequence.size());
        System.out.println("Sequence: " + sequence);
    }
}
