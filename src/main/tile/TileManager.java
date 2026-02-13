package tile;

import application.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class TileManager {

    private final GamePanel gp;
    public Tile[] tiles;

    /* [MAP NUMBER][ROW][COL] */
    public final int[][][] mapTileNum;

    /**
     * CONSTRUCTOR
     * @param gp GamePanel
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;
        mapTileNum = new int[gp.maxMap][33][18];
        loadTileData();
    }

    /**
     * LOAD MAP
     * Loads current map data
     */
    public void loadMap() {

        // Import current map
        InputStream inputStream = getClass().getResourceAsStream("/maps/" + gp.mapFiles[0]);
        int mapLength = 0;

        try {
            Scanner sc = new Scanner(Objects.requireNonNull(inputStream));

            for (int row = 0; sc.hasNextLine(); row++) {
                String line = sc.nextLine();
                String[] numbers = line.split(" ");
                mapLength = numbers.length;

                for (int col = 0; col < numbers.length; col++) {
                    int tileNum = Integer.parseInt(numbers[col]);
                    mapTileNum[0][col][row] = tileNum;
                }
            }

            sc.close();

            // Assign new world dimensions
           // gp.maxWorldCol = mapLength;
           // gp.maxWorldRow = mapLength;
           // gp.worldWidth = gp.tileSize * mapLength;
           // gp.worldHeight = gp.tileSize * mapLength;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * LOAD TILE DATA
     * Loads the tile data from a text document
     */
    private void loadTileData() {
        // Arrays to hold tile attributes
        ArrayList<String> tileNumbers = new ArrayList<>();
        ArrayList<String> collisionStatus = new ArrayList<>();

        // Import tile data
        InputStream is = getClass().getResourceAsStream("/maps/map_tile_data.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

        // Add tile data to arrays
        try {
            String line;
            while ((line = br.readLine()) != null) {
                tileNumbers.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Assign tiles array
        tiles = new Tile[tileNumbers.size()];

        String tileNumber;
        boolean hasCollision;

        // Loop through all tile data in fileNames
        for (int i = 0; i < tileNumbers.size(); i++) {

            // Assign each name to fileName
            tileNumber = tileNumbers.get(i);

            // Retrieve status for each tile
            hasCollision = collisionStatus.get(i).equals("true");

            createTile(i, tileNumber, hasCollision);
        }
    }

    /**
     * CREATE TILE
     * Assigns tile attributes to the tiles array
     * @param index Array index
     * @param tileNumber Tile number
     * @param hasCollision If tile has collision
     */
    private void createTile(int index, String tileNumber, boolean hasCollision) {
        try {
            tiles[index] = new Tile();

            tiles[index].image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/tiles/" + tileNumber)
            ));

            tiles[index].image = GamePanel.utility.scaleImage(tiles[index].image, gp.tileSize, gp.tileSize);

            tiles[index].hasCollision = hasCollision;
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        int x = 0;
        int y = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[0][worldCol][worldRow];

            g2.drawImage(tiles[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            worldCol++;
            x += gp.tileSize;

            if (worldCol == gp.maxScreenCol) {
                worldCol = 0;
                worldRow++;
                x = 0;
                y += gp.tileSize;
            }
        }

        drawGrid(g2);
    }

    private void drawGrid(Graphics2D g2) {
        // Semi-transparent white
        g2.setColor(new Color(255, 255, 255, 80));

        // Vertical lines
        for (int col = 0; col <= gp.maxWorldCol; col++) {
            int x = col * gp.tileSize;
            g2.drawLine(x, 0, x, gp.maxWorldRow * gp.tileSize);
        }

        // Horizontal lines
        for (int row = 0; row <= gp.maxWorldRow; row++) {
            int y = row * gp.tileSize;
            g2.drawLine(0, y, gp.maxWorldCol * gp.tileSize, y);
        }
    }

}
