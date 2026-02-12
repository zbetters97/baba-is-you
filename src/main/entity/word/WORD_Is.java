package entity.word;

import application.GamePanel;
import entity.Entity;

public class WORD_Is extends Entity {

    public static final String wordName = "IS";

    public WORD_Is(GamePanel gp, int x, int y) {
        super(gp);

        worldX = x * gp.tileSize;
        worldY = y * gp.tileSize;

        name = wordName;
        collisionOn = true;

        down1 = setupImage("/words/word_is");
    }
}