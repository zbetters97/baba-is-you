package data;

import application.GamePanel;
import entity.Entity;
import entity.character.CHR_Baba;
import entity.object.OBJ_Flag;
import entity.object.OBJ_Wall;

public record EntityGenerator(GamePanel gp) {

    public Entity getEntity(String objName) {
        return switch (objName) {
            case CHR_Baba.chrName -> new CHR_Baba(gp, 0, 0);
            case OBJ_Flag.objName -> new OBJ_Flag(gp, 0, 0);
            case OBJ_Wall.objName -> new OBJ_Wall(gp, 0, 0, 0, 0);
            default -> null;
        };
    }
}