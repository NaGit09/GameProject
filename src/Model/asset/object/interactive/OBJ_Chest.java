package Model.asset.object.interactive;

import Controller.GameController;
import Model.asset.object.Object;
import Controller.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Chest extends Object {
    private final GameController gameController;
    public OBJ_Chest(GameController gameController) {
        super(gameController);
        this.gameController = gameController;
        setName("Chest");
        setDescription("use key open check ");
        setValue(5);
        setPrice(25);
        try {
            BufferedImage image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/objects/chest.png")));
            setImage1(UtilityTool.scaleImage(image1, gameController.getTileSize(), gameController.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void use() {
        gameController.setGameState(gameController.getDialogueState());
        gameController.getUi().setCurrentDialogue("You  open  " + getName() + "!\n" + "You have restored " + getValue());

            gameController.player.getInventory().add(gameController.getObjects()[gameController.getCurrentMap()][5] );
            gameController.player.setExp(50);
            gameController.getUi().addMessage("Exp + " + 50);
            gameController.player.checkLevelUp();
            gameController.player.setCoins(100);
            gameController.player.getInventory().remove(this);


        gameController.playSoundEffect(2);

    }
}
