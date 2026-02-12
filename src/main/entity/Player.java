package entity;

import application.GamePanel;
import entity.object.OBJ_Flag;

import java.awt.*;
import java.awt.image.BufferedImage;

import static application.GamePanel.Direction.*;

public class Player extends Entity {

    private boolean canWin = true;

    /**
     * CONSTRUCTOR
     * @param gp GamePanel
     */
    public Player(GamePanel gp) {
        super(gp);
        name = "Baba";
    }

    /* GET IMAGES */
    protected void getImages() {
        up1 = setupImage("/player/player_up_1");
        up2 = setupImage("/player/player_up_2");
        down1 = setupImage("/player/player_down_1");
        down2 = setupImage("/player/player_down_2");
        left1 = setupImage("/player/player_left_1");
        left2 = setupImage("/player/player_left_2");
        right1 = setupImage("/player/player_right_1");
        right2 = setupImage("/player/player_right_2");
    }

    /**
     * SET DEFAULT VALUES
     * Resets all attributes to base values
     */
    public void setDefaultValues() {
        setDefaultAnimationValues();
        setDefaultPosition();
    }

    /**
     * SET DEFAULT ANIMATION VALUES
     */
    private void setDefaultAnimationValues() {
        direction = RIGHT;
    }

    /**
     * SET DEFAULT POSITON
     */
    private void setDefaultPosition() {
        worldX = gp.tileSize * 18;
        worldY = gp.tileSize * 3;
    }

    /**
     * UPDATE
     * Updates player character based on user inputs
     * Called by GamePanel every frame
     */
    public void update() {
        if (moving) moving();
        else handleMovementInput();
    }

    /**
     * CHECK COLLISION
     * Checks if the entity collides with something
     */
    protected void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        checkObjects();
        checkWords();
    }

    private void checkObjects() {
        int obj = gp.cChecker.checkEntity(this, gp.obj);

        if (obj != -1) {
            String name = gp.obj[0][obj].name;
            checkFlag(name);
        }
    }

    private void checkFlag(String name) {
        if (name.equals(OBJ_Flag.objName) && canWin) {
            System.out.println("WINNER!");
        }
    }

    private void checkWords() {
        int word = gp.cChecker.checkEntity(this, gp.words);

        if (word != -1) {
            gp.words[0][word].checkCollision();
            if (!gp.words[0][word].collisionOn) {
                gp.words[0][word].move(direction);
            }
        }
    }

    /**
     * HANDLE MOVEMENT INPUT
     * Calls directionPressed() to update direction when an arrow key is pressed
     * Called by update() if current action allows
     */
    private void handleMovementInput() {
        if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed) {
            updateDirection();
        }
        else {
            moving = false;
        }
    }

    /**
     * UPDATE DIRECTION
     * Handles player movement logic
     * Called by handleMovementInput() when an arrow key is pressed
     */
    protected void updateDirection() {
        updateFacing();
        checkCollision();

        if (!collisionOn) {
            moving = true;
        }
    }

    /**
     * UPDATE FACING
     * Sets new player direction
     * Called by directionPressed() if current action allows
     */
    private void updateFacing() {

        GamePanel.Direction nextDirection = direction;

        boolean up = gp.keyH.upPressed;
        boolean down = gp.keyH.downPressed;
        boolean left = gp.keyH.leftPressed;
        boolean right = gp.keyH.rightPressed;

        if (up) nextDirection = UP;
        else if (down) nextDirection = DOWN;
        else if (left) nextDirection = LEFT;
        else if (right) nextDirection = RIGHT;

        direction = nextDirection;
    }

    /**
     * DRAW
     * Draws the sprite data to the graphics
     * Called by GamePanel every frame
     * @param g2 GamePanel
     */
    public void draw(Graphics2D g2) {
        image = getSprite();

        g2.drawImage(image, worldX, worldY, null);

        // Draw hitbox (debug)
        g2.drawRect(worldX, worldY, hitbox.width, hitbox.height);
    }

    /** GET CURRENT SPRITE TO DRAW **/
    private BufferedImage getSprite() {
        BufferedImage sprite;

        if (spriteNum == 1) {
            sprite = switch (direction) {
                case UP -> up1;
                case DOWN -> down1;
                case LEFT -> left1;
                case RIGHT -> right1;
            };
        }
        else {
            sprite = switch (direction) {
                case UP -> up2;
                case DOWN -> down2;
                case LEFT -> left2;
                case RIGHT -> right2;
            };
        }

        return sprite;
    }
}
