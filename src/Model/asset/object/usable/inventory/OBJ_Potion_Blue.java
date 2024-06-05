package Model.asset.object.usable.inventory;

import Controller.GameController;
import Model.asset.object.Object;
import Controller.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Potion_Blue extends Object {

    private final GameController gameController;

    public OBJ_Potion_Blue(GameController gameController) {
        super(gameController);
        this.gameController = gameController;

        setName("Blue Potion");
        setValue(5);
        setDescription("[" + getName() + "]\nRestores " + getValue() + "Mana");
        setPrice(25);
        int tile = gameController.getTileSize();
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/objects/potion_blue.png")));
            setImage1(UtilityTool.scaleImage(image, tile, tile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void use() {
        gameController.setGameState(gameController.getDialogueState());
        gameController.getUi().setCurrentDialogue("You drink the " + getName() + "!\n" +
                "You have restored " + getValue() + " mana!");

        gameController.getPlayer().setCurrentMana(gameController.getPlayer().getCurrentMana() + getValue());

        gameController.playSoundEffect(2);
    }}