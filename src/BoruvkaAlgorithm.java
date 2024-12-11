import java.util.*;

public class BoruvkaAlgorithm {

    public static ArrayList<Edge> getTreeWithBoruvka(Graph graph) {
        ArrayList<Edge> partialTreeEdges = new ArrayList<>();

        DisjointSet disjointSet = new DisjointSet();
        disjointSet.makeSet(graph.nodes);

        int components = graph.nodes.size();

        while (components > 1) {
            HashMap<Node, Edge> cheapest = new HashMap<>();

            for (Edge edge : graph.edges) {
                Node root1 = disjointSet.find(edge.startNode);
                Node root2 = disjointSet.find(edge.endNode);

                if (!root1.equals(root2)) {
                    if (!cheapest.containsKey(root1) || edge.cost < cheapest.get(root1).cost) {
                        cheapest.put(root1, edge);
                    }
                    if (!cheapest.containsKey(root2) || edge.cost < cheapest.get(root2).cost) {
                        cheapest.put(root2, edge);
                    }
                }
            }

            for (Edge edge : cheapest.values()) {
                Node root1 = disjointSet.find(edge.startNode);
                Node root2 = disjointSet.find(edge.endNode);

                if (!root1.equals(root2)) {
                    partialTreeEdges.add(edge);
                    disjointSet.union(root1, root2);
                    components--;
                }
            }
        }

        return partialTreeEdges;
    }
}