package util;

import java.util.ArrayList;
import java.util.HashMap;

public class Heap<T extends Comparable<T>> {
    ArrayList<T> data = new ArrayList<>();
    HashMap<T,Integer> map = new HashMap<>();

    public void add(T item) {
        data.add(item);
        map.put(item, this.data.size() - 1);
        upHeapify(data.size() - 1);
    }

    private void upHeapify(int childIndex) {
        int parIndex = (childIndex - 1) / 2;
        if (isLarger(data.get(childIndex), data.get(parIndex)) > 0) {
            swap(parIndex, childIndex);
            upHeapify(parIndex);
        }
    }

    private void swap(int i, int j) {
        T ith = data.get(i);
        T jth = data.get(j);

        data.set(i, jth);
        data.set(j, ith);
        map.put(ith, j);
        map.put(jth, i);
    }

    public int size() {
        return this.data.size();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public T remove() {
        swap(0, this.data.size() - 1);
        T rv = this.data.remove(this.data.size() - 1);
        downHeapify(0);
        map.remove(rv);
        return rv;
    }

    private void downHeapify(int parIndex) {
        int leftChildIndex = 2 * parIndex + 1;
        int rightChildIndex = 2 * parIndex + 2;
        int minIndex = parIndex;
        if (leftChildIndex < this.data.size() && isLarger(data.get(leftChildIndex), data.get(minIndex)) > 0) {
            minIndex = leftChildIndex;
        }
        if (rightChildIndex < this.data.size() && isLarger(data.get(rightChildIndex), data.get(minIndex)) > 0) {
            minIndex = rightChildIndex;
        }
        if (minIndex != parIndex) {
            swap(minIndex, parIndex);
            downHeapify(minIndex);
        }
    }

    public int isLarger(T t1, T t2) {
        return t1.compareTo(t2);
    }

    public void updatePriority(T pair) {
        int index = map.get(pair);
        upHeapify(index);
    }
}