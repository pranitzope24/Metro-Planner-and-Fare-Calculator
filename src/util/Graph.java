package util;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    static HashMap<String, Vertex> vertices;
    public Graph() {
        vertices = new HashMap<>();
    }

    public class Vertex {
        HashMap<String, Integer> nbrs = new HashMap<>();
    }

    public int numberOfVertices() {
        return vertices.size();
    }

    public boolean containsVertex(String vName) {
        return vertices.containsKey(vName);
    }

    public void addVertex(String vName) {
        Vertex vtx = new Vertex();
        vertices.put(vName, vtx);
    }

    public void removeVertex(String vName) {
        Vertex vtx = vertices.get(vName);
        ArrayList<String> keys = new ArrayList<>(vtx.nbrs.keySet());

        for(String key : keys) {
            Vertex nbrVtx = vertices.get(key);
            nbrVtx.nbrs.remove(vName);
        }
        vertices.remove(vName);
    }

    public int numberOfEdges() {
        ArrayList<String> keys = new ArrayList<>(vertices.keySet());
        int count = 0;
        for (String key : keys) {
            Vertex vtx = vertices.get(key);
            count = count + vtx.nbrs.size();
        }
        return count / 2;
    }

    public boolean containsEdge(String vName1, String vName2) {
        Vertex vtx1 = vertices.get(vName1);
        Vertex vtx2 = vertices.get(vName2);
        return vtx1 != null && vtx2 != null && vtx1.nbrs.containsKey(vName2);
    }

    public void addEdge(String vName1, String vName2, int value) {
        Vertex vtx1 = vertices.get(vName1);
        Vertex vtx2 = vertices.get(vName2);
        if (vtx1 == null || vtx2 == null || vtx1.nbrs.containsKey(vName2)) {
            return;
        }
        vtx1.nbrs.put(vName2, value);
        vtx2.nbrs.put(vName1, value);
    }

    public void removeEdge(String vName1, String vName2) {
        Vertex vtx1 = vertices.get(vName1);
        Vertex vtx2 = vertices.get(vName2);
        if (vtx1 == null || vtx2 == null || !vtx1.nbrs.containsKey(vName2)) {
            return;
        }
        vtx1.nbrs.remove(vName2);
        vtx2.nbrs.remove(vName1);
    }

    public void displayMap() {
        System.out.println("------------------");
        ArrayList<String> keys = new ArrayList<>(vertices.keySet());
        for (String key : keys) {
            String str = key + " => ";
            Vertex vtx = vertices.get(key);
            str = str + vtx.nbrs;
            System.out.println(str);
        }
        System.out.println("------------------");
    }

    public void displayStations() {
        System.out.println("....................................");
        ArrayList<String> keys = new ArrayList<>(vertices.keySet());
        int i=1;
        for(String key : keys) {
            System.out.println(i + ". " + key);
            i++;
        }
        System.out.println("....................................");
    }

    public boolean hasPath(String vName1, String vName2, HashMap<String,Boolean> proc) {
        if(containsEdge(vName1, vName2)) {
            return true;
        }
        proc.put(vName1,true);
        Vertex vtx = vertices.get(vName1);
        ArrayList<String> nbrs = new ArrayList<>(vtx.nbrs.keySet());
        for (String nbr : nbrs) {
            if (!proc.containsKey(nbr) && hasPath(nbr, vName2, proc)){
                return true;
            }
        }
        return false;
    }

    public class DijkstraPair implements Comparable<DijkstraPair> {
        String vName;
        String psf;
        int cost;

        @Override
        public int compareTo(DijkstraPair dijkstraPair) {
            return dijkstraPair.cost - this.cost;
        }
    }

    public int dijkstra(String src, String dest, boolean nan) {
        //complete once priority_queue is completed.
        return 0; //rm
    }

    private class Pair {
        String vName;
        String psf;
        int minDistance;
        int minTime;
    }

}
