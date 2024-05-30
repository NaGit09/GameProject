package Model.asset.entity.npc;

import Controller.GameController;
import Model.asset.object.equipment.OBJ_Axe;
import Model.asset.object.equipment.OBJ_Shield_Blue;
import Model.asset.object.equipment.OBJ_Shield_Wood;
import Model.asset.object.equipment.OBJ_Sword_Normal;

public class NPC_Merchant extends NPC {

    public NPC_Merchant(GameController gamePanel) {
        super(gamePanel);

        setName("Merchant");

        getAnimationImages();
        setDialogue();
        setItems();
    }

    public void getAnimationImages() {
        int width = getgameController().getTileSize();
        int height = getgameController().getTileSize();
        setUp1(setup("/resources/images/npc/merchant_down_1", width, height));
        setUp2(setup("/resources/images/npc/merchant_down_2", width, height));
        setDown1(setup("/resources/images/npc/merchant_down_1", width, height));
        setDown2(setup("/resources/images/npc/merchant_down_2", width, height));
        setLeft1(setup("/resources/images/npc/merchant_down_1", width, height));
        setLeft2(setup("/resources/images/npc/merchant_down_2", width, height));
        setRight1(setup("/resources/images/npc/merchant_down_1", width, height));
        setRight2(setup("/resources/images/npc/merchant_down_2", width, height));
    }

    public void setDialogue() {
        getDialogues()[0] = "He he, do you buy equidment ?? \n ";
    }

    public void setItems() {

        getInventory().add(new OBJ_Sword_Normal(getgameController()));
        getInventory().add(new OBJ_Axe(getgameController()));
        getInventory().add(new OBJ_Shield_Wood(getgameController()));
        getInventory().add(new OBJ_Shield_Blue(getgameController()));
    }

    @Override
    public void speak() {
        super.speak();

        getgameController().setGameState(getgameController().getTradeState());
        getgameController().getUi().setNpc(this);
    }
}
