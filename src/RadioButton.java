public class RadioButton {

    public int x = 30;
    public int y = 30;
    public int radius = 30;
    public int strokeWidth = 3;

    public boolean selected = false;

    public boolean isClicked(int mouseX, int mouseY) {
        return Math.abs(x - mouseX) < radius / 2 && Math.abs(y - mouseY) < radius / 2;
    }

    public void switchGraphType(Graph graph) {
        graph.isOriented = !graph.isOriented;
        selected = !selected;
    }
}
