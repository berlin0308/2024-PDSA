import java.util.List;
import java.util.ArrayList;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.UF;


class Budget {
    public Budget() {};
    PrimMST MST;
    //return the total costs of the bridges
    public int plan(int island, List<int[]> bridge) {

        EdgeWeightedGraph EWG = new EdgeWeightedGraph(island);
        for(int[] b: bridge){
            Edge E = new Edge(b[0], b[1], b[2]);
            EWG.addEdge(E);
        }
        MST = new PrimMST(EWG);
        return (int)MST.weight();
    }

    public double findPathDistance(int p, int q){
        return MST.findPathDistance(p, q);
    }

    public static void main(String[] args) {
        Budget solution = new Budget();
        System.out.println(solution.plan(4, new ArrayList<int[]>(){{
            add(new int[]{0,1,2});
            add(new int[]{0,2,4});
            add(new int[]{1,3,5});
            add(new int[]{2,1,1});
        }}));
        System.out.println(solution.findPathDistance(0,3));
        System.out.println(solution.findPathDistance(2,1));
        System.out.println(solution.findPathDistance(1,1));
        System.out.println(solution.findPathDistance(0,2));

        System.out.println(solution.plan(4, new ArrayList<int[]>(){{
            add(new int[]{0,1,0});
            add(new int[]{0,2,4});
            add(new int[]{0,3,4});
            add(new int[]{1,2,1});
            add(new int[]{1,3,4});
            add(new int[]{2,3,2});
        }}));
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