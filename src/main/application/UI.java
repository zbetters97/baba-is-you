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

        if (gp.win) {
            drawWin();
        }
        drawDebug();
    }

    private void drawWin() {
        g2.setColor(Color.ORANGE);
        g2.setFont(new Font("Arial", Font.BOLD, 60));

        int x = getXforCenteredText("WINNER");
        int y = gp.screenHeight / 2;

        g2.drawString("WINNER!", x - gp.tileSize, y);
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
        g2.drawString("WorldX: " + gp.chr[0][0].worldX, x, y);
        y += lineHeight;
        g2.drawString("WorldY: " + gp.chr[0][0].worldY, x, y);
        y += lineHeight;
        g2.drawString("Column: " + (gp.chr[0][0].worldX + gp.chr[0][0].hitbox.x) / gp.tileSize, x, y);
        y += lineHeight;
        g2.drawString("Row: " + (gp.chr[0][0].worldY + gp.chr[0][0].hitbox.y) / gp.tileSize, x, y);
    }

    private int getXforCenteredText(String text) {
        try {
            int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            return (gp.screenWidth / 2) - (length / 2);
        }
        catch (Exception e) {
            return gp.screenWidth / 2;
        }
    }
}
