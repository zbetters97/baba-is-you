package entity.object;

import application.GamePanel;
import entity.Entity;

public class OBJ_Skull extends Entity {

    public static final String objName = "SKULL";

    public OBJ_Skull(GamePanel gp, int x, int y) {
        super(gp);

        worldX = x * gp.tileSize;
        worldY = y * gp.tileSize;

        name = objName;

        up1 = down1 = left1 = right1 = setupImage("/objects/obj_" + objName.toLowerCase());
    }
}