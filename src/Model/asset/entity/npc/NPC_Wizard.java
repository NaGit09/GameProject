package Model.asset.entity.npc;

import Controller.GameController;
import Model.asset.object.usable.inventory.OBJ_Key;
import Model.asset.object.usable.inventory.OBJ_Potion_Blue;
import Model.asset.object.usable.inventory.OBJ_Potion_Red;

public class NPC_Wizard  extends NPC{

    public NPC_Wizard(GameController gamePanel) {
        super(gamePanel);
        setName("Wizard");

        getAnimationImages();
        setDialogue();
        setItems();
    }
    public void getAnimationImages() {
        int width = getgameController().getTileSize();
        int height = getgameController().getTileSize();
        setUp1(setup("/resources/images/Wizard/wizard_down_1", width, height));
        setUp2(setup("/resources/images/Wizard/wizard_down_2", width, height));
        setDown1(setup("/resources/images/Wizard/wizard_down_1", width, height));
        setDown2(setup("/resources/images/Wizard/wizard_down_2",width, height));
        setLeft1(setup("/resources/images/Wizard/wizard_down_1", width, height));
        setLeft2(setup("/resources/images/Wizard/wizard_down_2", width, height));
        setRight1(setup("/resources/images/Wizard/wizard_down_1", width, height));
        setRight2(setup("/resources/images/Wizard/wizard_down_2", width, height));
    }

    public void setDialogue() {
        getDialogues()[0] = "He he, so you found me. \nI have some good stuff. \nDo you want to trade?";
    }

    public void setItems() {
        getInventory().add(new OBJ_Potion_Red(getgameController()));
        getInventory().add(new OBJ_Key(getgameController()));
        getInventory().add(new OBJ_Potion_Blue(getgameController()));
    }

    @Override
    public void speak() {
        super.speak();

        getgameController().setGameState(getgameController().getTradeState());
        getgameController().getUi().setNpc(this);
    }
}
