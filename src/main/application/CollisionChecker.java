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
                tile = gp.tileM.mapTileNum[gp.currentMap][x][y];
            }
            case DOWN -> {

                // Tile below entity
                x = entity.worldX / gp.tileSize;
                y = (entity.worldY + gp.tileSize) / gp.tileSize;
                tile = gp.tileM.mapTileNum[gp.currentMap][x][y];
            }
            case LEFT -> {

                // Tile left of entity
                x = (entity.worldX - gp.tileSize) / gp.tileSize;
                y = entity.worldY / gp.tileSize;
                tile = gp.tileM.mapTileNum[gp.currentMap][x][y];
            }
            case RIGHT -> {

                // Tile right of entity
                x = (entity.worldX + gp.tileSize) / gp.tileSize;
                y = entity.worldY / gp.tileSize;
                tile = gp.tileM.mapTileNum[gp.currentMap][x][y];
            }
        }

        if (gp.tileM.tiles[tile].hasCollision) {
            entity.collisionOn = true;
        }
    }
}