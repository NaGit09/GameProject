package Model.asset.object.usable.pickuponly;

import Controller.GameController;
import Model.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_ManaCrystal extends PickUpOnlyObject {

    private final GameController gameController;

    public OBJ_ManaCrystal(GameController gameController) {
        super(gameController);
        this.gameController = gameController;

        setName("Mana Crystal");
        setValue(1);
        setDescription("[" + getName() + "]\nWill restore " + getValue() + " mana");

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/objects/manacrystal_full.png")));
            setImage1(UtilityTool.scaleImage(image, gameController.getTileSize(), gameController.getTileSize()));

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/objects/manacrystal_blank.png")));
            setImage2(UtilityTool.scaleImage(image, gameController.getTileSize(), gameController.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void use() {
        gameController.playSoundEffect(1);
        gameController.getUi().addMessage("Mana +" + getValue());
        gameController.getPlayer().setCurrentMana(gameController.getPlayer().getCurrentMana() + getValue());
    }
}
