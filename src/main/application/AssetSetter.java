package application;

import entity.character.CHR_Baba;
import entity.object.OBJ_Flag;
import entity.object.OBJ_Wall;
import entity.word.*;

public record AssetSetter(GamePanel gp) {

    public void setup() {
        setCharacters();
        setObjects();
        setWords();
    }

    private void setCharacters() {
        int mapNum = 0;
        int i = 0;

        gp.chr[mapNum][i] = new CHR_Baba(gp, 11, 8);
    }

    private void setObjects() {
        int mapNum = 0;
        int i = 0;

        i = setWalls(mapNum, i);

        gp.obj[mapNum][i] = new OBJ_Flag(gp, 19, 8);
    }

    private int setWalls(int mapNum, int i) {

        gp.obj[mapNum][i] = new OBJ_Wall(gp, 10, 6, 0);
        i++;

        for (int c = 11; c <= 19; c++) {
            gp.obj[mapNum][i] = new OBJ_Wall(gp, c, 6, 1);
            i++;
        }
        gp.obj[mapNum][i] = new OBJ_Wall(gp, 20, 6, 2);
        i++;

        gp.obj[mapNum][i] = new OBJ_Wall(gp, 10, 10, 0);
        i++;
        for (int c = 11; c <= 19; c++) {
            gp.obj[mapNum][i] = new OBJ_Wall(gp, c, 10, 1);
            i++;
        }
        gp.obj[mapNum][i] = new OBJ_Wall(gp, 20, 10, 2);
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
        i++;

        gp.words[mapNum][i] = new WORD_Baba(gp, 4, 7);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 4, 8);
        i++;
        gp.words[mapNum][i] = new WORD_You(gp, 4, 9);

    }
}