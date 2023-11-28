package app;

import util.Graph;
import static util.Graph.createMetroMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

public class MetroApplication {
    public static void main(String[] args) throws IOException {
        Graph g = new Graph();
        boolean ex = false;
        createMetroMap(g);
        System.out.println("****WELCOME TO THE METRO APP*****");
        while(!ex) {
            printInterface();
            BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));

            int choice = Integer.parseInt(inp.readLine());
            switch(choice) {
                case 1:
                    g.displayStations();
                    g.displayMap();
                    break;

                case 2:
                    g.displayMap();
                    break;

                case 3:
                    System.out.println("ENTER THE SOURCE AND DESTINATION STATIONS");
                    String st1 = inp.readLine();
                    String st2 = inp.readLine();

                    HashMap<String, Boolean> processed = new HashMap<>();
                    if(!g.containsVertex(st1) && !g.containsVertex(st2) && !g.hasPath(st1, st2, processed))
                        System.out.println("THE INPUTS ARE INVALID");
                    else
                        System.out.println("SHORTEST DISTANCE FROM "+st1+" TO "+st2+" IS "+g.dijkstra(st1, st2, false)+"KM");
                    break;

                case 4:
                    System.out.println("ENTER THE SOURCE AND DESTINATION STATIONS");
                    String s1 = inp.readLine();
                    String s2 = inp.readLine();
                    HashMap<String, Boolean> processed2 = new HashMap<>();
                    if(!g.containsVertex(s1) || !g.containsVertex(s2) || !g.hasPath(s1, s2, processed2))
                        System.out.println("THE INPUTS ARE INVALID");
                    else {
                        List<String> str = g.getInterchanges(g.getMinDistance(s1, s2));
                        int len = str.size();
                        System.out.println("SOURCE STATION : " + s1);
                        System.out.println("SOURCE STATION : " + s2);
                        System.out.println("DISTANCE : " + str.get(len-1));
                        System.out.println("NUMBER OF INTERCHANGES : " + str.get(len-2));
                        System.out.println("~~~~~~~~~~~~~");
                        System.out.println("START  ==>  " + str.get(0));
                        for(int i=1; i<len-3; i++) {
                            System.out.println(str.get(i));
                        }
                        System.out.print(str.get(len-3) + "   ==>    END");
                        System.out.println("\n~~~~~~~~~~~~~");
                    }
                    break;

                case 5:
                    ex = true;
                    break;
                default:
                    System.out.println("INVALID CHOICE");
            }
        }
    }

    public static void printInterface() {
        System.out.println("      ~~MENU OPTIONS~~");
        System.out.println("1. LIST ALL THE STATIONS IN THE MAP");
        System.out.println("2. SHOW THE METRO MAP");
        System.out.println("3. GET SHORTEST DISTANCE FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
        System.out.println("4. GET SHORTEST PATH (DISTANCE WISE) TO REACH FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
        System.out.println("5. EXIT");
        System.out.println("ENTER YOUR CHOICE");
    }
}
