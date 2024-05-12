import com.google.gson.*;
import java.util.Random;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.UF;

import java.util.Collections;



class OutputFormat{
    LabNetworkCabling l;
    Map<Integer, String> deviceTypes;
    List<int[]> links;

    int cablingCost;
    int serverToRouter;
    int mostPopularPrinter;

    OutputFormat(Map<Integer, String> deviceTypes, List<int[]> links) {
        this.deviceTypes = deviceTypes;
        this.links = links;
        this.l = new LabNetworkCabling(this.deviceTypes, this.links);
        this.cablingCost = l.cablingCost();
        this.serverToRouter = l.serverToRouter();
        this.mostPopularPrinter = l.mostPopularPrinter();
        System.out.println("Total Cabling Cost: "+this.cablingCost);
        System.out.println("Distance between Server and Router: "+this.serverToRouter);
        System.out.println("Most Popular Printer: "+this.mostPopularPrinter);
    }
}
class RandomGenerator {
    public static OutputFormat CustomProblem(int case_id, int test_id){
            if(case_id==1 && test_id==1){ // the example
                Map<Integer, String> deviceTypes = Map.of(
                0, "Router",
                1, "Server",
                2, "Printer",
                3, "Printer",
                4, "Computer",
                5, "Computer",
                6, "Computer"
                );

                List<int[]> links = List.of(
                    new int[]{0, 1, 1},
                    new int[]{1, 2, 2},
                    new int[]{2, 4, 1},
                    new int[]{0, 3, 3},
                    new int[]{1, 3, 8},
                    new int[]{3, 5, 2},
                    new int[]{3, 6, 9},
                    new int[]{0, 6, 1}
                );
                return new OutputFormat(deviceTypes, links);
            }
            if(case_id==1 && test_id==2){ // simple
                Map<Integer, String> deviceTypes = Map.of(
                0, "Router",
                1, "Server",
                2, "Printer",
                3, "Computer"
                );

                List<int[]> links = List.of(
                    new int[]{0, 2, 2},
                    new int[]{1, 2, 4},
                    new int[]{1, 3, 5},
                    new int[]{0, 1, 9},
                    new int[]{0, 3, 12}
                );
                return new OutputFormat(deviceTypes, links);
            }
            if(case_id==1 && test_id==3){
                Map<Integer, String> deviceTypes = Map.of(
                    0, "Router",
                    1, "Server",
                    2, "Printer",
                    3, "Printer",
                    4, "Computer",
                    5, "Computer",
                    6, "Computer"
                );

                List<int[]> links = List.of(
                    new int[]{0, 1, 5},
                    new int[]{0, 4, 3},
                    new int[]{0, 5, 9},
                    new int[]{0, 2, 16},
                    new int[]{4, 2, 2},
                    new int[]{5, 3, 1},
                    new int[]{6, 3, 1},
                    new int[]{6, 2, 6}
                );
                return new OutputFormat(deviceTypes, links);
            }


            if(case_id==2 && test_id==1){ // if two printers are equally close, they choose the one with the lower device index.
                Map<Integer, String> deviceTypes = Map.of(
                    0, "Printer",
                    1, "Printer",
                    2, "Printer",
                    3, "Server",
                    4, "Router",
                    5, "Computer"
                );

                List<int[]> links = List.of(
                    new int[]{0, 5, 2},
                    new int[]{1, 5, 2},
                    new int[]{2, 5, 2},
                    new int[]{0, 3, 4},
                    new int[]{3, 5, 6},
                    new int[]{3, 4, 10},
                    new int[]{4, 5, 3},
                    new int[]{4, 2, 30}
                );
                return new OutputFormat(deviceTypes, links);
            }
            if(case_id==2 && test_id==2){ // If two or more printers have the same amount of usage, return the smallest.
                Map<Integer, String> deviceTypes = Map.of(
                    0, "Router",
                    1, "Server",
                    2, "Printer",
                    3, "Printer",
                    4, "Computer",
                    5, "Computer",
                    6, "Computer",
                    7, "Computer"
                );

                List<int[]> links = List.of(
                    new int[]{4, 2, 1},
                    new int[]{5, 2, 1},
                    new int[]{0, 2, 7},
                    new int[]{0, 6, 2},
                    new int[]{0, 1, 9},
                    new int[]{1, 6, 4},
                    new int[]{6, 3, 1},
                    new int[]{3, 7, 1}
                );
                return new OutputFormat(deviceTypes, links);
            }
            if(case_id==2 && test_id==3){
                Map<Integer, String> deviceTypes = Map.of(
                    0, "Router",
                    1, "Server",
                    2, "Printer",
                    3, "Printer",
                    4, "Printer",
                    5, "Computer"
                );

                List<int[]> links = List.of(
                    new int[]{4, 2, 1},
                    new int[]{3, 2, 1},
                    new int[]{0, 2, 6},
                    new int[]{0, 5, 9},
                    new int[]{5, 1, 7}
                );
                return new OutputFormat(deviceTypes, links);
            }
        
        else return new OutputFormat(null, null);
    }
    public static OutputFormat ProblemGenerator(int P, int C, int L){


        long startTime = System.nanoTime();
        Map<Integer, String> deviceTypes = new HashMap<>();
        deviceTypes.put(0, "Router");
        deviceTypes.put(1, "Server");
        for(int i=2;i<2+P;i++){
            deviceTypes.put(i, "Printer");
        }
        for(int j=2+P;j<2+P+C;j++){
            deviceTypes.put(j, "Computer");
        }
        // deviceTypes.forEach((key, value) -> System.out.println(key + " = " + value));

        int totalDevices = 2 + P + C;
        Random random = new Random();

        List<int[]> links = new ArrayList<>();
        HashSet<Integer> uniqueWeights = new HashSet<>();

        while (uniqueWeights.size() < L) {
            uniqueWeights.add(random.nextInt(L*2) + 1);
        }
        List<Integer> weightList = new ArrayList<>(uniqueWeights);
        Collections.shuffle(weightList);

        int weightIndex = 0;

        for (int i = 0; i < totalDevices - 1; i++) {
            int cost = weightList.get(weightIndex++);
            links.add(new int[]{i, i + 1, cost});
        }

        while (links.size() < L) {
            int a = random.nextInt(totalDevices);
            int b = random.nextInt(totalDevices);
            if (a != b && links.stream().noneMatch(link -> (link[0] == a && link[1] == b) || (link[0] == b && link[1] == a))) {
                int cost = weightList.get(weightIndex++);
                links.add(new int[]{a, b, cost});
            }
        }

        for (int[] link : links) {
            System.out.println("Edge from " + link[0] + " to " + link[1] + " with weight " + link[2]);
        }

        
        // Random random = new Random();
        // int totalDevices = 2 + P + C;

        // List<int[]> links = new ArrayList<>();

        // // connect all vertice with another to ensure all-connected
        // for (int i = 0; i < totalDevices - 1; i++) {
        //     int cost = random.nextInt(40) + 1;
        //     links.add(new int[]{i, i + 1, cost});
        // }

        // // append the remained links
        // while (links.size() < L) {
        //     int a = random.nextInt(totalDevices);
        //     int b = random.nextInt(totalDevices);
        //     int cost = random.nextInt(10) + 1;

        //     if (a != b && links.stream().noneMatch(link -> (link[0] == a && link[1] == b) || (link[0] == b && link[1] == a))) {
        //         links.add(new int[]{a, b, cost});
        //     }
        // }

        // for (int[] link : links) {
        //     System.out.println("Connect: " + link[0] + " -- " + link[1] + " , cost " + link[2]);
        // }

        LabNetworkCabling l = new LabNetworkCabling(deviceTypes, links);

        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + Long.toString((endTime-startTime)/1000000) + " ms");
        return new OutputFormat(deviceTypes, links);
        }


    public static void main(String[] args) throws Exception {
        Path path = Paths.get("./src/LabNetworkCabling.json");
        Gson myGson = new Gson();

        String output = "[";

        //case 1
        output+="{"+
                "\"case\": "+1+","+
                "\"score\": 20,"+
                "\"data\": ["+
                myGson.toJson(CustomProblem(1,1))+","+
                myGson.toJson(CustomProblem(1,2))+","+
                myGson.toJson(CustomProblem(1,3))+
                "]" +
                "},";
        //case 2
        output+="{"+
                "\"case\": "+2+","+
                "\"score\": 20,"+
                "\"data\": ["+
                myGson.toJson(CustomProblem(2,1))+","+
                myGson.toJson(CustomProblem(2,2))+","+
                myGson.toJson(CustomProblem(2,3))+
                "]" +
                "},";
        // case 3
        output+="{"+
                "\"case\": "+3+","+
                "\"score\": 20,"+
                "\"data\": ["+
                myGson.toJson(ProblemGenerator(5, 2, 20))+","+
                myGson.toJson(ProblemGenerator(10, 5, 30))+","+
                myGson.toJson(ProblemGenerator(10, 20, 80))+","+
                myGson.toJson(ProblemGenerator(20, 20, 60))+
                "]" +
                "},";
        // case 4
        output+="{"+
                "\"case\": "+4+","+
                "\"score\": 20,"+
                "\"data\": ["+
                myGson.toJson(ProblemGenerator(200, 100, 500))+","+
                myGson.toJson(ProblemGenerator(100, 2, 200))+","+
                myGson.toJson(ProblemGenerator(2, 350, 500))+","+
                myGson.toJson(ProblemGenerator(200, 400, 800))+
                "]" +
                "},";
        //case 5
        System.out.println("Case 5\n\n\n");
        output+="{"+
                "\"case\": "+5+","+
                "\"score\": 20,"+
                "\"data\": ["+
                myGson.toJson(ProblemGenerator(200, 200, 500))+","+
                myGson.toJson(ProblemGenerator(1000, 5, 4000))+","+
                myGson.toJson(ProblemGenerator(30, 2000, 2800))+
                "]" +
                "}"
                +"]";

        Files.writeString(path, output, StandardCharsets.UTF_8);
        //Files.writeString(path, output,StandardCharsets.UTF_8,StandardOpenOption.APPEND);

        //output = "\n]";

    }

}






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
