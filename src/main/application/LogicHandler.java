package application;

import entity.Entity;

import java.util.Arrays;
import java.util.Map;

public record LogicHandler(GamePanel gp) {

    // Map words to a property
    private static final Map<String, Entity.Property> PROPERTY_MAP = Map.of(
            "WIN", Entity.Property.WIN,
            "STOP", Entity.Property.STOP,
            "PUSH", Entity.Property.PUSH,
            "YOU", Entity.Property.YOU
    );

    /**
     * UPDATE
     * Runs the methods in LogicHandler
     * Called by GamePanel
     */
    public void update() {
        clearProperties();
        scanColumnRules();
        scanRowRules();
    }

    /**
     * CLEAR PROPERTIES
     * Resets all rules currently in place
     * Called by update();
     */
    private void clearProperties() {
        for (Entity e : gp.obj[0]) {
            if (e != null) {
                e.properties.clear();
            }
        }

        for (Entity c : gp.chr[0]) {
            if (c != null) {
                c.properties.clear();
            }
        }
    }

    /**
     * SCAN COLUMN RULES
     * Scans each column to find valid rules
     * Called by update()
     */
    private void scanColumnRules() {

        // Loop over each column (horizontally)
        for (int col = 0; col < gp.maxWorldCol; col++) {

            // Create new array per column, fill with blanks
            String[] colWords = new String[gp.maxWorldRow];
            Arrays.fill(colWords, "");

            // Loop over all pre-existing words
            for (Entity word : gp.words[0]) {
                if (word != null) {
                    int x = word.worldX / gp.tileSize;
                    int y = word.worldY / gp.tileSize;

                    // Word's X matches column, add to corresponding Y (row) in list
                    if (x == col) {
                        colWords[y] = word.name;
                    }
                }
            }

            // Check if any rules are active
            checkRules(colWords);
        }
    }

    /**
     * SCAN ROW RULES
     * Scans each row to find valid rules
     * Called by update()
     */
    private void scanRowRules() {

        // Loop over each row (vertically)
        for (int row = 0; row < gp.maxWorldRow; row++) {

            // Create new array per row, fill with blanks
            String[] rowWords = new String[gp.maxWorldCol];
            Arrays.fill(rowWords, "");

            // Loop over all pre-existing words
            for (Entity word : gp.words[0]) {
                if (word != null) {
                    int x = word.worldX / gp.tileSize;
                    int y = word.worldY / gp.tileSize;

                    // Word is on same row, add to corresponding X (column) in list
                    if (y == row) {
                        rowWords[x] = word.name;
                    }
                }
            }

            // Check if any rules are active
            checkRules(rowWords);
        }
    }

    /**
     * PARSE RULES
     * Parses through given array string and assigns properties where applicable
     * @param words Array of words to parse through
     */
    private void checkRules(String[] words) {

        // Stop at (length - 2) to count 3 consecutive words (0, 1, 2)
        for (int i = 0; i < words.length - 2; i++) {

            // Object receiving the property (ex: FLAG)
            String subject = words[i];

            // Linking verb (ex: IS)
            String verb = words[i + 1];

            // The action applied to the object (ex: WIN)
            String predicate = words[i + 2];

            // Does not equal a rule
            if (subject.isEmpty() || predicate.isEmpty()) continue;
            if (!verb.equals("IS")) continue;

            // Matching property to the predicate
            Entity.Property property = PROPERTY_MAP.get(predicate);
            if (property != null) {
                applyPropertyRule(subject, property);
                continue;
            }

            if (gp.eGenerator.getEntity(predicate) != null) {
                applyTransformationRule(subject, predicate);
            }
        }
    }

    /**
     * APPLY RULE
     * Finds the objects that have the name
     * And assigns the given property to that object
     * @param objectName The name of the object to pass the property to
     * @param property The property the object will be receiving
     */
    private void applyPropertyRule(String objectName, Entity.Property property) {
        gp.obj[0] = addProperty(gp.obj[0], objectName, property);
        gp.chr[0] = addProperty(gp.chr[0], objectName, property);
    }

    private Entity[] addProperty(Entity[] entityList, String entityName, Entity.Property property) {
        // For each entity in the list of entities
        for (Entity e : entityList) {

            // If entity's name matches passed name, provide property
            if (e != null && e.name.equals(entityName)) {
                e.properties.add(property);
            }
        }

        return entityList;
    }

    private void applyTransformationRule(String oldEntityName, String newEntityName) {
       gp.obj[0] = transformEntity(gp.obj[0], oldEntityName, newEntityName);
       gp.chr[0] = transformEntity(gp.chr[0], oldEntityName, newEntityName);
    }

    private Entity[] transformEntity(Entity[] entityList, String oldEntityName, String newEntityName) {
        for (int i = 0; i < entityList.length; i++) {

            // If entity's name matches passed name, convert to clone
            if (entityList[i] != null && entityList[i].name.equals(oldEntityName)) {
                Entity clone = gp.eGenerator.getEntity(newEntityName);
                assert clone != null;

                clone.worldX = entityList[i].worldX;
                clone.worldY = entityList[i].worldY;
                entityList[i] = clone;
            }
        }

        return entityList;
    }
}
