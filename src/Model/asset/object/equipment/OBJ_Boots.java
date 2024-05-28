package Model.asset.object.equipment;

import Controller.GameController;
import Controller.util.UtilityTool;
import Model.asset.object.Object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Boots extends Object {

    public OBJ_Boots(GameController gameController) {
        super(gameController);

        setName("Boots");
        setDescription("[" + getName() + "]\nA pair of old boots");

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/boots.png")));
            setImage1(UtilityTool.scaleImage(image, gameController.getTileSize(), gameController.getTileSize()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
