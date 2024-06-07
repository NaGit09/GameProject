package Model.asset.entity.npc;

import Controller.GameController;

public class NPCFactory {
    public GameController controller;

    public NPCFactory(GameController controller) {
        this.controller = controller;
    }

    public NPC CreateNPC(String Type) {
        switch(Type.toLowerCase()) {
            case "oldman" -> {
                return new NPC_OldMan(controller);
            }
            case "wizard" -> {
                return new NPC_Wizard(controller);
            }
            case "merchant" -> {
                return new NPC_Merchant(controller);
            }
            default -> throw new IllegalStateException("Unexpected value: " + Type.toLowerCase());
        }

    }
}
