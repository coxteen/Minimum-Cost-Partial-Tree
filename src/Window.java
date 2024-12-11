import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Window extends JPanel implements MouseListener, MouseMotionListener {

    private final RadioButton button = new RadioButton();

    private final Graph graph = new Graph();

    private boolean dragging = false;
    private Node draggedNode = null;
    private int dragOffsetX, dragOffsetY;
    private int initialPositionX, initialPositionY;

    public Window() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Draw.draw(g2d, graph, button);
    }

    private int insertCost() {
        String cost = JOptionPane.showInputDialog(
                null,
                "Enter the cost : ",
                "Input box",
                JOptionPane.PLAIN_MESSAGE
        );
        return Integer.parseInt(cost);
    }

    private void leftClickAction(MouseEvent e){
        if (button.isClicked(e.getX(), e.getY())) {
            button.switchGraphType(graph);
            graph.printAdjacentLists();
            graph.printCosts();
            return;
        }
        for (Node node : graph.nodes) {
            if (node.isClicked(e.getX(), e.getY())) {
                if (graph.selectedNode == null) {
                    graph.selectedNode = node;
                } else if (node != graph.selectedNode) {
                    graph.addEdge(graph.selectedNode, node, insertCost());
                    graph.selectedNode = null;
                } else {
                    graph.selectedNode = null;
                }
                return;
            }
        }
        graph.addNode(e.getX(), e.getY());
        graph.selectedNode = null;
    }

    private void rightClickAction(MouseEvent e){
        for (Node node : graph.nodes) {
            if (node.isClicked(e.getX(), e.getY())) {
                graph.deleteNode(node);
                return;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClickAction(e);
        }
        else if (e.getButton() == MouseEvent.BUTTON3) {
            rightClickAction(e);
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (Node node : graph.nodes) {
            if (node.isClicked(e.getX(), e.getY())) {
                dragging = true;
                draggedNode = node;
                dragOffsetX = e.getX() - node.x;
                dragOffsetY = e.getY() - node.y;

                initialPositionX = node.x;
                initialPositionY = node.y;
                return;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (dragging && draggedNode != null && graph.isOverlapping(draggedNode)) {
            draggedNode.x = initialPositionX;
            draggedNode.y = initialPositionY;
        }
        dragging = false;
        draggedNode = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragging && draggedNode != null) {
            draggedNode.x = e.getX() - dragOffsetX;
            draggedNode.y = e.getY() - dragOffsetY;
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
}
