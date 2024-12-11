import java.awt.Color;

public class Edge {

    public Node startNode, endNode;
    public Color edgeColor = Color.BLACK;

    public int cost;
    public int costFontSize = 12;
    public String costFont = "Arial";

    public int rectWidth = 30;
    public int rectHeight = 20;

    public int rectRadius = 3;

    public final int LINE_WIDTH = 3;
    public final int ARROW_LENGTH = 15;

    public Edge(Node start, Node end, int cost) {
        this.startNode = start;
        this.endNode = end;
        this.cost = cost;
    }
}
