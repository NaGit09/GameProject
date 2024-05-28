package Model.asset.object.usable.inventory;

import Controller.GameController;
import Model.asset.object.Object;
import Controller.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Key extends Object {
    private final GameController gameController;
    public OBJ_Key(GameController gameController) {
        super(gameController);
        this.gameController = gameController;
        setName("Key");
        setDescription("[" + getName() + "]\nIt opens a door");
        setPrice(100);

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/objects/key.png")));
            setImage1(UtilityTool.scaleImage(image, gameController.getTileSize(), gameController.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void use() {
        gameController.player.getInventory().removeIf(a -> a instanceof OBJ_Key);
    }
}
