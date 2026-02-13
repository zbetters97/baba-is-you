package application;

import entity.Entity;

import java.util.Arrays;
import java.util.Map;

public record LogicHandler(GamePanel gp) {

    // Map words to a property
    private static final Map<String, Entity.Property> PROPERTY_MAP = Map.of(
            "WIN", Entity.Property.WIN,
            "STOP", Entity.Property.STOP,
            "PUSH", Entity.Property.PUSH
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

            // Valid linking verb and matching property to the predicate
            if (verb.equals("IS") && PROPERTY_MAP.containsKey(predicate)) {

                // Retrieve matching property
                Entity.Property property = PROPERTY_MAP.get(predicate);

                // Apply the rule
                applyRule(subject, property);
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
    private void applyRule(String objectName, Entity.Property property) {

        // For each object in the list of objects
        for (Entity e : gp.obj[0]) {

            // If object's name matches passed name, provide property
            if (e != null && e.name.equals(objectName)) {
                e.properties.add(property);
            }
        }
    }
}
