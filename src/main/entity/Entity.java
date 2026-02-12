package entity;

import application.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static application.GamePanel.Direction.*;

public class Entity {

    protected GamePanel gp;

    /* GENERAL ATTRIBUTES */
    public int worldX, worldY;

    /* MOVEMENT VALUES */
    public GamePanel.Direction direction = RIGHT;
    protected int speed = 4;
    protected boolean moving = false;

    /* ANIMATION VALUES */
    protected int pixelCounter = 0;

    /* COLLISION VALUES */
    public boolean collisionOn = true;
    protected boolean canMove = true;
    public Rectangle hitbox = new Rectangle(0, 0, 48, 48);

    /* SPRITE ATTRIBUTES */
    protected BufferedImage image, up1, up2, down1, down2, left1, left2, right1, right2;
    protected int spriteNum = 1;
    protected int spriteCounter = 0;

    /**
     * CONSTRUCTOR
     * @param gp GamePanel
     */
    public Entity(GamePanel gp) {
        this.gp = gp;
        getImages();
    }

    /* CHILD FUNCTIONS */
    /**
     * GET IMAGE
     */
    protected void getImages() { }

    /**
     * SET ACTION
     */
    protected void setAction() { }
    /* END CHILD FUNCTIONS */

    /**
     * SETUP IMAGE
     * @param imagePath Path to image file
     * @return Scaled image
     */
    protected BufferedImage setupImage(String imagePath) {

        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream(imagePath + ".png")
            ));
            image = GamePanel.utility.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch (IOException e) {
            System.out.println("Error loading image:" + e.getMessage());
        }

        return image;
    }

    /**
     * UPDATE
     * Updates the entity
     * Called every frame by GamePanel
     */
    public void update() {
    }

    /**
     * UPDATE DIRECTION
     * Handles logic involving moving the entity
     */
    protected void updateDirection() {
        checkCollision();
        move(direction);
    }

    /**
     * CHECK COLLISION
     * Checks if the entity collides with something
     */
    protected void checkCollision() {
        collisionOn = false;

       // gp.cChecker.checkTile(this);
       // boolean contactPlayer = gp.cChecker.checkPlayer(this);
    }

    /**
     * MOVE
     * Repositions the entity's X, Y based on direction and speed
     * Called by updateDirection() if o collision
     */
    protected void move(GamePanel.Direction direction) {
        if (!canMove || collisionOn) {
            moving = false;
            return;
        }

        moving = true;

        switch (direction) {
            case UP -> worldY -= speed;
            case DOWN -> worldY += speed;
            case LEFT -> worldX -= speed;
            case RIGHT-> worldX += speed;
        }
    }

    /**
     * GET MOVE DIRECTION
     * Called by CollisionDetector
     * @return Current direction of the entity
     */
    public GamePanel.Direction getMoveDirection() {
        return direction;
    }

    /**
     * MANAGE VALUES
     * Resets or reassigns entity attributes
     * called at the end of update
     */
    protected void manageValues() {
    }

    /**
     * DRAW
     * Draws the sprite data to the graphics
     * @param g2 GamePanel
     */
    public void draw(Graphics2D g2) {

        // Match image to sprite direction
        image = switch (direction) {
                case UP -> up1;
                case DOWN -> down1;
                case LEFT -> left1;
                case RIGHT -> right1;
            };

        // Draw sprite
        g2.drawImage(image, worldX, worldY, null);
    }
}
