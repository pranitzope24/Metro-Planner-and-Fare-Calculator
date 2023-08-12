package src;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private HashMap<String, Vertex> vertices;

    public Graph() {
        vertices = new HashMap<>();
    }

    public void addVertex(String vertex) {
        Vertex vtx = new Vertex();
        vertices.put(vertex, vtx);
    }

    public int numVertex() {
        return this.vertices.size();
    }

    public boolean containsVertex(String vertex) {
        return this.vertices.containsKey(vertex);
    }

    public void removeVertex(String vertex) {
        Vertex vtx = vertices.get(vertex);
        ArrayList<String> keys = new ArrayList<>(vtx.numbers.keySet());
        for (String key : keys) {
            Vertex nbrVtx = vertices.get(key);
            nbrVtx.numbers.remove(vertex);
        }
        vertices.remove(vertex);
    }

    public int numEdges () {
        ArrayList<String> keys = new ArrayList<>(vertices.keySet());
        int count = 0;
        for (String key : keys) {
            Vertex vtx = vertices.get(key);
            count = count + vtx.numbers.size();
        }
        return count / 2;
    }

    void containsEdge() {

    }
    void addEdge() {

    }

    void removeEdge() {

    }

    public void displayMap() {

    }

    public void displayStations() {

    }

    public void hasPath() {

    }

    private class DjikstraPair implements Comparable<DjikstraPair>{
        String vname;
        String psf;
        int cost;

        @Override
        public int compareTo(DjikstraPair o) {
            return o.cost - this.cost;
        }
    }

    public int djikstra(String src, String dest, boolean nan) {
        // implement main logic here
        return 0; //change this later on
    }

    public static class Vertex {
        HashMap<String, Integer> numbers = new HashMap<>();
    }


}
