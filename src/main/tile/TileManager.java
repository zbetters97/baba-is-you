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
        InputStream inputStream = getClass().getResourceAsStream("/maps/" + gp.mapFiles[gp.currentMap]);

        try {
            Scanner sc = new Scanner(Objects.requireNonNull(inputStream));

            for (int row = 0; sc.hasNextLine(); row++) {
                String line = sc.nextLine();
                String[] numbers = line.split(" ");

                for (int col = 0; col < numbers.length; col++) {
                    int tileNum = Integer.parseInt(numbers[col]);
                    mapTileNum[gp.currentMap][col][row] = tileNum;
                }
            }

            sc.close();
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

        // Import tile data
        InputStream is = getClass().getResourceAsStream("/maps/map_tile_data.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

        // Add tile data to arrays
        try {
            String line;
            while ((line = br.readLine()) != null) {
                tileNumbers.add(line);
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Assign tiles array
        tiles = new Tile[tileNumbers.size()];

        String tileNumber;

        // Loop through all tile data in fileNames
        for (int i = 0; i < tileNumbers.size(); i++) {

            // Assign each name to fileName
            tileNumber = tileNumbers.get(i);

            createTile(i, tileNumber);
        }
    }

    /**
     * CREATE TILE
     * Assigns tile attributes to the tiles array
     * @param index Array index
     * @param tileNumber Tile number
     */
    private void createTile(int index, String tileNumber) {
        try {
            tiles[index] = new Tile();

            tiles[index].image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/tiles/" + tileNumber)
            ));

            tiles[index].image = GamePanel.utility.scaleImage(tiles[index].image, gp.tileSize, gp.tileSize);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * DRAW
     * Draws all the tiles to the screen
     * Called by GamePanel
     * @param g2 Graphics object
     */
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        int x = 0;
        int y = 0;

        // Loop until column and row are filled
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            // Grab tile
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            // Draw to x/y
            g2.drawImage(tiles[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);

            // Proceed to next column
            worldCol++;
            x += gp.tileSize;

            // Reached last column, jump to next row and first column
            if (worldCol == gp.maxScreenCol) {
                worldCol = 0;
                worldRow++;
                x = 0;
                y += gp.tileSize;
            }
        }

        drawGrid(g2);
    }

    /**
     * DRAW GRID
     * Draws a gray grid to the screen
     * Called by draw()
     * @param g2 Graphics object
     */
    private void drawGrid(Graphics2D g2) {
        // Semi-transparent white
        g2.setColor(new Color(255, 255, 255, 50));

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
