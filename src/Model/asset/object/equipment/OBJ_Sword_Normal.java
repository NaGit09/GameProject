package Model.asset.object.equipment;

import Controller.GameController;
import Model.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Sword_Normal extends Weapon {

    public OBJ_Sword_Normal(GameController gameController) {
        super(gameController);

        setName("Normal Sword");
        setDescription("[" + getName() + "]\nAn old sword");
        setAttackValue(1);
        getAttackArea().width = 36;
        getAttackArea().height = 36;
        setPrice(20);

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/objects/sword_normal.png")));
            setImage1(UtilityTool.scaleImage(image, gameController.getTileSize(), gameController.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
