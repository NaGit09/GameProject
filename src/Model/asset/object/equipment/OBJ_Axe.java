package Model.asset.object.equipment;

import Controller.GameController;
import Controller.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Axe extends Weapon {

    public OBJ_Axe(GameController gameController) {
        super(gameController);

        setName("Woodcutter's Axe");
        setDescription("[" + getName() + "]\nA bit rusty, but still \nenough for cutting trees");
        setAttackValue(10);
        getAttackArea().width = 30;
        getAttackArea().height = 30;
        setPrice(75);

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/objects/axe.png")));
            setImage1(UtilityTool.scaleImage(image, gameController.getTileSize(), gameController.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
