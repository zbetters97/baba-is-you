package data;

import application.GamePanel;
import entity.Entity;

import java.util.ArrayList;

public class SaveLoad {

    private final GamePanel gp;

    private final ArrayList<State[]> chrStateHistory = new ArrayList<>();
    private final ArrayList<State[]> wordStateHistory = new ArrayList<>();
    private final ArrayList<State[]> objStateHistory = new ArrayList<>();

    public SaveLoad(GamePanel pg) {
        this.gp = pg;
    }

    public void saveState() {
        saveEntityStates(chrStateHistory, gp.chr[gp.currentMap]);
        saveEntityStates(wordStateHistory, gp.words[gp.currentMap]);
        saveEntityStates(objStateHistory, gp.obj[gp.currentMap]);
    }
    private void saveEntityStates(ArrayList<State[]> eStateHistory, Entity[] entities) {
        State[] eStates = new State[50];
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] != null) {
                eStates[i] = new State(entities[i].name, entities[i].worldX, entities[i].worldY, entities[i].direction);
            }
        }

        eStateHistory.add(eStates);
    }

    public void loadState() {
        loadEntityStates(chrStateHistory, gp.chr[gp.currentMap]);
        loadEntityStates(wordStateHistory, gp.words[gp.currentMap]);
        loadEntityStates(objStateHistory, gp.obj[gp.currentMap]);
    }
    private void loadEntityStates(ArrayList<State[]> eStateHistory, Entity[] entities) {

        if (eStateHistory.isEmpty()) return;

        for (int i = 0; i < entities.length; i++) {
            if (entities[i] != null) {
                entities[i].worldX = eStateHistory.getLast()[i].point.x;
                entities[i].worldY = eStateHistory.getLast()[i].point.y;
                entities[i].direction = eStateHistory.getLast()[i].direction;
            }
        }

        eStateHistory.removeLast();
    }
}
