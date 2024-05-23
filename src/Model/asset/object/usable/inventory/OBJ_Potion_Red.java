package Model.asset.object.usable.inventory;

import Controller.GameController;
import Model.asset.object.Object;
import Model.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Potion_Red extends Object {

    private final GameController gameController;

    public OBJ_Potion_Red(GameController gameController) {
        super(gameController);
        this.gameController = gameController;

        setName("Red Potion");
        setValue(5);
        setDescription("[" + getName() + "]\nRestores " + getValue() + " health");
        setPrice(25);

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/objects/potion_red.png")));
            setImage1(UtilityTool.scaleImage(image, gameController.getTileSize(), gameController.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void use() {
        gameController.setGameState(gameController.getDialogueState());
        gameController.getUi().setCurrentDialogue("You drink the " + getName() + "!\n" +
                "You have restored " + getValue() + " life!");

        gameController.getPlayer().setCurrentLife(gameController.getPlayer().getCurrentLife() + getValue());

        gameController.playSoundEffect(2);
    }
}
