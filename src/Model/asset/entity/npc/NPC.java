package Model.asset.entity.npc;

import Controller.GameController;
import Model.asset.entity.Entity;

public class NPC extends Entity {

    private String[] dialogues = new String[20];
    private int dialogueIndex;

    public NPC(GameController gameController) {
        super(gameController);
    }

    @Override
    public void setupAI() {
        super.setupAI();
    }

    @Override
    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            setDialogueIndex(0);
        }

        getgameController().getUi().setCurrentDialogue(getDialogues()[dialogueIndex]);
        dialogueIndex++;

        switch (getgameController().getPlayer().getDirection()) {
            case "up" -> setDirection("down");
            case "down" -> setDirection("up");
            case "left" -> setDirection("right");
            case "right" -> setDirection("left");
        }
    }

    public String[] getDialogues() {
        return dialogues;
    }

    public Entity setDialogues(String[] dialogues) {
        this.dialogues = dialogues;
        return this;
    }

    public int getDialogueIndex() {
        return dialogueIndex;
    }

    public Entity setDialogueIndex(int dialogueIndex) {
        this.dialogueIndex = dialogueIndex;
        return this;
    }
}
