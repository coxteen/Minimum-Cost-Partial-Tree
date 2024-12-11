import java.util.ArrayList;
import java.util.HashMap;

public class DisjointSet {
    private final HashMap<Node, Node> parent = new HashMap<>();
    private final HashMap<Node, Integer> rank = new HashMap<>();

    public void makeSet(ArrayList<Node> nodes) {
        for (Node node : nodes) {
            parent.put(node, node);
            rank.put(node, 0);
        }
    }

    public Node find(Node node) {
        Node root = node;
        while (parent.get(root) != root) {
            root = parent.get(root);
        }
        Node current = node;
        while (current != root) {
            Node parentNode = parent.get(current);
            parent.put(current, root);
            current = parentNode;
        }
        return root;
    }


    public void union(Node node1, Node node2) {
        Node root1 = find(node1);
        Node root2 = find(node2);

        if (root1 != root2) {
            int rank1 = rank.get(root1);
            int rank2 = rank.get(root2);
            if (rank1 > rank2) {
                parent.put(root2, root1);
            } else if (rank1 < rank2) {
                parent.put(root1, root2);
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank1 + 1);
            }
        }
    }
}