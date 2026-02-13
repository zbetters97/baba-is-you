package entity;

import application.GamePanel;
import entity.word.WORD_Is;

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
    public String name;

    /* MOVEMENT VALUES */
    public GamePanel.Direction direction = DOWN;
    public int speed = 4;
    protected boolean moving = false;

    /* ANIMATION VALUES */
    protected int pixelCounter = 0;

    /* COLLISION VALUES */
    public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
    public boolean collisionOn = true;
    protected boolean canMove = true;

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
        if (moving) {
            moving();
        }
    }

    protected void moving() {
        if (canMove) {
            switch (direction) {
                case UP -> worldY -= speed;
                case DOWN -> worldY += speed;
                case LEFT -> worldX -= speed;
                case RIGHT-> worldX += speed;
            }

            cycleSprites();
        }

        pixelCounter += speed;
        if (pixelCounter >= gp.tileSize) {
            moving = false;
            pixelCounter = 0;
            spriteNum = 1;
            spriteCounter = 0;
            collisionOn = true;
        }
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
        gp.cChecker.checkTile(this);
        gp.cChecker.checkEntity(this, gp.obj);
        gp.cChecker.checkEntity(this, gp.words);
    }

    /**
     * MOVE
     * Repositions the entity's X, Y based on direction and speed
     * Called by updateDirection() if o collision
     */
    protected void move(GamePanel.Direction movingDirection) {
        if (!moving) {
            direction = movingDirection;

            checkCollision();

            if (!collisionOn) {
                moving = true;
            }
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

        // Draw hitbox (debug)
        g2.drawRect(worldX, worldY, hitbox.width, hitbox.height);
    }
}
