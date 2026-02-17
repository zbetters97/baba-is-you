package entity;

import application.GamePanel;
import entity.character.CHR_Baba;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import static application.GamePanel.Direction.*;

public class Entity {

    // Properties an object can have
    public enum Property {
        WIN,
        STOP,
        PUSH,
        YOU,
        SINK,
    }

    // Empty enum list to hold properties
    public EnumSet<Entity.Property> properties = EnumSet.noneOf(Entity.Property.class);

    protected GamePanel gp;

    /* GENERAL ATTRIBUTES */
    public int worldX, worldY;
    public String name;
    public boolean alive = true;

    /* MOVEMENT VALUES */
    public GamePanel.Direction direction = DOWN;
    public int speed = 4;
    public boolean moving = false;

    /* ANIMATION VALUES */
    protected int pixelCounter = 0;

    /* COLLISION VALUES */
    public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
    public boolean collisionOn = false;

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
            moveATile();
        } else if (properties.contains(Entity.Property.YOU)) {
            handleMovementInput();
        }
    }

    /**
     * MOVE A TILE
     * Moves the entity one tile if able
     * Called by update() if the entity is moving
     */
    private void moveATile() {
        switch (direction) {
            case UP -> worldY -= speed;
            case DOWN -> worldY += speed;
            case LEFT -> worldX -= speed;
            case RIGHT-> worldX += speed;
        }

        if (name.equals(CHR_Baba.chrName)) {
            cycleSprites();
        }

        pixelCounter += speed;
        if (pixelCounter >= gp.tileSize) {
            resetMovement();
        }
    }

    /**
     * RESET MOVEMENT
     * Resets all values when the entity is done moving
     * Called by moving()
     */
    private void resetMovement() {
        moving = false;
        pixelCounter = 0;
        spriteNum = 1;
        spriteCounter = 0;
        collisionOn = false;
        gp.rulesCheck = true;
    }

    /**
     * HANDLE MOVEMENT INPUT
     * Calls directionPressed() to update direction when an arrow key is pressed
     * Called by update() if current action allows
     */
    private void handleMovementInput() {
        if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed) {
            updateFacing();
            move(direction);
        }
    }

    /**
     * UPDATE FACING
     * Sets new player direction
     * Called by directionPressed() if current action allows
     */
    private void updateFacing() {
        if (gp.keyH.upPressed) direction = UP;
        else if (gp.keyH.downPressed) direction = DOWN;
        else if (gp.keyH.leftPressed) direction = LEFT;
        else if (gp.keyH.rightPressed) direction = RIGHT;
    }

    /**
     * MOVE
     * Repositions the entity's X, Y based on direction and speed
     * Called by handleMovementInput()
     */
    private void move(GamePanel.Direction movingDirection) {
        if (!moving && !properties.contains(Property.STOP)) {
            direction = movingDirection;

            if (canMove(this, direction)) {
                interactEntities(this, direction);
            }
        }
    }

    /**
     * CAN MOVE
     * Checks if the entity is able to move a tile
     * Called by move()
     * @param entity Entity that wants to move
     * @param dir The direction the entity is moving
     * @return True if able to move, false if not
     */
    private boolean canMove(Entity entity, GamePanel.Direction dir) {
        // Tile with collision in the way
        if (gp.cChecker.tileBlocked(entity, dir)) {
            return false;
        }

        // Get all entities sitting on the next tile
        List<Entity> stack = gp.cChecker.getEntitiesAtNextTile(entity, dir);
        for (Entity ent : stack) {
            // Can't move, entity has STOP
            if (ent.properties.contains(Property.STOP)) {
                return false;
            }
            // Entity has PUSH, attempt to move
            else if (ent.properties.contains(Property.PUSH)) {

                // Can't move
                if (!canMove(ent, dir)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * INTERACT ENTITIES
     * Sets moving to True for the entity passed
     * Called by move() and self
     * @param entity The entity that is getting interacted with
     * @param dir The direction of the interaction
     */
    private void interactEntities(Entity entity, GamePanel.Direction dir) {

        // Get all entities at the next tile
        for (Entity ent : gp.cChecker.getEntitiesAtNextTile(entity, dir)) {

            // Entity is pushable
            if (ent.properties.contains(Property.PUSH)) {
                interactEntities(ent, dir);
            }
        }

        entity.moving = true;
        entity.direction = dir;

        // Check for rules in play
        checkRules(entity);
    }

    /**
     * CHECK RULES
     * Checks various rules in play on each entity list
     * Called by pushEntities()
     * @param entity The entity to check rules on
     */
    private void checkRules(Entity entity) {
        checkEntities(entity, gp.words);
        checkEntities(entity, gp.obj);
        checkEntities(entity, gp.chr);
        checkWin(entity, this);
    }

    /**
     * CHECK ENTITIES
     * Checks each type of collision for given entity list
     * @param entities List of entities to check collision against
     */
    private void checkEntities(Entity entity, Entity[][] entities) {
        int ent = gp.cChecker.checkEntity(entity, entities);

        if (ent != -1) {
            checkSink(entity, entities[gp.currentMap][ent]);
            checkWin(entity, entities[gp.currentMap][ent]);
        }
    }

    /**
     * CHECK SINK
     * Sets alive to false if the object has SINK
     */
    private void checkSink(Entity entity, Entity obj) {
        if (obj.properties.contains(Property.SINK) && !obj.properties.contains(Property.STOP)) {
            entity.alive = false;
            obj.alive = false;
        }
    }

    /**
     * CHECK WIN
     * Checks if the entity can win the game/level
     */
    private void checkWin(Entity entity, Entity obj) {
        // Entity needs to be controlled by player to win
        if (entity.properties.contains(Property.YOU) && obj.properties.contains(Property.WIN)) {
            gp.win = true;
        }
    }

    /**
     * CYCLE SPRITES
     * Changes the animation counter for draw to render the correct sprite
     */
    private void cycleSprites() {
        if (pixelCounter > 0 && pixelCounter < gp.tileSize) {
            spriteNum = 2;
        }
        else  {
            spriteNum = 1;
        }
    }

    /**
     * SET FORM
     * Changes the entity's properties to match the new form
     * @param newFormName The new form the entity will possess
     */
    public void setForm(String newFormName) {
        Entity newForm = gp.eGenerator.getEntity(newFormName);
        if (newForm == null) return;

        collisionOn = false;

        // Copy all attributes from new form
        this.name = newForm.name;
        this.up1 = newForm.up1;
        this.up2 = newForm.up2;
        this.down1 = newForm.down1;
        this.down2 = newForm.down2;
        this.left1 = newForm.left1;
        this.left2 = newForm.left2;
        this.right1 = newForm.right1;
        this.right2 = newForm.right2;
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
