package entity.object;

import application.GamePanel;
import entity.Entity;

public class OBJ_Wall extends Entity {

    public static final String objName = "WALL";

    public OBJ_Wall(GamePanel gp, int x, int y, GamePanel.Direction direction) {
        super(gp);

        worldX = x * gp.tileSize;
        worldY = y * gp.tileSize;

        name = objName;

        down1 = switch (direction) {
            case UP, DOWN -> setupImage("/objects/obj_wall_middle");
            case LEFT -> setupImage("/objects/obj_wall_left");
            case RIGHT -> setupImage("/objects/obj_wall_right");
        };
    }
}