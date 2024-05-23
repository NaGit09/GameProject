package Model.asset.object.usable.pickuponly;

import Controller.GameController;
import Controller.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Coin_Bronze extends PickUpOnlyObject {

    private final GameController gameController;

    public OBJ_Coin_Bronze(GameController gameController) {
        super(gameController);
        this.gameController = gameController;

        setName("Bronze Coin");
        setValue(1);
        setDescription("[" + getName() + "]\nA coin worth " + getValue());

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/objects/coin_bronze.png")));
            setImage1(UtilityTool.scaleImage(image, gameController.getTileSize(), gameController.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void use() {
        gameController.playSoundEffect(1);
        gameController.getUi().addMessage("Coin +" + getValue());
        gameController.getPlayer().setCoins(gameController.getPlayer().getCoins() + getValue());
    }
}
