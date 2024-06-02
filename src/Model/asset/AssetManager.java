//<editor-fold desc="Description">
package Model.asset;

import Controller.GameController;
import Controller.controllerUpdate;
import Model.asset.entity.monster.BOSS_Samurai;
import Model.asset.entity.monster.MON_Slime;
import Model.asset.entity.monster.MonsterFactory;
import Model.asset.entity.npc.NPCFactory;
import Model.asset.object.equipment.OBJ_Axe;
import Model.asset.object.equipment.OBJ_Boots;
import Model.asset.object.interactive.OBJ_Chest;
import Model.asset.object.usable.inventory.OBJ_Key;
import Model.asset.tile.interactive.IT_DryTree;
import Model.asset.tile.interactive.IT_Teleport;
import Model.asset.tile.interactive.TileFactory;

public class AssetManager {

    private final GameController gameController;
    private final int tileSize;
    private int map = 0;

    public AssetManager(GameController gameController) {
        this.gameController = gameController;
        this.tileSize = gameController.getTileSize();
    }

    public void setObjects() {
        controllerUpdate update = gameController.update;
        // MAP 0
        map = 0;

        update.getObjects()[map][0] = new OBJ_Boots(gameController);
        update.getObjects()[map][0].setWorldX(tileSize * 33);
        update.getObjects()[map][0].setWorldY(tileSize * 7);
        update.getObjects()[map][0].setIndex(0);

        update.getObjects()[map][1] = new OBJ_Chest(gameController);
        update.getObjects()[map][1].setWorldX(tileSize * 12);
        update.getObjects()[map][1].setWorldY(tileSize * 8);
        update.getObjects()[map][1].setIndex(1);

        update.getObjects()[map][2] = new OBJ_Key(gameController);
        update.getObjects()[map][2].setWorldX(tileSize * 31);
        update.getObjects()[map][2].setWorldY(tileSize * 30);
        update.getObjects()[map][2].setIndex(2);

        update.getObjects()[map][3] = new OBJ_Axe(gameController);
        update.getObjects()[map][3].setWorldX(tileSize * 27);
        update.getObjects()[map][3].setWorldY(tileSize * 16);
        update.getObjects()[map][3].setIndex(3);



    }

    public void setNPCs() {
        NPCFactory factoryNPC = new NPCFactory(gameController);
        controllerUpdate update = gameController.update;

        // MAP 0
        map = 0;
        update.getNpcs()[map][0] = factoryNPC.CreateNPC("merchant");
        update.getNpcs()[map][0].setWorldX(tileSize * 20);
        update.getNpcs()[map][0].setWorldY(tileSize * 7);
        update.getNpcs()[map][0].setIndex(0);

        update.getNpcs()[map][1] = factoryNPC.CreateNPC("wizard");
        update.getNpcs()[map][1].setWorldX(tileSize * 11);
        update.getNpcs()[map][1].setWorldY(tileSize * 8);
        update.getNpcs()[map][1].setIndex(1);

        update.getNpcs()[map][2] = factoryNPC.CreateNPC("oldman");
        update.getNpcs()[map][2].setWorldX(tileSize * 25);
        update.getNpcs()[map][2].setWorldY(tileSize * 23);
        update.getNpcs()[map][2].setIndex(2);
        map = 1;
        update.getNpcs()[map][3] = factoryNPC.CreateNPC("oldman");
        update.getNpcs()[map][3].setWorldX(tileSize * 10);
        update.getNpcs()[map][3].setWorldY(tileSize * 9);
        update.getNpcs()[map][3].setIndex(3);

    }

    public void setMonsters() {
        controllerUpdate update = gameController.update;
        MonsterFactory monsterFactory = new MonsterFactory(gameController);

        // MAP 0
        map = 0;
        update.getMonsters()[map][0] = monsterFactory.createMonster("slime");;
        update.getMonsters()[map][0].setWorldX(tileSize * 21);
        update.getMonsters()[map][0].setWorldY(tileSize * 38);
        update.getMonsters()[map][0].setIndex(0);

        update.getMonsters()[map][1] = monsterFactory.createMonster("slime");;
        update.getMonsters()[map][1].setWorldX(tileSize * 23);
        update.getMonsters()[map][1].setWorldY(tileSize * 42);
        update.getMonsters()[map][1].setIndex(1);

        update.getMonsters()[map][2] = monsterFactory.createMonster("slime");;
        update.getMonsters()[map][2].setWorldX(tileSize * 24);
        update.getMonsters()[map][2].setWorldY(tileSize * 37);
        update.getMonsters()[map][2].setIndex(2);

        update.getMonsters()[map][3] = monsterFactory.createMonster("slime");;
        update.getMonsters()[map][3].setWorldX(tileSize * 34);
        update.getMonsters()[map][3].setWorldY(tileSize * 42);
        update.getMonsters()[map][3].setIndex(3);

        update.getMonsters()[map][4] = monsterFactory.createMonster("slime");;
        update.getMonsters()[map][4].setWorldX(tileSize * 38);
        update.getMonsters()[map][4].setWorldY(tileSize * 42);
        update.getMonsters()[map][4].setIndex(4);

        update.getMonsters()[map][5] = monsterFactory.createMonster("slime");;
        update.getMonsters()[map][5].setWorldX(tileSize * 10);
        update.getMonsters()[map][5].setWorldY(tileSize * 32);
        update.getMonsters()[map][5].setIndex(5);

        update.getMonsters()[map][6] = monsterFactory.createMonster("slime");;
        update.getMonsters()[map][6].setWorldX(tileSize * 11);
        update.getMonsters()[map][6].setWorldY(tileSize * 29);
        update.getMonsters()[map][6].setIndex(6);

        update.getMonsters()[map][7] = monsterFactory.createMonster("slime");;
        update.getMonsters()[map][7].setWorldX(tileSize * 12);
        update.getMonsters()[map][7].setWorldY(tileSize * 30);
        update.getMonsters()[map][7].setIndex(7);

        update.getMonsters()[map][8] = monsterFactory.createMonster("slime");;
        update.getMonsters()[map][8].setWorldX(tileSize * 15);
        update.getMonsters()[map][8].setWorldY(tileSize * 32);
        update.getMonsters()[map][8].setIndex(8);

        update.getMonsters()[map][9] = monsterFactory.createMonster("slime");;
        update.getMonsters()[map][9].setWorldX(tileSize * 12);
        update.getMonsters()[map][9].setWorldY(tileSize * 33);
        update.getMonsters()[map][9].setIndex(9);

        map = 1;
        update.getMonsters()[map][5] = monsterFactory.createMonster("samurai");
        update.getMonsters()[map][5].setWorldX(tileSize * 12);
        update.getMonsters()[map][5].setWorldY(tileSize * 9);
        update.getMonsters()[map][5].setIndex(5);


    }

    public void setInteractiveTiles() {
        controllerUpdate update = gameController.update;
        TileFactory factory = new TileFactory(gameController);
        // MAP 0
        map = 0;
        update.getInteractiveTiles()[map][0] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][0].setWorldX(tileSize * 27);
        update.getInteractiveTiles()[map][0].setWorldY(tileSize * 12);
        update.getInteractiveTiles()[map][0].setIndex(0);

        update.getInteractiveTiles()[map][1] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][1].setWorldX(tileSize * 28);
        update.getInteractiveTiles()[map][1].setWorldY(tileSize * 12);
        update.getInteractiveTiles()[map][1].setIndex(1);

        update.getInteractiveTiles()[map][2] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][2].setWorldX(tileSize * 29);
        update.getInteractiveTiles()[map][2].setWorldY(tileSize * 12);
        update.getInteractiveTiles()[map][2].setIndex(2);

        update.getInteractiveTiles()[map][3] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][3].setWorldX(tileSize * 30);
        update.getInteractiveTiles()[map][3].setWorldY(tileSize * 12);
        update.getInteractiveTiles()[map][3].setIndex(3);

        update.getInteractiveTiles()[map][4] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][4].setWorldX(tileSize * 31);
        update.getInteractiveTiles()[map][4].setWorldY(tileSize * 12);
        update.getInteractiveTiles()[map][4].setIndex(4);

        update.getInteractiveTiles()[map][5] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][5].setWorldX(tileSize * 32);
        update.getInteractiveTiles()[map][5].setWorldY(tileSize * 12);
        update.getInteractiveTiles()[map][5].setIndex(5);

        update.getInteractiveTiles()[map][6] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][6].setWorldX(tileSize * 33);
        update.getInteractiveTiles()[map][6].setWorldY(tileSize * 12);
        update.getInteractiveTiles()[map][6].setIndex(6);

        update.getInteractiveTiles()[map][7] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][7].setWorldX(tileSize * 31);
        update.getInteractiveTiles()[map][7].setWorldY(tileSize * 21);
        update.getInteractiveTiles()[map][7].setIndex(7);

        update.getInteractiveTiles()[map][8] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][8].setWorldX(tileSize * 18);
        update.getInteractiveTiles()[map][8].setWorldY(tileSize * 40);
        update.getInteractiveTiles()[map][8].setIndex(8);

        update.getInteractiveTiles()[map][9] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][9].setWorldX(tileSize * 17);
        update.getInteractiveTiles()[map][9].setWorldY(tileSize * 40);
        update.getInteractiveTiles()[map][9].setIndex(9);

        update.getInteractiveTiles()[map][10] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][10].setWorldX(tileSize * 16);
        update.getInteractiveTiles()[map][10].setWorldY(tileSize * 40);
        update.getInteractiveTiles()[map][10].setIndex(10);

        update.getInteractiveTiles()[map][11] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][11].setWorldX(tileSize * 15);
        update.getInteractiveTiles()[map][11].setWorldY(tileSize * 40);
        update.getInteractiveTiles()[map][11].setIndex(11);

        update.getInteractiveTiles()[map][12] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][12].setWorldX(tileSize * 14);
        update.getInteractiveTiles()[map][12].setWorldY(tileSize * 40);
        update.getInteractiveTiles()[map][12].setIndex(12);

        update.getInteractiveTiles()[map][13] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][13].setWorldX(tileSize * 13);
        update.getInteractiveTiles()[map][13].setWorldY(tileSize * 40);
        update.getInteractiveTiles()[map][13].setIndex(13);

        update.getInteractiveTiles()[map][14] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][14].setWorldX(tileSize * 13);
        update.getInteractiveTiles()[map][14].setWorldY(tileSize * 41);
        update.getInteractiveTiles()[map][14].setIndex(14);

        update.getInteractiveTiles()[map][15] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][15].setWorldX(tileSize * 12);
        update.getInteractiveTiles()[map][15].setWorldY(tileSize * 41);
        update.getInteractiveTiles()[map][15].setIndex(15);

        update.getInteractiveTiles()[map][16] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][16].setWorldX(tileSize * 11);
        update.getInteractiveTiles()[map][16].setWorldY(tileSize * 41);
        update.getInteractiveTiles()[map][16].setIndex(16);

        update.getInteractiveTiles()[map][17] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][17].setWorldX(tileSize * 10);
        update.getInteractiveTiles()[map][17].setWorldY(tileSize * 41);
        update.getInteractiveTiles()[map][17].setIndex(17);

        update.getInteractiveTiles()[map][18] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][18].setWorldX(tileSize * 10);
        update.getInteractiveTiles()[map][18].setWorldY(tileSize * 40);
        update.getInteractiveTiles()[map][18].setIndex(18);

        update.getInteractiveTiles()[map][20] = factory.createTile("Dry");
        update.getInteractiveTiles()[map][20].setWorldX(tileSize * 10);
        update.getInteractiveTiles()[map][20].setWorldY(tileSize * 28);
        update.getInteractiveTiles()[map][20].setIndex(20);
        map = 1;
        update.getInteractiveTiles()[map][19] = factory.createTile("teleport");
        update.getInteractiveTiles()[map][19].setWorldX(tileSize * 16);
        update.getInteractiveTiles()[map][19].setWorldY(tileSize * 13);
        update.getInteractiveTiles()[map][19].setIndex(19);
    }
}
//</editor-fold>
