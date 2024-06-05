package Model.asset.object.usable.pickuponly;

import Controller.GameController;
import Controller.util.UtilityTool;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Boots extends PickUpOnlyObject {
    private final GameController gameController;

    public OBJ_Boots(GameController gameController) {
        super(gameController);
        this.gameController = gameController;

        setName("Boots");
        setDescription("[" + getName() + "]\nA pair of old boots");

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/objects/boots.png")));
            setImage1(UtilityTool.scaleImage(image, gameController.getTileSize(), gameController.getTileSize()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void use() {
        gameController.player.getInventory().removeIf(a -> a instanceof  OBJ_Boots);
    }
}
