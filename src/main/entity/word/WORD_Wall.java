package entity.word;

import application.GamePanel;
import entity.WordEntity;

public class WORD_Wall extends WordEntity {

    public static final String wordName = "WALL";

    public WORD_Wall(GamePanel gp, int col, int row) {
        super(gp, col, row, wordName);
    }
}