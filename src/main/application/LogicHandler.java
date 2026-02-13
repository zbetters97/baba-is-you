package application;

import entity.Entity;

import java.util.Arrays;
import java.util.Map;

public record LogicHandler(GamePanel gp) {

    private static final Map<String, Entity.Property> WORD_TO_PROPERTY = Map.of(
            "WIN", Entity.Property.WIN,
            "STOP", Entity.Property.STOP,
            "PUSH", Entity.Property.PUSH
    );

    public void update() {
        clearProperties();
        scanForRules();
    }

    private void clearProperties() {
        for (Entity e : gp.obj[0]) {
            if (e != null) {
                e.properties.clear();
            }
        }
    }

    private void scanForRules() {
        for (int row = 0; row < gp.maxWorldRow; row++) {

            String[] rowWords = new String[gp.maxWorldCol];
            Arrays.fill(rowWords, "");

            for (Entity word : gp.words[0]) {
                if (word != null) {
                    int c = word.worldX / gp.tileSize;
                    int r = word.worldY / gp.tileSize;

                    if (r == row) {
                        rowWords[c] = word.name;
                    }
                }
            }

            parseRules(rowWords);
        }

        for (int col = 0; col < gp.maxWorldCol; col++) {

            String[] colWords = new String[gp.maxWorldRow];
            Arrays.fill(colWords, "");

            for (Entity word : gp.words[0]) {
                if (word != null) {
                    int c = word.worldX / gp.tileSize;
                    int r = word.worldY / gp.tileSize;

                    if (c == col) {
                        colWords[r] = word.name;
                    }
                }
            }

            parseRules(colWords);
        }
    }

    private void parseRules(String[] words) {

        for (int i = 0; i < words.length - 2; i++) {
            String subject = words[i];
            String verb = words[i + 1];
            String object = words[i + 2];

            if ("IS".equals(verb) && WORD_TO_PROPERTY.containsKey(object)) {
                Entity.Property property = WORD_TO_PROPERTY.get(object);
                applyRule(subject, property);
            }
        }
    }

    private void applyRule(String subject, Entity.Property property) {
        for (Entity e : gp.obj[0]) {
            if (e != null && e.name.equals(subject)) {
                e.properties.add(property);
            }
        }
    }
}
