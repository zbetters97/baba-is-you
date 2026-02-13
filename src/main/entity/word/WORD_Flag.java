package entity.word;

import application.GamePanel;
import entity.WordEntity;

public class WORD_Flag extends WordEntity {

    public WORD_Flag(GamePanel gp, int col, int row) {
        super(gp, col, row, "FLAG");
    }
}