package util;

import java.util.*;

public class Graph {
    private final HashMap<String, Vertex> vertices;

    public Graph() {
        vertices = new HashMap<>();
    }

    public static class Vertex {
        HashMap<String, Integer> nbrs = new HashMap<>();
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

    public boolean hasPath(String vName1, String vName2, Map<String,Boolean> proc) {
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

    public int dijkstra(String src, String des, boolean nan) {
        int val = 0;
        ArrayList<String> ans = new ArrayList<>();
        HashMap<String, DijkstraPair> map = new HashMap<>();
        Heap<DijkstraPair> heap = new Heap<>();
        for (String key : vertices.keySet()) {
            DijkstraPair np = new DijkstraPair();
            np.vName = key;
            np.cost = Integer.MAX_VALUE;
            if (key.equals(src)) {
                np.cost = 0;
                np.psf = key;
            }
            heap.add(np);
            map.put(key, np);
        }

        while (!heap.isEmpty()) {
            DijkstraPair rp = heap.remove();
            if(rp.vName.equals(des)) {
                val = rp.cost;
                break;
            }
            map.remove(rp.vName);
            ans.add(rp.vName);
            Vertex v = vertices.get(rp.vName);
            for (String nbr : v.nbrs.keySet()) {
                if (map.containsKey(nbr)) {
                    int oc = map.get(nbr).cost;
                    Vertex k = vertices.get(rp.vName);
                    int nc;
                    if(nan) {
                        nc = rp.cost + 120 + 40*k.nbrs.get(nbr);
                    }
                    else {
                        nc = rp.cost + k.nbrs.get(nbr);
                    }
                    if (nc < oc) {
                        DijkstraPair gp = map.get(nbr);
                        gp.psf = rp.psf + nbr;
                        gp.cost = nc;
                        heap.updatePriority(gp);
                    }
                }
            }
        }
        return val;
    }

    private class Pair {
        String vName;
        String psf;
        int minDistance;
        int minTime;
    }

    public String getMinDistance(String src, String dst) {
        int min = Integer.MAX_VALUE;
        String ans = "";
        HashMap<String, Boolean> processed = new HashMap<>();
        LinkedList<Pair> stack = new LinkedList<>();
        Pair sp = new Pair();
        sp.vName = src;
        sp.psf = src + "  ";
        sp.minDistance = 0;
        sp.minTime = 0;
        stack.addFirst(sp);
        while (!stack.isEmpty()) {
            Pair rp = stack.removeFirst();
            if (processed.containsKey(rp.vName)) {
                continue;
            }
            processed.put(rp.vName, true);
            if (rp.vName.equals(dst)) {
                int temp = rp.minDistance;
                if(temp<min) {
                    ans = rp.psf;
                    min = temp;
                }
                continue;
            }
            Vertex rpvtx = vertices.get(rp.vName);
            ArrayList<String> nbrs = new ArrayList<>(rpvtx.nbrs.keySet());
            for(String nbr : nbrs) {
                if (!processed.containsKey(nbr)) {
                    Pair np = new Pair();
                    np.vName = nbr;
                    np.psf = rp.psf + nbr + "  ";
                    np.minDistance = rp.minDistance + rpvtx.nbrs.get(nbr);
                    stack.addFirst(np);
                }
            }
        }
        ans = ans + Integer.toString(min);
        return ans;
    }


    public String getMinTime(String src, String dst) {
        int min = Integer.MAX_VALUE;
        String ans = "";
        HashMap<String, Boolean> processed = new HashMap<>();
        LinkedList<Pair> stack = new LinkedList<>();
        Pair sp = new Pair();
        sp.vName = src;
        sp.psf = src + "  ";
        sp.minDistance = 0;
        sp.minTime = 0;
        stack.addFirst(sp);
        while (!stack.isEmpty()) {
            Pair rp = stack.removeFirst();
            if (processed.containsKey(rp.vName)) {
                continue;
            }
            processed.put(rp.vName, true);
            if (rp.vName.equals(dst)) {
                int temp = rp.minTime;
                if(temp<min) {
                    ans = rp.psf;
                    min = temp;
                }
                continue;
            }
            Vertex rpvtx = vertices.get(rp.vName);
            ArrayList<String> nbrs = new ArrayList<>(rpvtx.nbrs.keySet());
            for (String nbr : nbrs) {
                if (!processed.containsKey(nbr)) {
                    Pair np = new Pair();
                    np.vName = nbr;
                    np.psf = rp.psf + nbr + "  ";
                    np.minTime = rp.minTime + 120 + 40*rpvtx.nbrs.get(nbr);
                    stack.addFirst(np);
                }
            }
        }
        double minutes = Math.ceil((double)min / 60);
        ans = ans + Double.toString(minutes);
        return ans;
    }

    public List<String> getInterchanges(String str) {
        ArrayList<String> arr = new ArrayList<>();
        String[] res = str.split(" {2}");
        arr.add(res[0]);
        int count = 0;
        for(int i=1;i<res.length-1;i++) {
            int index = res[i].indexOf('~');
            String s = res[i].substring(index+1);
            if(s.length()==2) {
                String prev = res[i-1].substring(res[i-1].indexOf('~')+1);
                String next = res[i+1].substring(res[i+1].indexOf('~')+1);
                if(prev.equals(next)) {
                    arr.add(res[i]);
                }
                else {
                    arr.add(res[i]+" ==> "+res[i+1]);
                    i++;
                    count++;
                }
            }
            else {
                arr.add(res[i]);
            }
        }
        arr.add(Integer.toString(count));
        arr.add(res[res.length-1]);
        return arr;
    }

    public static void createMetroMap(Graph g) {
        g.addVertex("Noida Sector 62~B");
        g.addVertex("Botanical Garden~B");
        g.addVertex("Yamuna Bank~B");
        g.addVertex("Rajiv Chowk~BY");
        g.addVertex("Vaishali~B");
        g.addVertex("Moti Nagar~B");
        g.addVertex("Janak Puri West~BO");
        g.addVertex("Dwarka Sector 21~B");
        g.addVertex("Huda City Center~Y");
        g.addVertex("Saket~Y");
        g.addVertex("Vishwavidyalaya~Y");
        g.addVertex("Chandni Chowk~Y");
        g.addVertex("New Delhi~YO");
        g.addVertex("AIIMS~Y");
        g.addVertex("Shivaji Stadium~O");
        g.addVertex("DDS Campus~O");
        g.addVertex("IGI Airport~O");
        g.addVertex("Jamia Millia Islamia");

        g.addEdge("Noida Sector 62~B", "Botanical Garden~B", 8);
        g.addEdge("Botanical Garden~B", "Yamuna Bank~B", 10);
        g.addEdge("Yamuna Bank~B", "Vaishali~B", 8);
        g.addEdge("Yamuna Bank~B", "Rajiv Chowk~BY", 6);
        g.addEdge("Rajiv Chowk~BY", "Moti Nagar~B", 9);
        g.addEdge("Moti Nagar~B", "Janak Puri West~BO", 7);
        g.addEdge("Janak Puri West~BO", "Dwarka Sector 21~B", 6);
        g.addEdge("Huda City Center~Y", "Saket~Y", 15);
        g.addEdge("Saket~Y", "AIIMS~Y", 6);
        g.addEdge("AIIMS~Y", "Rajiv Chowk~BY", 7);
        g.addEdge("Rajiv Chowk~BY", "New Delhi~YO", 1);
        g.addEdge("New Delhi~YO", "Chandni Chowk~Y", 2);
        g.addEdge("Chandni Chowk~Y", "Vishwavidyalaya~Y", 5);
        g.addEdge("New Delhi~YO", "Shivaji Stadium~O", 2);
        g.addEdge("Shivaji Stadium~O", "DDS Campus~O", 7);
        g.addEdge("DDS Campus~O", "IGI Airport~O", 8);
        g.addEdge("IGI Airport~O","Jamia Millia Islamia", 15);
    }

}
