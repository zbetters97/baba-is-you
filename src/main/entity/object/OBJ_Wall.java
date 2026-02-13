package entity.object;

import application.GamePanel;
import entity.Entity;

public class OBJ_Wall extends Entity {

    public static final String objName = "WALL";

    public OBJ_Wall(GamePanel gp, int x, int y, int side) {
        super(gp);

        worldX = x * gp.tileSize;
        worldY = y * gp.tileSize;

        name = objName;
        baseName = name;

        String imagePath;
        if (side == 0) {
            imagePath = "left";
        } else if (side == 1) {
            imagePath = "middle";
        } else {
            imagePath = "right";
        }

        up1 = down1 = left1 = right1 = setupImage("/objects/obj_wall_" + imagePath);
    }
}