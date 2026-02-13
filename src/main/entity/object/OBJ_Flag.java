package entity.object;

import application.GamePanel;
import entity.Entity;

import java.awt.*;

public class OBJ_Flag extends Entity {

    public static final String objName = "FLAG";

    public OBJ_Flag(GamePanel gp, int x, int y) {
        super(gp);

        worldX = x * gp.tileSize;
        worldY = y * gp.tileSize;

        name = objName;
        collisionOn = false;

        down1 = setupImage("/objects/obj_flag");
    }
}