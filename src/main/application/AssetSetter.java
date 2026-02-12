package application;

import entity.object.OBJ_Flag;
import entity.word.WORD_Is;

public record AssetSetter(GamePanel gp) {

    public void setup() {
        setObjects();
        setWords();
    }

    private void setObjects() {

        int mapNum = 0;
        int i = 0;

        gp.obj[mapNum][i] = new OBJ_Flag(gp, 19, 8);
    }

    private void setWords() {
        int mapNum = 0;
        int i = 0;

        gp.words[mapNum][i] = new WORD_Is(gp, 10, 5);
    }
}