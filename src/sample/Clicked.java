package sample;

import javafx.scene.paint.Color;

public class Clicked {
    private boolean clicked = false;
    private Color color;

    public Clicked() {
    }

    public Clicked(boolean clicked, Color color) {
        this.clicked = clicked;
        this.color = color;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
