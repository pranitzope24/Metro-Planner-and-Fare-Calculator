package src;

import java.util.ArrayList;
import java.util.HashMap;

public class Heap <T extends Comparable<T>> {

    private final ArrayList<T> data  = new ArrayList<>();
    private final HashMap<T, Integer> map = new HashMap<>();

    public void upHeapify(int ci) {
        int pi = (ci-1)/2;
        if(isLarger(data.get(ci), data.get(pi)) > 0) {
            swap(pi, ci);
            upHeapify(pi);
        }
    }
    public void downHeapify(int pi) {
        int lci = 2*pi+1;
        int rci = 2*pi+2;
        int mini = pi;
        if (lci < this.data.size() && isLarger(data.get(lci), data.get(mini)) > 0) {
            mini = lci;
        }
        if (rci < this.data.size() && isLarger(data.get(rci), data.get(mini)) > 0) {
            mini = rci;
        }
        if (mini != pi) {
            swap(mini, pi);
            downHeapify(mini);
        }

    }
    public int isLarger(T a, T b) {
        return a.compareTo(b);
    }
    public T get(){
        return this.data.get(0);
    }

    public void add(T item) {
        data.add(item);
        map.put(item, this.data.size() - 1);
    }

    public void swap(int i, int j) {
        T ith = this.data.get(i);
        T jth = this.data.get(j);

        data.set(j, ith);
        data.set(i, jth);
        map.put(ith, j);
        map.put(jth, i);
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    public void display() {
        System.out.println(data);
    }

    public int size() {
        return this.data.size();
    }

    public T remove() {
        swap(0, this.data.size() - 1);
        T rm = this.data.remove(this.data.size() - 1);
        downHeapify(0);
        map.remove(rm);
        return rm;
    }

    public void updatePriority(T pair) {
        int idx = map.get(pair);
        upHeapify(idx);
    }
}
