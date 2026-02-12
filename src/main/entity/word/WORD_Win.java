package entity.word;

import application.GamePanel;
import entity.Entity;

public class WORD_Win extends Entity {

    public static final String wordName = "WIN";

    public WORD_Win(GamePanel gp, int x, int y) {
        super(gp);

        worldX = x * gp.tileSize;
        worldY = y * gp.tileSize;

        name = wordName;
        collisionOn = true;

        up1 = down1 = left1 = right1 = setupImage("/words/word_win");
    }
}