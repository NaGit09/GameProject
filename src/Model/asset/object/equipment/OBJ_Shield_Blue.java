package Model.asset.object.equipment;

import Controller.GameController;
import Model.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Shield_Blue extends Shield {

    public OBJ_Shield_Blue(GameController gameController) {
        super(gameController);

        setName("Blue Shield");
        setDescription("[" + getName() + "]\nPainted blue");
        setDefenseValue(2);
        setPrice(250);

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/objects/shield_blue.png")));
            setImage1(UtilityTool.scaleImage(image, gameController.getTileSize(), gameController.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
