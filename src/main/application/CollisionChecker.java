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

        for (int i = 0; i < targets[0].length; i++) {

            if (targets[0][i] != null) {

                entity.hitbox.x = entity.worldX;
                entity.hitbox.y = entity.worldY;

                targets[0][i].hitbox.x = targets[0][i].worldX;
                targets[0][i].hitbox.y = targets[0][i].worldY;

                // Shift hitbox to find next tile based on direction
                switch (entity.direction) {
                    case UP -> entity.hitbox.y -= gp.tileSize;
                    case DOWN -> entity.hitbox.y += gp.tileSize;
                    case LEFT -> entity.hitbox.x -= gp.tileSize;
                    case RIGHT -> entity.hitbox.x += gp.tileSize;
                }

                // Entity and target collides
                if (entity.hitbox.intersects(targets[0][i].hitbox)) {
                    if (targets[0][i] != entity) {
                        index = i;
                        if (targets[0][i].collisionOn || targets[0][i].properties.contains(Entity.Property.STOP)) {
                            entity.collisionOn = true;
                        }
                    }
                }

                // Reset entity solid area
                entity.hitbox.x = 0;
                entity.hitbox.y = 0;

                // Reset target solid area
                targets[0][i].hitbox.x = 0;
                targets[0][i].hitbox.y = 0;
            }
        }

        return index;
    }
}