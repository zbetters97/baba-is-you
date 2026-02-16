package entity.object;

import application.GamePanel;
import entity.Entity;

public class OBJ_Rock extends Entity {

    public static final String objName = "ROCK";

    public OBJ_Rock(GamePanel gp, int x, int y) {
        super(gp);

        worldX = x * gp.tileSize;
        worldY = y * gp.tileSize;

        name = objName;

        up1 = down1 = left1 = right1 = setupImage("/objects/obj_rock");
    }
}