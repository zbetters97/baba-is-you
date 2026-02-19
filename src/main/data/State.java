package data;

import application.GamePanel;

import java.awt.*;

public class State {
    public Point point;
    public GamePanel.Direction direction;

    public State(int x, int y, GamePanel.Direction direction) {
        this.point = new Point(x, y);
        this.direction = direction;
    }
}
