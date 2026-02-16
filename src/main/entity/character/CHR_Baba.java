package entity.character;

import application.GamePanel;
import entity.Entity;

import static application.GamePanel.Direction.RIGHT;

public class CHR_Baba extends Entity {

    public static final String chrName = "BABA";

    public CHR_Baba(GamePanel gp, int col, int row) {
        super(gp);

        name = chrName;

        direction = RIGHT;

        worldX = col * gp.tileSize;
        worldY = row * gp.tileSize;
    }

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
}
