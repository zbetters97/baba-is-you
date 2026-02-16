package application;

import entity.Entity;

public record CollisionChecker(GamePanel gp) {

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

                if (isOutOfBounds(entity)) {
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
     * @param entity Entity to check collision on
     * @return True if entity is out of bounds
     */
    private boolean isOutOfBounds(Entity entity) {
        return entity.hitbox.x < 0 || entity.hitbox.x > gp.screenWidth - gp.tileSize ||
                entity.hitbox.y < 0 || entity.hitbox.y > gp.screenHeight - gp.tileSize;
    }
}