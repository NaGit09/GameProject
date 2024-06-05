package Model.asset.object.usable.pickuponly;

import Controller.GameController;
import Controller.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends PickUpOnlyObject {

    private final GameController gameController;

    public OBJ_Heart(GameController gameController) {
        super(gameController);
        this.gameController = gameController;

        setName("Heart");
        setValue(2);
        setDescription("[" + getName() + "]\nWill restore " + getValue() + " life");

        int tile = gameController.getTileSize();

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/objects/heart_full.png")));
            setImage1(UtilityTool.scaleImage(image, tile, tile));

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/objects/heart_half.png")));
            setImage2(UtilityTool.scaleImage(image, tile, tile));

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/objects/heart_blank.png")));
            setImage3(UtilityTool.scaleImage(image, tile, tile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void use() {
        gameController.playSoundEffect(1);
        gameController.getUi().addMessage("Life +" + getValue());
        gameController.getPlayer().setCurrentLife(gameController.getPlayer().getCurrentLife() + getValue());
    }
}
