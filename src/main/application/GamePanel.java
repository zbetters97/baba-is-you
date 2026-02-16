package application;

import entity.Entity;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    /* GENERAL CONFIG */
    private Graphics2D g2;
    private Thread gameThread;
    public static UtilityTool utility = new UtilityTool();

    /* CONTROLS / SOUND / UI */
    public KeyHandler keyH = new KeyHandler(this);
    public UI ui = new UI(this);

    /* SCREEN SETTINGS */
    private final int originalTileSize = 16; // 16x16 tile
    private final int scale = 3; // scale rate to accommodate for large screen
    public final int tileSize = originalTileSize * scale; // scaled tile (16*3 = 48px)
    public final int maxScreenCol = 33; // columns (width)
    public final int maxScreenRow = 18; // rows (height)
    public final int screenWidth = tileSize * maxScreenCol; // screen width (33 x 48: 1584px)
    public final int screenHeight = tileSize * maxScreenRow; // screen height (18 x 48: 864px)

    /* WORLD SIZE */
    public int maxWorldCol = 33;
    public int maxWorldRow = 18;

    /* MAPS */
    public final String[] mapFiles = {"map_lvl_1.txt"};
    public final int maxMap = mapFiles.length;

    /* FULL SCREEN SETTINGS */
    public boolean fullScreenOn = false;
    private int screenWidth2 = screenWidth;
    private int screenHeight2 = screenHeight;
    private BufferedImage tempScreen;

    /* GAME STATES */
    public int gameState;
    public final int playState = 1;

    /* HANDLERS */
    public TileManager tileM = new TileManager(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public final LogicHandler lHandler = new LogicHandler(this);
    public final EntityGenerator eGenerator = new EntityGenerator(this);

    /* ENTITIES */
    public Entity[][] chr = new Entity[maxWorldRow][50];
    public Entity[][] obj = new Entity[maxMap][50];
    public Entity[][] words = new Entity[maxMap][50];

    public boolean rulesCheck = false;
    public boolean win = false;

    /**
     * CONSTRUCTOR
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // screen size
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // improves rendering performance

        this.addKeyListener(keyH);
        this.setFocusable(true); // GamePanel in focus to receive input
    }

    /**
     * SETUP GAME
     * Prepares the game with default settings
     * Called by Driver
     */
    protected void setupGame() {

        gameState = playState;

        // Temp game window (before drawing to window)
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        tileM.loadMap();
        aSetter.setup();
        lHandler.checkRules();

       // player.setDefaultValues();

        if (fullScreenOn) {
            setFullScreen();
        }
    }

    /**
     * SET FULL SCREEN
     * Changes the graphics to full screen mode
     * Called by setupGame()
     */
    private void setFullScreen() {

        // Get system screen
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Driver.window);

        // Get full screen width and height
        screenWidth2 = Driver.window.getWidth();
        screenHeight2 = Driver.window.getHeight();
    }

    /**
     * START GAME THREAD
     * Runs a new thread
     * Called by Driver
     */
    protected void startGameThread() {

        // New Thread with GamePanel class
        gameThread = new Thread(this);

        // Calls run() endlessly
        gameThread.start();
    }

    /**
     * RUN
     * Draws and updates the game 60 times a second
     * Called using the game thread start() method
     */
    @Override
    public void run() {

        long currentTime;
        long lastTime = System.nanoTime();
        double drawInterval = 1000000000.0 / 60.0; // 1/60th of a second
        double delta = 0;

        // Update and repaint gameThread
        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval; // Time passed (1/60th second)
            lastTime = currentTime;

            if (delta >= 1) {

                // Update game information
                update();

                // Draw temp screen with new information
                drawToTempScreen();

                // Send temp screen to monitors
                drawToScreen();

                delta = 0;
            }
        }
    }

    /**
     * UPDATE
     * Runs each time the frame is updated
     * Called by run()
     */
    private void update() {
        updateEntities(obj[0]);
        updateEntities(chr[0]);
        updateEntities(words[0]);

        // Checks rules once per update if turned on by an entity
        if (rulesCheck) {
            lHandler.checkRules();
            rulesCheck = false;
        }
    }
    private void updateEntities(Entity[] entities) {
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] != null) {
                if (entities[i].alive) {
                    entities[i].update();
                }
                else {
                    entities[i] = null;
                }
            }
        }
    }

    /**
     * DRAW TO TEMP SCREEN
     * Draws to temporary screen before drawing to front-end
     * Called by run()
     */
    private void drawToTempScreen() {
        clearBackBuffer();
        tileM.draw(g2);
        drawObjects();
        drawWords();
        drawCharacters();
        ui.draw(g2);
    }

    /**
     * CLEAR BACK BUFFER
     * Fills the background with black to eliminate artifacting
     * Called by drawToTempScreen()
     */
    private void clearBackBuffer() {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, screenWidth, screenHeight);
    }

    /** DRAW METHODS **/
    private void drawObjects() {
        for (Entity o : obj[0]) {
            if (o != null) {
                o.draw(g2);
            }
        }
    }
    private void drawWords() {
        for (Entity w : words[0]) {
            if (w != null) {
                w.draw(g2);
            }
        }
    }
    private void drawCharacters() {
        for (Entity c : chr[0]) {
            if (c != null) {
                c.draw(g2);
            }
        }
    }

    /**
     * DRAW TO SCREEN
     * Draws graphics to screen
     * Called by run()
     */
    private void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    /**
     * RESET LEVEL
     * Resets the current level to starting position
     * Called by KeyHandler
     */
    public void resetLevel() {
        tileM.loadMap();
        aSetter.setup();
        lHandler.checkRules();
    }
}
