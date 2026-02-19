package application;

import entity.character.*;
import entity.object.*;
import entity.tile_interactive.*;
import entity.word.*;

public record AssetSetter(GamePanel gp) {

    public void setup() {

        if (gp.currentMap == 0) setupLevelOne();
        else if (gp.currentMap == 1) setupLevelTwo();
        else if (gp.currentMap == 2) setupLevelThree();
        else setupLevelFour();
    }

    private void setupLevelOne() {
        int mapNum = 0;
        int i = 0;

        gp.chr[mapNum][i] = new CHR_Baba(gp, 11, 8);

        gp.iTiles[mapNum][i] = new IT_Wall(gp, 10, 6, 0, 0);
        i++;

        for (int c = 11; c <= 19; c++) {
            gp.iTiles[mapNum][i] = new IT_Wall(gp, c, 6, 0, 1);
            i++;
        }
        gp.iTiles[mapNum][i] = new IT_Wall(gp, 20, 6, 0, 2);
        i++;

        gp.iTiles[mapNum][i] = new IT_Wall(gp, 10, 10, 0, 0);
        i++;
        for (int c = 11; c <= 19; c++) {
            gp.iTiles[mapNum][i] = new IT_Wall(gp, c, 10, 0, 1);
            i++;
        }
        gp.iTiles[mapNum][i] = new IT_Wall(gp, 20, 10, 0, 2);

        i = 0;
        gp.obj[mapNum][i] = new OBJ_Flag(gp, 19, 8);

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
    }

    private void setupLevelTwo() {
        int mapNum = 1;
        int i = 0;
        gp.chr[mapNum][i] = new CHR_Baba(gp, 15, 3);

        gp.obj[mapNum][i] = new OBJ_Rock(gp, 18, 3);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 18, 5);
        i++;
        gp.obj[mapNum][i] = new OBJ_Flag(gp, 11, 13);

        i = 0;

        gp.iTiles[mapNum][i] = new IT_Water(gp, 14, 7);
        i++;
        gp.iTiles[mapNum][i] = new IT_Water(gp, 15, 7);
        i++;
        gp.iTiles[mapNum][i] = new IT_Water(gp, 16, 7);
        i++;

        gp.iTiles[mapNum][i] = new IT_Water(gp, 11, 11);
        i++;
        gp.iTiles[mapNum][i] = new IT_Water(gp, 12, 11);
        i++;
        gp.iTiles[mapNum][i] = new IT_Water(gp, 13, 11);
        i++;
        gp.iTiles[mapNum][i] = new IT_Water(gp, 11, 12);
        i++;
        gp.iTiles[mapNum][i] = new IT_Water(gp, 12, 12);
        i++;
        gp.iTiles[mapNum][i] = new IT_Water(gp, 13, 12);
        i++;
        gp.iTiles[mapNum][i] = new IT_Water(gp, 12, 13);
        i++;
        gp.iTiles[mapNum][i] = new IT_Water(gp, 13, 13);

        i = 0;
        gp.words[mapNum][i] = new WORD_Baba(gp, 0, 0);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 0, 1);
        i++;
        gp.words[mapNum][i] = new WORD_You(gp, 0, 2);
        i++;

        gp.words[mapNum][i] = new WORD_Wall(gp, 1, 0);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 1, 1);
        i++;
        gp.words[mapNum][i] = new WORD_Stop(gp, 1, 2);
        i++;

        gp.words[mapNum][i] = new WORD_Water(gp, 12, 4);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 12, 5);
        i++;
        gp.words[mapNum][i] = new WORD_Sink(gp, 12, 6);
        i++;

        gp.words[mapNum][i] = new WORD_Rock(gp, 19, 9);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 20, 9);
        i++;
        gp.words[mapNum][i] = new WORD_Push(gp, 21, 9);
        i++;

        gp.words[mapNum][i] = new WORD_Flag(gp, 19, 12);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 20, 12);
        i++;
        gp.words[mapNum][i] = new WORD_Win(gp, 21, 12);
    }

    private void setupLevelThree() {
        int mapNum = 2;
        int i = 0;

        gp.chr[mapNum][i] = new CHR_Baba(gp, 10, 8);

        gp.obj[mapNum][i] = new OBJ_Flag(gp, 20, 8);

        gp.words[mapNum][i] = new WORD_Baba(gp, 13, 6);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 14, 6);
        i++;
        gp.words[mapNum][i] = new WORD_You(gp, 15, 6);
        i++;

        gp.words[mapNum][i] = new WORD_Flag(gp, 13, 9);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 14, 9);
        i++;
        gp.words[mapNum][i] = new WORD_Win(gp, 15, 9);
        i++;

        gp.words[mapNum][i] = new WORD_Wall(gp, 23, 7);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 23, 8);
        i++;
        gp.words[mapNum][i] = new WORD_Stop(gp, 23, 9);
    }

    private void setupLevelFour() {
        int mapNum = 3;
        int i = 0;

        gp.chr[mapNum][i] = new CHR_Baba(gp, 10, 8);

        gp.obj[mapNum][i] = new OBJ_Rock(gp, 2, 6);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 3, 6);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 4, 6);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 5, 6);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 6, 6);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 6, 5);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 6, 4);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 6, 3);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 6, 2);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 2, 5);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 2, 4);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 2, 3);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 2, 2);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 3, 2);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 4, 2);
        i++;
        gp.obj[mapNum][i] = new OBJ_Rock(gp, 5, 2);

        i = 0;
        gp.words[mapNum][i] = new WORD_Baba(gp, 10, 12);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 10, 13);
        i++;
        gp.words[mapNum][i] = new WORD_You(gp, 10, 14);
        i++;

        gp.words[mapNum][i] = new WORD_Rock(gp, 3, 3);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 4, 3);
        i++;
        gp.words[mapNum][i] = new WORD_Stop(gp, 5, 3);
        i++;

        gp.words[mapNum][i] = new WORD_Skull(gp, 3, 4);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 4, 4);
        i++;
        gp.words[mapNum][i] = new WORD_Defeat(gp, 5, 4);
        i++;

        gp.words[mapNum][i] = new WORD_Flag(gp, 3, 5);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 4, 5);
        i++;
        gp.words[mapNum][i] = new WORD_Win(gp, 5, 5);
        i++;

        gp.words[mapNum][i] = new WORD_Wall(gp, 15, 11);
        i++;
        gp.words[mapNum][i] = new WORD_Is(gp, 16, 11);
        i++;
        gp.words[mapNum][i] = new WORD_Stop(gp, 17, 11);
    }
}