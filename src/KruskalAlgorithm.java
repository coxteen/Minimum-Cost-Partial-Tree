import java.util.ArrayList;
import java.util.Comparator;

public class KruskalAlgorithm {

    public static ArrayList<Edge> getTreeWithKruskal(Graph graph) {
        ArrayList<Edge> partialTreeEdges = new ArrayList<>();
        DisjointSet ds = new DisjointSet();

        ArrayList<Edge> sortedEdges = new ArrayList<>(graph.edges);
        sortedEdges.sort(Comparator.comparingInt(e -> e.cost));

        ds.makeSet(graph.nodes);

        for (Edge edge : sortedEdges) {
            Node startRoot = ds.find(edge.startNode);
            Node endRoot = ds.find(edge.endNode);

            if (startRoot != endRoot) {
                partialTreeEdges.add(edge);
                ds.union(edge.startNode, edge.endNode);
            }
        }

        return partialTreeEdges;
    }
}
