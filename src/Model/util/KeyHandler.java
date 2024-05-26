package Model.util;

import Controller.GameController;
import View.UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, spacePressed, projectileKeyPressed;
    private final GameController gameController;

    // DEBUG
    private boolean showDebugText = false;

    public KeyHandler(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (gameController.getGameState()) {
            case titleState -> checkTitleStateKeyPressed(code);
            case playState -> checkPlayStateKeyPressed(code);
            case pauseState -> checkPauseStateKeyPressed(code);
            case dialogueState -> checkDialogueStateKeyPressed(code);
            case characterState -> checkCharacterStateKeyPressed(code);
            case optionState -> checkOptionStateKeyPressed(code);
            case gameOverState -> checkGameOverStateKeyPressed(code);
            case tradeState -> checkTradeStateKeyPressed(code);
        }
    }

    private void checkTitleStateKeyPressed(int code) {
        if (gameController.getUi().getTitleScreenState() == 0) {
            checkMainTitleScreenKeyPressed(code);

        } else if (gameController.getUi().getTitleScreenState() == 1) {
            checkCharacterSelectionScreenKeyPressed(code);
        }
    }

    private void checkMainTitleScreenKeyPressed(int code) {
        if (code == KeyEvent.VK_W) {
            gameController.getUi().setCommandNumber(gameController.getUi().getCommandNumber() - 1);
            gameController.playSoundEffect(9);
            if (gameController.getUi().getCommandNumber() < 0) {
                gameController.getUi().setCommandNumber(2);
            }
        }

        if (code == KeyEvent.VK_S) {
            gameController.getUi().setCommandNumber(gameController.getUi().getCommandNumber() + 1);
            gameController.playSoundEffect(9);
            if (gameController.getUi().getCommandNumber() > 2) {
                gameController.getUi().setCommandNumber(0);
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    private void checkCharacterSelectionScreenKeyPressed(int code) {
        if (code == KeyEvent.VK_W) {
            gameController.getUi().setCommandNumber(gameController.getUi().getCommandNumber() - 1);
            gameController.playSoundEffect(9);
            if (gameController.getUi().getCommandNumber() < 0) {
                gameController.getUi().setCommandNumber(3);
            }
        }

        if (code == KeyEvent.VK_S) {
            gameController.getUi().setCommandNumber(gameController.getUi().getCommandNumber() + 1);
            gameController.playSoundEffect(9);
            if (gameController.getUi().getCommandNumber() > 3) {
                gameController.getUi().setCommandNumber(0);
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    private void checkPlayStateKeyPressed(int code) {
        checkMovementKeys(code);
        checkGameStateKeys(code);
        checkInteractionKeys(code);
        checkAdminKeys(code);
    }

    private void checkMovementKeys(int code) {
        switch (code) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
        }

    }

    private void checkGameStateKeys(int code) {
        switch (code) {
            case KeyEvent.VK_P ->  gameController.setGameState(gameController.getPauseState());
            case KeyEvent.VK_C -> gameController.setGameState(gameController.getCharacterState());
            case KeyEvent.VK_ESCAPE ->  gameController.setGameState(gameController.getOptionState());
        }

    }

    private void checkInteractionKeys(int code) {

        switch (code) {
            case KeyEvent.VK_ENTER -> enterPressed = true;
            case KeyEvent.VK_F -> projectileKeyPressed = true;
            case KeyEvent.VK_SPACE -> spacePressed = true;

        }
    }

    private void checkAdminKeys(int code) {
        // DEBUG
        if (code == KeyEvent.VK_T) {
            showDebugText = !showDebugText;
        }

        if (code == KeyEvent.VK_R) {
            switch (gameController.getCurrentMap()) {
                case 0 -> gameController.getTileManager().loadMap("/resources/maps/worldV3.txt", 0);
                case 1 -> gameController.getTileManager().loadMap("/resources/maps/interior01.txt", 1);
            }
        }
    }

    private void checkPauseStateKeyPressed(int code) {
        if (code == KeyEvent.VK_P) {
            gameController.setGameState(gameController.getPlayState());
        }
    }

    private void checkDialogueStateKeyPressed(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gameController.setGameState(gameController.getPlayState());
        }
    }

    private void checkCharacterStateKeyPressed(int code) {
        if (code == KeyEvent.VK_C) {
            gameController.setGameState(gameController.getPlayState());
        }

        if (code == KeyEvent.VK_ENTER) {
            gameController.getPlayer().selectItem();

        }

        playerInventoryMovement(code);
    }

    private void playerInventoryMovement(int code) {
        switch (code) {
            case KeyEvent.VK_W -> {
                if (gameController.getUi().getPlayerSlotRow() != 0) {
                    gameController.playSoundEffect(9);
                    gameController.getUi().setPlayerSlotRow(gameController.getUi().getPlayerSlotRow() - 1);
                }
            }
            case  KeyEvent.VK_A -> {
                if (gameController.getUi().getPlayerSlotCol() != 0) {
                    gameController.playSoundEffect(9);
                    gameController.getUi().setPlayerSlotCol(gameController.getUi().getPlayerSlotCol() - 1);
                }
            }
            case KeyEvent.VK_S ->  {
                if (gameController.getUi().getPlayerSlotRow() != 3) {
                    gameController.playSoundEffect(9);
                    gameController.getUi().setPlayerSlotRow(gameController.getUi().getPlayerSlotRow() + 1);
                }
            }
            case  KeyEvent.VK_D -> {
                if (gameController.getUi().getPlayerSlotCol() != 4) {
                    gameController.playSoundEffect(9);
                    gameController.getUi().setPlayerSlotCol(gameController.getUi().getPlayerSlotCol() + 1);
                }
            }
        }


    }


    private void checkOptionStateKeyPressed(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gameController.setGameState(gameController.getPlayState());
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNumber;

        switch (gameController.getUi().getSubState()) {
            case 0 -> maxCommandNumber = 5;
            case 3 -> maxCommandNumber = 1;
            default -> maxCommandNumber = 0;
        }

        if (code == KeyEvent.VK_W) {
            gameController.getUi().setCommandNumber(gameController.getUi().getCommandNumber() - 1);
            gameController.playSoundEffect(9);
            if (gameController.getUi().getCommandNumber() < 0) {
                gameController.getUi().setCommandNumber(maxCommandNumber);
            }
        }

        if (code == KeyEvent.VK_S) {
            gameController.getUi().setCommandNumber(gameController.getUi().getCommandNumber() + 1);
            gameController.playSoundEffect(9);
            if (gameController.getUi().getCommandNumber() > maxCommandNumber) {
                gameController.getUi().setCommandNumber(0);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gameController.getUi().getSubState() == 0) {
                if (gameController.getUi().getCommandNumber() == 1 && gameController.getMusic().getVolumeScale() > 0) {
                    gameController.getMusic().setVolumeScale(gameController.getMusic().getVolumeScale() - 1);
                    gameController.getMusic().checkVolume();
                    gameController.playSoundEffect(9);
                }

                if (gameController.getUi().getCommandNumber() == 2 && gameController.getSoundEffect().getVolumeScale() > 0) {
                    gameController.getSoundEffect().setVolumeScale(gameController.getSoundEffect().getVolumeScale() - 1);
                    gameController.playSoundEffect(9);
                }
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gameController.getUi().getSubState() == 0) {
                if (gameController.getUi().getCommandNumber() == 1 && gameController.getMusic().getVolumeScale() < 5) {
                    gameController.getMusic().setVolumeScale(gameController.getMusic().getVolumeScale() + 1);
                    gameController.getMusic().checkVolume();
                    gameController.playSoundEffect(9);
                }

                if (gameController.getUi().getCommandNumber() == 2 && gameController.getSoundEffect().getVolumeScale() < 5) {
                    gameController.getSoundEffect().setVolumeScale(gameController.getSoundEffect().getVolumeScale() + 1);
                    gameController.playSoundEffect(9);
                }
            }
        }
    }

    private void checkGameOverStateKeyPressed(int code) {
        if (code == KeyEvent.VK_W) {
            gameController.getUi().setCommandNumber(gameController.getUi().getCommandNumber() - 1);
            gameController.playSoundEffect(9);
            if (gameController.getUi().getCommandNumber() < 0) {
                gameController.getUi().setCommandNumber(1);
            }
        }

        if (code == KeyEvent.VK_S) {
            gameController.getUi().setCommandNumber(gameController.getUi().getCommandNumber() + 1);
            gameController.playSoundEffect(9);
            if (gameController.getUi().getCommandNumber() > 1) {
                gameController.getUi().setCommandNumber(0);
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }



    private void checkTradeStateKeyPressed(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        UI ui = gameController.getUi();

        if (ui.getSubState() == 0) {
            if (code == KeyEvent.VK_W) {
                ui.setCommandNumber(ui.getCommandNumber() - 1);
                if (ui.getCommandNumber() < 0) {
                    ui.setCommandNumber(2);
                }
                gameController.playSoundEffect(9);
            }

            if (code == KeyEvent.VK_S) {
                ui.setCommandNumber(ui.getCommandNumber() + 1);
                if (ui.getCommandNumber() > 2) {
                    ui.setCommandNumber(0);
                }
                gameController.playSoundEffect(9);
            }
        }

        if (ui.getSubState() == 1) {
            npcInventoryMovement(code);
            if (code == KeyEvent.VK_ESCAPE) {
                ui.setSubState(0);
            }
        }

        if (ui.getSubState() == 2) {
            playerInventoryMovement(code);
            if (code == KeyEvent.VK_ESCAPE) {
                ui.setSubState(0);
            }
        }
    }

    private void npcInventoryMovement(int code) {
        UI   ui = gameController.getUi();
        switch (code) {
            case KeyEvent.VK_W -> {
                if (ui.getNpcSlotRow() != 0) {
                    gameController.playSoundEffect(9);
                    ui.setNpcSlotRow(ui.getNpcSlotRow() - 1);
                }
            }
            case KeyEvent.VK_A -> {
                if (ui.getNpcSlotCol() != 0) {
                    gameController.playSoundEffect(9);
                    ui.setNpcSlotCol(ui.getNpcSlotCol() - 1);
                }
            }
            case KeyEvent.VK_S -> {
                if (ui.getNpcSlotRow() != 3) {
                    gameController.playSoundEffect(9);
                    ui.setNpcSlotRow(ui.getNpcSlotRow() + 1);
                }
            }
            case KeyEvent.VK_D -> {
                if (ui.getNpcSlotCol() != 4) {
                    gameController.playSoundEffect(9);
                    ui.setNpcSlotCol(ui.getNpcSlotCol() + 1);
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case  KeyEvent.VK_W -> upPressed=false;
            case KeyEvent.VK_S -> downPressed=false;
            case KeyEvent.VK_A -> leftPressed=false;
            case KeyEvent.VK_D -> rightPressed=false;
            case KeyEvent.VK_SPACE -> spacePressed=false;
            case KeyEvent.VK_F -> projectileKeyPressed=false;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

// CÁC GETTER SETTER METOD
    public boolean isUpPressed() {
        return upPressed;
    }

    public KeyHandler setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
        return this;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public KeyHandler setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
        return this;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public KeyHandler setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
        return this;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public KeyHandler setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
        return this;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public KeyHandler setEnterPressed(boolean enterPressed) {
        this.enterPressed = enterPressed;
        return this;
    }

    public boolean isShowDebugText() {
        return showDebugText;
    }

    public KeyHandler setShowDebugText(boolean showDebugText) {
        this.showDebugText = showDebugText;
        return this;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

    public KeyHandler setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
        return this;
    }

    public boolean isProjectileKeyPressed() {
        return projectileKeyPressed;
    }

    public KeyHandler setProjectileKeyPressed(boolean projectileKeyPressed) {
        this.projectileKeyPressed = projectileKeyPressed;
        return this;
    }
}
