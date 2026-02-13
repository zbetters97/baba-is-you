package application;

import entity.object.OBJ_Flag;
import entity.object.OBJ_Wall;
import entity.word.*;

public record AssetSetter(GamePanel gp) {

    public void setup() {
        setObjects();
        setWords();
    }

    private void setObjects() {
        int mapNum = 0;
        int i = 0;

        i = setWalls(mapNum, i);

        gp.obj[mapNum][i] = new OBJ_Flag(gp, 19, 8);
    }

    private int setWalls(int mapNum, int i) {

        gp.obj[mapNum][i] = new OBJ_Wall(gp, 10, 6, GamePanel.Direction.LEFT);
        i++;

        for (int c = 11; c <= 19; c++) {
            gp.obj[mapNum][i] = new OBJ_Wall(gp, c, 6, GamePanel.Direction.DOWN);
            i++;
        }
        gp.obj[mapNum][i] = new OBJ_Wall(gp, 20, 6, GamePanel.Direction.RIGHT);
        i++;

        gp.obj[mapNum][i] = new OBJ_Wall(gp, 10, 10, GamePanel.Direction.LEFT);
        i++;
        for (int c = 11; c <= 19; c++) {
            gp.obj[mapNum][i] = new OBJ_Wall(gp, c, 10, GamePanel.Direction.DOWN);
            i++;
        }
        gp.obj[mapNum][i] = new OBJ_Wall(gp, 20, 10, GamePanel.Direction.RIGHT);
        i++;

        return i;
    }

    private void setWords() {
        int mapNum = 0;
        int i = 0;

        gp.words[mapNum][i] = new WORD_Flag(gp, 14, 4);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 15, 3);
        i++;
        gp.words[mapNum][i] = new WORD_Win(gp, 16, 4);
        i++;

        gp.words[mapNum][i] = new WORD_Wall(gp, 15, 7);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 15, 8);
        i++;
        gp.words[mapNum][i] = new WORD_Stop(gp, 15, 9);
    }
}