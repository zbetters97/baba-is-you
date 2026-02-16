package application;

import entity.character.CHR_Baba;
import entity.object.OBJ_Flag;
import entity.object.OBJ_Rock;
import entity.object.OBJ_Wall;
import entity.object.OBJ_Water;
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

        mapNum = 1;
        i = 0;

        gp.chr[mapNum][i] = new CHR_Baba(gp, 15, 5);
    }

    private void setObjects() {
        int mapNum = 0;
        int i = 0;

        i = setWalls(mapNum, i);

        gp.obj[mapNum][i] = new OBJ_Flag(gp, 19, 8);

        mapNum = 1;
        i = 0;

        gp.obj[mapNum][i] = new OBJ_Rock(gp, 18, 5);
        i++;

        gp.obj[mapNum][i] = new OBJ_Water(gp, 14, 7);
        i++;
        gp.obj[mapNum][i] = new OBJ_Water(gp, 15, 7);
        i++;
        gp.obj[mapNum][i] = new OBJ_Water(gp, 16, 7);
    }

    private int setWalls(int mapNum, int i) {

        gp.obj[mapNum][i] = new OBJ_Wall(gp, 10, 6, 0, 0);
        i++;

        for (int c = 11; c <= 19; c++) {
            gp.obj[mapNum][i] = new OBJ_Wall(gp, c, 6, 0, 1);
            i++;
        }
        gp.obj[mapNum][i] = new OBJ_Wall(gp, 20, 6, 0, 2);
        i++;

        gp.obj[mapNum][i] = new OBJ_Wall(gp, 10, 10, 0, 0);
        i++;
        for (int c = 11; c <= 19; c++) {
            gp.obj[mapNum][i] = new OBJ_Wall(gp, c, 10, 0, 1);
            i++;
        }
        gp.obj[mapNum][i] = new OBJ_Wall(gp, 20, 10, 0, 2);
        i++;

        return i;
    }

    private void setWords() {
        int mapNum = 0;
        int i = 0;

        gp.words[mapNum][i] = new WORD_Baba(gp, 4, 7);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 4, 8);
        i++;
        gp.words[mapNum][i] = new WORD_You(gp, 4, 9);
        i++;

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
        gp.words[mapNum][i] = new WORD_Push(gp, 15, 9);

        mapNum = 1;
        i = 0;

        gp.words[mapNum][i] = new WORD_Baba(gp, 4, 7);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 4, 8);
        i++;
        gp.words[mapNum][i] = new WORD_You(gp, 4, 9);
    }
}