package application;

import entity.Entity;

import java.util.ArrayList;
import java.util.List;

public record CollisionChecker(GamePanel gp) {

    public List<Entity> getEntitiesAtNextTile(Entity entity, GamePanel.Direction dir) {
        int nextX = entity.worldX;
        int nextY = entity.worldY;

        // Find next X/Y using direction
        switch (dir) {
            case UP -> nextY -= gp.tileSize;
            case DOWN -> nextY += gp.tileSize;
            case LEFT -> nextX -= gp.tileSize;
            case RIGHT -> nextX += gp.tileSize;
        }

        // Get all entities at the next tile
        List<Entity> result = new ArrayList<>();
        for (Entity[] group : new Entity[][] { gp.words[gp.currentMap], gp.chr[gp.currentMap], gp.obj[gp.currentMap]}) {
            for (Entity ent : group) {
                if (ent != null && ent.worldX == nextX && ent.worldY == nextY) {
                    result.add(ent);
                }
            }
        }

        return result;
    }

    public boolean tileBlocked(Entity entity, GamePanel.Direction dir) {
        int nextX = entity.worldX;
        int nextY = entity.worldY;

        // Find next tile using direction and X/Y
        switch (dir) {
            case UP -> nextY -= gp.tileSize;
            case DOWN -> nextY += gp.tileSize;
            case LEFT -> nextX -= gp.tileSize;
            case RIGHT -> nextX += gp.tileSize;
        }

        // Tile out of bounds
        if (gp.cChecker.isOutOfBounds(nextX, nextY)) {
            return true;
        }

        // Retrieve tile object at index
        int tile = gp.tileM.mapTileNum[gp.currentMap][nextX / gp.tileSize][nextY / gp.tileSize];

        // True if tile has collision
        return gp.tileM.tiles[tile].hasCollision;
    }

    /**
     * CHECK ENTITY
     * Detects if given entity will collide with any entity from the given list
     * @param entity Entity to check collision on
     * @param targets List of entities to check collision against
     * @return Entity the given entity will interact with, -1 if none
     */
    public int checkEntity(Entity entity, Entity[][] targets) {

        int index = -1;
        for (int i = 0; i < targets[1].length; i++) {

            if (targets[gp.currentMap][i] != null) {

                entity.hitbox.x = entity.worldX;
                entity.hitbox.y = entity.worldY;

                targets[gp.currentMap][i].hitbox.x = targets[gp.currentMap][i].worldX;
                targets[gp.currentMap][i].hitbox.y = targets[gp.currentMap][i].worldY;

                // Shift hitbox to find next tile based on direction
                switch (entity.direction) {
                    case UP -> entity.hitbox.y -= gp.tileSize;
                    case DOWN -> entity.hitbox.y += gp.tileSize;
                    case LEFT -> entity.hitbox.x -= gp.tileSize;
                    case RIGHT -> entity.hitbox.x += gp.tileSize;
                }

                // Entity and target collides
                if (entity.hitbox.intersects(targets[gp.currentMap][i].hitbox)) {
                    if (targets[gp.currentMap][i] != entity) {
                        index = i;
                        if (targets[gp.currentMap][i].properties.contains(Entity.Property.STOP) || targets[gp.currentMap][i].collisionOn) {
                            entity.collisionOn = true;
                        }
                    }
                }

                if (isOutOfBounds(entity.hitbox.x, entity.hitbox.y)) {
                    entity.collisionOn = true;
                }

                // Reset entity solid area
                entity.hitbox.x = 0;
                entity.hitbox.y = 0;

                // Reset target solid area
                targets[gp.currentMap][i].hitbox.x = 0;
                targets[gp.currentMap][i].hitbox.y = 0;
            }
        }

        return index;
    }

    /**
     * IS OUT-OF-BOUNDS
     * Checks if the given entity's hitbox is out of world boundary
     * @param x X coordinate of entity
     * @param y Y coordinate of entity
     * @return True if entity is out of bounds
     */
    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || x > gp.screenWidth - gp.tileSize ||
                y < 0 || y > gp.screenHeight - gp.tileSize;
    }

}