import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

    public ArrayList<Node> nodes = new ArrayList<>();
    public ArrayList<Edge> edges = new ArrayList<>();

    public HashMap<Node, ArrayList<Node>> adjacentList = new HashMap<>();
    public HashMap<Node, ArrayList<Node>> orientedAdjacentList = new HashMap<>();

    public Node selectedNode = null;

    public boolean isOriented = false;

    public Boolean isOverlapping(Node node) {
        for (Node list_node : nodes) {
            if(node != list_node &&
                    Math.abs(list_node.x - node.x) <= Node.radius &&
                    Math.abs(list_node.y - node.y) <= Node.radius) {
                return true;
            }
        }
        return false;
    }

    public void addNode(int x, int y) {
        Node node = new Node(x, y, nodes.size());
        if(!isOverlapping(node)) {
            nodes.add(node);
        }
    }

    private void removeEdges(Node deletedNode) {
        ArrayList<Edge> edgesToRemove = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.startNode == deletedNode || edge.endNode == deletedNode) {
                edgesToRemove.add(edge);
            }
        }
        for (Edge edge : edgesToRemove) {
            edges.remove(edge);
        }
    }

    private void removeNode(Node nodeToDelete) {
        nodes.remove(nodeToDelete);
        adjacentList.remove(nodeToDelete);
        orientedAdjacentList.remove(nodeToDelete);
        for (Node node : nodes) {
            if (adjacentList.get(node) != null) {
                adjacentList.get(node).remove(nodeToDelete);
            }
            if (orientedAdjacentList.get(node) != null) {
                orientedAdjacentList.get(node).remove(nodeToDelete);
            }
        }
    }

    private void correctNodeValues() {
        for (Node node : nodes) {
            node.value = nodes.indexOf(node);
        }
    }

    public void deleteNode(Node nodeToDelete) {
        removeEdges(nodeToDelete);
        removeNode(nodeToDelete);
        correctNodeValues();
    }

    private void addInOrientedAdjacentList(Node start, Node end) {
        if (!orientedAdjacentList.containsKey(start)) {
            orientedAdjacentList.put(start, new ArrayList<>());
        }
        orientedAdjacentList.get(start).add(end);
    }

    private void addInAdjacentList(Node start, Node end) {
        if (!adjacentList.containsKey(start)) {
            adjacentList.put(start, new ArrayList<>());
        }
        adjacentList.get(start).add(end);
    }

    public void addEdge(Node start, Node end, Integer cost) {
        if(start == end) {
            return;
        }
        for(Edge edge : edges) {
            if ((edge.startNode == start && edge.endNode == end) ||
                    (isOriented && edge.startNode == end && edge.endNode == start)) {
                return;
            }
        }
        Edge edge = new Edge(start, end, cost);
        edges.add(edge);
        addInOrientedAdjacentList(edge.startNode, edge.endNode);
        addInAdjacentList(edge.startNode, edge.endNode);
        addInAdjacentList(edge.endNode, edge.startNode);
    }

    public void switchGraphType(Graph graph) {
        graph.isOriented = !graph.isOriented;
    }
}
