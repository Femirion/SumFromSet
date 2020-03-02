import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String... args) {
        Sumator s = new Sumator();
        int[] arr = {2, 3, 5};
        System.out.println(s.combinationSum(arr, 7));
    }

    private static class Sumator {
        private List<List<Integer>> combinationSum(int[] candidates, int target) {
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
            List<MyList> result = new ArrayList<>();
            List<MyList> tmpResult = new ArrayList<>();
            List<MyList> listOfSets = new ArrayList<>();
            int currentElement;
            int prevElement = tmp[0];

            if (tmp[0] == target) {
                result.add(new MyList(tmp[0]));
                List<List<Integer>> total = new ArrayList<>();
                total.add(Collections.singletonList(tmp[0]));
                return total;

            }

            List<List<Integer>> total = new ArrayList<>();
            tmpResult.add(new MyList(tmp[1]));
            for (int i = 1; i < tmp.length; i++) {
                currentElement = tmp[i];
                if (currentElement== target) {
                    result.add(new MyList(currentElement));
                    continue;
                }

                if (currentElement != prevElement) {
                    listOfSets.clear();
                    for (int k = 0; k < tmpResult.size(); k++) {
                        MyList t = tmpResult.get(k).deepCopy();
                        if (t.sum + currentElement > target) {
                            continue;
                        }
                        if (t.sum + currentElement == target) {
                            t.addElement(currentElement);
                            if (result.contains(t)) {
                                continue;
                            }
                            result.add(t);
                            continue;
                        }
                        t.addElement(currentElement);
                        if (tmpResult.contains(t)) {
                            continue;
                        }
                        listOfSets.add(t);
                    }
                    tmpResult.addAll(listOfSets);
                    tmpResult.add(new MyList(currentElement));
                    prevElement = currentElement;
                    continue;
                }


                listOfSets.clear();
                for (int x = 0; x < tmpResult.size(); x++) {
                    MyList list1 = tmpResult.get(x).deepCopy();

                    if (list1.sum + currentElement > target) {
                        continue;
                    }
                    if (list1.sum + currentElement == target) {
                        list1.addElement(currentElement);
                        if (result.contains(list1)) {
                            continue;
                        }
                        result.add(list1);
                        continue;
                    }

                    list1.addElement(currentElement);
                    if (tmpResult.contains(list1)) {
                        continue;
                    }

                    listOfSets.add(list1);
                }
                tmpResult.addAll(listOfSets);

            }


            for (MyList myList : result) {
                total.add(Arrays.stream(myList.list).boxed().collect(Collectors.toList()));

            }
            return total;
        }

        private class MyList {
            private int[] list;
            private int sum;

            public MyList(int element) {
                this.list = new int[1];
                this.list[0] = element;
                this.sum = element;
            }

            public MyList(int[] elements, int sum) {
                this.list = new int[elements.length];
                this.list = elements;
                this.sum = sum;
            }

            public void addElement(int element) {
                int[] tmp = new int[list.length + 1];
                System.arraycopy(list, 0, tmp, 0, list.length);
                tmp[list.length] = element;
                this.list = tmp;
                this.sum = sum + element;
            }

            public MyList deepCopy() {
                return new MyList(this.list, this.sum);
            }


            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                MyList myList = (MyList) o;
                return sum == myList.sum &&
                        Arrays.equals(list, myList.list);
            }

            @Override
            public int hashCode() {
                int result = Objects.hash(sum);
                result = 31 * result + Arrays.hashCode(list);
                return result;
            }
        }
    }

}
