package Model.asset;

import Controller.GameController;
import Model.asset.entity.monster.BOSS_Ramurai;
import Model.asset.entity.monster.MON_Slime;
import Model.asset.entity.npc.NPC_Merchant;
import Model.asset.entity.npc.NPC_OldMan;
import Model.asset.entity.npc.NPC_Wizard;
import Model.asset.object.equipment.OBJ_Axe;
import Model.asset.object.equipment.OBJ_Boots;
import Model.asset.object.interactive.OBJ_Chest;
import Model.asset.tile.interactive.IT_DryTree;
import Model.asset.tile.interactive.IT_Teleport;

public class AssetManager {

    private final GameController gameController;
    private final int tileSize;
    private int map = 0;

    public AssetManager(GameController gameController) {
        this.gameController = gameController;
        this.tileSize = gameController.getTileSize();
    }

    public void setObjects() {

        // MAP 0
        map = 0;

        gameController.getObjects()[map][0] = new OBJ_Boots(gameController);
        gameController.getObjects()[map][0].setWorldX(tileSize * 33);
        gameController.getObjects()[map][0].setWorldY(tileSize * 7);
        gameController.getObjects()[map][0].setIndex(0);


        gameController.getObjects()[map][1] = new OBJ_Chest(gameController);
        gameController.getObjects()[map][1].setWorldX(tileSize * 12);
        gameController.getObjects()[map][1].setWorldY(tileSize * 8);
        gameController.getObjects()[map][1].setIndex(1);




    }

    public void setNPCs() {

        // MAP 0
        map = 0;
        gameController.getNpcs()[map][0] = new NPC_Merchant(gameController);
        gameController.getNpcs()[map][0].setWorldX(tileSize * 20);
        gameController.getNpcs()[map][0].setWorldY(tileSize * 7);
        gameController.getNpcs()[map][0].setIndex(0);

        gameController.getNpcs()[map][1] = new NPC_Wizard(gameController);
        gameController.getNpcs()[map][1].setWorldX(tileSize * 11);
        gameController.getNpcs()[map][1].setWorldY(tileSize * 8);
        gameController.getNpcs()[map][1].setIndex(1);

        gameController.getNpcs()[map][2] = new NPC_OldMan(gameController);
        gameController.getNpcs()[map][2].setWorldX(tileSize * 25);
        gameController.getNpcs()[map][2].setWorldY(tileSize * 23);
        gameController.getNpcs()[map][2].setIndex(2);

    }

    public void setMonsters() {

        // MAP 0
        map = 0;
        gameController.getMonsters()[map][0] = new MON_Slime(gameController);
        gameController.getMonsters()[map][0].setWorldX(tileSize * 21);
        gameController.getMonsters()[map][0].setWorldY(tileSize * 38);
        gameController.getMonsters()[map][0].setIndex(0);

        gameController.getMonsters()[map][1] = new MON_Slime(gameController);
        gameController.getMonsters()[map][1].setWorldX(tileSize * 23);
        gameController.getMonsters()[map][1].setWorldY(tileSize * 42);
        gameController.getMonsters()[map][1].setIndex(1);

        gameController.getMonsters()[map][2] = new MON_Slime(gameController);
        gameController.getMonsters()[map][2].setWorldX(tileSize * 24);
        gameController.getMonsters()[map][2].setWorldY(tileSize * 37);
        gameController.getMonsters()[map][2].setIndex(2);

        gameController.getMonsters()[map][3] = new MON_Slime(gameController);
        gameController.getMonsters()[map][3].setWorldX(tileSize * 34);
        gameController.getMonsters()[map][3].setWorldY(tileSize * 42);
        gameController.getMonsters()[map][3].setIndex(3);

        gameController.getMonsters()[map][4] = new MON_Slime(gameController);
        gameController.getMonsters()[map][4].setWorldX(tileSize * 38);
        gameController.getMonsters()[map][4].setWorldY(tileSize * 42);
        gameController.getMonsters()[map][4].setIndex(4);

        gameController.getMonsters()[map][5] = new MON_Slime(gameController);
        gameController.getMonsters()[map][5].setWorldX(tileSize * 10);
        gameController.getMonsters()[map][5].setWorldY(tileSize * 32);
        gameController.getMonsters()[map][5].setIndex(5);

        gameController.getMonsters()[map][6] = new MON_Slime(gameController);
        gameController.getMonsters()[map][6].setWorldX(tileSize * 11);
        gameController.getMonsters()[map][6].setWorldY(tileSize * 29);
        gameController.getMonsters()[map][6].setIndex(6);

        gameController.getMonsters()[map][7] = new MON_Slime(gameController);
        gameController.getMonsters()[map][7].setWorldX(tileSize * 12);
        gameController.getMonsters()[map][7].setWorldY(tileSize * 30);
        gameController.getMonsters()[map][7].setIndex(7);

        gameController.getMonsters()[map][8] = new MON_Slime(gameController);
        gameController.getMonsters()[map][8].setWorldX(tileSize * 15);
        gameController.getMonsters()[map][8].setWorldY(tileSize * 32);
        gameController.getMonsters()[map][8].setIndex(8);

        gameController.getMonsters()[map][9] = new MON_Slime(gameController);
        gameController.getMonsters()[map][9].setWorldX(tileSize * 12);
        gameController.getMonsters()[map][9].setWorldY(tileSize * 33);
        gameController.getMonsters()[map][9].setIndex(9);

        map = 1;
        gameController.getMonsters()[map][5] = new BOSS_Ramurai(gameController);
        gameController.getMonsters()[map][5].setWorldX(tileSize * 12);
        gameController.getMonsters()[map][5].setWorldY(tileSize * 9);
        gameController.getMonsters()[map][5].setIndex(5);


    }

    public void setInteractiveTiles() {

        // MAP 0
        map = 0;
        gameController.getInteractiveTiles()[map][0] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][0].setWorldX(tileSize * 27);
        gameController.getInteractiveTiles()[map][0].setWorldY(tileSize * 12);
        gameController.getInteractiveTiles()[map][0].setIndex(0);

        gameController.getInteractiveTiles()[map][1] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][1].setWorldX(tileSize * 28);
        gameController.getInteractiveTiles()[map][1].setWorldY(tileSize * 12);
        gameController.getInteractiveTiles()[map][1].setIndex(1);

        gameController.getInteractiveTiles()[map][2] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][2].setWorldX(tileSize * 29);
        gameController.getInteractiveTiles()[map][2].setWorldY(tileSize * 12);
        gameController.getInteractiveTiles()[map][2].setIndex(2);

        gameController.getInteractiveTiles()[map][3] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][3].setWorldX(tileSize * 30);
        gameController.getInteractiveTiles()[map][3].setWorldY(tileSize * 12);
        gameController.getInteractiveTiles()[map][3].setIndex(3);

        gameController.getInteractiveTiles()[map][4] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][4].setWorldX(tileSize * 31);
        gameController.getInteractiveTiles()[map][4].setWorldY(tileSize * 12);
        gameController.getInteractiveTiles()[map][4].setIndex(4);

        gameController.getInteractiveTiles()[map][5] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][5].setWorldX(tileSize * 32);
        gameController.getInteractiveTiles()[map][5].setWorldY(tileSize * 12);
        gameController.getInteractiveTiles()[map][5].setIndex(5);

        gameController.getInteractiveTiles()[map][6] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][6].setWorldX(tileSize * 33);
        gameController.getInteractiveTiles()[map][6].setWorldY(tileSize * 12);
        gameController.getInteractiveTiles()[map][6].setIndex(6);

        gameController.getInteractiveTiles()[map][7] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][7].setWorldX(tileSize * 31);
        gameController.getInteractiveTiles()[map][7].setWorldY(tileSize * 21);
        gameController.getInteractiveTiles()[map][7].setIndex(7);

        gameController.getInteractiveTiles()[map][8] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][8].setWorldX(tileSize * 18);
        gameController.getInteractiveTiles()[map][8].setWorldY(tileSize * 40);
        gameController.getInteractiveTiles()[map][8].setIndex(8);

        gameController.getInteractiveTiles()[map][9] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][9].setWorldX(tileSize * 17);
        gameController.getInteractiveTiles()[map][9].setWorldY(tileSize * 40);
        gameController.getInteractiveTiles()[map][9].setIndex(9);

        gameController.getInteractiveTiles()[map][10] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][10].setWorldX(tileSize * 16);
        gameController.getInteractiveTiles()[map][10].setWorldY(tileSize * 40);
        gameController.getInteractiveTiles()[map][10].setIndex(10);

        gameController.getInteractiveTiles()[map][11] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][11].setWorldX(tileSize * 15);
        gameController.getInteractiveTiles()[map][11].setWorldY(tileSize * 40);
        gameController.getInteractiveTiles()[map][11].setIndex(11);

        gameController.getInteractiveTiles()[map][12] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][12].setWorldX(tileSize * 14);
        gameController.getInteractiveTiles()[map][12].setWorldY(tileSize * 40);
        gameController.getInteractiveTiles()[map][12].setIndex(12);

        gameController.getInteractiveTiles()[map][13] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][13].setWorldX(tileSize * 13);
        gameController.getInteractiveTiles()[map][13].setWorldY(tileSize * 40);
        gameController.getInteractiveTiles()[map][13].setIndex(13);

        gameController.getInteractiveTiles()[map][14] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][14].setWorldX(tileSize * 13);
        gameController.getInteractiveTiles()[map][14].setWorldY(tileSize * 41);
        gameController.getInteractiveTiles()[map][14].setIndex(14);

        gameController.getInteractiveTiles()[map][15] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][15].setWorldX(tileSize * 12);
        gameController.getInteractiveTiles()[map][15].setWorldY(tileSize * 41);
        gameController.getInteractiveTiles()[map][15].setIndex(15);

        gameController.getInteractiveTiles()[map][16] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][16].setWorldX(tileSize * 11);
        gameController.getInteractiveTiles()[map][16].setWorldY(tileSize * 41);
        gameController.getInteractiveTiles()[map][16].setIndex(16);

        gameController.getInteractiveTiles()[map][17] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][17].setWorldX(tileSize * 10);
        gameController.getInteractiveTiles()[map][17].setWorldY(tileSize * 41);
        gameController.getInteractiveTiles()[map][17].setIndex(17);

        gameController.getInteractiveTiles()[map][18] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][18].setWorldX(tileSize * 10);
        gameController.getInteractiveTiles()[map][18].setWorldY(tileSize * 40);
        gameController.getInteractiveTiles()[map][18].setIndex(18);

        gameController.getInteractiveTiles()[map][20] = new IT_DryTree(gameController);
        gameController.getInteractiveTiles()[map][20].setWorldX(tileSize * 10);
        gameController.getInteractiveTiles()[map][20].setWorldY(tileSize * 28);
        gameController.getInteractiveTiles()[map][20].setIndex(20);
        map = 1;
        gameController.getInteractiveTiles()[map][19] = new IT_Teleport(gameController);
        gameController.getInteractiveTiles()[map][19].setWorldX(tileSize * 16);
        gameController.getInteractiveTiles()[map][19].setWorldY(tileSize * 13);
        gameController.getInteractiveTiles()[map][19].setIndex(19);
    }
}
