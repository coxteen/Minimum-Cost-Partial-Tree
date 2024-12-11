import java.util.ArrayList;

public class Menu {

    public int menuRightLimit = 160;

    private final int bX = 30;
    private final int bY = 40;
    private final int lX = 60;
    private final int lY = 43;

    private final RadioButton isOrientedButton = new RadioButton(
            bX, bY, "Orientat", lX, lY);
    private final RadioButton kruskalAlg = new RadioButton(
            bX, bY * 2, "Kruskal", lX, lY * 2);
    private final RadioButton boruvkaAlg = new RadioButton(
            bX, bY * 3, "Boruvka", lX, lY * 3);

    public RadioButton getIsOrientedButton() {
        return isOrientedButton;
    }

    public RadioButton getKruskalAlgButton() { return kruskalAlg; }

    public RadioButton getBoruvkaAlgButton() { return boruvkaAlg; }

    public ArrayList<RadioButton> getRadioButtons() {
        ArrayList<RadioButton> radioButtons = new ArrayList<>();
        radioButtons.add(isOrientedButton);
        radioButtons.add(kruskalAlg);
        radioButtons.add(boruvkaAlg);
        return radioButtons;
    }
}
