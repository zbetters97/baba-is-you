package entity.word;

import application.GamePanel;
import entity.WordEntity;

public class WORD_Rock extends WordEntity {

    public static final String wordName = "ROCK";

    public WORD_Rock(GamePanel gp, int col, int row) {
        super(gp, col, row, wordName);
    }
}