import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String ... args) {
        int[] arr = {1};

        System.out.println(combinationSum(arr, 1));
    }


    private static List<List<Integer>> combinationSum(int[] candidates, int target) {
        // convert to array with count of elements
        // like 1-9, 2-4, 6-1, 3-3, 4-2
        // for {1, 2, 6, 3, 4, 17}
        int size = candidates.length;
        int[][] tmpCandidates = new int[size][2];
        int totalCount = 0;
        for (int i = 0; i < size; i++) {
            int currentSum = 0;
            int candidate = candidates[i];
            tmpCandidates[i][0] = candidate;
            while (currentSum + candidate <= target) {
                currentSum = currentSum + candidate;
                tmpCandidates[i][1] = tmpCandidates[i][1] + 1;
                totalCount = totalCount + 1;
            }
        }

        // convert to normal array
        // delete bigger then target
        // and sort
        // like 1,1,1,1,1,1,1,1,2,2,2,2,3,3,3,4,4,6
        int[] tmp = new int[totalCount];
        int count = 0;
        for (int i = 0; i < tmpCandidates.length; i++) {
            int number = tmpCandidates[i][0];
            int currentCount = tmpCandidates[i][1];
            int start = count;
            int end = count + currentCount;
            for (int j = start; j < end; j++) {
                count++;
                tmp[j] = number;
            }
        }
        Arrays.sort(tmp);

        if (tmp.length == 0) {
            return Collections.emptyList();
        }

        // getting result
        List<List<Integer>> result = new ArrayList<>();
        List<List<Integer>> tmpResult = new ArrayList<>();
        List<List<Integer>> listOfSets = new ArrayList<>();
        int currentSum = 0;
        int currentElement;
        int prevElement = tmp[0];
        int currentListIndex = 0;


        if (tmp[0] == target) {
            List<Integer> startList = new ArrayList<>(1);
            startList.add(tmp[0]);
            result.add(startList);
        } else {
            List<Integer> startList = new ArrayList<>(1);
            startList.add(tmp[0]);
            tmpResult.add(startList);
        }


        for (int i = 1; i < tmp.length; i++) {
            currentElement = tmp[i];
            List<Integer> currentList = new ArrayList<>(1);
            currentList.add(currentElement);

            if (currentElement == target) {
                result.add(currentList);
                continue;
            }

            if (currentElement != prevElement) {
                listOfSets.clear();
                for (int k = 0; k < tmpResult.size(); k++) {
                    List<Integer> t = new ArrayList<>(tmpResult.get(k));
                    // todo sum in memory
                    currentSum = 0;
                    for (int m = 0; m < t.size(); m++) {
                        currentSum = currentSum + t.get(m);
                    }
                    if (currentSum + currentElement > target) {
                        continue;
                    }
                    if (currentSum + currentElement == target) {
                        t.addAll(currentList);
                        if (result.contains(t)) {
                            continue;
                        }
                        result.add(t);
                        continue;
                    }
                    t.addAll(currentList);
                    if (tmpResult.contains(t)) {
                        continue;
                    }
                    listOfSets.add(t);
                }
                tmpResult.addAll(listOfSets);
                tmpResult.add(currentList);
                currentListIndex = 0;
//                currentListIndex++;
                prevElement = currentElement;
                continue;
            }


//            List<Integer> list1 = new ArrayList<>(tmpResult.get(currentListIndex));
            listOfSets.clear();
            for (int x = 0; x < tmpResult.size(); x++) {
                List<Integer> list1 = new ArrayList<>(tmpResult.get(x));

                // todo sum in memory
                currentSum = 0;
                for (int m = 0; m < list1.size(); m++) {
                    currentSum = currentSum + list1.get(m);
                }
                if (currentSum + currentElement > target) {
                    continue;
                }
                if (currentSum + currentElement == target) {
                    list1.addAll(currentList);
                    if (result.contains(list1)) {
                        continue;
                    }
                    result.add(list1);
                    continue;
                }

                list1.addAll(currentList);
                if (tmpResult.contains(list1)) {
                    continue;
                }

                listOfSets.add(list1);
                currentListIndex++;
            }
            tmpResult.addAll(listOfSets);

        }

        return result;
    }

}
