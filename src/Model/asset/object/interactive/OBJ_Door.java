package Model.asset.object.interactive;

import Controller.GameController;
import Model.asset.object.Object;
import Model.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Door extends Object {

    public OBJ_Door(GameController gameController) {
        super(gameController);

        setName("Door");
        setCollision(false);

        setDescription("use key open check ");
        getCollisionArea().x = 0;
        getCollisionArea().y = 16;
        getCollisionArea().width = 48;
        getCollisionArea().height = 32;
        setCollisionDefaultX(getCollisionArea().x);
        setCollisionDefaultY(getCollisionArea().y);

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/door.png")));
            setImage1(UtilityTool.scaleImage(image, gameController.getTileSize(), gameController.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
