package Model.asset.tile.interactive;

import Controller.GameController;
import Model.asset.entity.monster.BOSS_Samurai;
import Model.asset.entity.monster.MON_Slime;

public class TileFactory {
    public GameController controller;

    public TileFactory(GameController controller) {
        this.controller = controller;
    }
    public InteractiveTile createTile (String type) {
        switch (type.toLowerCase()) {
            case "dry" -> {
                return new IT_DryTree(controller);

            }
            case "trunk" -> {
                return new IT_Trunk(controller);
            }
            case "teleport" -> {
                return new IT_Teleport(controller);
            }
            default -> {
                return null;
            }
        }
    }
}
