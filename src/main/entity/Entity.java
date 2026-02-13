package entity;

import application.GamePanel;
import entity.character.CHR_Baba;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Objects;

import static application.GamePanel.Direction.*;

public class Entity {

    // Properties an object can have
    public enum Property {
        WIN,
        STOP,
        PUSH,
        YOU,
    }

    // Empty enum list to hold properties
    public EnumSet<Entity.Property> properties = EnumSet.noneOf(Entity.Property.class);

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
    public boolean collisionOn = false;
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
        } else if (properties.contains(Entity.Property.YOU)) {
            handleMovementInput();
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

            if (name.equals(CHR_Baba.chrName)) {
                cycleSprites();
            }
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
     * Handles logic involving moving the entity
     */
    protected void updateDirection() {
        updateFacing();
        checkCollision();
        move(direction);
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
     * CHECK COLLISION
     * Checks if the entity collides with something
     */
    protected void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkEntity(this, gp.obj);
        gp.cChecker.checkEntity(this, gp.words);

        checkWords();

        if (properties.contains(Entity.Property.YOU)) {
            checkObjects();
        }
    }

    private void checkWords() {
        int word = gp.cChecker.checkEntity(this, gp.words);

        if (word != -1) {
            checkPush(gp.words[0][word]);
        }
    }

    private void checkObjects() {
        int obj = gp.cChecker.checkEntity(this, gp.obj);

        // If self has WIN property, player wins
        checkWin(this);

        if (obj != -1) {
            checkPush(gp.obj[0][obj]);
            checkWin(gp.obj[0][obj]);
        }
    }

    private void checkPush(Entity obj) {
        if (obj.properties.contains(Property.PUSH) && !obj.properties.contains(Property.STOP)) {
            obj.move(direction);
        }
    }

    private void checkWin(Entity obj) {
        if (obj.properties.contains(Property.WIN)) {
            gp.win = true;
        }
    }

    /**
     * MOVE
     * Repositions the entity's X, Y based on direction and speed
     * Called by updateDirection() if o collision
     */
    protected void move(GamePanel.Direction movingDirection) {
        if (!moving && !properties.contains(Property.STOP)) {
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
        image = getSprite();

        // Draw sprite
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
