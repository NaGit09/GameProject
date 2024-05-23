package Model.asset.entity.npc;

import Controller.GameController;

import java.util.Random;

public class NPC_OldMan extends NPC {

    public NPC_OldMan(GameController gameController) {
        super(gameController);

        setName("Old Man");
        setSpeed(2);

        getAnimationImages();
        setDialogue();
    }

    public void getAnimationImages() {
        int width = getgameController().getTileSize();
        int height = getgameController().getTileSize();

        setUp1(setup("/resources/images/npc/oldman_up_1", width, height));
        setUp2(setup("/resources/images/npc/oldman_up_2", width, height));
        setDown1(setup("/resources/images/npc/oldman_down_1", width, height));
        setDown2(setup("/resources/images/npc/oldman_down_2", width, height));
        setLeft1(setup("/resources/images/npc/oldman_left_1", width, height));
        setLeft2(setup("/resources/images/npc/oldman_left_2", width, height));
        setRight1(setup("/resources/images/npc/oldman_right_1", width, height));
        setRight2(setup("/resources/images/npc/oldman_right_2", width, height));
    }

    public void setAction () {
        actionLockCounter++;
        if (onPath) {
            int goalCol = 12 ;
            int goalRow =9 ;
            searchPath (goalCol , goalRow);
        }
        else {
            if (actionLockCounter == 120){
                Random random = new Random();
                int i = random.nextInt();
                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75 && i <= 100) {
                    direction = "right";
                }
                actionLockCounter =0;


            }
        }

    }
    @Override
    public void setupAI() {
        super.setupAI();
    }

    public void setDialogue() {
        getDialogues()[0] = "Hello, lad.";
        getDialogues()[1] = "So you've come to this island to find \nthe treasure?";
        getDialogues()[2] = "I used to be a great wizard, but now... \nI'm a bit too old for adventuring.";
        getDialogues()[3] = "Well, good luck to you lad.";
    }

    @Override
    public void speak() {
        super.speak();
        onPath = true;
    }
}
