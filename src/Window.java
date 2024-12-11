import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Window extends JPanel implements MouseListener, MouseMotionListener {

    private final Graph graph = new Graph();

    Menu menu = new Menu();

    ArrayList<RadioButton> buttons = menu.getRadioButtons();

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
        Draw.draw(g2d, graph, buttons, menu);
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
        for (RadioButton button : buttons) {
            if (button.isClicked(e.getX(), e.getY())) {
                if (button == menu.getIsOrientedButton()) {
                    graph.switchGraphType(graph);
                    button.switchButtonState();
                    return;
                }
            }
        }
        if (e.getX() <= menu.menuRightLimit + Node.radius / 2) {
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
        if (dragging && draggedNode != null && (graph.isOverlapping(draggedNode) || e.getX() <= menu.menuRightLimit + Node.radius / 2)) {
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
