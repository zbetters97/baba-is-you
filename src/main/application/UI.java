package application;

import java.awt.*;

public class UI {

    /* CONFIG */
    private final GamePanel gp;
    private Graphics2D g2;

    /**
     * CONSTRUCTOR
     * Instance created by GamePanel
     * @param gp GamePanel
     */
    public UI(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * DRAW
     * Draws the UI
     * Called by GamePanel
     * @param g2 Graphics2D enginge
     */
    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setColor(Color.white);
        drawHUD();
    }

    /**
     * DRAW HUD
     * Draws the HUD during playstate
     * called by draw()
     */
    private void drawHUD() {
        drawDebug();
    }

    /**
     * DRAW DEBUG
     * UI for debug information
     * Called by drawHUD()
     */
    private void drawDebug() {

        int x = 10;
        int y = gp.tileSize * 6;
        int lineHeight = 20;

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));

        // Draw coordinates
        g2.drawString("WorldX: " + gp.player.worldX, x, y);
        y += lineHeight;
        g2.drawString("WorldY: " + gp.player.worldY, x, y);
        y += lineHeight;
        g2.drawString("Column: " + (gp.player.worldX + gp.player.hitbox.x) / gp.tileSize, x, y);
        y += lineHeight;
        g2.drawString("Row: " + (gp.player.worldY + gp.player.hitbox.y) / gp.tileSize, x, y);
    }
}
