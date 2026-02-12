package entity;

import application.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

import static application.GamePanel.Direction.*;

public class Player extends Entity {

    /**
     * CONSTRUCTOR
     * @param gp GamePanel
     */
    public Player(GamePanel gp) {
        super(gp);
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
        worldX = gp.tileSize * 1;
        worldY = gp.tileSize * 2;

        gp.currentMap = 0;
    }

    /**
     * UPDATE
     * Updates player character based on user inputs
     * Called by GamePanel every frame
     */
    public void update() {
        checkCollision();

        if (moving) {
            walking();
        }
        else {
            handleMovementInput();
        }

        manageValues();
    }

    /**
     * CHECK COLLISION
     * Checks if player has collided with anything
     * Called by update()
     */
    protected void checkCollision() {
        collisionOn = false;
    }

    public void walking() {
        if (canMove) {
            move();
            cycleSprites();
        }

        pixelCounter += speed;
        if (pixelCounter >= gp.tileSize - speed) {
            moving = false;
            pixelCounter = 0;
            spriteNum = 1;
            spriteCounter = 0;
        }
    }

    /**
     * CYCLE SPRITES
     * Changes the animation counter for draw to render the correct sprite
     */
    protected void cycleSprites() {
        if (pixelCounter > 0 && pixelCounter < gp.tileSize) {
            spriteNum = 2;
        }
        else  {
            spriteNum = 1;
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
        move();
        cycleSprites();
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
     * MOVE
     * Repositions the player's X, Y based on direction and speed
     * Called by updateDirection() if no collision
     */
    protected void move() {
        GamePanel.Direction newDirection = getMoveDirection();
        super.move(newDirection);
    }

    /**
     * RESOLVE MOVE DIRECTION
     * @return updated player direction
     * Decides if direction or lockonDirection should be used
     * Called by move()
     */
    public GamePanel.Direction getMoveDirection() {
        return direction;
    }

    /**
     * MANAGE VALUES
     * Resets or reassigns player attributes
     * Called by update() at the end
     */
    protected void manageValues() {
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
