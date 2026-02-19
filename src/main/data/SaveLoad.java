package data;

import application.GamePanel;
import entity.Entity;

import java.util.ArrayDeque;
import java.util.Deque;

public class SaveLoad {

    private final GamePanel gp;

    // Max number of redo-s allowed
    private final static int MAX_UNDO = 25;

    // Stack to hold Object of entity states
    private final Deque<UndoFrame> undoStack = new ArrayDeque<>();

    /**
     * CONSTRUCTOR
     * @param pg GamePanel object
     */
    public SaveLoad(GamePanel pg) {
        this.gp = pg;
    }

    /**
     * SAVE STATE
     * Saves entity states to each set of entities
     * Called by GamePanel when canSave is TRUE
     */
    public void saveState() {
        State[] wordStateStack = saveEntityStates(gp.words[gp.currentMap]);
        State[] objStateStack = saveEntityStates(gp.obj[gp.currentMap]);
        State[] chrStateStack = saveEntityStates(gp.chr[gp.currentMap]);

        undoStack.push(new UndoFrame(wordStateStack, objStateStack, chrStateStack));

        while (undoStack.size() > MAX_UNDO) {
            undoStack.removeLast();
        }
    }

    /**
     * SAVE ENTITY STATES
     * Iterates over the given entity array and saves the creates an
     *  array list inside the given stack
     * Called by saveState()
     * @param entities List of entities to save states from
     * @return Array of entity states
     */
    private State[] saveEntityStates(Entity[] entities) {

        // Array same size as GamePanel entity list
        State[] eStates = new State[entities.length];

        for (int i = 0; i < entities.length; i++) {

            // Found entity
            if (entities[i] != null) {

                // For each entity, create a new state at same index to hold current state
                eStates[i] = new State(
                        entities[i].name,
                        entities[i].worldX,
                        entities[i].worldY,
                        entities[i].direction
                );
            }
        }

        return eStates;
    }

    /**
     * LOAD STATE
     * Loads entity states from the stacks and assigns
     *  the loaded values to each entity in each entity list
     * Called by GamePanel when B is pressed
     */
    public void loadState() {

        if (undoStack.isEmpty()) return;

        UndoFrame frame = undoStack.pop();

        loadEntityStates(frame.words(), gp.words[gp.currentMap]);
        loadEntityStates(frame.obj(), gp.obj[gp.currentMap]);
        loadEntityStates(frame.chr(), gp.chr[gp.currentMap]);
    }

    /**
     * LOAD ENTITY STATES
     * Iterates over the given stack and loads the
     *  data inside to the entities found in the given list
     * Called by loadState()
     * @param saved The stack to load the entity data from
     * @param entities List of entities to write the data into
     */
    private void loadEntityStates(State[] saved, Entity[] entities) {

        // Iterate over each entity in given list
        for (int i = 0; i < entities.length; i++) {

            // No data, entity is null, go to next
            if (saved[i] == null) {
                entities[i] = null;
                continue;
            }

            // Has data but entity is already null
            if (entities[i] == null) {
                // Resurrect entity using saved state
                entities[i] = gp.eGenerator.getEntity(saved[i].name);
            }

            // Entity data found or successful resurrection
            if (entities[i] != null) {

                // Assign values to entity
                entities[i].worldX = saved[i].point.x;
                entities[i].worldY = saved[i].point.y;
                entities[i].direction = saved[i].direction;
            }
        }
    }
}
