package entity.word;

import application.GamePanel;
import entity.WordEntity;

public class WORD_Wall extends WordEntity {

    public WORD_Wall(GamePanel gp, int col, int row) {
        super(gp, col, row, "WALL");
    }
}