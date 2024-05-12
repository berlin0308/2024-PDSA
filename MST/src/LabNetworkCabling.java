import java.util.List;
import java.util.Map;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Collections;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.UF;

// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.IOException;

// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;
// import org.json.simple.parser.JSONParser;
// import org.json.simple.parser.ParseException;



class LabNetworkCabling {

    private PrimMST MST;
    private int router;
    private int server;
    private List<Integer> printers;
    private List<Integer> computers;
    
    public LabNetworkCabling(Map<Integer, String> deviceTypes, List<int[]> links){
        EdgeWeightedGraph EWG = new EdgeWeightedGraph(deviceTypes.size());
        for(int[] link: links){
            Edge E = new Edge(link[0], link[1], link[2]);
            EWG.addEdge(E);
        }
        MST = new PrimMST(EWG);

        // record the device indices
        printers = new ArrayList<Integer>();
        computers = new ArrayList<Integer>();
        for (Map.Entry<Integer, String> entry : deviceTypes.entrySet()) {
            String device = entry.getValue();
            if(device.equals("Router")) router = entry.getKey();
            if(device.equals("Server")) server = entry.getKey();
            if(device.equals("Printer")) printers.add(entry.getKey());
            if(device.equals("Computer")) computers.add(entry.getKey());
        }
        Collections.sort(printers);
        Collections.sort(computers);
        // System.out.println(router);
        // System.out.println(server);
        // System.out.println(printers);
        // System.out.println(computers);
    };
    
    public int cablingCost() {
        return (int)MST.weight();
    }

    public int serverToRouter(){
        return (int)MST.findPathDistance(server, router);
    }

    public int mostPopularPrinter(){
        Map<Integer, Integer> printerVote = new HashMap<Integer, Integer>();
        for(int printer: printers){
            printerVote.put(printer, 0);
        }
        for(int computer: computers){

            int minDistance = Integer.MAX_VALUE;
            int minPrinter = -1;
            for(int printer: printers){

                if((int)MST.findPathDistance(computer, printer) < minDistance){
                    minDistance = (int)MST.findPathDistance(computer, printer);
                    minPrinter = printer;
                }
            }

            // System.out.println("Computer:"+computer+" -> Printer:"+minPrinter);

            int count = printerVote.get(minPrinter);
            printerVote.put(minPrinter, count + 1);
        }
        // System.out.println(printerVote);

        int popularPrinter = -1;
        int maxCount = -1;
        for(int printer: printers){
            int count = printerVote.get(printer);
            if(count>maxCount){ // if two printers have the same amount of voting, return the smallest one
                popularPrinter = printer;
                maxCount = count;
            }
        }
        return popularPrinter;
    }

    // public static void main(String[] args) {
    //     Map<Integer, String> deviceTypes = Map.of(
    //         0, "Router",
    //         1, "Server",
    //         2, "Printer",
    //         3, "Printer",
    //         4, "Computer",
    //         5, "Computer",
    //         6, "Computer"
    //     );

    //     List<int[]> links = List.of(
    //         new int[]{0, 1, 1},
    //         new int[]{1, 2, 2},
    //         new int[]{2, 4, 1},
    //         new int[]{0, 3, 3},
    //         new int[]{1, 3, 8},
    //         new int[]{3, 5, 2},
    //         new int[]{3, 6, 9},
    //         new int[]{0, 6, 1}
    //     );

    //     LabNetworkCabling Network = new LabNetworkCabling(deviceTypes, links);
    //     System.out.println("Total Cabling Cost: "+Network.cablingCost());
    //     System.out.println("Distance between Server and Router: "+Network.serverToRouter());
    //     System.out.println("Most Popular Printer: "+Network.mostPopularPrinter());
    // }



    //   public static void test(String[] args){
    //     LabNetworkCabling l;
    //     JSONParser jsonParser = new JSONParser();
    //     try (FileReader reader = new FileReader(args[0])){
    //         JSONArray all = (JSONArray) jsonParser.parse(reader);
    //         int count = 0;
    //         for(Object CaseInList : all){
    //             count++;
    //             JSONObject aCase = (JSONObject) CaseInList;
    //             JSONArray dataArray = (JSONArray) aCase.get("data");

    //             // JSONObject data = (JSONObject) aCase.get("data");
    //             // JSONArray dataArray = (JSONArray) data.get("data");

    //             int testSize = 0; int waSize = 0;
    //             System.out.print("Case ");
    //             System.out.println(count);

    //             for (Object dataObj : dataArray) {
    //                 JSONObject data = (JSONObject) dataObj;

    //                 // Extracting device types
    //                 JSONObject jsonDeviceTypes = (JSONObject) data.get("deviceTypes");
    //                 Map<Integer, String> deviceTypes = new HashMap<>();
    //                 for (Object key : jsonDeviceTypes.keySet()) {
    //                     String keyStr = (String) key;
    //                     String value = (String) jsonDeviceTypes.get(keyStr);
    //                     deviceTypes.put(Integer.parseInt(keyStr), value);
    //                 }

    //                 // Extracting links
    //                 JSONArray jsonLinks = (JSONArray) data.get("links");
    //                 List<int[]> links = new ArrayList<>();
    //                 for (Object linkObj : jsonLinks) {
    //                     JSONArray linkArr = (JSONArray) linkObj;
    //                     int[] link = new int[linkArr.size()];
    //                     for (int i = 0; i < linkArr.size(); i++) {
    //                         link[i] = ((Long) linkArr.get(i)).intValue();
    //                     }
    //                     links.add(link);
    //                 }

    //                 long cablingCost = (long) data.get("cablingCost");
    //                 long serverToRouter = (long) data.get("serverToRouter");
    //                 long mostPopularPrinter = (long) data.get("mostPopularPrinter");

    //                 l = new LabNetworkCabling(deviceTypes, links);

    //                 int ans1 = l.cablingCost();
    //                 int ans2 = l.serverToRouter();
    //                 int ans3 = l.mostPopularPrinter();

    //                 testSize++;
    //                 if(ans1==cablingCost && ans2==serverToRouter && ans3==mostPopularPrinter){
    //                     // System.out.println("AC");

    //                 }else{
    //                     waSize++;
    //                     // System.out.println("WA");
    //                 }
    //             }
    //             System.out.println("Score: " + (testSize-waSize) + " / " + testSize + " ");

    //         }
    //     }catch (FileNotFoundException e) {
    //         e.printStackTrace();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     } catch (ParseException e) {
    //         e.printStackTrace();
    //     }
    // }

    // public static int[] JSONArraytoIntArray(JSONArray x){
    //     int sizeLim = x.size();
    //     int MyInt[] = new int[sizeLim];
    //     for(int i=0;i<sizeLim;i++){
    //         MyInt[i]= Integer.parseInt(x.get(i).toString());
    //     }
    //     return MyInt;
    // }

    // public static void main(String[] args) {
    //     // test(args);
    //     test(new String[]{"test/test.json"});
    // }
}

class PrimMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private Edge[] edgeTo;        // edgeTo[v] = shortest edge from tree vertex to non-tree vertex
    private double[] distTo;      // distTo[v] = weight of shortest such edge
    private boolean[] marked;     // marked[v] = true if v on tree, false otherwise
    private IndexMinPQ<Double> pq;

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     * @param G the edge-weighted graph
     */
    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        for (int v = 0; v < G.V(); v++)      // run from each vertex to find
            if (!marked[v]) prim(G, v);      // minimum spanning forest

        // check optimality conditions
        assert check(G);
    }

    // run Prim's algorithm in graph G, starting from vertex s
    private void prim(EdgeWeightedGraph G, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(G, v);
        }
    }

    // scan vertex v
    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;         // v-w is obsolete edge
            if (e.weight() < distTo[w]) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
    }

    /**
     * Returns the edges in a minimum spanning tree (or forest).
     * @return the edges in a minimum spanning tree (or forest) as
     *    an iterable of edges
     */
    public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<Edge>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double weight() {
        double weight = 0.0;
        for (Edge e : edges())
            weight += e.weight();
        return weight;
    }


    // check optimality conditions (takes time proportional to E V lg* V)
    private boolean check(EdgeWeightedGraph G) {

        // check weight
        double totalWeight = 0.0;
        for (Edge e : edges()) {
            totalWeight += e.weight();
        }
        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
            return false;
        }

        // check that it is acyclic
        UF uf = new UF(G.V());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) == uf.find(w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        // check that it is a spanning forest
        for (Edge e : G.edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) != uf.find(w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for (Edge e : edges()) {

            // all edges in MST except e
            uf = new UF(G.V());
            for (Edge f : edges()) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }

            // check that e is min weight edge in crossing cut
            for (Edge f : G.edges()) {
                int x = f.either(), y = f.other(x);
                if (uf.find(x) != uf.find(y)) {
                    if (f.weight() < e.weight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }

    public double findPathDistance(int p, int q) {
    if (p < 0 || p >= marked.length || q < 0 || q >= marked.length) {
        throw new IllegalArgumentException("Vertex out of bounds");
    }
    if (!marked[p] || !marked[q]) {
        throw new IllegalArgumentException("One or more vertices are not in the MST");
    }

    List<Integer> pathP = findPathToRoot(p, new ArrayList<>());
    List<Integer> pathQ = findPathToRoot(q, new ArrayList<>());

    // Find the lowest common ancestor
    int lca = findLowestCommonAncestor(pathP, pathQ);

    // Calculate distance from p and q to the lowest common ancestor
    double distanceP = calculateDistanceToAncestor(p, lca);
    double distanceQ = calculateDistanceToAncestor(q, lca);

    return distanceP + distanceQ;
}

private List<Integer> findPathToRoot(int vertex, List<Integer> path) {
    while (edgeTo[vertex] != null) {
        path.add(vertex);
        vertex = edgeTo[vertex].other(vertex);
    }
    path.add(vertex); // Add the root vertex
    return path;
}

private int findLowestCommonAncestor(List<Integer> path1, List<Integer> path2) {
    int i = path1.size() - 1;
    int j = path2.size() - 1;

    // Find the lowest common ancestor
    int lca = -1;
    while (i >= 0 && j >= 0 && path1.get(i).equals(path2.get(j))) {
        lca = path1.get(i);
        i--; j--;
    }
    return lca;
}

private double calculateDistanceToAncestor(int start, int ancestor) {
    double distance = 0.0;
    while (start != ancestor) {
        Edge e = edgeTo[start];
        distance += e.weight();
        start = e.other(start);
    }
    return distance;
}

}