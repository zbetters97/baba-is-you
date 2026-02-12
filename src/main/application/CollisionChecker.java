package application;

import entity.Entity;

public record CollisionChecker(GamePanel gp) {

    /**
     * CHECK TILE
     * Checks if the given entity will collide with a tile
     * @param entity Entity to check collision for
     */
    public void checkTile(Entity entity) {

        int x;
        int y;
        int tile = 0;

        // Find tile entity will interact with
        switch (entity.direction) {
            case UP -> {

                // Tile above entity
                x = entity.worldX / gp.tileSize;
                y = (entity.worldY - gp.tileSize) / gp.tileSize;
                tile = gp.tileM.mapTileNum[0][x][y];
            }
            case DOWN -> {

                // Tile below entity
                x = entity.worldX / gp.tileSize;
                y = (entity.worldY + gp.tileSize) / gp.tileSize;
                tile = gp.tileM.mapTileNum[0][x][y];
            }
            case LEFT -> {

                // Tile left of entity
                x = (entity.worldX - gp.tileSize) / gp.tileSize;
                y = entity.worldY / gp.tileSize;
                tile = gp.tileM.mapTileNum[0][x][y];
            }
            case RIGHT -> {

                // Tile right of entity
                x = (entity.worldX + gp.tileSize) / gp.tileSize;
                y = entity.worldY / gp.tileSize;
                tile = gp.tileM.mapTileNum[0][x][y];
            }
        }

        if (gp.tileM.tiles[tile].hasCollision) {
            entity.collisionOn = true;
        }
    }

    public int checkEntity(Entity entity, Entity[][] target) {

        int index = -1;

        for (int i = 0; i < target[0].length; i++) {

            if (target[0][i] != null) {

                entity.hitbox.x = entity.worldX;
                entity.hitbox.y = entity.worldY;

                target[0][i].hitbox.x = target[0][i].worldX;
                target[0][i].hitbox.y = target[0][i].worldY;

                switch (entity.direction) {
                    case UP -> entity.hitbox.y -= gp.tileSize;
                    case DOWN -> entity.hitbox.y += gp.tileSize;
                    case LEFT -> entity.hitbox.x -= gp.tileSize;
                    case RIGHT -> entity.hitbox.x += gp.tileSize;
                }

                if (entity.hitbox.intersects(target[0][i].hitbox)) {
                    if (target[0][i] != entity) {
                        index = i;
                        if (target[0][i].collisionOn) {
                            entity.collisionOn = true;
                        }
                    }
                }

                // reset entity solid area
                entity.hitbox.x = 0;
                entity.hitbox.y = 0;

                // reset object solid area
                target[0][i].hitbox.x = 0;
                target[0][i].hitbox.y = 0;
            }
        }

        return index;
    }
}